package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.ContenuCommandeClient;
import Programme.Connexion;

public class ContenuCommandeClientDAO implements DAO<ContenuCommandeClient>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public ContenuCommandeClientDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public ContenuCommandeClient select(int secu, int ccnum, int PID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from CONTENUCOMMANDECLIENT where CSECU = ? and CCNUMEERO = ? and PID = ?");
			
			select.setObject(1, secu);
			select.setObject(2, ccnum);
			select.setObject(3, PID);
			
			ResultSet result = select.executeQuery();
			
			ContenuCommandeClient contenuCommande = new ContenuCommandeClient();
			while(result.next())
			{
				contenuCommande.setCommande(Connexion.getCommandeClientDAO().select(result.getInt("CSECU"), result.getInt("CCNUMERO")));
				contenuCommande.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				contenuCommande.setQuantite(result.getInt("QUANTITE"));
				contenuCommande.setPrix(result.getBigDecimal("PRIX").doubleValue());
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return contenuCommande;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(ContenuCommandeClient obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into CONTENUCOMMANDECLIENT values(?, ?, ?, ?, ?)");
			
			insert.setObject(1, obj.getCommande().getClient().getSecu());
			insert.setObject(2, obj.getCommande().getNumero());
			insert.setObject(3, obj.getProduit().getPID());
			insert.setObject(4, obj.getQuantite());
			insert.setObject(5, obj.getPrix());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(ContenuCommandeClient obj)
	{
		return false;
	}

	public boolean delete(ContenuCommandeClient obj)
	{
		return false;
	}
}