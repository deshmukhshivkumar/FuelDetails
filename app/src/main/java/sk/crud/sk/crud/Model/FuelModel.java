package sk.crud.sk.crud.Model;
import java.util.Calendar;
import java.util.Date;
public class FuelModel {

    public static final String TABLE = "fuel";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_amount = "amount";
    public static final String KEY_km = "km";
    public static final String KEY_date = "date";

    // property help us to keep data
    public int id;
    public float amount;
    public int km;
    public Calendar date;

}
