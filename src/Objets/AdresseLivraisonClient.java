package Objets;

public class AdresseLivraisonClient
{
	private Client client;
	private Adresse adresse;
	
	public AdresseLivraisonClient()
	{
		
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
}
