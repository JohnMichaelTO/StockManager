package Programme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Objets.Adresse;
import Objets.Client;
import Objets.CommandeClient;
import Objets.CommandeFournisseur;
import Objets.ContenuCommandeClient;
import Objets.ContenuCommandeFournisseur;
import Objets.EntrepotSaitStocker;
import Objets.EntrepotStock;
import Objets.EntrepotStockLot;
import Objets.Lot;
import Objets.LotCommande;
import Objets.Produit;

public class Application
{
	private static int testTotal = 0;
    private static int testOK = 0;
    
    public Application(String url, String user, String password) throws SQLException
    {
    	new Connexion(url, user, password);
    }
    
    private static void check(String test, boolean ok)
    {
	    testTotal += 1;
	    System.out.print(test + ": ");
	    if (ok)
	    {
		    testOK += 1;
		    System.out.println("ok");
	    }
	    else
	    {
	    	System.out.println("FAILED");
	    }
    }
    
	public boolean CommandeProduitsClient(int numero, Client client, List<ContenuCommandeClient> contenu, Adresse adresseLivraison)
	{
		try
		{
			Connexion.getBdd().setAutoCommit(false);
			
			CommandeClient commande = new CommandeClient(numero, client, adresseLivraison);
			// Insertion de la commande client
			Connexion.getCommandeClientDAO().insert(commande);
			
			for(ContenuCommandeClient e : contenu)
			{
				e.setCommande(commande);
				// Insertion du contenu de la commande
				Connexion.getContenuCommandeClientDAO().insert(e);
				
				// Stocks disponibles toutes entrep�ts confondus
				int StockTotal = Connexion.getEntrepotStockDAO().getTotalStocksProduit(e.getProduit().getPID());
				
				// V�rification de la demande par rapport au stock total
				if(e.getQuantite() <= StockTotal)
				{
					// S�lection d'un entrep�t contenant le produit
					List<EntrepotStock> entrepots = Connexion.getEntrepotStockDAO().getStocksProduit(e.getProduit().getPID());
					Iterator<EntrepotStock> it = entrepots.iterator();
					
					while(it.hasNext() && e.getQuantite() > 0)
					{
						EntrepotStock element = it.next();
						
						int diff = element.getQuantite() - e.getQuantite();
						
						EntrepotStock entrepotStock = new EntrepotStock();
						entrepotStock.setEntrepot(element.getEntrepot());
						entrepotStock.setProduit(e.getProduit());
						
						int quantiteLot = 0;
						// Quantit� suffisante dans l'entrep�t pour r�pondre � la demande
						if(diff >= 0 )
						{
							entrepotStock.setQuantite(diff);
							e.setQuantite(0);
							quantiteLot = e.getQuantite();
						}
						else // Quantit� insuffisante pour r�pondre � la demande
						{
							entrepotStock.setQuantite(0);
							e.setQuantite(-diff);
							quantiteLot = element.getQuantite();
						}
						
						// Modification des stocks de l'entrep�t
						Connexion.getEntrepotStockDAO().update(entrepotStock);
						
						// Recherche des lots du produit de l'entrep�t
						List<EntrepotStockLot> entrepotStockLots = Connexion.getEntrepotStockLotDAO().getList(element.getEntrepot().getNom(), e.getProduit().getPID());
						
						Iterator<EntrepotStockLot> itLot = entrepotStockLots.iterator();
						while(itLot.hasNext() && quantiteLot > 0)
						{
							EntrepotStockLot eLot = itLot.next();
							
							// Si le produit concordonne et qu'il reste un lot � retirer
							if(eLot.getLot().getProduit() == e.getProduit() && eLot.getQuantite() > 0)
							{
								int diff2 = quantiteLot - eLot.getQuantite();
								
								EntrepotStockLot entrepotStockLot = new EntrepotStockLot();
								entrepotStockLot.setEntrepot(element.getEntrepot());
								entrepotStockLot.setLot(eLot.getLot());
								
								LotCommande lotCommande = new LotCommande();
								lotCommande.setCommande(commande);
								lotCommande.setLot(eLot.getLot());
								
								
								// Retrait de tout le lot si la diff�rence est positive
								if(diff2 >= 0)
								{
									entrepotStockLot.setQuantite(0);
									// Mise � jour de la quantit� restante
									quantiteLot = diff2;
									eLot.setQuantite(0);
									lotCommande.setQuantite(eLot.getQuantite());
								}
								else
								{
									entrepotStockLot.setQuantite(-diff2);
									// Mise � jour de la quantit� restante
									eLot.setQuantite(-diff2);
									lotCommande.setQuantite(quantiteLot);
									quantiteLot = 0;
								}
								
								// Modification du lot stock� dans l'entrep�t
								Connexion.getEntrepotStockLotDAO().update(entrepotStockLot);
								
								// Ajout dans les lots command�s
								Connexion.getLotCommandeDAO().insert(lotCommande);
							}
						}
					}
				}
				else // Stock insuffisant
				{
					Connexion.getBdd().rollback();
					Connexion.getBdd().setAutoCommit(true);
					return false;
				}
			}
			
			Connexion.getBdd().commit();
			Connexion.getBdd().setAutoCommit(true);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{
				Connexion.getBdd().rollback();
				Connexion.getBdd().setAutoCommit(true);
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean CommandeProduitsFournisseur(int numero, List<ContenuCommandeFournisseur> contenu, List<Lot> lots)
	{
		try
		{
			Connexion.getBdd().setAutoCommit(false);
			
			CommandeFournisseur commande = new CommandeFournisseur(numero);
			// Insertion de la commande fournisseur
			Connexion.getCommandeFournisseurDAO().insert(commande);
			
			List<Produit> produits = new ArrayList<Produit>();
			
			for(ContenuCommandeFournisseur e : contenu)
			{
				e.setCommande(commande);
				produits.add(e.getProduit());
				// Insertion du contenu de la commande
				Connexion.getContenuCommandeFournisseurDAO().insert(e);
			}
			
			for(Lot e : lots)
			{
				// Insertion des lots pr�sentes dans la liste des produits command�s
				if(produits.contains(e.getProduit()))
				{
					// TODO : V�rifier les quantit�s dans la liste des produits et des lots
					Connexion.getLotDAO().insert(e);
				}
				else // Cas o� un lot ne correspond pas � un produit de la commande
				{
					Connexion.getBdd().rollback();
					Connexion.getBdd().setAutoCommit(true);
					return false;
				}
			}
			
			// Ne pas oublier l'entrepot � stocker
			for(ContenuCommandeFournisseur e : contenu)
			{
				// S�lection de l'entrep�t qui a le plus de capacit� restante pour un type de produit donn�
				List<EntrepotSaitStocker> entrepots = Connexion.getEntrepotSaitStockerDAO().getListMaxCapacity(e.getProduit().getType().getNom());
				Iterator<EntrepotSaitStocker> it = entrepots.iterator();
				// Stockage dans un entrep�t jusqu'� sa capacit� maximale et jusqu'� qu'il n'y a plus de quantit� � stocker
				while(it.hasNext() && e.getQuantite() > 0)
				{
					EntrepotSaitStocker element = it.next();
					
					// Partie enti�re inf�rieure pour la quantit� stock�e (quantiteStockee <= capacite max / volume)
					int quantiteStockee = element.getCapacite() / e.getProduit().getVolume();
					
					// Stockage dans l'entrep�t jusqu'� la capacit� maximale
					EntrepotStock entrepotStock = new EntrepotStock();
					entrepotStock.setEntrepot(element.getEntrepot());
					entrepotStock.setProduit(e.getProduit());
					entrepotStock.setQuantite(quantiteStockee);
					
					// Stockage dans l'entrep�t
					Connexion.getEntrepotStockDAO().insert(entrepotStock);
					
					// Mise � jour de la quantit� restante
					e.setQuantite(e.getQuantite() - quantiteStockee);
					
					int quantiteLotStockee = quantiteStockee;
					
					// Stockage du lot
					Iterator<Lot> itLot = lots.iterator();
					while(itLot.hasNext() && quantiteLotStockee > 0)
					{
						Lot eLot = itLot.next();
						
						// Si le produit concordonne et qu'il reste un lot � stocker
						if(eLot.getProduit() == e.getProduit() && eLot.getQuantite() > 0)
						{
							int diff = quantiteLotStockee - eLot.getQuantite();
							
							EntrepotStockLot entrepotStockLot = new EntrepotStockLot();
							entrepotStockLot.setEntrepot(element.getEntrepot());
							entrepotStockLot.setLot(eLot);
							
							// Stockage de tout le lot si la diff�rence est positive
							if(diff >= 0)
							{
								entrepotStockLot.setQuantite(eLot.getQuantite());
								// Mise � jour de la quantit� restante
								quantiteLotStockee = diff;
								eLot.setQuantite(0);
							}
							else
							{
								entrepotStockLot.setQuantite(quantiteLotStockee);
								// Mise � jour de la quantit� restante
								quantiteLotStockee = 0;
								eLot.setQuantite(-diff);
							}
							
							// Stockage du lot dans l'entrep�t
							Connexion.getEntrepotStockLotDAO().insert(entrepotStockLot);
						}
					}
					
					// Si un lot existe pour un produit donn�, les quantit�s de lot et de produit ne correspondent pas
					if(quantiteLotStockee != quantiteStockee && quantiteLotStockee > 0)
					{
						Connexion.getBdd().rollback();
						Connexion.getBdd().setAutoCommit(true);
						return false;
					}
				}
				
				// Impossible de tout stocker
				if(e.getQuantite() > 0)
				{
					Connexion.getBdd().rollback();
					Connexion.getBdd().setAutoCommit(true);
					return false;
				}
			}
			
			Connexion.getBdd().commit();
			Connexion.getBdd().setAutoCommit(true);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{
				Connexion.getBdd().rollback();
				Connexion.getBdd().setAutoCommit(true);
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	public List<EntrepotStock> EtatStockProduit(Produit produit)
	{
		return Connexion.getEntrepotStockDAO().getStocksProduit(produit.getPID());
	}
	
	public List<EntrepotSaitStocker> CapaciteStockageEntrepot(String nom)
	{
		return Connexion.getEntrepotSaitStockerDAO().getList(nom);
	}
	
	public static void main(String[] args) throws SQLException
	{
		// check parameters
		if (args.length != 3)
		{
		    System.err.println("usage: Application <url> <user> <password>");
		    System.exit(-1);
		}
		
		try
		{
			Application app = new Application(args[0], args[1], args[2]);
			
			
			List<Produit> produits = Connexion.getProduitDAO().getList();
			
			// Tests EtatStockProduit
			List<EntrepotStock> entrepotStock = app.EtatStockProduit(produits.get(0));
			for(EntrepotStock e : entrepotStock)
			{
				check("EtatStockProduit", e.getQuantite() == 25);
			}
			
			entrepotStock = app.EtatStockProduit(produits.get(1));
			for(EntrepotStock e : entrepotStock)
			{
				check("EtatStockProduit", e.getQuantite() == 50);
			}
			
			// Tests CapaciteStockageEntrepot
			List<EntrepotSaitStocker> entrepotSaitStocker = app.CapaciteStockageEntrepot("Marseille");
			for(EntrepotSaitStocker e : entrepotSaitStocker)
			{
				check("CapaciteStockageEntrepot", e.getCapacite() == 1500);
			}
			
			entrepotSaitStocker = app.CapaciteStockageEntrepot("Amiens");
			for(EntrepotSaitStocker e : entrepotSaitStocker)
			{
				check("CapaciteStockageEntrepot", e.getCapacite() == 1500);
			}
			
			// Tests CommandeProduitsFournisseur
			List<ContenuCommandeFournisseur> listeContenu = new ArrayList<ContenuCommandeFournisseur>();
			List<Lot> listeLot = new ArrayList<Lot>();
			
			// Commande de produits provenant de deux fournisseurs diff�rents
			listeContenu.add(new ContenuCommandeFournisseur(produits.get(0), 3, 2.0));
			listeContenu.add(new ContenuCommandeFournisseur(produits.get(1), 5, 3.0));
			listeLot.add(new Lot(11, 11, produits.get(0), 3));
			listeLot.add(new Lot(12, 12, produits.get(1), 5));
			check("CommandeProduitsFournisseur", app.CommandeProduitsFournisseur(2, listeContenu, listeLot) == false);
			
			listeContenu.clear();
			listeLot.clear();
			
			// Commande de produits avec des lots o� la quantit� ne correspond pas
			listeContenu.add(new ContenuCommandeFournisseur(produits.get(0), 3, 2.0));
			listeLot.add(new Lot(13, 13, produits.get(0), 5));
			check("CommandeProduitsFournisseur", app.CommandeProduitsFournisseur(3, listeContenu, listeLot) == false);
			
			listeContenu.clear();
			listeLot.clear();
			
			// Commande de produits avec des lots valide
			listeContenu.add(new ContenuCommandeFournisseur(produits.get(0), 3, 2.0));
			listeLot.add(new Lot(14, 20, produits.get(0), 3));
			check("CommandeProduitsFournisseur", app.CommandeProduitsFournisseur(4, listeContenu, listeLot) == true);
			
			Client client = Connexion.getClientDAO().select(000000000000001);
			
			// Commande de produits client
			//check("CommandeProduitsClient", true);
		}
		catch (Exception e)
		{
			System.err.println("test aborted: " + e);
			e.printStackTrace();
		}
		
		// print test results
		if (testTotal == 0)
		{
			System.out.println("no test performed");
		}
		else
		{
			String r = "test results: ";
			r += "total=" + testTotal;
			r += ", ok=" + testOK + "(" + ((testOK * 100) / testTotal) + "%)";
			System.out.println(r);
		}
	}
}