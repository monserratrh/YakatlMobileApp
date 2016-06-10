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
        TextView sensor1 = (TextView) findViewById(R.id.nivelSensor1);
        TextView sensor2 = (TextView) findViewById(R.id.nivelSensor2);
        TextView sensor3 = (TextView) findViewById(R.id.nivelSensor3);

        sensor1.setText(leerSensor(1));
        sensor2.setText(leerSensor(2));
        sensor3.setText(leerSensor(3));

        /*MODIFICAR */

        TextView calidad = (TextView)findViewById(R.id.calidad);
        calidad.setText("\nLa calidad del aire es: \n BUENA\n");

        /*MODIFICAR */
    }

    public String leerSensor(int numSensor)
    {
        List<Sensor> sensor = RestApi.consultarWebServiceSensor(numSensor,1);
        String aux="";
        if(numSensor == 1)
        {
            for(int i=0; i<6; i++) {
                if(i%2 == 1)
                    aux += "\t\t\t\t";
                else
                    aux += "\n";
                aux += sensor.get(i).getSensor() + ": " + sensor.get(i).getValue();
            }
        }
        else
        {
            for(int i=0; i<4; i++) {
                if(i%2 == 1)
                    aux += "\t\t\t\t";
                else
                    aux += "\n";
                aux += sensor.get(i).getSensor() + ": " + sensor.get(i).getValue();
            }
        }
        return aux+"\n";
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
