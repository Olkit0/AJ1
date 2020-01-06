package element;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Interfejs 
{
    static Scanner sc = new Scanner(System.in);
    static Drzewo drzewo = new Drzewo();
    static Drzewo drzewoTymczas= new Drzewo();
    public static void main(String[] args) 
    {
    	wczytanieZBazy(drzewo);
     	String wyborUzytkownika;
    	int aktualnyId=0,idDocelowy=0;
    	do {
     		System.out.println();
    		System.out.print("Menu: ");
	    	System.out.println("1. przejdŸ do 2. dodaj wêze³ 3. czytaj wêze³ 4. modyfikuj wêze³ 5. usuñ wêze³ 6. kopiuj wêze³ 7. wstaw wêze³ 8. przenieœ wêze³ 9. zapisz do bazy 0. wyjdŸ");
    		System.out.println("  ");
	    	wyswietlZawartosc(aktualnyId);
			wyborUzytkownika = getUserInput();
	    	if (wyborUzytkownika.equals("1")) {
	   		 System.out.print(" Podaj docelowy ");
	    		idDocelowy=pytajOIdDocelowy();
	    		if (drzewo.znajdzIndexPoId(idDocelowy)<0)
	    			{idDocelowy=0;}
	    		aktualnyId=idDocelowy;
	    	} else if (wyborUzytkownika.equals("2")) {
	    		dodajWezel(aktualnyId);
 	    	} else if (wyborUzytkownika.equals("3")) {
	    		pokazWezel(aktualnyId);
	    	} else if (wyborUzytkownika.equals("4")) {
		    	modyfikujWezel(aktualnyId);
	    	} else if (wyborUzytkownika.equals("5")) {
	    	   System.out.print(" Podaj do usuniêcia ");
	    		idDocelowy=pytajOIdDocelowy();
	    		if (drzewo.znajdzIndexPoId(idDocelowy)<0)
	    			{idDocelowy=0;}
	    		usunWezel(idDocelowy);
	    		//aktualnyId=idDocelowy;
	    	} else if (wyborUzytkownika.equals("6")) {
		    	System.out.print(" Podaj do skopiowania ");
	    		idDocelowy=pytajOIdDocelowy();
	    		if (drzewo.znajdzIndexPoId(idDocelowy)<0)
	    			{idDocelowy=0;}
	    		kopiujWezel(idDocelowy);
	    	} else if (wyborUzytkownika.equals("7")) {
	    		wstawWezel(aktualnyId);
	    	} else if (wyborUzytkownika.equals("8")) {
		    	System.out.print(" Podaj do przeniesienia ");
		    	aktualnyId=pytajOIdDocelowy();
		    	System.out.print(" Podaj docelowy ");
	    		idDocelowy=pytajOIdDocelowy();
	    		if (idDocelowy==0) 
	    		{
	    			przeniesWezel(aktualnyId,idDocelowy);
	    		}
	    		else
	    		{
		    		if (drzewo.znajdzIndexPoId(idDocelowy)<0)
	    			{
		    	        System.out.println("Nie znaleziono wêz³a docelowego o id "+idDocelowy+" Wêze³ nie zosta³ przeniesiony");
		    			idDocelowy=0;
		    		}
		    		else
		    		{
		    			przeniesWezel(aktualnyId,idDocelowy);	
		    		}
	    		}	
    			
	    	} else if (wyborUzytkownika.equals("9")) {
	    		zapisDoBazy(drzewo);	
	    	}	
    	} while (!wyborUzytkownika.equalsIgnoreCase("0"));
    }

    public static String getUserInput() {
        return sc.nextLine().trim();
    }

	private static void dodajWezel(int idPoprzedniego) 
	{
		System.out.println("**Dodawanie nowego wêz³a** ");
		Element element = new Element();
		element.setIdWezla(dajNastepnyWolnyId(drzewo.getElementy()));
		System.out.print("Podaj nazwê: ");
        element.setNazwa(getUserInput());
        element.setIdPoprzedniego(idPoprzedniego);
        Pattern wzorzecZawartosci = Pattern.compile("[0-9]+(\\.[0-9]+)?");
        String zawartoscWczytana;
      	 System.out.print("Podaj zawartoœæ (liczba rzeczywista): ");
         zawartoscWczytana = getUserInput();
         if (wzorzecZawartosci.matcher(zawartoscWczytana).matches()) 
         {
                element.setZawartosc(Double.valueOf(zawartoscWczytana));
         }
            else
         { 
            	element.setZawartosc(0);
         }
        drzewo.dodajElement(element);
        System.out.println("Dodano wêze³ jako podrzêdny dla wêz³a o id "+idPoprzedniego);
	} 
	private static void pokazWezel(int idWezla) 
	{
		
		if (idWezla!=0) 
		{
			System.out.println("**Dane wêz³a** "+idWezla);
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
			System.out.println("Id wêz³a "+idWezla);
		    System.out.println("Id nadrzêdnego wêz³a "+element.getIdPoprzedniego());
			System.out.print("Aktualna nazwa: "+element.getNazwa()+" ");
	     	System.out.print("Aktualna wartoœæ: "+element.getZawartosc()+" ");
		}
		else
		{
			System.out.println("**Dane wêz³a - korzenia**");
			System.out.println("Id wêz³a "+idWezla);
			System.out.print("Korzeñ drzewa. Nie posiada ¿adnego wêz³a nadrzêdnego.");
		}
	} 
	private static void modyfikujWezel(int idWezla) 
	{
		if (idWezla!=0) 
		{
			System.out.println("**Edycja wêz³a**");
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
		
			System.out.print("Aktualna nazwa: "+element.getNazwa()+" Podaj now¹ nazwê: ");
	        element.setNazwa(getUserInput());
	      
	        Pattern wzorzecZawartosci = Pattern.compile("[0-9]+(\\.[0-9]+)?");
	        String zawartoscWczytana;
	      	 System.out.print("Aktualna wartoœæ: "+element.getZawartosc()+" Podaj now¹ wartoœæ (liczba rzeczywista): ");
	         zawartoscWczytana = getUserInput();
	         if (wzorzecZawartosci.matcher(zawartoscWczytana).matches()) 
	         {
	                element.setZawartosc(Double.valueOf(zawartoscWczytana));
	         }
	            else
	         { 
	            	element.setZawartosc(0);
	         }
	        drzewo.zmienElement(indeks, element);
	        System.out.println("Zmodyfikowano wêze³ o id "+idWezla);
		}
		else 
		{
			System.out.print("Korzeñ drzewa. Nie posiada danych które mo¿na by modyfikowaæ");
		}
	} 
	private static void usunWezel(int idWezla) 
	{
		if (idWezla!=0) 
		{
			System.out.println("**Usuwanie wêz³a** "+idWezla);
	        usunPodrzedne(idWezla);
			drzewo.usunElement(drzewo.znajdzIndexPoId(idWezla));
	        System.out.println("Usuniêto wraz z ca³¹ podrzêdn¹ struktur¹ wêze³ o id "+idWezla);
		}
		else 
		{
			System.out.print("Korzeñ drzewa. Nie mo¿na usun¹æ");
		}
	} 
	private static void usunPodrzedne(int idWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s¹ pod nim
		//jeœli nie natrafi na ¿aden to wraca do góry
		//w przeciwnym wypadku wywo³anie rekurencyjne dla ni¿ej po³o¿onych 
		//i nastêpnie usuwanie bie¿¹cego

        for (Element el:drzewo.elementy) 
        {
        	if (el.getIdPoprzedniego()==idWezla) 
        	{
        		usunPodrzedne(el.getIdWezla());
        	//	System.out.println(" Usuwanie liscia nr "+el.getIdWezla());
        		drzewo.usunElement(drzewo.znajdzIndexPoId(el.getIdWezla()));
        	}
        }
	}
	private static void kopiujWezel(int idWezla) 
	{
		if (idWezla!=0) 
		{
		System.out.println("**Kopiowanie wêz³a do schowka**");
		Element el= new Element();
				
		drzewoTymczas = new Drzewo();
		el.setIdPoprzedniego(0);
		el.setIdWezla(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getIdWezla());
		el.setZawartosc(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getZawartosc());
		el.setNazwa(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getNazwa());
		drzewoTymczas.dodajElement(el);
        kopiujPodrzedne(idWezla);
        System.out.println("Skopiowano wraz z ca³¹ podrzêdn¹ struktur¹ "+drzewoTymczas.getElementy().size()+" wêze³ o id "+idWezla);
		}
		else
		{
			System.out.print("Korzeñ drzewa. Nie mo¿na skopiowaæ w ca³oœci");
		}
	} 
	private static void kopiujPodrzedne(int idWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s¹ pod nim
		//jeœli natrafi na jakiœ, to skopiowanie do schowkowego drzewa i wywo³anie rekurencyjne dla ni¿ej po³o¿onych

        for (Element el:drzewo.getElementy()) 
        {
        	if (el.getIdPoprzedniego()==idWezla) 
        	{
        		drzewoTymczas.dodajElement(el);
        		kopiujPodrzedne(el.getIdWezla());
        	}
        }
	}
	private static void wstawWezel(int idWezlaDocelowego) 
	{
		
		if (drzewoTymczas.getElementy().size()>0) 
		{
			 // sprawdzenie czy nie próbujemy wstawiæ wêz³a we w³asny podwêze³ 
			if (!czyTenSam(drzewoTymczas.getElementy().get(0).getIdWezla(),idWezlaDocelowego)) 
			{
				int nowyId=0,pamietanyId=0;
				System.out.println("**Wstawianie w wybranym miejscu wêz³a ze schowka** ");
				Element el= new Element();
				//el=;
				pamietanyId=drzewoTymczas.getElementy().get(0).getIdWezla();
				nowyId=dajNastepnyWolnyId(drzewo.getElementy());
				el.setIdPoprzedniego(idWezlaDocelowego);
				el.setIdWezla(nowyId);
				el.setNazwa(drzewoTymczas.getElementy().get(0).getNazwa());
				el.setZawartosc(drzewoTymczas.getElementy().get(0).getZawartosc());
				drzewo.dodajElement(el);
				wklejPodrzedne(pamietanyId,nowyId);
		        System.out.println("Wstawiono zawartoœæ schowka do wêz³a o id "+idWezlaDocelowego);
		    }
			else
			{
				System.out.println("Nie mo¿na wstawiæ wêz³a do w³asnego podwêz³a");
			}
		}
	} 
	
	private static void wklejPodrzedne(int idWezla,int idNowegoWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s¹ pod nim
		//jeœli natrafi na jakiœ, to skopiowanie do schowkowego drzewa i wywo³anie rekurencyjne dla ni¿ej po³o¿onych
        int nowyId=0,staryId=0;
        for (Element el:drzewoTymczas.getElementy()) 
        {
        	if (el.getIdPoprzedniego()==idWezla) 
        	{
        		Element elT= new Element();
        		staryId=el.getIdWezla();
        		nowyId=dajNastepnyWolnyId(drzewo.getElementy());
        		elT.setIdPoprzedniego(idNowegoWezla);
        		elT.setIdWezla(nowyId);
          		elT.setNazwa(el.getNazwa());
        		elT.setZawartosc(el.getZawartosc());
        		
        		drzewo.dodajElement(elT);
        		wklejPodrzedne(staryId,nowyId);
        	}
        }
	}

	private static void przeniesWezel(int idWezla, int idWezlaDocelowego) 
	{
	    // sprawdzenie czy nie próbujemy przenieœæ wêz³a we w³asny podwêze³ 
		if (!czyTenSam(idWezla,idWezlaDocelowego)) 
		{
			System.out.println(" Przenoszenie wêz³a ");
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
		    element.setIdPoprzedniego(idWezlaDocelowego);
	        drzewo.zmienElement(indeks, element);
	        System.out.println("Przeniesiono wêze³ do nowej lokalizacji o id "+idWezlaDocelowego);
		}
		else
		{
			System.out.println("Nie mo¿na przenieœæ wêz³a do w³asnego podwêz³a");
		}
	}
	private static int pytajOIdDocelowy() 
	{
		Pattern wzorzecZawartosci = Pattern.compile("[0-9]+");
        String zawartoscWczytana;
		 System.out.print("id wêz³a: ");
         zawartoscWczytana = getUserInput();
         
         if (wzorzecZawartosci.matcher(zawartoscWczytana).matches()) 
         {
               return Integer.valueOf(zawartoscWczytana);
         }
            else
         { 
            	return 0;
         }
	}
	private static void wyswietlZawartosc(int idWezla) 
	{
		int poziom=0;
		Element element;
		if (idWezla==0) 
		{
			System.out.println("**Korzeñ drzewa (poziom zerowy)**");
		}
		else 
		{
			element=drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla));
			poziom=liczPoziom(idWezla)+1;
			System.out.println("**Zawartoœæ wêz³a o id: "+idWezla+" i nazwie: "+element.getNazwa()+" (poziom "+poziom+")");
		}
		for (int i=0; i<drzewo.getElementy().size(); i++) 
		{
			element = drzewo.getElementy().get(i);
			if (element.idPoprzedniego==idWezla) 
			{
				for (int j=0;j<poziom;j++) 
				{
					System.out.print("-");
				}
			System.out.println(element.getNazwa()+" ["+element.getIdWezla()+"] "+zliczZawartosc(element.getIdWezla()));
			}
		}
	}
	private static int liczPoziom(int idWezla) 
	{
		int idPoprz=0,poziom=0;
		idPoprz=drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).idPoprzedniego;
		while (idPoprz!=0) 
		{
			idPoprz=drzewo.elementy.get(drzewo.znajdzIndexPoId(idPoprz)).idPoprzedniego;
			poziom++;
		}
		return poziom;
	} 
	private static boolean czyTenSam(int idWstawiany, int idDocelowy) 
	{
		boolean zwrot=false;
		// znalezc wêze³ idDocelowy, sprawdziæ czy jego idPoprzedniego nie wskazuje na idWstawiany 
		//i tak sprawdzaæ po kolei a¿ idPoprzedniego==0 czyli osi¹gniêto korzeñ
		int idPoprz=0;
		if (idDocelowy!=0) 
		{
			idPoprz=drzewo.elementy.get(drzewo.znajdzIndexPoId(idDocelowy)).idPoprzedniego;
			while (idPoprz!=0) 
			{
				if (idPoprz==idWstawiany)
				{
					zwrot=true;
			    }
				idPoprz=drzewo.elementy.get(drzewo.znajdzIndexPoId(idPoprz)).idPoprzedniego;
			}
		}
		return zwrot;
	}
	private static double zliczZawartosc(int idWezla) 
	{
		
  	// znalezc wêze³ idWezla, odczytaæ jego idPoprzedniego i dodaæ zawartoœæ do rezultatu
	//i tak dodawaæ po kolei a¿ idPoprzedniego==0 czyli osi¹gniêto korzeñ
		
		Element el=drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla));
		int idPoprz=el.getIdPoprzedniego();
		double rezultat=el.getZawartosc();
		while (idPoprz!=0) 
		{
			el=drzewo.elementy.get(drzewo.znajdzIndexPoId(idPoprz));
			idPoprz=el.getIdPoprzedniego();
			rezultat+=el.getZawartosc();
		}
		return rezultat;
	}
	private static int dajNastepnyWolnyId(List<Element> lista) 
	{
		int tymczas=1,tymczas2=1,wynik=1;
		lista.sort(Comparator.comparing(Element::getIdWezla));
	//	lista.sort(Comparator.comparing(Element::getIdWezla).reversed());
		for(Element el:lista) 
		{
			tymczas=el.getIdWezla();
	        if (tymczas-tymczas2>1) 
	        {
	        	wynik=tymczas2+1;
	        	break;
 	        }		
	        else
	        {
	        	tymczas2=tymczas;
	        	wynik=tymczas2+1;
	        }
		}
		return wynik;
	}
	private static void wczytanieZBazy(Drzewo drzewo) 
	{
		//wczytujemy z bazy aktualny stan drzewa do obiektu drzewo
	      Baza b = new Baza();
            drzewo.elementy= b.selectElementy();

	        b.closeConnection();
	}
	private static void zapisDoBazy(Drzewo drzewo) 
	{
		//zapisujemy do bazy aktualny stan drzewa
	      Baza b = new Baza();
	      b.czyscBaze();
	      for(Element el:drzewo.getElementy()) 
	      {
	          b.insertWezel(Integer.toString(el.getIdWezla()) ,Integer.toString(el.getIdPoprzedniego()) ,el.getNazwa() ,Double.toString(el.getZawartosc()));
	      }
	      b.closeConnection();
	}
}
