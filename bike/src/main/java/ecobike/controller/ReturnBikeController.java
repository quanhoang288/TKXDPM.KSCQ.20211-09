package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.Dock;
import ecobike.common.exception.DockFullException;
import ecobike.repository.DockRepo;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnBikeController extends BaseController {
    private List<Dock> dockList;

    @FXML
    private ListView dockListView;

    public ReturnBikeController() {
        this.initializeDockList();
    }


    public List<String> getDockNames() {
        List<String> dockNames = new ArrayList<>();
        for (Dock dock : this.dockList) {
            dockNames.add(dock.getName());
        }
        return dockNames;
    }

    public void returnBike(int selectedDockIndex) throws IOException {
        Dock selectedDock = this.dockList.get(selectedDockIndex);
//        boolean isDockFull = DockService.checkDockFull(selectedDock);
//        if (isDockFull) throw new DockFullException();

        //TODO: init controller and redirect to payment form
//        PaymentFormHandler paymentFormHandler = new PaymentFormHandler(Configs.PAYMENT_FORM_PATH);
    }

    private void initializeDockList() {
        this.dockList = DockRepo.getAll();
    }




}
