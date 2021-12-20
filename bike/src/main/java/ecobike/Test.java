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
        Dock dock = Dock.builder().address("HUSTI").area("HAIBATRUNG").capacity(20).name("DOCK XE MAY").build();

        entityManager.persist(dock);
        entityManager.getTransaction().commit();
    }


}
