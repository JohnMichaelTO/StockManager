package Objets;

public class EntrepotSaitStocker
{
	private Entrepot entrepot;
	private TypeProduit type;
	private int capacite;
	
	public EntrepotSaitStocker()
	{
		
	}

	public Entrepot getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public TypeProduit getType() {
		return type;
	}

	public void setType(TypeProduit type) {
		this.type = type;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	@Override
	public String toString() {
		return "Type : " + type + " - Capacite : " + capacite + "\n";
	}
}
