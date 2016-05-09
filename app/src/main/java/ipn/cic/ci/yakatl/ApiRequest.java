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


        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"card\":{\n      \"card_number\":\"4111111111111111\",\n      \"holder_name\":\"Juan Perez Ramirez\",\n      \"expiration_year\":\"20\",\n      \"expiration_month\":\"12\",\n      \"cvv2\":\"110\"\n    },\n    \"plan_id\":\"pzxtp8o88pipie9tfmps\"\n}");
        Request request = new Request.Builder()
                .url("http://localhost/MonitoreoContaminantesCIC-Yakatl/webservice/obtener_imecas.php?numeroSensor=-1&numeroElementos=-1&ordenamiento=mayor&filtro=")
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "a7ea2fb0-a2a4-9eda-a9db-a74d300c67c2")
                .build();


       /* try {

            JSONObject response = new RequesApi().execute(request).get();
            //String retorno = response.getString("retorno");

             JSONObject jsonObject = new JSONObject(response.getString("retorno"));

            String id_wasp =     jsonObject.getString("id");


            return new String();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
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
