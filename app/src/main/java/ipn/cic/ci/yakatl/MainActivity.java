package ipn.cic.ci.yakatl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        // Mandar a llamar el api ejemplo
        List<Sensor> sensor1 = RestApi.consultarWebServiceSensor(1,0);
        List<Sensor> sensor2 = RestApi.consultarWebServiceSensor(2,0);
        List<Sensor> sensor3 = RestApi.consultarWebServiceSensor(2,0);

        for(int i = 0; i< sensor1.size(); i++) {
                String[] arreglo = {
                        sensor1.get(i).getId_wasp(),
                        sensor1.get(i).getSensor(),
                        sensor1.get(i).getValue(),
                        sensor1.get(i).getTimestamp()
                };

                Toast.makeText(this,arreglo[0], Toast.LENGTH_SHORT).show();
                Toast.makeText(this,arreglo[1], Toast.LENGTH_SHORT).show();
                Toast.makeText(this,arreglo[2], Toast.LENGTH_SHORT).show();
                Toast.makeText(this,arreglo[3], Toast.LENGTH_SHORT).show();

                break;
            }

        }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
