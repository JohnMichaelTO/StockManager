package Objets;

public class CommandeFournisseur
{
	private int numero;

	public CommandeFournisseur()
	{
		
	}
	
	public CommandeFournisseur(int numero)
	{
		setNumero(numero);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
