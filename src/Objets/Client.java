package Objets;

public class Client
{
	private int secu;
	private String nom;
	private String telephone;
	private Adresse adresse;
	
	public Client()
	{
		
	}

	public int getSecu() {
		return secu;
	}

	public void setSecu(int secu) {
		this.secu = secu;
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
}
