package Programme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DAO.AdresseDAO;
import DAO.AdresseLivraisonClientDAO;
import DAO.AlimentDAO;
import DAO.ClientDAO;
import DAO.CommandeClientDAO;
import DAO.CommandeFournisseurDAO;
import DAO.ContenuCommandeClientDAO;
import DAO.ContenuCommandeFournisseurDAO;
import DAO.EntrepotDAO;
import DAO.EntrepotSaitStockerDAO;
import DAO.EntrepotStockDAO;
import DAO.EntrepotStockLotDAO;
import DAO.FournisseurDAO;
import DAO.LotCommandeDAO;
import DAO.LotDAO;
import DAO.ProduitDAO;
import DAO.TypeProduitDAO;
import DAO.VetementDAO;

public class Connexion
{
	private static Connection bdd = null;
	private static String url;
	private static String user;
	private static String password;
	private static AdresseDAO adresseDAO;
	private static ClientDAO clientDAO;
	private static CommandeClientDAO commandeClientDAO;
	private static FournisseurDAO fournisseurDAO;
	private static CommandeFournisseurDAO commandeFournisseurDAO;
	private static EntrepotDAO entrepotDAO;
	private static TypeProduitDAO typeProduitDAO;
	private static ProduitDAO produitDAO;
	private static VetementDAO vetementDAO;
	private static AlimentDAO alimentDAO;
	private static LotDAO lotDAO;
	private static AdresseLivraisonClientDAO adresseLivraisonClientDAO;
	private static ContenuCommandeClientDAO contenuCommandeClientDAO;
	private static ContenuCommandeFournisseurDAO contenuCommandeFournisseurDAO;
	private static EntrepotStockDAO entrepotStockDAO;
	private static LotCommandeDAO lotCommandeDAO;
	private static EntrepotSaitStockerDAO entrepotSaitStockerDAO;
	private static EntrepotStockLotDAO entrepotStockLotDAO;

	public Connexion(String url, String user, String password) throws SQLException
	{
		Connexion.url = url;
		Connexion.user = user;
		Connexion.password = password;
		
		System.setProperty("jdbc.drivers", "oracle.jdbc.driver.OracleDriver");
		if(bdd == null) setBdd(DriverManager.getConnection(url, user, password));
		
		adresseDAO = new AdresseDAO();
		clientDAO = new ClientDAO();
		commandeClientDAO = new CommandeClientDAO();
		fournisseurDAO = new FournisseurDAO();
		commandeFournisseurDAO = new CommandeFournisseurDAO();
		entrepotDAO = new EntrepotDAO();
		typeProduitDAO = new TypeProduitDAO();
		produitDAO = new ProduitDAO();
		vetementDAO = new VetementDAO();
		alimentDAO = new AlimentDAO();
		lotDAO = new LotDAO();
		adresseLivraisonClientDAO = new AdresseLivraisonClientDAO();
		contenuCommandeClientDAO = new ContenuCommandeClientDAO();
		contenuCommandeFournisseurDAO = new ContenuCommandeFournisseurDAO();
		entrepotStockDAO = new EntrepotStockDAO();
		lotCommandeDAO = new LotCommandeDAO();
		entrepotSaitStockerDAO = new EntrepotSaitStockerDAO();
	}

	public static Connection getBdd() throws SQLException {
		if(bdd == null) setBdd(DriverManager.getConnection(url, user, password));
		return bdd;
	}

	public static void setBdd(Connection bdd) {
		Connexion.bdd = bdd;
	}

	public static AdresseDAO getAdresseDAO() {
		return adresseDAO;
	}

	public static void setAdresseDAO(AdresseDAO adresseDAO) {
		Connexion.adresseDAO = adresseDAO;
	}

	public static ClientDAO getClientDAO() {
		return clientDAO;
	}

	public static void setClientDAO(ClientDAO clientDAO) {
		Connexion.clientDAO = clientDAO;
	}
	
	public static CommandeClientDAO getCommandeClientDAO() {
		return commandeClientDAO;
	}

	public static void setCommandeClientDAO(CommandeClientDAO commandeClientDAO) {
		Connexion.commandeClientDAO = commandeClientDAO;
	}

	public static FournisseurDAO getFournisseurDAO() {
		return fournisseurDAO;
	}

	public static void setFournisseurDAO(FournisseurDAO fournisseurDAO) {
		Connexion.fournisseurDAO = fournisseurDAO;
	}

	public static CommandeFournisseurDAO getCommandeFournisseurDAO() {
		return commandeFournisseurDAO;
	}

	public static void setCommandeFournisseurDAO(
			CommandeFournisseurDAO commandeFournisseurDAO) {
		Connexion.commandeFournisseurDAO = commandeFournisseurDAO;
	}

	public static EntrepotDAO getEntrepotDAO() {
		return entrepotDAO;
	}

	public static void setEntrepotDAO(EntrepotDAO entrepotDAO) {
		Connexion.entrepotDAO = entrepotDAO;
	}

	public static TypeProduitDAO getTypeProduitDAO() {
		return typeProduitDAO;
	}

	public static void setTypeProduitDAO(TypeProduitDAO typeProduitDAO) {
		Connexion.typeProduitDAO = typeProduitDAO;
	}

	public static ProduitDAO getProduitDAO() {
		return produitDAO;
	}

	public static void setProduitDAO(ProduitDAO produitDAO) {
		Connexion.produitDAO = produitDAO;
	}

	public static VetementDAO getVetementDAO() {
		return vetementDAO;
	}

	public static void setVetementDAO(VetementDAO vetementDAO) {
		Connexion.vetementDAO = vetementDAO;
	}

	public static AlimentDAO getAlimentDAO() {
		return alimentDAO;
	}

	public static void setAlimentDAO(AlimentDAO alimentDAO) {
		Connexion.alimentDAO = alimentDAO;
	}

	public static LotDAO getLotDAO() {
		return lotDAO;
	}

	public static void setLotDAO(LotDAO lotDAO) {
		Connexion.lotDAO = lotDAO;
	}

	public static AdresseLivraisonClientDAO getAdresseLivraisonClientDAO() {
		return adresseLivraisonClientDAO;
	}

	public static void setAdresseLivraisonClientDAO(
			AdresseLivraisonClientDAO adresseLivraisonClientDAO) {
		Connexion.adresseLivraisonClientDAO = adresseLivraisonClientDAO;
	}

	public static ContenuCommandeClientDAO getContenuCommandeClientDAO() {
		return contenuCommandeClientDAO;
	}

	public static void setContenuCommandeClientDAO(
			ContenuCommandeClientDAO contenuCommandeClientDAO) {
		Connexion.contenuCommandeClientDAO = contenuCommandeClientDAO;
	}

	public static ContenuCommandeFournisseurDAO getContenuCommandeFournisseurDAO() {
		return contenuCommandeFournisseurDAO;
	}

	public static void setContenuCommandeFournisseurDAO(
			ContenuCommandeFournisseurDAO contenuCommandeFournisseurDAO) {
		Connexion.contenuCommandeFournisseurDAO = contenuCommandeFournisseurDAO;
	}

	public static EntrepotStockDAO getEntrepotStockDAO() {
		return entrepotStockDAO;
	}

	public static void setEntrepotStockDAO(EntrepotStockDAO entrepotStockDAO) {
		Connexion.entrepotStockDAO = entrepotStockDAO;
	}

	public static LotCommandeDAO getLotCommandeDAO() {
		return lotCommandeDAO;
	}

	public static void setLotCommandeDAO(LotCommandeDAO lotCommandeDAO) {
		Connexion.lotCommandeDAO = lotCommandeDAO;
	}

	public static EntrepotSaitStockerDAO getEntrepotSaitStockerDAO() {
		return entrepotSaitStockerDAO;
	}

	public static void setEntrepotSaitStockerDAO(EntrepotSaitStockerDAO entrepotSaitStockerDAO) {
		Connexion.entrepotSaitStockerDAO = entrepotSaitStockerDAO;
	}

	public static EntrepotStockLotDAO getEntrepotStockLotDAO() {
		return entrepotStockLotDAO;
	}

	public static void setEntrepotStockLotDAO(EntrepotStockLotDAO entrepotStockLotDAO) {
		Connexion.entrepotStockLotDAO = entrepotStockLotDAO;
	}
}
