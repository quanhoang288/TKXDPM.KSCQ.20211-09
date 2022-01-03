package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Dock;
import ecobike.repository.DockRepo;

public class DockInfoController extends BaseController {
    private Dock dock;

    public DockInfoController(Dock dock) {
        this.dock = dock;
    }

    public Dock getDock() {
        return this.dock;
    }
}
