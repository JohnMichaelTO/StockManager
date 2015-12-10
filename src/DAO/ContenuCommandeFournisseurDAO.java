package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Objets.ContenuCommandeFournisseur;
import Programme.Connexion;

public class ContenuCommandeFournisseurDAO implements DAO<ContenuCommandeFournisseur>
{
	private Connection bdd = null;
	private PreparedStatement select = null;
	private PreparedStatement insert = null;
	//private PreparedStatement update = null;
	//private PreparedStatement delete = null;
	
	public ContenuCommandeFournisseurDAO() throws SQLException
	{
		this.bdd = Connexion.getBdd();
	}
	
	public ContenuCommandeFournisseur select(int cfnum, int PID)
	{
		try
		{
			if(select == null)
			select = bdd.prepareStatement("select * from CONTENUCOMMANDEFOURNISSEUR where CFNUMEERO = ? and PID = ?");
			
			select.setObject(1, cfnum);
			select.setObject(2, PID);
			
			ResultSet result = select.executeQuery();
			
			ContenuCommandeFournisseur contenuCommande = new ContenuCommandeFournisseur();
			while(result.next())
			{
				contenuCommande.setCommande(Connexion.getCommandeFournisseurDAO().select(result.getInt("CFNUMERO")));
				contenuCommande.setProduit(Connexion.getProduitDAO().select(result.getInt("PID")));
				contenuCommande.setQuantite(result.getInt("QUANTITE"));
				contenuCommande.setPrix(result.getBigDecimal("PRIX").doubleValue());
			}
			while(result.next())
			{
				result.close();
				return null;
			}
			
			result.close();
			
			return contenuCommande;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert(ContenuCommandeFournisseur obj)
	{
		try
		{
			if(insert == null)
			insert = bdd.prepareStatement("insert into CONTENUCOMMANDEFOURNISSEUR values(?, ?, ?, ?)");
			
			insert.setObject(1, obj.getCommande().getNumero());
			insert.setObject(2, obj.getProduit().getPID());
			insert.setObject(3, obj.getQuantite());
			insert.setObject(4, obj.getPrix());
			insert.executeUpdate();
			
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(ContenuCommandeFournisseur obj)
	{
		return false;
	}

	public boolean delete(ContenuCommandeFournisseur obj)
	{
		return false;
	}
}