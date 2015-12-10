package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objets.EntrepotSaitStocker;
import Programme.Connexion;

public class EntrepotSaitStockerDAO implements DAO<EntrepotSaitStocker>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	private PreparedStatement list = null;
	private PreparedStatement listMaxCapacity = null;
	
	public EntrepotSaitStockerDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public EntrepotSaitStocker select(String enom, String tpnom)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from ENTREPOTSAITSTOCKER where ENOM = ? and TPNOM = ?");

			select.setObject(1, enom);
			select.setObject(2, tpnom);
			
			ResultSet result = select.executeQuery();
			
			EntrepotSaitStocker entrepotSaitStocker = new EntrepotSaitStocker();
			while(result.next())
			{
				entrepotSaitStocker.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotSaitStocker.setType(Connexion.getTypeProduitDAO().select(result.getString("TPNOM")));
				entrepotSaitStocker.setCapacite(result.getInt("CAPACITE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return entrepotSaitStocker;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(EntrepotSaitStocker obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into ENTREPOTSAITSTOCKER values(?, ?, ?)");
			
			insert.setObject(1, obj.getEntrepot().getNom());
			insert.setObject(2, obj.getType().getNom());
			insert.setObject(3, obj.getCapacite());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EntrepotSaitStocker obj)
	{
		return false;
	}

	public boolean delete(EntrepotSaitStocker obj)
	{
		return false;
	}
	
	public List<EntrepotSaitStocker> getList(String enom)
	{
		try
		{
			if(list == null)
			list = bdd.prepareStatement("select * from ENTREPOTSAITSTOCKER where ENOM = ?");

			list.setObject(1, enom);
			
			ResultSet result = list.executeQuery();
			
			List<EntrepotSaitStocker> liste = new ArrayList<EntrepotSaitStocker>();
			while(result.next())
			{
				EntrepotSaitStocker entrepotSaitStocker = new EntrepotSaitStocker();
				entrepotSaitStocker.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotSaitStocker.setType(Connexion.getTypeProduitDAO().select(result.getString("TPNOM")));
				entrepotSaitStocker.setCapacite(result.getInt("CAPACITE"));
				
				liste.add(entrepotSaitStocker);
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
	
	public List<EntrepotSaitStocker> getListMaxCapacity(String tpnom)
	{
		try
		{
			if(listMaxCapacity == null)
			listMaxCapacity = bdd.prepareStatement("select A.ENOM, A.TPNOM, (A.CAPACITE - (select sum(PVOLUME * QUANTITE) from ENTREPOTSTOCK natural join PRODUIT where ENOM = A.ENOM and PTYPE = A.TPNOM)) as CAPACITE_RESTANTE from ENTREPOTSAITSTOCKER A where A.TPNOM = ? order by CAPACITE_RESTANTE desc");

			listMaxCapacity.setObject(1, tpnom);
			
			ResultSet result = listMaxCapacity.executeQuery();
			
			List<EntrepotSaitStocker> liste = new ArrayList<EntrepotSaitStocker>();
			while(result.next())
			{
				EntrepotSaitStocker entrepotSaitStocker = new EntrepotSaitStocker();
				entrepotSaitStocker.setEntrepot(Connexion.getEntrepotDAO().select(result.getString("ENOM")));
				entrepotSaitStocker.setType(Connexion.getTypeProduitDAO().select(result.getString("TPNOM")));
				entrepotSaitStocker.setCapacite(result.getInt("CAPACITE_RESTANTE"));
				
				liste.add(entrepotSaitStocker);
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