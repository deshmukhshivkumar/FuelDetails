package sk.crud.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

import sk.crud.R;
import sk.crud.db.FuelRepo;

public class MainActivity extends ListActivity implements android.view.View.OnClickListener {

    Button btnAdd,btnGetAll;
    TextView fuel_Id;

    @Override
    public void onClick(View view)
    {
        if (view == findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this, FuelDetails.class);
            intent.putExtra("id",0);
            startActivity(intent);

        }else {

            FuelRepo repo = new FuelRepo(this);

            ArrayList<HashMap<String, String>> fuelDetailsList =  repo.getFuelList();

            if(fuelDetailsList.size()!=0) {

                ListView listViewFuelDetails = getListView();

                listViewFuelDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        fuel_Id = (TextView) view.findViewById(R.id.fuel_Id);
                        String fuelDetailsId = fuel_Id.getText().toString();

                        Intent objIndent = new Intent(getApplicationContext(), FuelDetails.class);
                        objIndent.putExtra("fuel_Id", Integer.parseInt(fuelDetailsId));

                        startActivity(objIndent);
                    }
                });

                ListAdapter adapter =
                        new SimpleAdapter(
                                MainActivity.this,
                                fuelDetailsList,
                                R.layout.view_fuel_entry,
                                new String[] { "id","amount","date"},
                                new int[] {R.id.fuel_Id, R.id.fuel_amount, R.id.fuel_filling_date});


                setListAdapter(adapter);
            }else{
                Toast.makeText(this, "No Record!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
