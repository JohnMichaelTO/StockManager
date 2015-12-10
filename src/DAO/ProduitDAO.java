package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objets.Produit;
import Programme.Connexion;

public class ProduitDAO implements DAO<Produit>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	private PreparedStatement list = null;
	private PreparedStatement last = null;
	
	public ProduitDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Produit select(int PID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from PRODUIT where PID = ?");
			
			select.setObject(1, PID);
			
			ResultSet result = select.executeQuery();
			
			Produit produit = new Produit();
			while(result.next())
			{
				produit.setPID(result.getInt("PID"));
				produit.setNom(result.getString("PNOM"));
				produit.setFournisseur(Connexion.getFournisseurDAO().select(result.getString("PFOURNISSEUR")));
				produit.setPrix(result.getBigDecimal("PPRIX").doubleValue());
				produit.setType(Connexion.getTypeProduitDAO().select(result.getString("PTYPE")));
				produit.setVolume(result.getInt("PVOLUME"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return produit;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Produit obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into PRODUIT values('', ?, ?, ?, ?, ?)");

			insert.setObject(1, obj.getNom());
			insert.setObject(2, obj.getFournisseur().getNom());
			insert.setObject(3, obj.getPrix());
			insert.setObject(4, obj.getType().getNom());
			insert.setObject(5, obj.getVolume());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Produit obj)
	{
		return false;
	}

	public boolean delete(Produit obj)
	{
		return false;
	}
	
	public List<Produit> getList()
	{
		try
		{
			if(list == null)
			list = bdd.prepareStatement("select * from PRODUIT order by PID asc");
			
			ResultSet result = list.executeQuery();
			
			List<Produit> liste = new ArrayList<Produit>();
			while(result.next())
			{
				Produit produit = new Produit();
				produit.setPID(result.getInt("PID"));
				produit.setNom(result.getString("PNOM"));
				produit.setFournisseur(Connexion.getFournisseurDAO().select(result.getString("PFOURNISSEUR")));
				produit.setPrix(result.getBigDecimal("PPRIX").doubleValue());
				produit.setType(Connexion.getTypeProduitDAO().select(result.getString("PTYPE")));
				produit.setVolume(result.getInt("PVOLUME"));
				
				liste.add(produit);
			}
			
			result.close();
			
			return liste;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public int getLastID()
	{
		try
		{
			if(last == null)
			last = bdd.prepareStatement("select max(PID) from PRODUIT");
			
			ResultSet result = last.executeQuery();
			
			int PID = 0;
			while(result.next())
			{
				PID = result.getInt("PID");
			}
			while(result.next())
			{
				result.close();
				return 0;
			}
			
			result.close();
			
			return PID;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}