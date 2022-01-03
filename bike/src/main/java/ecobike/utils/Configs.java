package ecobike.utils;

public class Configs {
    public static final String MONGODB_URI      =   "mongodb+srv://" +
                                                    "<username>" +
                                                    ":" +
                                                    "<password>" +
                                                    "@cluster0.zezkr.mongodb.net/" +
                                                    "myFirstDatabase?" +
                                                    "retryWrites=true&" +
                                                    "w=majority";

    public static final String DATABASE_NAME    =   "<database-name>";
    public static final String COLLECTION_NAME  =   "<collection-name>";
    public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";

    public static final String[] BIKE_TYPES = {"standard-bike", "standard-e-bike", "twin-bike"};

    public static final String IMAGE_PATH = "/images";

    public static final String BIKE_DETAIL_PATH = "/fxml/bike/bike-detail.fxml";
    public static final String DOCK_LIST_PATH = "/fxml/dock/dock-list.fxml";
    public static final String DOCK_DETAIL_PATH = "/fxml/dock/dock-detail.fxml";
    public static final String RENT_BIKE_PATH = "/fxml/rental/rent-bike.fxml";
    public static final String RENTAL_STATUS_PATH = "/fxml/rental/rental-status.fxml";
    public static final String RETURN_BIKE_PATH = "/fxml/rental/select-return-dock.fxml";
    public static final String PAYMENT_FORM_PATH = "/fxml/payment/payment-form.fxml";
    public static final String PAYMENT_FAILURE_PATH = "/fxml/payment/payment-failure.fxml";
    public static final String PAYMENT_SUCCESS_PATH = "/fxml/payment/payment-success.fxml";
    public static final String PAYMENT_RENT_SUCCESS_PATH = "/fxml/payment/payment-rent-success.fxml";
    public static final String POPUP_PATH = "/fxml/popup.fxml";
    public static final String LOGIN_PATH = "/fxml/auth/login.fxml";
}
