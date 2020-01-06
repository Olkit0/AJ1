package element;

public class Element 
{
	int idWezla;
	int idPoprzedniego;
	String nazwa;
	double zawartosc;
	public Element()	
	{
		}
	public Element(int idWezla, int idPoprzedniego, String nazwa, double zawartosc) 
	{
		this.idWezla=idWezla;
		this.idPoprzedniego=idPoprzedniego;
		this.nazwa=nazwa;
		this.zawartosc=zawartosc;
	}

	public int getIdWezla() {
		return idWezla;
	}
	public void setIdWezla(int idWezla) {
		this.idWezla = idWezla;
	}
	public int getIdPoprzedniego() {
		return idPoprzedniego;
	}
	public void setIdPoprzedniego(int idPoprzedniego) {
		this.idPoprzedniego = idPoprzedniego;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public double getZawartosc() {
		return zawartosc;
	}
	public void setZawartosc(double zawartosc) {
		this.zawartosc = zawartosc;
	}

}
