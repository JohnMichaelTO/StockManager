package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.CommandeClient;
import Programme.Connexion;

public class CommandeClientDAO implements DAO<CommandeClient>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public CommandeClientDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public CommandeClient select(int secu, int ccnum)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from COMMANDECLIENT where CSECU = ? and CCNUMERO = ?");
			
			select.setObject(1, secu);
			select.setObject(2, ccnum);
			
			ResultSet result = select.executeQuery();
			
			CommandeClient commandeClient = new CommandeClient();
			while(result.next())
			{
				commandeClient.setClient(Connexion.getClientDAO().select(result.getInt("CSECU")));
				commandeClient.setNumero(result.getInt("CCNUMERO"));
				commandeClient.setAdresse(Connexion.getAdresseDAO().select(result.getInt("CCADRESSE")));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return commandeClient;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(CommandeClient obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into COMMANDECLIENT values(?, ?, ?)");

			insert.setObject(1, obj.getClient().getSecu());
			insert.setObject(2, obj.getNumero());
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
	
	public boolean update(CommandeClient obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update COMMANDECLIENT set CCADRESSE = ? where CSECU = ? and CCNUMERO = ?");
			
			update.setObject(1, obj.getAdresse().getAID());
			update.setObject(2, obj.getClient().getSecu());
			update.setObject(3, obj.getNumero());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(CommandeClient obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from COMMANDECLIENT where CSECU = ? and CCNUMERO = ?");
			
			delete.setObject(1, obj.getClient().getSecu());
			delete.setObject(2, obj.getNumero());
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