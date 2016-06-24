package ipn.cic.ci.yakatl;

import android.content.pm.PackageManager;
<<<<<<< HEAD
import android.location.LocationListener;
import android.location.LocationManager;
=======
import android.content.pm.Signature;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
>>>>>>> origin/master
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.ViewGroup;
import android.widget.RelativeLayout;
=======
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
>>>>>>> origin/master
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
<<<<<<< HEAD
=======
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
>>>>>>> origin/master
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Random;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private TextView calidad;
=======
public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private TextView calidad;
    private GoogleMap googleMap;
    private LatLng currentLocation = null;
    private static final int DEFAULT_ZOOM_LEVEL = 19;
    private TextView sensor1;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
<<<<<<< HEAD
=======
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
>>>>>>> origin/master

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        OnMapReadyCallback maprcallback = this;
        mapFragment.getMapAsync(maprcallback);

        setSupportActionBar(toolbar);
        consultarSensores();

    }

<<<<<<< HEAD
=======
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
>>>>>>> origin/master


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Interiores
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.50305715, -99.14784282)).title("Sensor 1"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.50311277, -99.147816)).title("Sensor 3"));
        //Exterior
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.50327459, -99.14803058)).title("Sensor 2"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(19.50342881, -99.14703058))
                .zoom(13)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
    public String leerSensor(int numSensor)
    {
        String aux = "";
        List<Sensor> lSensores = RestApi.consultarWebServiceSensor(numSensor,"es");

        String calidadGlobal = "BUENA";

<<<<<<< HEAD
        for(Sensor sensor:lSensores){
            aux += "\n"+sensor.getContaminante() + " : "+sensor.getValor()+ (sensor.getContaminante().equals("TCA") ? "°C":"");
            if( sensor.getCalidad() != null && !(sensor.getCalidad().equals("BUENA") || sensor.getCalidad().equals("GOOD"))){
                calidadGlobal = sensor.getCalidad();
            }
        }
        calidad.setText("\nLa calidad del aire es: \n" + calidadGlobal);
        return aux;
    }
    private void consultarSensores() {
        TextView sensor1 = (TextView) findViewById(R.id.nivelSensor1);
        TextView sensor2 = (TextView) findViewById(R.id.nivelSensor2);
        TextView sensor3 = (TextView) findViewById(R.id.nivelSensor3);
        calidad = (TextView)findViewById(R.id.calidad);
        sensor1.setText(leerSensor(1));
        sensor2.setText(leerSensor(2));
        sensor3.setText(leerSensor(3));
=======
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

>>>>>>> origin/master
    }
}
