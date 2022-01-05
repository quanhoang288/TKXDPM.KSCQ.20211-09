package ecobike.subsystem.interbank;

import ecobike.common.exception.*;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.Configs;
import ecobike.utils.MyMap;
import ecobike.utils.Utils;

import java.util.Map;

/**
 * Controller class for creating and sending request to API for payment-related transactions
 */
public class InterbankSubsystemController {

    private static final String PUBLIC_KEY = "A5NouKUnbP8=";
    private static final String SECRET_KEY = "Bb/W/eu7ZdY=";
    private static final String PAY_COMMAND = "pay";
    private static final String REFUND_COMMAND = "refund";
    private static final String VERSION = "1.0.1";

    /**
     * Instance of boundary class for communicating directly with API
     */
    private static InterbankBoundary interbankBoundary = new InterbankBoundary();

    /**
     * Create and send request for refund transaction
     * Process and reformat API response afterward
     * @param card information of user credit card
     * @param amount amount of money to perform transaction
     * @param contents description of transaction
     * @return {@link MyMap} object containing transaction response
     * @throws PaymentException if API responded with an error code
     * @throws UnrecognizedException if error code is not recognizable
     */
    public MyMap performRefund(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        Map<String, Object> transaction = new MyMap();

        try {
            transaction.putAll(MyMap.toMyMap(card));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new InvalidCardException();
        }
        transaction.put("command", REFUND_COMMAND);
        transaction.put("transactionContent", contents);
        transaction.put("amount", amount);
        transaction.put("createdAt", Utils.getToday());

        Map<String, Object> requestMap = new MyMap();
        requestMap.put("secretKey", SECRET_KEY);
        requestMap.put("version", VERSION);
        requestMap.put("transaction", transaction);
        requestMap.put("appCode", PUBLIC_KEY);

        requestMap.put("hashCode", Utils.md5(((MyMap) requestMap).toJSON()));
        requestMap.remove("secretKey");

        System.out.println("REQUEST JSON: " + ((MyMap) requestMap).toJSON());

        String responseText = interbankBoundary.query(Configs.PROCESS_TRANSACTION_URL, ((MyMap) requestMap).toJSON());
        System.out.println("RESPONSE: " + responseText);

        return processResponse(responseText);
    }

    /**
     * Create and send request for payment transaction.
     * Process and reformat API response afterward
     * @param card information of user credit card
     * @param amount amount of money to perform transaction
     * @param contents description of transaction
     * @return {@link MyMap} object containing transaction response
     * @throws PaymentException if API responded with an error code
     * @throws UnrecognizedException if error code is not recognizable
     */
    public MyMap performPayment(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException{
        Map<String, Object> transaction = new MyMap();

        try {
            transaction.putAll(MyMap.toMyMap(card));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new InvalidCardException();
        }
        transaction.put("command", PAY_COMMAND);
        transaction.put("transactionContent", contents);
        transaction.put("amount", amount);
        transaction.put("createdAt", Utils.getToday());

        Map<String, Object> requestMap = new MyMap();
        requestMap.put("secretKey", SECRET_KEY);
        requestMap.put("version", VERSION);
        requestMap.put("transaction", transaction);
        requestMap.put("appCode", PUBLIC_KEY);

        requestMap.put("hashCode", Utils.md5(((MyMap) requestMap).toJSON()));
        requestMap.remove("secretKey");

        System.out.println("REQUEST JSON: " + ((MyMap) requestMap).toJSON());

        String responseText = interbankBoundary.query(Configs.PROCESS_TRANSACTION_URL, ((MyMap) requestMap).toJSON());
        System.out.println("RESPONSE: " + responseText);

        return processResponse(responseText);
    }

    /**
     * Check for error code and convert response text to map-like format
     * @param responseText response received from API
     * @return {@link MyMap} object containing transaction response
     * @throws InvalidCardException if card information is not valid
     * @throws NotEnoughBalanceException if there is not enough money in card
     * @throws InternalServerErrorException if there is an error occurred from API side
     * @throws SuspiciousTransactionException if transaction does not contain hashed code
     * @throws InvalidVersionException if API version provided in request body is not valid
     * @throws InvalidTransactionAmountException if amount provided in request body is not valid
     * @throws UnrecognizedException if not able to parse API response
     */
    private MyMap processResponse(String responseText) {
        MyMap response;
        try {
            response = MyMap.toMyMap(responseText, 0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new UnrecognizedException();
        }

        String errCode = (String)  response.get("errorCode");

        switch (errCode) {
            case "00":
                break;
            case "01":
                throw new InvalidCardException();
            case "02":
                throw new NotEnoughBalanceException();
            case "03":
                throw new InternalServerErrorException();
            case "04":
                throw new SuspiciousTransactionException();
            case "05":
                throw new NotEnoughTransactionInfoException();
            case "06":
                throw new InvalidVersionException();
            case "07":
                throw new InvalidTransactionAmountException();
            default:
                throw new UnrecognizedException();
        }

        return (MyMap) response.get("transaction");
    }

}
