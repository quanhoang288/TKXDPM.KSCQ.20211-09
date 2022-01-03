package ecobike.repository;

import ecobike.entity.Bike;

import java.util.List;

public interface IBikeRepo {
    List<Bike> getPagingByDock(int start, int count, String dockId);

    Bike findById(String id);
}
