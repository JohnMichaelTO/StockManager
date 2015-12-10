package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.Client;
import Programme.Connexion;

public class ClientDAO implements DAO<Client>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public ClientDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Client select(int secu)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from CLIENT where CSECU = ?");
			
			select.setObject(1, secu);
			
			ResultSet result = select.executeQuery();
			
			Client client = new Client();
			while(result.next())
			{
				client.setSecu(result.getInt("CSECU"));
				client.setNom(result.getString("CNOM"));
				client.setTelephone(result.getString("CTELEPHONE"));
				client.setAdresse(Connexion.getAdresseDAO().select(result.getInt("CADRESSE")));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return client;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Client obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into CLIENT values(?, ?, ?, ?)");

			insert.setObject(1, obj.getSecu());
			insert.setObject(2, obj.getNom());
			insert.setObject(3, obj.getTelephone());
			insert.setObject(4, obj.getAdresse().getAID());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Client obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update CLIENT set CNOM = ? and CTELEPHONE = ? and CADRESSE = ? where CSECU = ?");
			
			update.setObject(1, obj.getNom());
			update.setObject(2, obj.getTelephone());
			update.setObject(3, obj.getAdresse().getAID());
			update.setObject(4, obj.getSecu());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Client obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from CLIENT where CSECU = ?");
			
			delete.setObject(1, obj.getSecu());
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