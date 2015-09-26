package sk.crud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sk.crud.sk.crud.Model.FuelModel;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fuel.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_FUEL = "CREATE TABLE " + FuelModel.TABLE + "("
                + FuelModel.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + FuelModel.KEY_amount + " REAL, "
                + FuelModel.KEY_km + " KM, "
                + FuelModel.KEY_date + " DATETIME )";

        db.execSQL(CREATE_TABLE_FUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + FuelModel.TABLE);

        // Create tables again
        onCreate(db);

    }
}