//package ecobike.seeders;
//
//import com.github.javafaker.Faker;
//import ecobike.entity.Bike;
//import ecobike.entity.BikeRentalInfo;
//import ecobike.entity.Dock;
//import ecobike.entity.User;
//import ecobike.utils.Configs;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.util.Random;
//
//public class RentalInfoSeeder {
//    public static void main(String[] args) {
//        seed();
//    }
//
//    public static void seed() {
//        Random r = new Random();
//        Faker faker = new Faker();
//        int numOfBikeTypes = Configs.BIKE_TYPES.length;
//
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        for (int i = 0; i < 3; i++) {
//            String address = faker.address().toString();
//            double area = r.nextDouble();
//            int capacity = r.nextInt(100);
//            String dockName = faker.name().fullName();
//
//            Dock dock = Dock.builder().address(address).area(area).capacity(capacity).name(dockName).build();
//            entityManager.persist(dock);
//
//            int bikeTypeIndex = r.nextInt(numOfBikeTypes);
//            String bikeType = Configs.BIKE_TYPES[bikeTypeIndex];
//            String licensePlate = faker.code().toString();
//            int batterPercent = r.nextInt(101);
//            int value = r.nextInt(1000000);
//
//            Bike bike = Bike.builder().dock(dock).type(bikeType).licensePlate(licensePlate).batteryPercent(batterPercent).value(value).build();
//            entityManager.persist(bike);
//
//            String userAddress = faker.address().toString();
//            String dateOfBirth = faker.date().toString();
//            String email = faker.bothify("????##@gmail.com");
//            String name = faker.name().fullName();
//            String password = "12345678";
//            String phoneNumber = faker.phoneNumber().phoneNumber();
//            String province = faker.address().cityName();
//
//            User user = User
//                    .builder()
//                    .address(userAddress)
//                    .dateOfBirth(dateOfBirth)
//                    .email(email)
//                    .password(password)
//                    .name(name)
//                    .phoneNumber(phoneNumber)
//                    .province(province)
//                    .build();
//            entityManager.persist(user);
//
//            String startTime = faker.date().toString();
//
//            BikeRentalInfo rentalInfo = BikeRentalInfo
//                    .builder()
//                    .startAt(startTime)
//                    .bike(bike)
//                    .user(user)
//                    .build();
//            entityManager.persist(rentalInfo);
//
//        }
//
//        entityManager.getTransaction().commit();
//
//    }
//}
