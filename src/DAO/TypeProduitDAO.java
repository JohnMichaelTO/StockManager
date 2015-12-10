package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.TypeProduit;
import Programme.Connexion;

public class TypeProduitDAO implements DAO<TypeProduit>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public TypeProduitDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public TypeProduit select(String tpnom)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from TYPEPRODUIT where TPNOM = ?");
			
			select.setObject(1, tpnom);
			
			ResultSet result = select.executeQuery();
			
			TypeProduit typeProduit = new TypeProduit();
			while(result.next())
			{
				typeProduit.setNom(result.getString("TPNOM"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return typeProduit;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(TypeProduit obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into TYPEPRODUIT values(?)");
			
			insert.setObject(1, obj.getNom());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(TypeProduit obj)
	{
		return false;
	}

	public boolean delete(TypeProduit obj)
	{
		return false;
	}
}