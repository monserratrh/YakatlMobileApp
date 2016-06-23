package ipn.cic.ci.yakatl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private TextView calidad;
    private GoogleMap googleMap;
    private LatLng currentLocation = null;
    private static final int DEFAULT_ZOOM_LEVEL = 19;
    private TextView sensor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        consultarSensores();

        currentLocation = new LatLng(19.503298, -99.147772);

        Spinner spinner = (Spinner) findViewById(R.id.spCountries);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.503298, -99.147772)).title("Sensor 1"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.503311, -99.147888)).title("Sensor 2"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.503039, -99.147858)).title("Sensor 3"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(DEFAULT_ZOOM_LEVEL)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    public void consultarSensores() {
        sensor1 = (TextView) findViewById(R.id.nivelSensor1);
        calidad = (TextView)findViewById(R.id.calidad);

        sensor1.setText(leerSensor(1));

        /*MODIFICAR */


        /*MODIFICAR */
    }

    public String leerSensor(int numSensor)
    {
        String aux = "";
        List<Sensor> lSensores = RestApi.consultarWebServiceSensor(numSensor,"es");

        boolean cambio = false;
        String calidadGlobal = "BUENA";

        for(Sensor sensor:lSensores){
            if(sensor.getContaminante().equals("TCA")){
                calidadGlobal += " " +sensor.getValor()+ "ºC";
            }
            else{

                if(!sensor.getCalidad().equals("BUENA") && cambio == false){
                    calidadGlobal = sensor.getCalidad();
                    cambio = true;
                }

                aux += "\n"+sensor.getContaminante()+": "+sensor.getValor()+" IMECAS";
            }


        }

        if(cambio == false){
            Bitmap bMap1 = BitmapFactory.decodeResource(getResources(), R.drawable.estudia);
            Bitmap bMap2 = BitmapFactory.decodeResource(getResources(), R.drawable.trabaja);
            ((ImageView) findViewById(R.id.actividades1)).setImageBitmap(bMap1);
            ((ImageView) findViewById(R.id.actividades2)).setImageBitmap(bMap2);

            ((TextView) findViewById(R.id.estudio)).setText("EL clima es perfecto para estudiar");
            ((TextView) findViewById(R.id.trabajo)).setText("El aire tiene la calidad optima para trabajar");
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
                .setContentDescription(calidad.getText().toString() +" \n"+sensor1.getText().toString())
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView sensor1 = (TextView) findViewById(R.id.nivelSensor1);
        calidad = (TextView)findViewById(R.id.calidad);

        sensor1.setText(leerSensor(position+1));

        LatLng location = null;

        switch (position+1){
            case 1:
                location = new LatLng(19.503298, -99.147772);
                break;
            case 2:
                location = new LatLng(19.503311, -99.147888);
                break;
            case 3:
                location = new LatLng(19.503039, -99.147858);
                break;
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(DEFAULT_ZOOM_LEVEL)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
