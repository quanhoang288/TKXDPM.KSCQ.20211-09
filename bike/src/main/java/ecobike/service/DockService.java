package ecobike.service;

import ecobike.entity.Dock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DockService {
    public static List<Dock> list() {
        System.out.println("fetching dock list...");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query listQuery = entityManager.createQuery("select d from Dock d");
        List<Dock> dockList = listQuery.getResultList();
        entityManager.getTransaction().commit();

        System.out.println("" + dockList.size());

        return dockList;
    }

    public static boolean checkDockFull(Dock dock) {
        return false;
    }
}
