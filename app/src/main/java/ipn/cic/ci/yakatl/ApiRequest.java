package ipn.cic.ci.yakatl;
import android.os.AsyncTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by loajrla on 06/03/16.
 */
public class ApiRequest {


    public static String consumirAPiSelectAll(){


        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://localhost/MonitoreoContaminantesCIC-Yakatl/webservice/obtener_imecas.php?numeroSensor=-1&numeroElementos=-1&ordenamiento=mayor&filtro=")
                .get()
                .addHeader("authorization", "Basic c2tfNzUwNmI4MTgzYmMzNGUwMzhlZTllODQ5ZTJlNTI5OTQ6Og==")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "b19eab66-f5c8-6695-2ad5-fe5641d89ec5")
                .build();



        try {

            JSONObject response = new RequesApi().execute(request).get();
            JSONObject jsonObject = new JSONObject(response.getString("retorno"));


            return new String();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new String();

    }

    // conexion a internet
    private static class RequesApi extends AsyncTask<Request, Void, JSONObject>{
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
