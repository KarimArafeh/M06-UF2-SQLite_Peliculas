/**
 * Created by dremon on 09/11/15.
 */
import java.sql.*;

public class createSQLite {

    public static void main(String[] args) {
        {

            // connection : para gestionar connection
            Connection c = null;

            // statment : Formata instrucciones SQL
            Statement stmt1 = null;

            try {
                // driver :
                Class.forName("org.sqlite.JDBC");
                // crear la coneccion a la base de datos :
                c = DriverManager.getConnection("jdbc:sqlite:movie.db");
                System.out.println("Opened database successfully");

                //creacion de latabla de peliculas
                stmt1 = c.createStatement();
                String sql1 = "CREATE TABLE PELICULAS " +
                        "(ID_peli INT PRIMARY KEY     NOT NULL," +
                        " NOM_peli           TEXT    NOT NULL," +
                        " Fecha          DATE   NOT NULL)";
                stmt1.executeUpdate(sql1);
                stmt1.close();

                //creacion de latabla de personajes
                Statement stmt2 = null;
                stmt2 = c.createStatement();
                String sql2 = "CREATE TABLE PERSONAJES " +
                        "(ID_pelicula INT     NOT NULL," +
                        " ID_Personaje   INT         NOT NULL," +
                        " ID_Actor  INT         NOT NULL," +
                        " PERSONAJE           TEXT    NOT NULL)";
                stmt2.executeUpdate(sql2);
                stmt2.close();

                //creacion de latabla de actores
                Statement stmt3 = null;
                stmt3 = c.createStatement();
                String sql3 = "CREATE TABLE ACTOR " +
                        "(ID_act INT PRIMARY KEY     NOT NULL," +
                        " NOM_act           TEXT    NOT NULL," +
                        " Fecha_Nacamiento          DATE   NOT NULL)";
                stmt3.executeUpdate(sql3);
                stmt3.close();



                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Table created successfully");
        }

    }


}
