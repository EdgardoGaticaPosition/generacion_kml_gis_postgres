package generacion_kml_gis_postgres;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpURLConnection_json
{
    public String detete(final String url, final String parameters) {
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("DELETE");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200: {
                    return "ok";
                }
                case 201: {
                    return "error";
                }
            }
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(HttpURLConnection_json.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(HttpURLConnection_json.class.getName()).log(Level.SEVERE, null, ex2);
        }
        return null;
    }
    
    public String post(final String url, final String parameters) {
        final String salida = null;
        try {
            final byte[] bytes = parameters.getBytes();
            final URL u = new URL(url);
            final HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-length", "" + bytes.length);
            c.connect();
            final OutputStream out = c.getOutputStream();
            out.write(bytes);
            out.flush();
            final int status = c.getResponseCode();
            switch (status) {
                case 200: {
                    final BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
                }
                case 201: {
                    return "error";
                }
                
            }
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(HttpURLConnection_json.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(HttpURLConnection_json.class.getName()).log(Level.SEVERE, null, ex2);
        }
        return null;
    }
    
    public String get(final String url, final int timeout) {
        HttpURLConnection c = null;
        final String salida = null;
        try {
            final URL u = new URL(url);
            c = (HttpURLConnection)u.openConnection();
            c.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            c.setRequestProperty("Accept", "application/json;odata=verbose");
            c.setRequestMethod("GET");
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            final int status = c.getResponseCode();
            switch (status) {
                case 200: {
                    final BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
                }
                case 201: {
                    return salida;
                }
                case 500: {
                    final BufferedReader br2 = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    final StringBuilder sb2 = new StringBuilder();
                    String line2;
                    while ((line2 = br2.readLine()) != null) {
                        sb2.append(line2).append("\n");
                    }
                    br2.close();
                    return sb2.toString();
                }
                default: {
                    break;
                }
            }
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex2) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex2);
        }
        finally {
            if (c != null) {
                try {
                    c.disconnect();
                }
                catch (Exception ex3) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex3);
                }
            }
        }
        return null;
    }
}
