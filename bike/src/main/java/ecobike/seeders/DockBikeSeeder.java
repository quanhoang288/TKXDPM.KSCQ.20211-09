package ecobike.seeders;

import ecobike.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Random;
import java.util.UUID;

public class DockBikeSeeder {
    public static void main(String[] args) {
        seed();
    }

    public static void seed() {
        Random r = new Random();


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int i = 0; i < 5; i++) {
            String address = "address " + UUID.randomUUID().toString();
            double area = r.nextDouble();
            int capacity = r.nextInt(100);
            String dockName = "dock name " + UUID.randomUUID().toString();

            Dock dock = Dock.builder().address(address).area(area).capacity(capacity).name(dockName).build();
            entityManager.persist(dock);


            String licensePlate = UUID.randomUUID().toString();
            int batterPercent = r.nextInt(101);
            int value = r.nextInt(1000000);

            Bike bike = StandardEBike.builder().dock(dock).licensePlate(licensePlate).batteryPercent(batterPercent).value(value).build();
            entityManager.persist(bike);


            value = r.nextInt(1000000);

            bike = StandardBike.builder().dock(dock).value(value).build();
            entityManager.persist(bike);

            value = r.nextInt(1000000);

            bike = TwinBike.builder().dock(dock).value(value).build();
            entityManager.persist(bike);

        }

        entityManager.getTransaction().commit();
    }
}
