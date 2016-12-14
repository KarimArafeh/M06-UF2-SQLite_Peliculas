import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by y2793623b on 12/12/16.
 */
public class selectSQLite {

    public static void main( String args[] )
    {

        Scanner scr = new Scanner(System.in);
        System.out.println("elige el numero del opcion que quieres : ");
        System.out.println("1) mostrar las peliculas . ");
        System.out.println("2) mostrar los actores . ");
        System.out.println("3) mostrar los actores de una pelicula . ");
        System.out.println("4) mostrar las peliculas de un actor . ");

        int opcion = scr.nextInt();

        switch (opcion)
        {
            case 1:
                sacar_peliculas();
                break;
            case 2:
                sacar_actores();
                break;
            case 3:
                relacion_pelicula();
                break;
            case 4:
                relacion_actor();
                break;
            default:
                break;
        }



    }


    private static void sacar_peliculas() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:movie.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PELICULAS;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID_peli");
                String nom = rs.getString("NOM_peli");
                String fecha  = rs.getString("Fecha");


                System.out.println( "ID : " + id );
                System.out.println( "NAME : " + nom );
                System.out.println( "DATE : " + fecha );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private static void sacar_actores() {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:movie.db");
            conn.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACTOR;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID_act");
                String nom = rs.getString("NOM_act");
                String fecha  = rs.getString("Fecha_Nacamiento");


                System.out.println( "ID : " + id );
                System.out.println( "NAME : " + nom );
                System.out.println( "DATE OF BIRTH : " + fecha );
                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private static void relacion_pelicula() {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:movie.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID_peli, NOM_peli FROM PELICULAS;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID_peli");
                String nom = rs.getString("NOM_peli");

                System.out.println( "La pelicla : " + nom + " tiene la ID :-->> " + id );
                System.out.println();
            }
            System.out.println("Escribe la id de la pelicula que quieres mostrar sus actores :");
            Scanner scr = new Scanner(System.in);
            int idPeliculaValor = scr.nextInt();

            rs = stmt.executeQuery( "select  pel.ID_peli, pel.NOM_peli, a.ID_act, a.NOM_act, per.PERSONAJE\n" +
                    "  from ACTOR a\n" +
                    "  INNER JOIN PERSONAJES per on a.ID_act = per.ID_Actor\n" +
                    "  INNER JOIN PELICULAS pel on per.ID_pelicula = pel.ID_peli\n" +
                    "  where pel.ID_peli = "+idPeliculaValor+";" );

            System.out.println("ID_PELICULA   |    NOM_PELICULA    |    ID_ACTOR   |   NOM_ACTOR   |   PERSONAJE");
            System.out.println();
            while ( rs.next() ) {
                int id_peli = rs.getInt("ID_peli");
                String nom_peli = rs.getString("NOM_peli");
                int id_actor = rs.getInt("ID_act");
                String nom_actor = rs.getString("NOM_act");
                String nom_personaje = rs.getString("PERSONAJE");


                System.out.println( id_peli + "   |   " + nom_peli + "   |   " + id_actor + "   |   " + nom_actor + "   |   " + nom_personaje);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    private static void relacion_actor() {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:movie.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID_act, NOM_act FROM ACTOR;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID_act");
                String nom = rs.getString("NOM_act");

                System.out.println( "El actor : " + nom + " tiene la ID :-->> " + id );
                System.out.println();
            }
            System.out.println("Escribe la id del actor que quieres mostrar sus peliculas :");
            Scanner scr = new Scanner(System.in);
            int idActorvalor = scr.nextInt();

            rs = stmt.executeQuery( "select a.ID_act, a.NOM_act, pel.NOM_peli\n" +
                    "  from PELICULAS pel\n" +
                    "  INNER JOIN PERSONAJES per on pel.ID_peli = per.ID_pelicula\n" +
                    "  INNER JOIN ACTOR a on per.ID_Actor = a.ID_act\n" +
                    "  where a.ID_act = "+idActorvalor+";" );

            System.out.println("ID_ACTOR   |   NOM_ACTOR   |    NOM_PELICULA");
            System.out.println();
            while (rs.next()){
                int id_actor = rs.getInt("ID_act");
                String nom_actor = rs.getString("NOM_act");
                String nom_pelicula = rs.getString("NOM_peli");

                System.out.println( id_actor + "   |   " + nom_actor + "   |   " + nom_pelicula);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


    }

}
