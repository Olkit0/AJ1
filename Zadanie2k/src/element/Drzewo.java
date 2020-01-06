package element;

import java.util.ArrayList;
import java.util.List;


public class Drzewo 
{

	List<Element> elementy = new ArrayList<Element>();
      	
      	public void dodajElement(Element element) 
      	{
      		elementy.add(element);
      		System.out.println("Dodano wezel/lisc do drzewa!");
      	}
    	public List<Element> getElementy() {
    		return elementy;
    	}
    	public int znajdzIndexElement(Element element) {
 		
    		return elementy.indexOf(element);
    	}
    	public void zmienElement(int indeks, Element element) {
     		elementy.set(indeks,element);
     		System.out.println("Zmodyfikowano wezel/lisc na drzewie!");
    	}
       	public void usunElement(int indeks) {
     		elementy.remove(indeks);
     		//tej metody u�ywamy do usuwania tylko pojedynczych li�ci
     		//obs�uga usuwania ca�ej ga��zi drzewa b�dzie rekurencyjna i znajdzie si� w interfejsie
     		System.out.println("Usuni�to z drzewa lisc!"+indeks);
    	}
    /*	public double sumaLiscia(int indeks) 
    	{ 
    		Element el;
    		double suma=0;
    		int indeks1=indeks;
    		do
    		{
    		el=elementy.get(indeks1);
    		suma+=el.zawartosc;
    		indeks1=znajdzIndexPoId(el.idPoprzedniego);
    		}
    		while (el.idPoprzedniego!=0);
    		return suma;
    	}*/
    	public int znajdzIndexPoId(int id) {
     		int indekszwrot=-1;
     		for(Element el:elementy) 
     		{
     			if (el.getIdWezla()==id) 
     			{
     				indekszwrot=elementy.indexOf(el);
     			}
     		}
    		return indekszwrot;
    	}
		
}