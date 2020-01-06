package element;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
public class Baza {
	   public static final String DRIVER = "org.sqlite.JDBC";
	    public static final String DB_URL = "jdbc:sqlite:zadanie2.db";

	    private Connection conn;
	    private Statement stat;

	    public Baza() {
	        try {
	            Class.forName(Baza.DRIVER);
	        } catch (ClassNotFoundException e) {
	            System.err.println("Brak sterownika JDBC");
	            e.printStackTrace();
	        }

	        try {
	            conn = DriverManager.getConnection(DB_URL);
	            stat = conn.createStatement();
	        } catch (SQLException e) {
	            System.err.println("Problem z otwarciem polaczenia");
	            e.printStackTrace();
	        }

	        createTables();
	    }
        public boolean czyscBaze() 
        {
        	boolean wynik=true;
	        String createDrzewo = "DROP TABLE drzewo";
	        try {
	            stat.execute(createDrzewo);

	        } catch (SQLException e) {
	            System.err.println("Blad przy tworzeniu tabeli");
	            e.printStackTrace();
	            wynik= false;
	        }
	        if (wynik) 
	        {
		        createDrzewo = "CREATE TABLE drzewo (idIndeks INTEGER PRIMARY KEY AUTOINCREMENT, idWezla int, idPoprzedniego int, nazwa varchar(255), zawartosc double)";
	
		        try {
		            stat.execute(createDrzewo);
	
		        } catch (SQLException e) {
		            System.err.println("Blad przy tworzeniu tabeli");
		            e.printStackTrace();
		            wynik=false;
		        }
	        }
	        return wynik;
        }
        
	    public boolean createTables()  {
	        String createDrzewo = "CREATE TABLE IF NOT EXISTS drzewo (idIndeks INTEGER PRIMARY KEY AUTOINCREMENT, idWezla int, idPoprzedniego int, nazwa varchar(255), zawartosc double)";

	        try {
	            stat.execute(createDrzewo);

	        } catch (SQLException e) {
	            System.err.println("Blad przy tworzeniu tabeli");
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }

	    public boolean insertWezel(String idWezla, String idPoprzedniego, String nazwa, String zawartosc) {
	        try {
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into drzewo values (NULL, ?, ?, ?, ?);");
	            prepStmt.setString(1, idWezla);
	            prepStmt.setString(2, idPoprzedniego);
	            prepStmt.setString(3, nazwa);
	            prepStmt.setString(4, zawartosc);
	            prepStmt.execute();
	        } catch (SQLException e) {
	            System.err.println("Blad przy wstawianiu wezla");
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }
	    public List<Element> selectElementy() {
	        List<Element> elementy = new LinkedList<Element>();
	        try {
	            ResultSet result = stat.executeQuery("SELECT * FROM drzewo");
	           // int idIndeks;
	            int idWezla, idPoprzedniego;
	            String nazwa;
	            double zawartosc;
	            while(result.next()) {
	               // idIndeks = result.getInt("idIndeks");
	                idWezla = result.getInt("idWezla");
	                idPoprzedniego = result.getInt("idPoprzedniego");
	                nazwa = result.getString("nazwa");
	                zawartosc = result.getDouble("zawartosc");
	                elementy.add(new Element(idWezla, idPoprzedniego, nazwa, zawartosc));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	        return elementy;
	    }
	    public void closeConnection() {
	        try {
	            conn.close();
	        } catch (SQLException e) {
	            System.err.println("Problem z zamknieciem polaczenia");
	            e.printStackTrace();
	        }
	    }
}
