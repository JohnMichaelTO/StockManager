package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.LotCommande;
import Programme.Connexion;

public class LotCommandeDAO implements DAO<LotCommande>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public LotCommandeDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public LotCommande select(int secu, int ccnum, int LID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from LOTCOMMANDE natural join LOT where CSECU = ? and CCNUMEERO = ? and LID = ?");
			
			select.setObject(1, secu);
			select.setObject(2, ccnum);
			select.setObject(3, LID);
			
			ResultSet result = select.executeQuery();
			
			LotCommande lotCommande = new LotCommande();
			while(result.next())
			{
				lotCommande.setCommande(Connexion.getCommandeClientDAO().select(result.getInt("CSECU"), result.getInt("CCNUMERO")));
				lotCommande.setLot(Connexion.getLotDAO().select(result.getInt("LID"), result.getInt("PID")));
				lotCommande.setQuantite(result.getInt("QUANTITE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return lotCommande;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(LotCommande obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into LOTCOMMANDE (CSECU, CCNUMERO, LID, QUANTITE) values(?, ?, ?, ?) on duplicate key update QUANTITE = QUANTITE + values(QUANTITE)");

			insert.setObject(1, obj.getCommande().getClient().getSecu());
			insert.setObject(2, obj.getCommande().getNumero());
			insert.setObject(3, obj.getLot().getLID());
			insert.setObject(4, obj.getQuantite());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(LotCommande obj)
	{
		return false;
	}

	public boolean delete(LotCommande obj)
	{
		return false;
	}
}