package ecobike.security;

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
    public static Authentication getInstance(String username, String userId) {
        if(authenticationInstance == null){
            authenticationInstance = new Authentication(username, userId);
        }
        return authenticationInstance;
    }


    private static void clearSession() {
        authenticationInstance = null;
    }

}
