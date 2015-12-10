package Objets;

public class Adresse
{
	private int AID;
	private String numero;
	private String rue;
	private String cp;
	private String ville;
	
	public Adresse()
	{
		
	}
	
	public Adresse(int AID, String numero, String rue, String cp, String ville)
	{
		setAID(AID);
		setNumero(numero);
		setRue(rue);
		setCp(cp);
		setVille(ville);
	}
	
	public int getAID() {
		return AID;
	}
	public void setAID(int aID) {
		AID = aID;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		return "Adresse [numero=" + numero + ", rue=" + rue + ", cp=" + cp
				+ ", ville=" + ville + "]";
	}
	
}
