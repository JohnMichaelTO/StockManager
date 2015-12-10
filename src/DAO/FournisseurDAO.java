package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.Fournisseur;
import Programme.Connexion;

public class FournisseurDAO implements DAO<Fournisseur>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public FournisseurDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Fournisseur select(String fnom)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from FOURNISSEUR where FNOM = ?");
			
			select.setObject(1, fnom);
			
			ResultSet result = select.executeQuery();
			
			Fournisseur fournisseur = new Fournisseur();
			while(result.next())
			{
				fournisseur.setNom(result.getString("FNOM"));
				fournisseur.setTelephone(result.getString("FTELEPHONE"));
				fournisseur.setAdresse(Connexion.getAdresseDAO().select(result.getInt("FADRESSE")));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return fournisseur;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Fournisseur obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into FOURNISSEUR values(?, ?, ?)");

			insert.setObject(1, obj.getNom());
			insert.setObject(2, obj.getTelephone());
			insert.setObject(3, obj.getAdresse().getAID());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Fournisseur obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update FOURNISSEUR set FTELEPHONE = ? and FADRESSE = ? where FNOM = ?");
			
			update.setObject(1, obj.getTelephone());
			update.setObject(2, obj.getAdresse().getAID());
			update.setObject(3, obj.getNom());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Fournisseur obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from FOURNISSEUR where FNOM = ?");
			
			delete.setObject(1, obj.getNom());
			delete.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}