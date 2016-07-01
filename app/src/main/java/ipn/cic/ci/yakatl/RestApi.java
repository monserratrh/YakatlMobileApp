/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.ci.yakatl;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sergio
 */
public class RestApi {
    
   /**
     * @param idSensor numero del sensor
     * @param idioma es para español y en para ingles
    */
    public static List<Sensor> consultarWebServiceSensor(Integer idSensor, String idioma){
        
        List<Sensor> lSensor = new ArrayList<Sensor>();
    
         try {
             // TODO code application logic here

             OkHttpClient client = new OkHttpClient();

             MediaType mediaType = MediaType.parse("application/octet-stream");
             Request request = new Request.Builder()
                     .url("http://airmx.net/webservice/ObtenerDatosMovil.php?numeroSensor=" + idSensor + "&idioma=" + idioma)
                     .get()
                     .addHeader("cache-control", "no-cache")
                     .addHeader("postman-token", "b983b2f6-8cd7-5956-32f5-bc7cf4e53b9f")
                     .build();


             JSONObject jsonObject = new RequestApi().execute(request).get();

             Integer estatus = jsonObject.getInt("estado");

             if (estatus == 1) { // exito
                 JSONArray values = jsonObject.getJSONArray("retorno");

                 for (int i = 0; i < values.length(); i++) {

                     JSONObject sensorApi = values.getJSONObject(i);
                     Sensor sensor = new Sensor();
                     sensor.setSensor(sensorApi.getString("sensor"));
                     sensor.setFecha(sensorApi.getString("fecha"));
                     sensor.setContaminante(sensorApi.getString("contaminante"));
                     sensor.setValor(sensorApi.getString("valor"));
                     sensor.setPuntos_imeca(sensorApi.getString("puntos_imeca"));
                     sensor.setCalidad(sensorApi.getString("calidad"));

                     lSensor.add(sensor);
                 }


             }

         }

         catch (JSONException ex) {
             ex.printStackTrace();
        } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }catch (Exception ex){
             ex.printStackTrace();
         }
        return lSensor;
    }


    public static Sensor consultarWebServicePrediccion(Integer numeroSensor){


        Sensor sensor = new Sensor();

        try {
            // TODO code application logic here

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/octet-stream");
            Request request = new Request.Builder()
                    .url("http://airmx.net/webservice/obtener_prediccion.php?sensor="+numeroSensor)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "b983b2f6-8cd7-5956-32f5-bc7cf4e53b9f")
                    .build();


            JSONObject jsonObject = new RequestApi().execute(request).get();

            Integer estatus = jsonObject.getInt("estado");

            if (estatus == 1) { // exito
                JSONArray values = jsonObject.getJSONArray("retorno");


                    JSONObject sensorApi = values.getJSONObject(0);
                    sensor.setContaminante(sensorApi.getString("contaminante"));
                    sensor.setPuntos_imeca(sensorApi.getString("imecas"));
                    sensor.setCalidad(sensorApi.getString("calidad"));

            }

        }

        catch (JSONException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sensor;
    }

    private static class RequestApi extends AsyncTask<Request, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Request... params) {
            try {

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(params[0]).execute();

                String string = response.body().string();
                JSONObject jsonObject = new JSONObject(string);

                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }






}

