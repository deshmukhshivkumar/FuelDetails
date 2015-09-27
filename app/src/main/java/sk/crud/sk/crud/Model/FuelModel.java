package sk.crud.sk.crud.Model;
import java.util.Calendar;
import java.util.Date;
public class FuelModel {

    public static final String TABLE = "fuel";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_amount = "amount";
    public static final String KEY_km = "odometerKm";
    public static final String KEY_date = "fuelAddedDate";
    public static final String KEY_quantity = "quantityInLiters";
    // property help us to keep data
    public int id;
    public float quantityInLiters;
    public float amount;
    public int odometerKm;
    public Calendar fuelAddedDate;

}
