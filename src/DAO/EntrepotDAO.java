package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.Entrepot;
import Programme.Connexion;

public class EntrepotDAO implements DAO<Entrepot>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public EntrepotDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Entrepot select(String enom)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ENTREPOT where ENOM = ?");
			
			select.setObject(1, enom);
			
			ResultSet result = select.executeQuery();
			
			Entrepot entrepot = new Entrepot();
			while(result.next())
			{
				entrepot.setNom(result.getString("ENOM"));
				entrepot.setAdresse(Connexion.getAdresseDAO().select(result.getInt("EADRESSE")));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return entrepot;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Entrepot obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ENTREPOT values(?, ?)");

			insert.setObject(1, obj.getNom());
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
	
	public boolean update(Entrepot obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update ENTREPOT set FADRESSE = ? where ENOM = ?");
			
			update.setObject(1, obj.getAdresse().getAID());
			update.setObject(2, obj.getNom());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Entrepot obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from ENTREPOT where ENOM = ?");
			
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