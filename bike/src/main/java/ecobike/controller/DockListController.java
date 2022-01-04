package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Dock;
import ecobike.repository.DockRepo;

import java.util.List;

/**
 * This class fetches dock list from database for displaying dock list in view
 */
public class DockListController extends BaseController {
    public DockListController() { }

    /**
     * Fetch list of docks from database
     * @return {@link List<Dock>}
     */
    public List<Dock> findAll(){
        return DockRepo.getAll();
    }
}
