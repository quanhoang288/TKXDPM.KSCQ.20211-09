package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.Dock;
import ecobike.repository.DockRepo;

import java.util.List;

public class DockListController extends BaseController {
    public List<Dock> findAll(){
        return DockRepo.getAll();
    }
}
