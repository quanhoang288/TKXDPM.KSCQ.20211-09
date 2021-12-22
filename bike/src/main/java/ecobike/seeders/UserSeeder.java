package ecobike.seeders;

import ecobike.entity.Bike;
import ecobike.entity.Dock;
import ecobike.entity.User;
import ecobike.utils.Configs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserSeeder {
    public static void main(String[] args) {
        seed();
    }

    public static void seed() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            String email = "email" + i + "@gmail.com";
            String name = "name " + i;
            String password = "12345678";

            User user = User
                    .builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
            entityManager.persist(user);

        }
        entityManager.getTransaction().commit();
    }
}
