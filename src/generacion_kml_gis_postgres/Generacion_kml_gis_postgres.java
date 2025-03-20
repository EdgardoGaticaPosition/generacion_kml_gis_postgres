/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generacion_kml_gis_postgres;



/**
 *
 * @author egatica
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*; 
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class Generacion_kml_gis_postgres {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        final HttpURLConnection_json js = new HttpURLConnection_json();
        JSONObject json;
        // TODO code application logic here
        ResultSet regi_postgree;
        String nombreArchivo; // Aqui se le asigna el nombre y 
        
        Class.forName("org.postgresql.Driver");
        //java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://"+conf.ipc_post+"/"+conf.nbd_post+"", ""+conf.use_post+"", ""+conf.cla_post+"");
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:6666/position", "DBPosition", "1234Position")) {
            Statement postgree = conn.createStatement();
            regi_postgree = postgree.executeQuery("select nombre,color,st_astext(datos_geo) as latlong,vel from gis.poligono pol inner join gis.pst_capa_poligono cpol on cpol.id_capa = pol.id_capa where pol.id_capa =13105 and pol.activo=1"); 

            //ssh -i "Edhardo.Gatica.pem" -L 6666:dbposition-plataforma3.c54k6thwy5yi.us-east-1.rds.amazonaws.com:5432 ec2-user@35.168.240.65      
            //ssh -i "Edgardo.Gatica.pem" -L 4444:dbparameter.c54k6thwy5yi.us-east-1.rds.amazonaws.com:3306 ec2-user@35.168.240.65
            //ssh -i "Edgardo.Gatica.pem" -L 3333:dbcontenedor.c54k6thwy5yi.us-east-1.rds.amazonaws.com:3306 ec2-user@35.168.240.65
            //ssh -i "Edgardo.Gatica.pem" -L 5555:db-226.c54k6thwy5yi.us-east-1.rds.amazonaws.com:3306 ec2-user@35.168.240.65 
            String val_elevation="";
            nombreArchivo="poligono_AGUNSA_RYCV19.kml";
            FileWriter fw ;
            fw = new FileWriter(nombreArchivo);
            PrintWriter salArch;
/*en  el tag  <altitudeMode>clampToGround</altitudeMode> que quede asi
y agrega antes  de ese <altitude>2500</altitude>      */      
            String kml_ouput_ini="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
"<Document>\n" +
"	<name>"+nombreArchivo+"</name>\n" +
"	<open>1</open>\n" +
"	<StyleMap id=\"failed\">\n" +
"		<Pair>\n" +
"			<key>normal</key>\n" +
"			<styleUrl>#failed1</styleUrl>\n" +
"		</Pair>\n" +
"		<Pair>\n" +
"			<key>highlight</key>\n" +
"			<styleUrl>#failed0</styleUrl>\n" +
"		</Pair>\n" +
"	</StyleMap>\n" +
"	<Style id=\"failed0\">\n" +
"		<PolyStyle>\n" +
"			<color>ffff0055</color>\n" +
"		</PolyStyle>\n" +
"	</Style>\n" +
"	<Style id=\"sn_ylw-pushpin\">\n" +
"		<BalloonStyle>\n" +
"		</BalloonStyle>\n" +
"	</Style>\n" +
"	<StyleMap id=\"msn_ylw-pushpin\">\n" +
"		<Pair>\n" +
"			<key>normal</key>\n" +
"			<styleUrl>#sn_ylw-pushpin</styleUrl>\n" +
"		</Pair>\n" +
"		<Pair>\n" +
"			<key>highlight</key>\n" +
"			<styleUrl>#sh_ylw-pushpin</styleUrl>\n" +
"		</Pair>\n" +
"	</StyleMap>\n" +
"	<Style id=\"failed1\">\n" +
"		<PolyStyle>\n" +
"			<color>ffff0055</color>\n" +
"		</PolyStyle>\n" +
"	</Style>\n" +
"	<StyleMap id=\"mfailed\">\n" +
"		<Pair>\n" +
"			<key>normal</key>\n" +
"			<styleUrl>#failed1</styleUrl>\n" +
"		</Pair>\n" +
"		<Pair>\n" +
"			<key>highlight</key>\n" +
"			<styleUrl>#sh_ylw-pushpin0</styleUrl>\n" +
"		</Pair>\n" +
"	</StyleMap>\n" +
"	<Style id=\"sh_ylw-pushpin\">\n" +
"		<IconStyle>\n" +
"			<scale>1.2</scale>\n" +
"		</IconStyle>\n" +
"		<BalloonStyle>\n" +
"		</BalloonStyle>\n" +
"	</Style>\n" +
"	<Style id=\"sh_ylw-pushpin0\">\n" +
"		<IconStyle>\n" +
"			<scale>1.2</scale>\n" +
"		</IconStyle>\n" +
"		<BalloonStyle>\n" +
"		</BalloonStyle>\n" +
"		<PolyStyle>\n" +
"			<color>ffff0055</color>\n" +
"		</PolyStyle>\n" +
"	</Style>\r\n";
            
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                salArch = new PrintWriter(bw);
                salArch.print(kml_ouput_ini);
                
                // inicio lineas para leer archivo texto      
                //final String path = new File(".").getCanonicalPath();
                //final File f = new File(path + "/poligono_ELECCON.csv");
                //final BufferedReader entrada = new BufferedReader(new FileReader(f));
                //if (f.exists()) {
                //String[] linea_new;
                //String line;
                //int linea_n=0;
                //while ((line = entrada.readLine()) != null) {    
                //    linea_new=line.split(";");
                //    linea_n++;
                //    System.out.println("Numero:" + linea_n);
                //    String nombre_local=linea_new[0];
                //    String color_local=linea_new[1];
                //    String latlong_local=linea_new[2];
                //    String vel_local="";                
                // fin lineas para leer archivo texto 
                
                // linea para leer datos base de datos
                while (regi_postgree.next()) {
                    // la extension al archivo
                    String nombre_local=regi_postgree.getString("nombre");
                    String color_local=regi_postgree.getString("color");
                    String latlong_local=regi_postgree.getString("latlong");
                    String vel_local=regi_postgree.getString("vel");
                    // fin lineas para leer datos base de datos

                    latlong_local=latlong_local.replace("POLYGON((", "");

                    latlong_local=latlong_local.replace("))", "");

                    latlong_local=latlong_local.replace(" ", ",");
                    // solo para texto
                    latlong_local=latlong_local.replace("|", ",");

                    String[] parts_latlong = latlong_local.split(",");
                    latlong_local="";

                    //
                    for(int x=0; x <= parts_latlong.length-1 ; x+=2) {
                        /*if (x==0) {
                            //(girar lat long 
                            String param_api_open_elevation= "locations="+parts_latlong[x].substring(0, parts_latlong[x].length()-5)+","+ parts_latlong[x+1].substring(0, parts_latlong[x+5].length()-1);
                            //String param_api_open_elevation= "locations="+parts_latlong[x+1]+","+ parts_latlong[x];
                            String resp_api_open_elevation = js.get("https://api.open-elevation.com/api/v1/lookup?"+param_api_open_elevation, 20000);
                            val_elevation="";
                            System.out.println(nombre_local);
                            System.out.println("https://api.open-elevation.com/api/v1/lookup?"+param_api_open_elevation);

                            json = new JSONObject(resp_api_open_elevation);

                            System.out.println("json =:"+resp_api_open_elevation);
                            if (existe_json_str(json,"elevation") && existe_json_str(json,"results")){
                                final JSONArray data = json.getJSONArray("results");
                                final JSONObject dato = data.getJSONObject(0);
                                final Object objet_eleva = dato.get("elevation");
                                val_elevation = (String) objet_eleva.toString();
                            }


                        }*/
                        
                        //(girar lat long 
                        latlong_local=latlong_local + parts_latlong[x+1]+","+parts_latlong[x] + ",0\r\n";
                        //latlong_local=latlong_local + parts_latlong[x]+","+parts_latlong[x] + ",0\r\n";
                    }
                    String kml_ouput =  "<Placemark>\r\n";
                    kml_ouput = kml_ouput + "<name>" + nombre_local + "</name>\r\n";
                    kml_ouput = kml_ouput + "<description>Velocidad máxima:"+ vel_local +" Km/h</description>\r\n";
                    kml_ouput = kml_ouput + "<Style id=\"transBluePoly\">\r\n";
                    kml_ouput = kml_ouput + "<LineStyle>\r\n";
                    kml_ouput = kml_ouput + "<width>2</width>\r\n";
                    kml_ouput = kml_ouput + "<color>90ffffff</color>\r\n";
                    kml_ouput = kml_ouput + "</LineStyle>\r\n";
                    kml_ouput = kml_ouput + "<PolyStyle>\r\n";
                    kml_ouput = kml_ouput + "<color>ff" + color_local + "</color>\r\n";

                    kml_ouput = kml_ouput + "</PolyStyle>\r\n";
                    kml_ouput = kml_ouput + "</Style>\r\n";
                    kml_ouput = kml_ouput + "<Polygon>\r\n";
                    kml_ouput = kml_ouput + "<extrude>1</extrude>\r\n";

                    kml_ouput = kml_ouput + "<altitudeMode>clampToGround</altitudeMode>\r\n";
                    kml_ouput = kml_ouput + "<altitude>" + val_elevation + "</altitude>";

                    kml_ouput = kml_ouput + "<outerBoundaryIs>\r\n";
                    kml_ouput = kml_ouput + "<LinearRing>\r\n";
                    kml_ouput = kml_ouput + "<coordinates>\r\n";
                    kml_ouput = kml_ouput + latlong_local ;
                    kml_ouput = kml_ouput + "</coordinates>\r\n";
                    kml_ouput = kml_ouput + "</LinearRing>\r\n";
                    kml_ouput = kml_ouput + "</outerBoundaryIs>\r\n";
                    kml_ouput = kml_ouput + "</Polygon>\r\n";
                    kml_ouput = kml_ouput + "</Placemark>\r\n";
                    System.out.println("kml = "+kml_ouput);
                    salArch.print(kml_ouput);

                }
                String kml_ouput_fin="</Document>\n" +
                                      "</kml>\r\n";
                salArch.print(kml_ouput_fin);
                salArch.close();                
            }
    

               
        }catch (IOException ex) {
            System.out.println("Error "+ex);
        }  
        //fw.close();
        //regi_postgree.close(); 
    }
    public static boolean existe_json_str(JSONObject data, String find_str){
        boolean existe=false;
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            //Object value = data.get(key);
            //System.out.printf("%s=%s (%s)\n", key, value, value.getClass().getSimpleName());
            if (find_str.equals((String)key)){
                existe=true;
                //System.out.printf("%s=%s (%s)\n", key, value, value.getClass().getSimpleName());
            }
        }
        return existe;
    }    
}
