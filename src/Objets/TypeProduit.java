package Objets;

public class TypeProduit
{
	private String nom;

	public TypeProduit()
	{
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}