package Objets;

public class EntrepotStock
{
	private Entrepot entrepot;
	private Produit produit;
	private int quantite;
	
	public EntrepotStock()
	{
		
	}

	public Entrepot getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return entrepot.getNom() + " - Quantite = " + quantite + "\n";
	}
}
