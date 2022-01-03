package ecobike;


import ecobike.entity.Dock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (int i = 1; i < 10; i++) {
            Dock dock = Dock.builder().address("Address " + i).area(10000).capacity(100).name("Dock " + i).build();
            entityManager.persist(dock);
        }
        entityManager.getTransaction().commit();

    }


}
