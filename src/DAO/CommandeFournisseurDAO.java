package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.CommandeFournisseur;
import Programme.Connexion;

public class CommandeFournisseurDAO implements DAO<CommandeFournisseur>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	private PreparedStatement delete = null;
	
	public CommandeFournisseurDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public CommandeFournisseur select(int cfnum)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from COMMANDEFOURNISSEUR where CFNUMERO = ?");
			
			select.setObject(1, cfnum);
			
			ResultSet result = select.executeQuery();
			
			CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
			while(result.next())
			{
				commandeFournisseur.setNumero(result.getInt("CFNUMERO"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return commandeFournisseur;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(CommandeFournisseur obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into COMMANDEFOURNISSEUR values(?)");
			
			insert.setObject(1, obj.getNumero());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(CommandeFournisseur obj)
	{
		return false;
	}

	public boolean delete(CommandeFournisseur obj)
	{
		try
		{
			if(delete == null)
			delete = bdd.prepareStatement("delete from COMMANDEFOURNISSEUR where CFNUMERO = ?");
			
			delete.setObject(1, obj.getNumero());
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