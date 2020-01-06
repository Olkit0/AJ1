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
	    	System.out.println("1. przejd� do 2. dodaj w�ze� 3. czytaj w�ze� 4. modyfikuj w�ze� 5. usu� w�ze� 6. kopiuj w�ze� 7. wstaw w�ze� 8. przenie� w�ze� 9. zapisz do bazy 0. wyjd�");
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
	    	   System.out.print(" Podaj do usuni�cia ");
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
		    	        System.out.println("Nie znaleziono w�z�a docelowego o id "+idDocelowy+" W�ze� nie zosta� przeniesiony");
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
		System.out.println("**Dodawanie nowego w�z�a** ");
		Element element = new Element();
		element.setIdWezla(dajNastepnyWolnyId(drzewo.getElementy()));
		System.out.print("Podaj nazw�: ");
        element.setNazwa(getUserInput());
        element.setIdPoprzedniego(idPoprzedniego);
        Pattern wzorzecZawartosci = Pattern.compile("[0-9]+(\\.[0-9]+)?");
        String zawartoscWczytana;
      	 System.out.print("Podaj zawarto�� (liczba rzeczywista): ");
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
        System.out.println("Dodano w�ze� jako podrz�dny dla w�z�a o id "+idPoprzedniego);
	} 
	private static void pokazWezel(int idWezla) 
	{
		
		if (idWezla!=0) 
		{
			System.out.println("**Dane w�z�a** "+idWezla);
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
			System.out.println("Id w�z�a "+idWezla);
		    System.out.println("Id nadrz�dnego w�z�a "+element.getIdPoprzedniego());
			System.out.print("Aktualna nazwa: "+element.getNazwa()+" ");
	     	System.out.print("Aktualna warto��: "+element.getZawartosc()+" ");
		}
		else
		{
			System.out.println("**Dane w�z�a - korzenia**");
			System.out.println("Id w�z�a "+idWezla);
			System.out.print("Korze� drzewa. Nie posiada �adnego w�z�a nadrz�dnego.");
		}
	} 
	private static void modyfikujWezel(int idWezla) 
	{
		if (idWezla!=0) 
		{
			System.out.println("**Edycja w�z�a**");
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
		
			System.out.print("Aktualna nazwa: "+element.getNazwa()+" Podaj now� nazw�: ");
	        element.setNazwa(getUserInput());
	      
	        Pattern wzorzecZawartosci = Pattern.compile("[0-9]+(\\.[0-9]+)?");
	        String zawartoscWczytana;
	      	 System.out.print("Aktualna warto��: "+element.getZawartosc()+" Podaj now� warto�� (liczba rzeczywista): ");
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
	        System.out.println("Zmodyfikowano w�ze� o id "+idWezla);
		}
		else 
		{
			System.out.print("Korze� drzewa. Nie posiada danych kt�re mo�na by modyfikowa�");
		}
	} 
	private static void usunWezel(int idWezla) 
	{
		if (idWezla!=0) 
		{
			System.out.println("**Usuwanie w�z�a** "+idWezla);
	        usunPodrzedne(idWezla);
			drzewo.usunElement(drzewo.znajdzIndexPoId(idWezla));
	        System.out.println("Usuni�to wraz z ca�� podrz�dn� struktur� w�ze� o id "+idWezla);
		}
		else 
		{
			System.out.print("Korze� drzewa. Nie mo�na usun��");
		}
	} 
	private static void usunPodrzedne(int idWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s� pod nim
		//je�li nie natrafi na �aden to wraca do g�ry
		//w przeciwnym wypadku wywo�anie rekurencyjne dla ni�ej po�o�onych 
		//i nast�pnie usuwanie bie��cego

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
		System.out.println("**Kopiowanie w�z�a do schowka**");
		Element el= new Element();
				
		drzewoTymczas = new Drzewo();
		el.setIdPoprzedniego(0);
		el.setIdWezla(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getIdWezla());
		el.setZawartosc(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getZawartosc());
		el.setNazwa(drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla)).getNazwa());
		drzewoTymczas.dodajElement(el);
        kopiujPodrzedne(idWezla);
        System.out.println("Skopiowano wraz z ca�� podrz�dn� struktur� "+drzewoTymczas.getElementy().size()+" w�ze� o id "+idWezla);
		}
		else
		{
			System.out.print("Korze� drzewa. Nie mo�na skopiowa� w ca�o�ci");
		}
	} 
	private static void kopiujPodrzedne(int idWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s� pod nim
		//je�li natrafi na jaki�, to skopiowanie do schowkowego drzewa i wywo�anie rekurencyjne dla ni�ej po�o�onych

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
			 // sprawdzenie czy nie pr�bujemy wstawi� w�z�a we w�asny podw�ze� 
			if (!czyTenSam(drzewoTymczas.getElementy().get(0).getIdWezla(),idWezlaDocelowego)) 
			{
				int nowyId=0,pamietanyId=0;
				System.out.println("**Wstawianie w wybranym miejscu w�z�a ze schowka** ");
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
		        System.out.println("Wstawiono zawarto�� schowka do w�z�a o id "+idWezlaDocelowego);
		    }
			else
			{
				System.out.println("Nie mo�na wstawi� w�z�a do w�asnego podw�z�a");
			}
		}
	} 
	
	private static void wklejPodrzedne(int idWezla,int idNowegoWezla) 
	{
	    //tu poszukiwanie wszystkich, ktore s� pod nim
		//je�li natrafi na jaki�, to skopiowanie do schowkowego drzewa i wywo�anie rekurencyjne dla ni�ej po�o�onych
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
	    // sprawdzenie czy nie pr�bujemy przenie�� w�z�a we w�asny podw�ze� 
		if (!czyTenSam(idWezla,idWezlaDocelowego)) 
		{
			System.out.println(" Przenoszenie w�z�a ");
			int indeks=drzewo.znajdzIndexPoId(idWezla);
			Element element = drzewo.elementy.get(indeks);
		    element.setIdPoprzedniego(idWezlaDocelowego);
	        drzewo.zmienElement(indeks, element);
	        System.out.println("Przeniesiono w�ze� do nowej lokalizacji o id "+idWezlaDocelowego);
		}
		else
		{
			System.out.println("Nie mo�na przenie�� w�z�a do w�asnego podw�z�a");
		}
	}
	private static int pytajOIdDocelowy() 
	{
		Pattern wzorzecZawartosci = Pattern.compile("[0-9]+");
        String zawartoscWczytana;
		 System.out.print("id w�z�a: ");
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
			System.out.println("**Korze� drzewa (poziom zerowy)**");
		}
		else 
		{
			element=drzewo.elementy.get(drzewo.znajdzIndexPoId(idWezla));
			poziom=liczPoziom(idWezla)+1;
			System.out.println("**Zawarto�� w�z�a o id: "+idWezla+" i nazwie: "+element.getNazwa()+" (poziom "+poziom+")");
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
		// znalezc w�ze� idDocelowy, sprawdzi� czy jego idPoprzedniego nie wskazuje na idWstawiany 
		//i tak sprawdza� po kolei a� idPoprzedniego==0 czyli osi�gni�to korze�
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
		
  	// znalezc w�ze� idWezla, odczyta� jego idPoprzedniego i doda� zawarto�� do rezultatu
	//i tak dodawa� po kolei a� idPoprzedniego==0 czyli osi�gni�to korze�
		
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
