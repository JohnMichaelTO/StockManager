package Objets;

public class Fournisseur
{
	private String nom;
	private String telephone;
	private Adresse adresse;

	public Fournisseur()
	{
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return nom + " - telephone = " + telephone
				+ " - adresse = " + adresse;
	}
}
