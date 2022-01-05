package ecobike.security;

import ecobike.repository.UserRepo;
import lombok.Getter;

/**
 * Singleton class for saving currently authenticated user information
 */
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

    /**
     * Get singleton instance for accessing currently authenticated user information
     * @return
     */
    public static Authentication getInstance() {
        return authenticationInstance;
    }

    /**
     * Initialize if not existed {@link Authentication} singleton object for accessing currently authenticated user information
     * @param username
     * @param userId
     * @return {@link Authentication}
     */
    public static Authentication createInstance(String username, String userId){
        if (authenticationInstance== null){
            authenticationInstance = new Authentication(username, userId);
        }
        return authenticationInstance;
    }

    /**
     * Check if currently authenticated user is renting any bike
     * @return
     */
    public static boolean isAlreadyRenting() {
        return UserRepo.isRentingBike(authenticationInstance.getUserId());
    }


}
