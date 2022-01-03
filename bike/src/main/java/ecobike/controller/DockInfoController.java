package ecobike.controller;

import ecobike.controller.base.BaseController;

public class DockInfoController extends BaseController {
    String dockId;

    public DockInfoController(String dockId) {
        this.dockId = dockId;
    }

    public String getDockId() {
        return dockId;
    }
}
