package sk.crud.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sk.crud.sk.crud.Model.FuelModel;

public class FuelRepo {
    
    private DBHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FuelRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(FuelModel fuelModel) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(FuelModel.KEY_ID, fuelModel.id);
        values.put(FuelModel.KEY_amount,fuelModel.amount);
        values.put(FuelModel.KEY_km, fuelModel.km);


        values.put(FuelModel.KEY_date, sdf.format(fuelModel.date));

        // Inserting Row
        long id = db.insert(FuelModel.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) id;
    }

    public void delete(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(FuelModel.TABLE, FuelModel.KEY_ID + "= ?", new String[] { String.valueOf(id) });
        db.close(); // Closing database connection
    }

    public void update(FuelModel fuelModel) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FuelModel.KEY_amount,fuelModel.amount);
        values.put(FuelModel.KEY_km, fuelModel.km);
        values.put(FuelModel.KEY_date, fuelModel.date.toString());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(FuelModel.TABLE, values, FuelModel.KEY_ID + "= ?", new String[] { String.valueOf(fuelModel.id) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getFuelList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        double floatAmount = 0.0;

        String selectQuery =  "SELECT  " +
                FuelModel.KEY_ID + "," +
                FuelModel.KEY_amount + "," +
                FuelModel.KEY_km + "," +
                FuelModel.KEY_date +
                " FROM " + FuelModel.TABLE;

        //FuelModel student = new FuelModel();
        ArrayList<HashMap<String, String>> fuelDetailsList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> fuelModelHashMap = new HashMap<>();
                fuelModelHashMap.put("id", cursor.getString(cursor.getColumnIndex(FuelModel.KEY_ID)));

                floatAmount = cursor.getFloat(cursor.getColumnIndex(FuelModel.KEY_amount));
                fuelModelHashMap.put("amount", Double.toString(floatAmount));

                fuelModelHashMap.put("date", cursor.getString(cursor.getColumnIndex(FuelModel.KEY_date)));
                fuelDetailsList.add(fuelModelHashMap);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return fuelDetailsList;
    }

    public FuelModel getFuelDetailsById(int Id)  {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                FuelModel.KEY_ID + "," +
                FuelModel.KEY_amount + "," +
                FuelModel.KEY_km + "," +
                FuelModel.KEY_date +
                " FROM " + FuelModel.TABLE
                + " WHERE " +
                FuelModel.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        FuelModel fuelModel = new FuelModel();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                fuelModel.id =cursor.getInt(cursor.getColumnIndex(FuelModel.KEY_ID));
                fuelModel.amount =cursor.getFloat(cursor.getColumnIndex(FuelModel.KEY_amount));
                fuelModel.km  =cursor.getInt(cursor.getColumnIndex(FuelModel.KEY_km));
                String date =  cursor.getString(cursor.getColumnIndexOrThrow(fuelModel.KEY_date));
                try {
                    fuelModel.date.setTime(sdf.parse(date));
                }
                catch(ParseException p)
                {

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return fuelModel;
    }

}