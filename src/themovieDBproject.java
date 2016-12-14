

import Pelicula.Pelicula;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;




/**
 * Created by dremon on 09/11/15.
 */
public class themovieDBproject {


    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static void main(String[] args)
    {
        String s = "";
        String api_key = "f24da3bb7fa1ec6f6f0ce0955f22a41b";  //

        //llamar el funcion que inserta los datos de peliculas
        for (int i = 0; i < 40; i++) {
            int peli = 600 + i;
            String film = String.valueOf(peli);

            String peticionPeliculas = "https://api.themoviedb.org/3/movie/" + film + "?api_key=" + api_key;
            //https://api.themoviedb.org/3/movie/"+600+"?api_key="f24da3bb7fa1ec6f6f0ce0955f22a41b
            try {
                s = getHTML(peticionPeliculas);
                SJSpeliculas(s);
            } catch (Exception e) {
                System.out.println("La peli " + film + " no existeix");
            }
        }

        //llamar el funcion que inserta los datos de actores
        for (int j = 0; j < 40; j++) {
            String actor = String.valueOf(j + 1);
            String peticionActores = "https://api.themoviedb.org/3/person/" + actor + "?api_key=" + api_key;

            try {
                s = getHTML(peticionActores);
                SJSActores(s);
            } catch (Exception e) {
                System.out.println("La persona " + actor + " no existeix");
            }

        }

        //llamar el funcion que inserta los datos de personajes (relaciona peliculas con actores)
        for (int x = 0; x<40; x++)
        {
            String film = String.valueOf(x + 600);
            String peticionPersonaje = "https://api.themoviedb.org/3/movie/" + film + "/credits?api_key=" + api_key;

            try {
                s = getHTML(peticionPersonaje);
                SJSPersonajes(s);
            } catch (Exception e) {
                System.out.println("La peli " + film + " no existeix");
            }
        }


    }

    public static void SJSpeliculas (String cadena){

        Object obj =JSONValue.parse(cadena);

        JSONObject arrayMovies =(JSONObject)obj;

        int id = Integer.parseInt(arrayMovies.get("id").toString());
        String titul = arrayMovies.get("original_title").toString();
        String fecha = arrayMovies.get("release_date").toString();

        insertSQLite.insertMovies(id, arrayMovies.get("original_title").toString(), arrayMovies.get("release_date").toString());

    }

    public static void SJSActores(String cadena){

        Object obj =JSONValue.parse(cadena);
        JSONObject arrayActores =(JSONObject)obj;

        int id = Integer.parseInt(arrayActores.get("id").toString());
        String nom = arrayActores.get("name").toString();
        String data = arrayActores.get("birthday").toString();
        //per veure les dades instalades :
        /*
        System.out.println("------" + arrayActores.get("id"));
        System.out.println("------" + arrayActores.get("name"));
        System.out.println("------" + arrayActores.get("birthday"));
        */
        insertSQLite.insertActores(id, nom, data);

    }

    public static void SJSPersonajes (String cadena)
    {
        Object obj = JSONValue.parse(cadena);
        JSONObject arrayMovies = (JSONObject)obj;

        JSONArray personajes = (JSONArray)arrayMovies.get("cast");

        for (int i = 0; i <personajes.size() ; i++)
        {
            JSONObject objPersonajes = (JSONObject)personajes.get(i);

            int id_peli = Integer.parseInt(arrayMovies.get("id").toString());
            int id_personaje = Integer.parseInt(objPersonajes.get("cast_id").toString());
            int id_actor = Integer.parseInt(objPersonajes.get("id").toString());
            String nomPersonaje = objPersonajes.get("character").toString();
            // per veure los datos cargados :
            //System.out.println(id_peli + "-------"  + id_personaje + "--------" + id_actor + "-----------" + nomPersonaje);
            insertSQLite.insertPersonajes(id_peli, id_personaje, id_actor, nomPersonaje);
        }

    }

}
