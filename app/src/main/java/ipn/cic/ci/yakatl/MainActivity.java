package ipn.cic.ci.yakatl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView calidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        consultarSensores();

    }

    public void consultarSensores() {
        TextView sensor1 = (TextView) findViewById(R.id.nivelSensor1);
        TextView sensor2 = (TextView) findViewById(R.id.nivelSensor2);
        TextView sensor3 = (TextView) findViewById(R.id.nivelSensor3);
        calidad = (TextView)findViewById(R.id.calidad);

        sensor1.setText(leerSensor(1));
        sensor2.setText(leerSensor(2));
        sensor3.setText(leerSensor(3));

        /*MODIFICAR */


        /*MODIFICAR */
    }

    public String leerSensor(int numSensor)
    {
        String aux = "";
        List<Sensor> lSensores = RestApi.consultarWebServiceSensor(numSensor,"es");

        String calidadGlobal = "BUENA";

        for(Sensor sensor:lSensores){
            aux += "\n"+sensor.getContaminante()+": "+sensor.getValor()+ (sensor.getContaminante().equals("TCA") ? "° C":  " calidad " + sensor.getCalidad());
            if( sensor.getCalidad() != null && !(sensor.getCalidad().equals("BUENA") || sensor.getCalidad().equals("GOOD"))){
                calidadGlobal = sensor.getCalidad();
            }
        }

        calidad.setText("\nLa calidad del aire es: \n"+calidadGlobal);


        return aux;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                consultarSensores();
                return true;
            case R.id.action_share_fb:
                compartirFb();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void compartirFb() {
        // compartir una imagen

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://airmx.net"))
                .setContentTitle("La calidad del aire por Yakatl")
                .setContentDescription(calidad.getText().toString())
                .setImageUrl(Uri.parse("http://airmx.net/img/profile.png"))
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(MainActivity.this, "Calidad del aire actual compartida", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Por ahora el servicio no esta disponible intenta mas tarde", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });

        if(ShareDialog.canShow((ShareLinkContent.class))){
            shareDialog.show(content);
        }


    }



}
