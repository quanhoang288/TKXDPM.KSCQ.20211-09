package eco.bike;

import eco.bike.entity.Bike;
import eco.bike.entity.Dock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Dock dock = Dock.builder().address("HUST").area("HAIBATRUNG").capacity("10").name("DOCK XE MAY").build();
        entityManager.persist(dock);
        entityManager.getTransaction().commit();
    }


}
