package eco.bike.entity;

import eco.bike.utils.MongoDB;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class User {
    private ObjectId id;
    private int userId;
    private String name;
    private String phoneNumber;
    private String province;
    private String address;
    private String dateOfBirth;
    private String email;
    private String password;
    private List<Integer> bikeRentalIds;

    public User() {}

    public User(int userId, String name, String phoneNumber, String province, String address, String dateOfBirth,
                String email, String password,
                List<Integer> bikeRentalIds) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.bikeRentalIds = bikeRentalIds;
    }

    public static User loadFromDatabase(int id) {
        return null;
    }

    public static User loadFromDatabase(String email, String password) {
        MongoDB mongoDB = new MongoDB();
        Document doc = mongoDB.findOneUser(email, password);
        if (doc == null) return null;
        return new User(
                doc.getInteger("userId"),
                doc.getString("name"),
                doc.getString("phoneNumber"),
                doc.getString("province"),
                doc.getString("address"),
                doc.getString("dataOfBirth"),
                doc.getString("email"),
                doc.getString("password"),
                doc.getList("bikeRentalIds", Integer.class)
        );
    }

    public static void saveToDatabase(User user) {
        MongoDB mongoDB = new MongoDB();
        mongoDB.updateOneUser(user);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getBikeRentalIds() {
        return bikeRentalIds;
    }

    public void setBikeRentalIds(List<Integer> bikeRentalIds) {
        this.bikeRentalIds = bikeRentalIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bikeRentalIds=" + bikeRentalIds +
                '}';
    }
}
