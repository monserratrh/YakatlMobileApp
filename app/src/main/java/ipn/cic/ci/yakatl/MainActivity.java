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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        consultarSensores();

        }

    public void consultarSensores() {
        TextView sensores = (TextView) findViewById(R.id.nivelSensores);
        String aux = "\n" +String.valueOf(leerSensor(1)) + "\n" + String.valueOf(leerSensor(2)) + "\n" + String.valueOf(leerSensor(3) + "\n");
        sensores.setText(aux);

        /*MODIFICAR */

        TextView calidad = (TextView)findViewById(R.id.calidad);
        calidad.setText("\nLa calidad del aire es: \n BUENA\n");

        /*MODIFICAR */
    }

    public char[] leerSensor(int numSensor)
    {
        List<Sensor> sensor = RestApi.consultarWebServiceSensor(numSensor,0);
        int i = sensor.size()-1;
        return ((sensor.get(i).getSensor() + ": "+ sensor.get(i).getValue()).toCharArray());
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
                Toast.makeText(getApplicationContext(), "SHARE ON FB", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
