package ecobike.subsystem.interbank;

import ecobike.common.exception.*;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.Configs;
import ecobike.utils.MyMap;
import ecobike.utils.Utils;

import java.util.Map;

public class InterbankSubsystemController {

    private static final String PUBLIC_KEY = "A5NouKUnbP8=";
    private static final String SECRET_KEY = "Bb/W/eu7ZdY=";
    private static final String PAY_COMMAND = "pay";
    private static final String VERSION = "1.0.1";

    private static InterbankBoundary interbankBoundary = new InterbankBoundary();

    public MyMap performRefund(CreditCard card, int amount, String contents) {
        return null;
    }

    public MyMap performPayment(CreditCard card, int amount, String contents) throws PaymentException{
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

    private MyMap processResponse(String responseText) {
        MyMap response = null;
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

//    private PaymentTransaction makePaymentTransaction(MyMap response) {
//
//        if (response == null)
//            return null;
//        MyMap transaction = (MyMap) response.get("transaction");
//
//        PaymentTransaction trans = new PaymentTransaction((String) response.get("errorCode"),
//                (String) transaction.get("transactionId"), (String) transaction.get("transactionContent"),
//                Integer.parseInt((String) transaction.get("amount")));
//
//        switch (trans.getErrorCode()) {
//            case "00":
//                break;
//            case "01":
//                throw new InvalidCardException();
//            case "02":
//                throw new NotEnoughBalanceException();
//            case "03":
//                throw new InternalServerErrorException();
//            case "04":
//                throw new SuspiciousTransactionException();
//            case "05":
//                throw new NotEnoughTransactionInfoException();
//            case "06":
//                throw new InvalidVersionException();
//            case "07":
//                throw new InvalidTransactionAmountException();
//            default:
//                throw new UnrecognizedException();
//        }
//
//        return trans;
//    }
}
