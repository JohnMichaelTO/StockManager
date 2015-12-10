package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.AdresseLivraisonClient;
import Programme.Connexion;

public class AdresseLivraisonClientDAO implements DAO<AdresseLivraisonClient>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public AdresseLivraisonClientDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public AdresseLivraisonClient select(int secu, int AID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ADRESSELIVRAISONCLIENT where CSECU = ? and AID = ?");
			
			select.setObject(1, secu);
			select.setObject(2, AID);
			
			ResultSet result = select.executeQuery();
			
			AdresseLivraisonClient adresseLivraison = new AdresseLivraisonClient();
			while(result.next())
			{
				adresseLivraison.setClient(Connexion.getClientDAO().select(result.getInt("CSECU")));
				adresseLivraison.setAdresse(Connexion.getAdresseDAO().select(result.getInt("AID")));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return adresseLivraison;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(AdresseLivraisonClient obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ADRESSELIVRAISONCLIENT values(?, ?)");
			
			insert.setObject(1, obj.getClient().getSecu());
			insert.setObject(2, obj.getAdresse().getAID());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(AdresseLivraisonClient obj)
	{
		return false;
	}

	public boolean delete(AdresseLivraisonClient obj)
	{
		return false;
	}
}