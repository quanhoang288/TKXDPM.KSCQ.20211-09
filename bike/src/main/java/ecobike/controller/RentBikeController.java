package ecobike.controller;


import ecobike.common.exception.BikeAlreadyRentedException;
import ecobike.common.exception.InvalidCardException;
import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UserAlreadyRentingException;
import ecobike.entity.*;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.repository.PaymentTransactionRepo;
import ecobike.repository.UserRepo;
import ecobike.security.Authentication;
import ecobike.utils.MyMap;
import ecobike.utils.Utils;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RentBikeController extends AbstractPaymentController{

    private Bike bike;

    public RentBikeController(Bike bike) {
        if (bike.isBeingRented()) {
            throw new BikeAlreadyRentedException();
        }
        if (Authentication.isAlreadyRenting()) {
            throw new UserAlreadyRentingException();
        }
        this.bike = bike;
    }


    @Override
    public void performTransactions(HashMap<String, String> paymentInfo) throws PaymentException {
        System.out.println("Renting transaction");
        int depositAmount = (int) (bike.getValue() * 0.4);
        String contents = paymentInfo.get("desc");
        CreditCard card = new CreditCard(paymentInfo);
        MyMap paymentResponse = interbank.performPayment(card, depositAmount, contents);
        handleTransactionComplete(paymentResponse);
    }

    protected void handleTransactionComplete(MyMap transactionResponse) throws NoResultException {
        System.out.println("Transaction response: " + transactionResponse);
        Date createdAt = null;
        try {
            createdAt = Utils.parseDateString((String) transactionResponse.get("createdAt"));
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            return;
        }

        String userId = Authentication.getInstance().getUserId();
        User user = UserRepo.findById(userId);

        BikeRentalInfo rentalInfo = BikeRentalInfoRepo.create(user, bike, Utils.getToday());

        PaymentTransaction paymentTransaction = PaymentTransactionRepo.create(
            (String)transactionResponse.get("content"),
            PAYCONTENT.RENT_DEPOSIT,
               Integer.parseInt((String)transactionResponse.get("amount")),
            createdAt,
            rentalInfo
        );

        // todo: start the stop watch

        this.paymentTransaction = paymentTransaction;
    }

}
