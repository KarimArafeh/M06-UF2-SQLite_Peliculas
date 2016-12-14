/**
 * Created by dremon on 09/11/15.
 */

    import com.sun.corba.se.spi.monitoring.StatisticMonitoredAttribute;

    import java.sql.*;

public class insertSQLite {

    public static void insertMovies(int id, String original_title, String release_date) {
            Connection c = null;
            Statement stmt = null;
            try {

                //conectem amb la base de dades
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:movie.db");
                System.out.println("Opened database successfully");

                //fem el insert
                String sql_insert = "insert into PELICULAS (ID_peli, NOM_peli, Fecha) values (?, ?, ?)";
                PreparedStatement prepStatment = c.prepareStatement(sql_insert);
                System.out.println("insertando");
                prepStatment.setInt(1, id);
                prepStatment.setString(2, original_title);
                prepStatment.setString(3, release_date);
                //para ejecutar el sql
                prepStatment.executeUpdate();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Records created successfully");
        }


    public static void insertActores(int id, String name, String date)  {
            Connection c = null;
            Statement stmt = null;
            try {

                //conectem amb la base de dades
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:movie.db");
                System.out.println("Opened database successfully");

                //fem el insert
                String sql = "insert into ACTOR (ID_act, NOM_act, Fecha_Nacamiento) values (?, ?, ?)";
                PreparedStatement prepStat = c.prepareStatement(sql);
                System.out.println("insertando");
                prepStat.setInt(1, id);
                prepStat.setString(2, name);
                prepStat.setString(3, date);
                //para ejecutar el sql
                prepStat.executeUpdate();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Records created successfully");
        }


    public static void insertPersonajes(int id_peli, int id_pers, int id_act, String personaje) {
            Connection c = null;
            Statement stmt = null;

            try {

                //conectem amb la base de dades
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:movie.db");
                System.out.println("Opened database successfully");

                String sql = "insert into PERSONAJES (ID_pelicula, ID_Actor, ID_Personaje, PERSONAJE) values (?, ?, ?, ?)";
                PreparedStatement prepStat = c.prepareStatement(sql);
                System.out.println("insertando");
                prepStat.setInt(1, id_peli);
                prepStat.setInt(2, id_pers);
                prepStat.setInt(3, id_act);
                prepStat.setString(4, personaje);
                //ejecutar
                prepStat.executeUpdate();

            }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Records created successfully");

        }

}
