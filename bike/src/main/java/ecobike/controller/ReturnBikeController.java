package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.Dock;
import ecobike.common.exception.DockFullException;
import ecobike.repository.DockRepo;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReturnBikeController extends BaseController {
    private BikeRentalInfo rentalInfo;
    private List<Dock> dockList;

    @FXML
    private ListView dockListView;

    public ReturnBikeController(BikeRentalInfo rentalInfo) {
        this.rentalInfo = rentalInfo;
        this.initializeDockList();
    }

    public void checkDockHasEmptySlot(int selectedIndex) {
        Dock selectedDock = this.dockList.get(selectedIndex);
        boolean isDockFull = DockRepo.checkDockFull(selectedDock);
        if (isDockFull) throw new DockFullException();
    }

    public List<String> getDockNames() {
        List<String> dockNames = new ArrayList<>();
        for (Dock dock : this.dockList) {
            dockNames.add(dock.getName());
        }
        return dockNames;
    }

    private void initializeDockList() {
        this.dockList = DockRepo.getAll();
    }




}
