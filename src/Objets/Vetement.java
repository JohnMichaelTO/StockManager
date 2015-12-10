package Objets;

public class Vetement extends Produit
{
	private String couleur;
	private String taille;
	
	public Vetement()
	{
		
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getTaille() {
		return taille;
	}

	public void setTaille(String taille) {
		this.taille = taille;
	}
	
}
