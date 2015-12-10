package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.Produit;
import Objets.Vetement;
import Programme.Connexion;

public class VetementDAO implements DAO<Vetement>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public VetementDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public Vetement select(int PID, String couleur, String taille)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from PRODUIT natural join VETEMENT where PID = ? and VCOULEUR = ? and VTAILLE = ?");
			
			select.setObject(1, PID);
			select.setObject(2, couleur);
			select.setObject(3, taille);

			ResultSet result = select.executeQuery();
			
			Vetement vetement = new Vetement();
			while(result.next())
			{
				vetement.setNom(result.getString("PNOM"));
				vetement.setFournisseur(Connexion.getFournisseurDAO().select(result.getString("PFOURNISSEUR")));
				vetement.setPrix(result.getBigDecimal("PPRIX").doubleValue());
				vetement.setType(Connexion.getTypeProduitDAO().select(result.getString("PTYPE")));
				vetement.setVolume(result.getInt("PVOLUME"));
				vetement.setCouleur(result.getString("VCOULEUR"));
				vetement.setTaille(result.getString("VTAILLE"));
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return vetement;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(Vetement obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into VETEMENT values(?, ?, ?)");
			
			Produit produit = (Vetement) obj;
			Connexion.getProduitDAO().insert(produit);
			
			insert.setObject(1, Connexion.getProduitDAO().getLastID());
			insert.setObject(2, obj.getCouleur());
			insert.setObject(3, obj.getTaille());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Vetement obj)
	{
		return false;
	}

	public boolean delete(Vetement obj)
	{
		return false;
	}
}