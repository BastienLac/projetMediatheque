import java.sql.*;
public class Main {
    public static void main(String[] args) {
        try
        {
            Connection conn = MySQLConnection.getConnexion();
            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM utilisateur");
            //étape 4: exécuter la requête
            while(res.next())
                System.out.println(res.getInt(1)+"  "+res.getString(2)
                        +"  "+res.getString(4));
            conn.close();
            //étape 5: fermez l'objet de connexion
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}