package sk.crud.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import sk.crud.R;
import sk.crud.db.FuelRepo;
import sk.crud.sk.crud.Model.FuelModel;

public class FuelDetails extends AppCompatActivity implements View.OnClickListener {

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextAmount;
    EditText editTextKm;
    EditText editTextQuantity;
    DatePicker editTextDate;
    private int _Fuel_Details_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_details);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextAmount = (EditText) findViewById(R.id.txtEditAmount);
        editTextKm = (EditText) findViewById(R.id.txtEditKmReading);
        editTextDate = (DatePicker) findViewById(R.id.dtpDate);
        editTextQuantity = (EditText) findViewById(R.id.txtEditQuantity);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        _Fuel_Details_Id = 0;
        Intent intent= getIntent();
        _Fuel_Details_Id = intent.getIntExtra("fuel_Id", 0);

        FuelRepo repo = new FuelRepo(this);

        if(_Fuel_Details_Id != 0) {
            FuelModel fuelModel = repo.getFuelDetailsById(_Fuel_Details_Id);
            editTextAmount.setText(String.valueOf(fuelModel.amount));
            editTextKm.setText(String.valueOf(fuelModel.odometerKm));
            editTextQuantity.setText(String.valueOf(fuelModel.quantityInLiters));

            if (fuelModel.fuelAddedDate != null) {
                int year = fuelModel.fuelAddedDate.get(Calendar.YEAR);
                int month = fuelModel.fuelAddedDate.get(Calendar.MONTH);
                int day = fuelModel.fuelAddedDate.get(Calendar.DAY_OF_MONTH);
                editTextDate.updateDate(year, month, day);
            }
        }
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
            fuelModel.odometerKm = Integer.parseInt(editTextKm.getText().toString());
            fuelModel.quantityInLiters = Float.parseFloat(editTextQuantity.getText().toString());

            int day = editTextDate.getDayOfMonth();
            int month = editTextDate.getMonth() + 1;
            int year = editTextDate.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH, day);

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fuelModel.fuelAddedDate =  calendar;

            fuelModel.id =_Fuel_Details_Id;

            if (_Fuel_Details_Id==0){
                _Fuel_Details_Id = repo.insert(fuelModel);

                Toast.makeText(this, "New Record Inserted", Toast.LENGTH_SHORT).show();
            }else{

                repo.update(fuelModel);
                Toast.makeText(this,"Record updated",Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

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
