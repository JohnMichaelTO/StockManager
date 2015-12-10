package Objets;

public class CommandeClient
{
	private Client client;
	private int numero;
	private Adresse adresse;
	
	public CommandeClient()
	{
		
	}
	
	public CommandeClient(int numero, Client client, Adresse adresse)
	{
		setNumero(numero);
		setClient(client);
		setAdresse(adresse);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
}
