package app.eatit.appeatit.Utils;

/**
 * Created by Tobias on 07/07/2016.
 */
public class DatabaseHelper {

    private static DatabaseHelper ourInstance = new DatabaseHelper();
    public static DatabaseHelper getInstance() {
        return ourInstance;
    }

    private DatabaseHelper() {
    }

    public static final String CARDSERVICE = "http://www.acesolutions.com.br/Appeatit/services/credit_card.php";
    public static final String BOOKINGSERVICE = "http://www.acesolutions.com.br/Appeatit/services/booking.php";


}
