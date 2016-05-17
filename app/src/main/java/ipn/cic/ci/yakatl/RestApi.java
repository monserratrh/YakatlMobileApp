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
     * @param tipoOrdenamiento 1 si se desea ordenar del dato mas reciente al mas antiguo, 0 en otro caso
    */
    public static List<Sensor> consultarWebServiceSensor(Integer idSensor, Integer tipoOrdenamiento){
        
        List<Sensor> lSensor = new ArrayList<Sensor>();
    
         try {
             // TODO code application logic here

             OkHttpClient client = new OkHttpClient();

             MediaType mediaType = MediaType.parse("application/octet-stream");
             Request request = new Request.Builder()
                     .url("http://airmx.net/webservice/ObtenerDatos.php?numeroSensor=" + idSensor + "&ordenamiento=" + tipoOrdenamiento)
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
                     sensor.setId_wasp(sensorApi.getString("id_wasp"));
                     sensor.setSensor(sensorApi.getString("sensor"));
                     sensor.setValue(sensorApi.getString("value"));
                     sensor.setTimestamp(sensorApi.getString("timestamp"));

                     lSensor.add(sensor);
                 }


             }

         }catch (JSONException ex) {
             ex.printStackTrace();
        } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }
        return lSensor;
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

