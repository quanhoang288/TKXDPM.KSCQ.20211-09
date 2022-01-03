package ecobike.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private static EntityManager entityManager;
    public static EntityManager getEntityManager()
    {
        if(entityManager == null)
        {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

}
