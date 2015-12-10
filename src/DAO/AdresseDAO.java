package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.Adresse;
import Programme.Connexion;

public class AdresseDAO implements DAO<Adresse>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public AdresseDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Adresse select(int ID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ADRESSE where AID = ?");
			
			select.setObject(1, ID);
			ResultSet result = select.executeQuery();
			
			Adresse adresse = new Adresse();
			while(result.next())
			{
				adresse.setAID(result.getInt("AID"));
				adresse.setNumero(result.getString("ANUMERO"));
				adresse.setRue(result.getString("ARUE"));
				adresse.setCp(result.getString("ACP"));
				adresse.setVille(result.getString("AVILLE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return adresse;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Adresse obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ADRESSE values(null, ?, ?, ?, ?)");
			
			insert.setObject(1, obj.getNumero());
			insert.setObject(2, obj.getRue());
			insert.setObject(3, obj.getCp());
			insert.setObject(4, obj.getVille());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Adresse obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update ADRESSE set ANUMERO = ? and ARUE = ? and ACP = ? and AVILLE = ? where AID = ?");
			
			update.setObject(1, obj.getNumero());
			update.setObject(2, obj.getRue());
			update.setObject(3, obj.getCp());
			update.setObject(4, obj.getVille());
			update.setObject(5, obj.getAID());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Adresse obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from ADRESSE where AID = ?");
			
			delete.setObject(1, obj.getAID());
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