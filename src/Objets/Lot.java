package Objets;

public class Lot
{
	private int LID;
	private int numero;
	private Produit produit;
	private int quantite;
	
	public Lot()
	{
		
	}
	
	public Lot(int LID, int numero, Produit produit, int quantite)
	{
		this.setLID(LID);
		this.setNumero(numero);
		this.setProduit(produit);
		this.setQuantite(quantite);
	}

	public int getLID() {
		return LID;
	}

	public void setLID(int lID) {
		LID = lID;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + LID;
		result = prime * result + numero;
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
		result = prime * result + quantite;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lot other = (Lot) obj;
		if (LID != other.LID)
			return false;
		if (numero != other.numero)
			return false;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		if (quantite != other.quantite)
			return false;
		return true;
	}
}
