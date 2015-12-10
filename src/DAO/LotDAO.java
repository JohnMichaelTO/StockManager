package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objets.Lot;
import Programme.Connexion;

public class LotDAO implements DAO<Lot>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	private PreparedStatement list = null;
	
	public LotDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Lot select(int lnum, int PID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from LOT where LNUMERO = ? and PID = ?");
			
			select.setObject(1, lnum);
			select.setObject(2, PID);
			
			ResultSet result = select.executeQuery();
			
			Lot lot = new Lot();
			while(result.next())
			{
				lot.setNumero(result.getInt("LNUMERO"));
				lot.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				lot.setQuantite(result.getInt("QUANTITE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return lot;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Lot obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into LOT (LID, LNUMERO, PID, QUANTITE) values('', ?, ?, ?) on duplicate key update QUANTITE = QUANTITE + values(QUANTITE)");
			
			insert.setObject(1, obj.getNumero());
			insert.setObject(2, obj.getProduit().getPID());
			insert.setObject(3, obj.getQuantite());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Lot obj)
	{
		return false;
	}

	public boolean delete(Lot obj)
	{
		return false;
	}
	
	public List<Lot> getList(int PID)
	{
		try
		{
			if(list == null)
			list = bdd.prepareStatement("select * from LOT where PID = ? order by PID");
			
			list.setObject(1, PID);
			
			ResultSet result = list.executeQuery();
			
			List<Lot> liste = new ArrayList<Lot>();
			while(result.next())
			{
				Lot lot = new Lot();
				lot.setNumero(result.getInt("LNUMERO"));
				lot.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				lot.setQuantite(result.getInt("QUANTITE"));
				
				liste.add(lot);
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
}