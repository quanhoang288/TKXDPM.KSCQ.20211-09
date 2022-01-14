package ecobike.controller;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UserNotRentingException;
import ecobike.entity.*;
import ecobike.common.exception.DockFullException;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.repository.DockRepo;
import ecobike.repository.PaymentTransactionRepo;
import ecobike.security.Authentication;
import ecobike.utils.MyMap;
import ecobike.utils.StopWatch;
import ecobike.utils.Utils;


import javax.persistence.NoResultException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This class perform payment transaction and update status of rental info for returning rented bike
 */
public class ReturnBikeController extends AbstractPaymentController {
    private List<Dock> dockList;
    private BikeRentalInfo bikeRentalInfo;
    private Dock selectedDock;

    /**
     * Perform check if authenticated user is not currently renting any bike.
     * If false fetch current bike rental info of that user and initialize dock list for returning bike
     * @throws  UserNotRentingException if authenticated user is not currently renting any bike
     */
    public ReturnBikeController() {
        if (!Authentication.isAlreadyRenting()) {
            throw new UserNotRentingException();
        }
        this.bikeRentalInfo = BikeRentalInfoRepo.findInProgressByUserId(Authentication.getInstance().getUserId());
        initializeDockList();
    }

    /**
     * Constructor receiving an existing rental info and store in an instance variable. Initialize dock list for returning bike
     * @param rentalInfo {@link BikeRentalInfo} object containing authenticated user's current rental information
     */
    public ReturnBikeController(BikeRentalInfo rentalInfo) {
        this.bikeRentalInfo = rentalInfo;
        initializeDockList();
    }

    /**
     * Get list of dock names for displaying in view
     * @return {@link List<String>}
     */
    public List<String> getDockNames() {
        List<String> dockNames = new ArrayList<>();
        for (Dock dock : this.dockList) {
            dockNames.add(dock.getName());
        }
        return dockNames;
    }

    /**
     * Check if selected dock to return is full or not
     * @param selectedDockIndex index of selected dock in dock list array
     * @throws DockFullException if currently selected dock is full
     */
    public void checkDockFull(int selectedDockIndex) {
        Dock selectedDock = this.dockList.get(selectedDockIndex);
        if (selectedDock.isFull()) throw new DockFullException();
        this.selectedDock = selectedDock;
    }

    /**
     * Fetch list of docks from database and store in an instance variable for displaying in view
     */
    private void initializeDockList() {
        this.dockList = DockRepo.getAll();
    }

    /**
     * Perform payment transaction for returning bike
     * @param paymentInfo contains pairs of key-value containing user's payment information
     * @throws PaymentException if API sends back error code
     */
    @Override
    public void performTransactions(HashMap<String, String> paymentInfo) {
        System.out.println("Returning transaction");
        String contents = paymentInfo.get("desc");
        CreditCard card = new CreditCard(paymentInfo);

        int depositAmount = bikeRentalInfo.getBike().getDepositAmount();
        int curDuration = StopWatch.getInstance().getElapsedTimeInSecond();
        int amountToPay = bikeRentalInfo.calculateRentalFee(curDuration);

        int totalToPay = amountToPay - depositAmount;
        System.out.println("Total to pay: " + totalToPay);

        MyMap paymentResponse;

        if (totalToPay < 0)
            paymentResponse = interbank.performRefund(card, totalToPay * -1, contents);
        else
            paymentResponse = interbank.performPayment(card, totalToPay, contents);

        handleTransactionComplete(paymentResponse);
    }

    /**
     * Update rental info status to finished and store payment transaction in database.
     * After successful save start the stopwatch for real-time rental time tracking
     * @param transactionResponse {@link MyMap} object representing transaction response from API
     * @throws NoResultException if no rental info record is found
     */
    @Override
    protected void handleTransactionComplete(MyMap transactionResponse) {
        System.out.println("Transaction response: " + transactionResponse);
        Date createdAt;
        try {
            createdAt = Utils.parseDateString(Utils.getToday());
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            return;
        }

        // update rental info status and move bike to the returned dock
        bikeRentalInfo.updateStatus(RENTALSTATUS.FINISHED, StopWatch.getInstance().getElapsedTimeInSecond());
        bikeRentalInfo.getBike().updateDock(selectedDock);


        PAYCONTENT method = ((String)transactionResponse.get("command")).equals("pay") ? PAYCONTENT.RETURN_PAY : PAYCONTENT.RETURN_REFUND;

        // save payment transaction
        PaymentTransaction paymentTransaction = PaymentTransactionRepo.create(
            (String)transactionResponse.get("content"),
            method,
            Integer.parseInt((String)transactionResponse.get("amount")),
            createdAt,
            bikeRentalInfo
        );

        // stop the stop watch
        StopWatch.getInstance().cancel();

        this.paymentTransaction = paymentTransaction;
    }
}
