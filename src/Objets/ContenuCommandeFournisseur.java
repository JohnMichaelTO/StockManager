package Objets;

public class ContenuCommandeFournisseur
{
	private CommandeFournisseur commande;
	private Produit produit;
	private int quantite;
	private double prix;
	
	public ContenuCommandeFournisseur()
	{
		
	}
	
	public ContenuCommandeFournisseur(Produit produit, int quantite, double prix)
	{
		this.setProduit(produit);
		this.setQuantite(quantite);
		this.setPrix(prix);
	}

	public CommandeFournisseur getCommande() {
		return commande;
	}

	public void setCommande(CommandeFournisseur commande) {
		this.commande = commande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}
}
