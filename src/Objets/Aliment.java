package Objets;

import java.sql.Date;

public class Aliment extends Produit
{
	private Date date;
	private double temperature;
	
	public Aliment()
	{
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}	
}
