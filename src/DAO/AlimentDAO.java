package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Objets.Aliment;
import Objets.Produit;
import Programme.Connexion;

public class AlimentDAO implements DAO<Aliment>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public AlimentDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Aliment select(int PID, Date adate)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from PRODUIT natural join ALIMENT where PID = ? and ADATE = ?");
			
			select.setObject(1, PID);
			select.setObject(2, new java.sql.Date(adate.getTime()));

			ResultSet result = select.executeQuery();
			
			Aliment aliment = new Aliment();
			while(result.next())
			{
				aliment.setNom(result.getString("PNOM"));
				aliment.setFournisseur(Connexion.getFournisseurDAO().select(result.getString("PFOURNISSEUR")));
				aliment.setPrix(result.getBigDecimal("PPRIX").doubleValue());
				aliment.setType(Connexion.getTypeProduitDAO().select(result.getString("PTYPE")));
				aliment.setVolume(result.getInt("PVOLUME"));
				aliment.setDate(result.getDate("ADATE"));
				aliment.setTemperature(result.getBigDecimal("ATEMPERATURE").doubleValue());
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return aliment;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Aliment obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ALIMENT values(?, ?, ?)");
			
			Produit produit = (Aliment) obj;
			Connexion.getProduitDAO().insert(produit);
			
			insert.setObject(1, Connexion.getProduitDAO().getLastID());
			insert.setObject(2, new java.sql.Date(obj.getDate().getTime()));
			insert.setObject(3, obj.getTemperature());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Aliment obj)
	{
		return false;
	}

	public boolean delete(Aliment obj)
	{
		return false;
	}
}