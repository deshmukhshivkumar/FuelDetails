package sk.crud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FuelDetails extends AppCompatActivity implements View.OnClickListener {

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextAmount;
    EditText editTextKm;
    EditText editTextDate;
    private int     _Fuel_Details_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_details);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextAmount = (EditText) findViewById(R.id.txtEditAmount);
        editTextKm = (EditText) findViewById(R.id.txtEditKmReading);
        //editTextDate = (EditText) findViewById(R.id.dtpDate);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fuel_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            FuelRepo repo = new FuelRepo(this);
            FuelModel fuelModel = new FuelModel();
            fuelModel.amount= Float.parseFloat(editTextAmount.getText().toString());
            fuelModel.km= Integer.parseInt(editTextKm.getText().toString());
            //fuelModel.date=editTextName.getText().toString();
            fuelModel.id=_Fuel_Details_Id;

            if (_Fuel_Details_Id==0){
                _Fuel_Details_Id = repo.insert(fuelModel);

                Toast.makeText(this, "New Student Insert", Toast.LENGTH_SHORT).show();
            }else{

                repo.update(fuelModel);
                Toast.makeText(this,"Student Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            FuelRepo repo = new FuelRepo(this);
            repo.delete(_Fuel_Details_Id);
            Toast.makeText(this, "Fuel Details Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }
    }
}
