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
import ecobike.utils.StopWatch;
import ecobike.utils.Utils;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This class perform payment transaction and initialize new rental info for renting a bike
 */
public class RentBikeController extends AbstractPaymentController{

    /**
     * {@link Bike} instance storing selected bike for renting
     */
    private Bike bike;

    /**
     * Perform check if bike has already been rented or current user's already renting another bike,
     * if not store {@link Bike} object to an instance variable
     * @param bike {@link Bike} object chosen to be rented by current authenticated user
     * @throws BikeAlreadyRentedException if the chosen bike has already been rented
     * @throws UserAlreadyRentingException if the current user's already renting another bike
     */
    public RentBikeController(Bike bike) {
        if (bike.isBeingRented()) {
            throw new BikeAlreadyRentedException();
        }
        if (Authentication.isAlreadyRenting()) {
            throw new UserAlreadyRentingException();
        }
        this.bike = bike;
    }


    /**
     * Perform payment transaction for renting bike
     * @param paymentInfo contains pairs of key-value containing user's payment information
     * @throws PaymentException
     */
    @Override
    public void performTransactions(HashMap<String, String> paymentInfo) throws PaymentException {
        System.out.println("Renting transaction");
        int depositAmount = (int) (bike.getValue() * 0.4);
        String contents = paymentInfo.get("desc");
        CreditCard card = new CreditCard(paymentInfo);
        MyMap paymentResponse = interbank.performPayment(card, depositAmount, contents);
        handleTransactionComplete(paymentResponse);
    }

    /**
     * Initialize rental info and payment transaction and persist in database.
     * After successful initialization start the stopwatch for real-time rental time tracking
     * @param transactionResponse {@link MyMap} object representing transaction response from API
     * @throws NoResultException
     */
    protected void handleTransactionComplete(MyMap transactionResponse) throws NoResultException {
        System.out.println("Transaction response: " + transactionResponse);
        Date createdAt = null;
        try {
            createdAt = Utils.parseDateString(Utils.getToday());
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            return;
        }

        // Get current authenticated user
        String userId = Authentication.getInstance().getUserId();
        User user = UserRepo.findById(userId);

        // initialize and save rental info
        BikeRentalInfo rentalInfo = BikeRentalInfoRepo.create(user, bike, createdAt);
        bike.updateDock(null);

        // save payment transaction for corresponding rental info
        PaymentTransaction paymentTransaction = PaymentTransactionRepo.create(
            (String)transactionResponse.get("content"),
            PAYCONTENT.RENT_DEPOSIT,
               Integer.parseInt((String)transactionResponse.get("amount")),
            createdAt,
            rentalInfo
        );

        // start the stopwatch for real-time tracking
        StopWatch.getInstance().start();

        this.paymentTransaction = paymentTransaction;
    }

}
