package ecobike.controller;

import ecobike.common.exception.UserNotRentingException;
import ecobike.controller.base.BaseController;
import ecobike.entity.*;
import ecobike.common.exception.DockFullException;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.repository.DockRepo;
import ecobike.repository.PaymentTransactionRepo;
import ecobike.repository.UserRepo;
import ecobike.security.Authentication;
import ecobike.utils.MyMap;
import ecobike.utils.StopWatch;
import ecobike.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReturnBikeController extends AbstractPaymentController {
    private List<Dock> dockList;
    private BikeRentalInfo bikeRentalInfo;
    private Dock selectedDock;

    @FXML
    private ListView dockListView;

    public ReturnBikeController() {
        if (!Authentication.isAlreadyRenting()) {
            throw new UserNotRentingException();
        }
        this.bikeRentalInfo = BikeRentalInfoRepo.findInProgressByUserId(Authentication.getInstance().getUserId());
        initializeDockList();
    }

    public ReturnBikeController(BikeRentalInfo rentalInfo) {
        this.bikeRentalInfo = rentalInfo;
        initializeDockList();
    }

    public List<String> getDockNames() {
        List<String> dockNames = new ArrayList<>();
        for (Dock dock : this.dockList) {
            dockNames.add(dock.getName());
        }
        return dockNames;
    }

    public void checkDockFull(int selectedDockIndex) throws IOException {
        Dock selectedDock = this.dockList.get(selectedDockIndex);
        if (selectedDock.isFull()) throw new DockFullException();
        this.selectedDock = selectedDock;
    }

    private void initializeDockList() {
        this.dockList = DockRepo.getAll();
    }

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

        MyMap paymentResponse = null;

        if (totalToPay < 0)
            paymentResponse = interbank.performRefund(card, totalToPay * -1, contents);
        else
            paymentResponse = interbank.performPayment(card, totalToPay, contents);

        handleTransactionComplete(paymentResponse);
    }

    @Override
    protected void handleTransactionComplete(MyMap transactionResponse) {
        System.out.println("Transaction response: " + transactionResponse);
        Date createdAt = null;
        try {
            createdAt = Utils.parseDateString(Utils.getToday());
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            return;
        }

        bikeRentalInfo.updateStatus(RENTALSTATUS.FINISHED, StopWatch.getInstance().getElapsedTimeInSecond());
        bikeRentalInfo.getBike().moveToNewDock(selectedDock);

        PAYCONTENT method = ((String)transactionResponse.get("command")).equals("pay") ? PAYCONTENT.RETURN_PAY : PAYCONTENT.RETURN_REFUND;

        PaymentTransaction paymentTransaction = PaymentTransactionRepo.create(
            (String)transactionResponse.get("content"),
            method,
            Integer.parseInt((String)transactionResponse.get("amount")),
            createdAt,
            bikeRentalInfo
        );

        this.paymentTransaction = paymentTransaction;
    }
}
