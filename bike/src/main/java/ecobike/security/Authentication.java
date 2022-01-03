package ecobike.security;

import ecobike.repository.UserRepo;
import lombok.Getter;


public class Authentication {
    private static Authentication authenticationInstance;
    private String username;
    private String userId;

    public String getUserId() {
        return userId;
    }
    public String getPrinciple(){
        return username;
    }

    private Authentication(String username, String userId){
        this.username = username;
        this.userId = userId;
    }

    //init session
    public static Authentication getInstance() {
        return authenticationInstance;
    }

    public static Authentication createInstance(String username, String userId){
        if (authenticationInstance== null){
            authenticationInstance = new Authentication(username, userId);
        }
        return authenticationInstance;
    }

    public static boolean isAlreadyRenting() {
        return UserRepo.isRentingBike(authenticationInstance.getUserId());
    }

    private static void clearSession() {
        authenticationInstance = null;
    }

}
