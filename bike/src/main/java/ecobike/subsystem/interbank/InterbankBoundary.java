package ecobike.subsystem.interbank;

import ecobike.common.exception.UnrecognizedException;
import ecobike.utils.API;

public class InterbankBoundary {
    String query(String url, String data) {
        String response = null;
        try {
            response = API.post(url, data, "bearer code");
        } catch (Exception e) {
            throw new UnrecognizedException();
        }
        return response;
    }
}
