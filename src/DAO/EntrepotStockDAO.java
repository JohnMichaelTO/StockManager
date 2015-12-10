package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objets.EntrepotStock;
import Programme.Connexion;

public class EntrepotStockDAO implements DAO<EntrepotStock>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	private PreparedStatement list = null;
	private PreparedStatement listTotal = null;
	
	public EntrepotStockDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public EntrepotStock select(String enom, int PID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ENTREPOTSTOCK where ENOM = ? and PID = ?");
			
			select.setObject(1, enom);
			select.setObject(2, PID);
			
			ResultSet result = select.executeQuery();
			
			EntrepotStock entrepotStock = new EntrepotStock();
			while(result.next())
			{
				entrepotStock.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotStock.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				entrepotStock.setQuantite(result.getInt("QUANTITE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return entrepotStock;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(EntrepotStock obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ENTREPOTSTOCK (ENOM, PID, QUANTITE) values(?, ?, ?)");
			
			insert.setObject(1, obj.getEntrepot().getNom());
			insert.setObject(2, obj.getProduit().getPID());
			insert.setObject(3, obj.getQuantite());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			this.update(obj);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EntrepotStock obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update ENTREPOTSTOCK set QUANTITE = ? where ENOM = ? and PID = ?");
			
			update.setObject(1, obj.getQuantite());
			update.setObject(2, obj.getEntrepot().getNom());
			update.setObject(3, obj.getProduit().getPID());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(EntrepotStock obj)
	{
		return false;
	}
	
	public List<EntrepotStock> getStocksProduit(int PID)
	{
		try
		{
			if(list == null)
			list = bdd.prepareStatement("select * from ENTREPOTSTOCK where PID = ?");
			
			list.setObject(1, PID);
			
			ResultSet result = list.executeQuery();
			
			List<EntrepotStock> liste = new ArrayList<EntrepotStock>();
			while(result.next())
			{
				EntrepotStock entrepotStock = new EntrepotStock();
				entrepotStock.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotStock.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				entrepotStock.setQuantite(result.getInt("QUANTITE"));
				
				liste.add(entrepotStock);
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
	
	public int getTotalStocksProduit(int PID)
	{
		try
		{
			if(listTotal == null)
			listTotal = bdd.prepareStatement("select sum(QUANTITE) as SOMME from ENTREPOTSTOCK where PID = ?");
			
			listTotal.setObject(1, PID);
			
			ResultSet result = listTotal.executeQuery();
			
			int quantite = 0;
			while(result.next())
			{
				quantite = result.getInt("SOMME");
			}
			while(result.next())
			{
				result.close();
				return -1;
			}
			result.close();
			
			return quantite;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
}