package com.reto.chacao.database;

/**
 * Created by gustavo on 25/06/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.reto.chacao.model.Comment;
import com.reto.chacao.model.Event;
import com.reto.chacao.model.New;
import com.reto.chacao.model.Photo;
import com.reto.chacao.model.Place;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/*Para llamar al helper y usar las funciones se debe hacer de la siguiente manera
*
* DataBaseHelper dt =
            new DataBaseHelper(getBaseContext());
 LUEGO
 SQLiteDatabase db = dt.getWritableDatabase();

 ya en db podemos llamar a la funciones para hacer las consultas

 ejemplo en http://notasprogramacion.sodenet.es/crud-android-sqlite-bbdd/
* */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String NAME = "RetoChacaoDb";
    private static CursorFactory FACTORY = null;

    private static final String LOG = DataBaseHelper.class.getName();

    private static final String CREATE_EVENT = "CREATE TABLE event (" +
            "ev_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ev_name TEXT," +
            "ev_description TEXT," +
            "ev_url TEXT," +
            "ev_category TEXT," +
            "ev_tags TEXT," +
            "ev_type TEXT," +
            "ev_dateEnd TEXT,"+
            "ev_facebook TEXT," +
            "ev_twitter TEXT," +
            "ev_instagram TEXT)";
    private static final String CREATE_PHOTO = "CREATE TABLE photo (" +
            "pho_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pho_id_event INTEGER," +
            "pho_type TEXT," +
            "pho_name TEXT," +
            "pho_url TEXT)";
    private static final String CREATE_NEW = "CREATE TABLE new (" +
            "new_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "new_name TEXT," +
            "new_description TEXT)";
    private static final String CREATE_REPORT = "CREATE TABLE report (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "text TEXT)";
    private static final String CREATE_PLACE = "CREATE TABLE place (" +
            "pl_id INTEGER," +
            "pl_id_event INTEGER," +
            "pl_name TEXT," +
            "pl_description TEXT," +
            "pl_url TEXT," +
            "pl_latitude FLOAT," +
            "pl_longitude FLOAT," +
            "PRIMARY KEY (pl_id, pl_id_event))";

    private static final String CREATE_COMMENT  = "CREATE TABLE comment (" +
            "cm_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "cm_id_event INTEGER," +
            "cm_firstname TEXT," +
            "cm_lastname TEXT," +
            "cm_text TEXT," +
            "cm_date TEXT," +
            "cm_photo TEXT)";


    public DataBaseHelper(Context context) {
        super(context, NAME, FACTORY, VERSION);
    }

    private void load_data(SQLiteDatabase db){
        //ev1
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Proidiomas','DIR: Centro Plaza, Torre B, piso 6','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (1,1,'Centro Plaza', 10.497661, -66.846371)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname,cm_lastname, cm_text) VALUES (1,'Domingo','de Abreu', 'Al fin un lugar donde aprender idiomas en general')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (1,'Carlos','Aponte', 'Idiomaaaas!!!')");
        //ev2
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_url,ev_type) VALUES ('Cursos de Portugues','Quinta 65, 6ta Transversal con Av San Juan Bosco, Caracas\n" +
                "0212-2679107','Idioma','Cultura','www.icbv.org.ve','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (2,2,'Instituto Cultural Brasil-Venezuela', 10.504211, -66.851001)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (2,'Domingo', 'de Abreu', 'o melhor é aprender Português')");


        //ev3
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Cursos de Frances','Quinta Wilmarú Avenida Mohedano, Caracas\n" +
                "0212-2644611','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (3,3,'Alianza Francesa', 10.503536, -66.857572)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (3,'Gustavo','Ortega', 'un deux trois quatre cinq six sept huit neuf dix')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname,cm_text) VALUES (3,'Carlos','Aponte', 'omelet du fromage omelet du fromage omelet du fromage omelet du fromage')");

        //ev4
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Cursos de Aleman','Torre Altávila, Avenida Luis Roche\n" +
                "+ 58 212 814 3030','Idioma;','Cultura','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (4,4,'Goethe-Institut Caracas', 10.501405, -66.848544)");

        //ev5 PDVSA La estancia
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Yoga PDVSA La Estancia','Horarios\n" +
                "Martes: 5:30PM a 6:30PM\n" +
                "Sábados: 11:00AM a 12:00M\n" +
                "Para la mujer embarazada:\n" +
                "Jueves: 5:30PM a 6:30PM\n" +
                "Viernes: 11:00AM a 12:00M','Fitness;','Deporte','Movil')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (5,5,'PDVSA La Estancia', 10.495116, -66.847530)");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (5,'Karen','Troiano', 'La mejor forma de estirar los musculos y relajar el cuerpoi')");
        db.execSQL("INSERT INTO comment (cm_id_event, cm_firstname, cm_lastname, cm_text) VALUES (5,'Domingo','de Abreu', 'Vine gracias a una amiga, ahora no me quiero ir')");

        //ev6 Centro Cultura Cacao
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('CCS FORUM 2015','Larry Black\n" +
                "cssforum2015@gmail.com\n" +
                "Centro Cultural Chacao Av.Tamanaco. El Rosal','Ponencia;','Cultura','Movil')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (6,6,'Centro Cultural Chacao', 10.490290, -66.863381)");

        //ev7 Sala Cabrujas
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_dateEnd,ev_type) VALUES ('Charla sobre Festival Nuevas Bandas','Sala Cabrujas 7pm','Musica;Concierto','Cultura','07/07/2015','Movil')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (7,7,'Sala Cabrujas', 10.497285, -66.843743)");

        // Eugenio Mendoza
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (8,0,'Polideportivo Eugenio Medoza', 10.504332, -66.856002)");

        //ev8
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('FOTOMARATÓN ACNUR','El FOTOMARATÓN ACNUR,  es un concurso de fotografía, una actividad de entretenimiento dirigido a los aficionados de las fotos, donde deberán interpretar un tema a través de imágenes.\n" +
                "\n" +
                "Sin importar el grado de conocimiento que se posea del lenguaje fotográfico, el objetivo del FOTOMARATÓN es que puedan vivir la experiencia de retratar la ciudad y sus habitantes en el marco de una campaña en específico.\n" +
                "Punto de Salida: Plaza Miranda, Altamira','Carrera;Fotografia','Cultura','Movil')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (9,8,'Plaza Miranda', 10.504595, -66.851025)");

        //ev9
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type) VALUES ('Yoga Parque Miranda','Parque Miranda','Fitness','Deporte','Fijo')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (10,9,'Parque Miranda', 10.4941731, -66.8456668)");

        //ev10
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Festival nuevas Bandas','Homenajeando a los 25 años del Festival Nuevas Bandas, el Festival Nuevas Bandas llega cargado de actividades a desarrollarse en los espacios del Centro Cultural Chacao del 14 al 19 de julio','Festival;Musica','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,10,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev11
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Caracas Inmortal','Caracas inmortal es la continuidad de la investigación sobre la ciudad que ha venido desarrollando Javier León. En esta ocasión parte de una aproximación a la evolución desde sus orígenes, y desarrolla un cuerpo de obras amplio y puntual, constituido por pinturas, gráficas, fotografías y bocetos digitales, que el artista acompaña de una conferencia que complementa la proposición.','Arte;Pintura;Fotografia','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,11,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev12
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('De tango y filosofía','El profesor Alfredo Vallota expondrá la relación \"De tango y filosofía\" el 9 de julio\n" +
                "Para celebrar los cinco años de Filosofía en la ciudad y unirnos a la conmemoración del día de la Independencia de Argentina tendremos una exhibición de tango.','Arte;Baile','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,12,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev13
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Alfredo Naranjo y el Guajeo','El conocido y respetado internacionalmente grupo Alfredo Naranjo y Guajeo, en esta oportunidad van por el lado de la descarga, especial para el melómano y el bailador. A través de la improvisación los músicos de Guajeo demostrarán porque son tan solicitados y queridos  por el público, sus destrezas interpretativas se pondrán de manifiesto la tarde del viernes 10 de julio.','Musica','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,13,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev14
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('El Concurso Internacional de Ballet Clásico (CIBC-Caracas)','El Concurso Internacional de Ballet Clásico (CIBC-Caracas) es el único evento en su tipo en Venezuela y funciona con estándares similares a las más importantes competiciones de ballet a nivel mundial.','Baile','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,14,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev15
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('El Cocotero Mágico: Una Adaptación Tropical De La Leyenda De Las Habichuelas Mágicas (Taller-Montaje Infantil Teatro Abierto)'," +
                "'El pequeño Chuito tendrá la oportunidad, gracias al Cocotero Mágico, de descubrir un mundo fantástico: el Reino de las Siete Potencias. Allí se topará con un mundo de riquezas, con las cuales podrá encontrarle solución a todos sus problemas. Sin embargo, no es fácil tener tanto dinero repentinamente, una lección que Chuito tendrá que aprender. Esta historia fantástica, inspirada en la leyenda anglo-sajona de \"Las Habichuelas Mágicas\", nos llevará por hilarantes situaciones con personajes divertidos y dinámicos, una obra para todo público.'," +
                "'Teatro','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,15,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev16 contratiempo casa cultura
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Festival Caracas en Contratiempo','La tercera edición del Festival Caracas en Contratiempo es una vitrina que reúne a los mejores exponentes de las nuevas tendencias  de la música venezolana de vanguardia.  Esta fiesta reunirá en escena a más de 200 artistas, repartidos en 15 conciertos, talleres y conservatorios, gracias al respaldo de Pepsi, Telefónica|Movistar, Natuchips y el Centro Cultural Chacao','Musica;Festival','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,16,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev16 contratiempo torre bod
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (12,16,'Torre BOD', 10.4982346, -66.8527346)");

        //ev16 contratiempo plaza francia
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (13,16,'Plaza Francia', 10.4964012, -66.8489576)");

        //ev17
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Taller de Teatro Musical','El Taller de Teatro Musical con La Escuela Jr. consiste en brindar a niños y adolescentes un espacio para acercarse a la experiencia del teatro musical como disciplina integral en donde podrán tener en un mismo espacio clases de canto, baile y actuación todos en nivel de iniciación y atendido por profesionales en cada uno de los aspectos.','Teatro','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,17,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        //ev18
        db.execSQL("INSERT INTO event (ev_name,ev_description,ev_tags,ev_category,ev_type,ev_url) VALUES ('Proyecto Celuloide','Proyecto Celuloide es un programa de estudio de alto poder recreativo, diseñado para lograr el aprendizaje de las posibilidades que brinda el arte audiovisual para forjarse un mejor futuro y vivir experiencias gratificantes para el desarrollo personal y profesional de los jóvenes participantes. Durante este taller vacacional, los jóvenes asumirán el compromiso de realizar un cortometraje desde la creación de la idea hasta la proyección, contando con el apoyo y asesoría de profesionales del área.','Arte','Cultura','Movil','http://www.centroculturalchacao.com/')");
        db.execSQL("INSERT INTO place (pl_id,pl_id_event, pl_name, pl_latitude, pl_longitude) VALUES (11,18,'Centro Cultural de Chacao', 10.4902528, -66.8631565)");

        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (1,'new1','descripcion 1')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (2,'new2','descripcion 2')");
        db.execSQL("INSERT INTO new (new_id,new_name,new_description) VALUES (3,'new3','descripcion 3')");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_NEW);
        db.execSQL(CREATE_PHOTO);
        db.execSQL(CREATE_REPORT);
        db.execSQL(CREATE_PLACE);
        db.execSQL(CREATE_COMMENT);

        load_data(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS photo");
        db.execSQL("DROP TABLE IF EXISTS report");
        db.execSQL("DROP TABLE IF EXISTS place");
        db.execSQL("DROP TABLE IF EXISTS comment");

        //Se crea la nueva versión de la tabla
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


//    querys by event

/*    Insertar enventos
*            si es true el evento se inserto correctamente
*            si es false es que hubo un error
    */
    public Boolean insertEvent(Event e, Place p, List<Photo> photos){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean ret = true;
        long id = 0;
        ContentValues newRegistryEv = new ContentValues();
        ContentValues newRegistryPl = new ContentValues();
        ContentValues newRegistryPh = new ContentValues();
        newRegistryEv.put("ev_name",e.getName());
        newRegistryEv.put("ev_description",e.getDescription());
        newRegistryEv.put("ev_url",e.getUrl());
        newRegistryEv.put("ev_category",e.getCategory());
        newRegistryEv.put("ev_tags",e.getTags());
        newRegistryEv.put("ev_type",e.getType());
        newRegistryEv.put("ev_dateEnd",e.getDateEnd());
        newRegistryEv.put("ev_facebook",e.getFacebook());
        newRegistryEv.put("ev_twitter",e.getTwitter());
        newRegistryEv.put("ev_instagram", e.getInstagram());

        newRegistryPl.put("pl_name",p.getName());
        newRegistryPl.put("pl_description",p.getDescription());
        newRegistryPl.put("pl_url",p.getUrl());
        newRegistryPl.put("pl_latitude",p.getLatitude());
        newRegistryPl.put("pl_longitude",p.getLongitude());


        db.beginTransaction();
        try{
//            insertando evento
            id = db.insertOrThrow("event", null, newRegistryEv);
//          insertando lugar
            newRegistryPl.put("pl_id_event",id);
            db.insertOrThrow("place", null, newRegistryPl);
            //insertando foto(s)

            for(Photo ph : photos){
                newRegistryPh.put("pho_id_event", id);
                newRegistryPh.put("pho_name",ph.getName());
                newRegistryPh.put("pho_type",ph.getType());
                newRegistryPh.put("pho_url", ph.getUrl());
                db.insertOrThrow("photo",null,newRegistryPh);
            }

            db.setTransactionSuccessful();
        }catch(Exception err){
            err.printStackTrace();
            ret =false;

        }finally {
            db.endTransaction();
        }
        return ret;

    }

    public Boolean updateEvent(JSONArray args){
        Log.e(LOG, "Actualizando");
        return true;
    }
    public Boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String values[] = {Integer.toString(id)};
        boolean ret = true;
        db.beginTransaction();
        try {
//        eliminando de photo
            db.delete("photo", "pho_id_event=?", values);
//        eliminando de place
            db.delete("place", "pl_id_event=?", values);
//        eliminando evento
            db.delete("event", "id=?", values);
            db.setTransactionSuccessful();
        }catch(Exception err){
            err.printStackTrace();
            ret =false;

        }finally {
            db.endTransaction();
        }
        return ret;



    }

    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM event AS e, place AS p  WHERE e.ev_id =" + id+" AND p.pl_id_event = "+id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Event ev = new Event();
        ev.setId(c.getInt(c.getColumnIndex("ev_id")));
        ev.setName((c.getString(c.getColumnIndex("ev_name"))));
        ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
        ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
        ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
        ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
        ev.setType(c.getString(c.getColumnIndex("ev_type")));
        ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
        ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
        ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
        ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
        ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
        ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

        return ev;
    }

    public List<Event> getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getEventsByPlace (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT  * FROM event AS e, place AS p WHERE p.pl_id="+id+" AND p.pl_id_event = e.ev_id";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getBySearch(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event AND (e.ev_name LIKE ('%" +key
                +"%') OR e.ev_description LIKE ('%"+key+"%'))";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }

    public List<Event> getByTag(String tag){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Event> events = new ArrayList<Event>();

        String selectQuery = "SELECT * FROM event AS e, place AS p WHERE e.ev_id = p.pl_id_event AND e.ev_tags LIKE ('%" +tag
                +"%')";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Event ev = new Event();
                ev.setId(c.getInt(c.getColumnIndex("ev_id")));
                ev.setName((c.getString(c.getColumnIndex("ev_name"))));
                ev.setDescription((c.getString(c.getColumnIndex("ev_description"))));
                ev.setCategory(c.getString((c.getColumnIndex("ev_category"))));
                ev.setTags(c.getString((c.getColumnIndex("ev_tags"))));
                ev.setUrl(c.getString((c.getColumnIndex("ev_url"))));
                ev.setType(c.getString(c.getColumnIndex("ev_type")));
                ev.setDateEnd(c.getString(c.getColumnIndex("ev_dateEnd")));
                ev.setFacebook((c.getString(c.getColumnIndex("ev_facebook"))));
                ev.setTwitter((c.getString(c.getColumnIndex("ev_twitter"))));
                ev.setInstagram((c.getString(c.getColumnIndex("ev_instagram"))));
                ev.setLatitude(c.getFloat(c.getColumnIndex("pl_latitude")));
                ev.setLongitude(c.getFloat(c.getColumnIndex("pl_longitude")));

                events.add(ev);

            } while (c.moveToNext());
        }
        return events;
    }


//querys by comments

    public List<Comment> getCommentByEvent(int id_event){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Comment> comments = new ArrayList<Comment>();

        String selectQuery = "SELECT * FROM comment AS c WHERE c.cm_id_event = '" +id_event+"'" ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Comment cm = new Comment();
                cm.setId(c.getInt(c.getColumnIndex("cm_id")));
                cm.setFirstname((c.getString(c.getColumnIndex("cm_firstname"))));
                cm.setLastname((c.getString(c.getColumnIndex("cm_lastname"))));
                cm.setText((c.getString(c.getColumnIndex("cm_text"))));
                cm.setId_event(c.getInt((c.getColumnIndex("cm_id_event"))));
                cm.setPhoto(c.getString((c.getColumnIndex("cm_photo"))));

                comments.add(cm);

            } while (c.moveToNext());
        }
        return comments;
    }

//    querys by new
    public New getNew(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM new WHERE new_id =" + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        New news = new New();
        news.setId(c.getInt(c.getColumnIndex("new_id")));
        news.setName((c.getString(c.getColumnIndex("new_name"))));
        news.setDescription((c.getString(c.getColumnIndex("new_description"))));
        return news;

    }

    public List<New> getAllNews() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<New> new_list = new ArrayList<New>();

        String selectQuery = "SELECT  * FROM new";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                New news = new New();
                news.setId(c.getInt(c.getColumnIndex("new_id")));
                news.setName((c.getString(c.getColumnIndex("new_name"))));
                news.setDescription((c.getString(c.getColumnIndex("new_description"))));

                new_list.add(news);

            } while (c.moveToNext());
        }
        return new_list;
    }
}

