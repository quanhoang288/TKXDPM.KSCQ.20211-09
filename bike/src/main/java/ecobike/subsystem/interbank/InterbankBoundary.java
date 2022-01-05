package ecobike.subsystem.interbank;

import ecobike.common.exception.UnrecognizedException;
import ecobike.utils.API;

/**
 * Boundary class for communicating directly with API
 */
public class InterbankBoundary {
    /**
     * Performing a post request to API with given url and request data
     * @param url url of API to send request
     * @param data request content in JSON format
     * @return response from API in JSON format
     */
    String query(String url, String data) {
        String response = null;
        try {
            response = API.post(url, data, null);
        } catch (Exception e) {
            throw new UnrecognizedException();
        }
        return response;
    }
}
