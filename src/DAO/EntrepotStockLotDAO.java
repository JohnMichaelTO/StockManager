package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objets.EntrepotStockLot;
import Programme.Connexion;

public class EntrepotStockLotDAO implements DAO<EntrepotStockLot>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	private PreparedStatement list = null;
	
	public EntrepotStockLotDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public EntrepotStockLot select(String enom, int LID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ENTREPOTSTOCKLOT natural join LOT where ENOM = ? and LID = ?");
			
			select.setObject(1, enom);
			select.setObject(2, LID);
			
			ResultSet result = select.executeQuery();
			
			EntrepotStockLot entrepotStockLot = new EntrepotStockLot();
			while(result.next())
			{
				entrepotStockLot.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotStockLot.setLot(Connexion.getLotDAO().select(result.getInt("LID"), result.getInt("PID")));
				entrepotStockLot.setQuantite(result.getInt("QUANTITE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return entrepotStockLot;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(EntrepotStockLot obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ENTREPOTSTOCKLOT (ENOM, LID, QUANTITE) values(?, ?, ?) on duplicate key update QUANTITE = QUANTITE + values(QUANTITE)");

			insert.setObject(1, obj.getEntrepot().getNom());
			insert.setObject(2, obj.getLot().getLID());
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
	
	public boolean update(EntrepotStockLot obj)
	{
		try
		{
			if(update == null)
			update = bdd.prepareStatement("update ENTREPOTSTOCKLOT set QUANTITE = ? where ENOM = ? and LID = ?");

			update.setObject(1, obj.getQuantite());
			update.setObject(2, obj.getEntrepot().getNom());
			update.setObject(3, obj.getLot().getLID());
			update.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(EntrepotStockLot obj)
	{
		return false;
	}
	
	public List<EntrepotStockLot> getList(String enom, int PID)
	{
		try
		{
			if(list == null)
			list = bdd.prepareStatement("select * from ENTREPOTSTOCKLOT natural join LOT where ENOM = ? and PID = ?");
			
			list.setObject(1, enom);
			list.setObject(2, PID);
			
			ResultSet result = list.executeQuery();
			
			List<EntrepotStockLot> liste = new ArrayList<EntrepotStockLot>();
			while(result.next())
			{
				EntrepotStockLot entrepotStockLot = new EntrepotStockLot();
				entrepotStockLot.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotStockLot.setLot(Connexion.getLotDAO().select(result.getInt("LID"), result.getInt("PID")));
				entrepotStockLot.setQuantite(result.getInt("QUANTITE"));
				
				liste.add(entrepotStockLot);
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