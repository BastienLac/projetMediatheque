import java.sql.*;

public class MySQLConnection {
    public static String lien = "jdbc:mysql://localhost:3306/mediathequee";
    public static String utilisateur = "root";
    public static Connection getConnexion(){
        try
        {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    MySQLConnection.lien, utilisateur, "");

            return conn;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
