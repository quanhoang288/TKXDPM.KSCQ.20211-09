package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Dock;

/**
 * This class store instance of {@link Dock} for displaying dock information in view
 */
public class DockInfoController extends BaseController {
    private Dock dock;

    public DockInfoController(Dock dock) {
        this.dock = dock;
    }

    public Dock getDock() {
        return this.dock;
    }
}
