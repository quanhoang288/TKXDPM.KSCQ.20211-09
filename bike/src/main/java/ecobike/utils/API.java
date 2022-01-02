package ecobike.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * API class provide methods to communicate with the server
 *
 * @author Vu Tan Khang - 20183561
 * @version 1.1
 */
public class API {

    /**
     * Correct date format
     */
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Log information to console
     */
    private static final Logger LOGGER = Utils.getLogger(Utils.class.getName());

    /**
     * GET method function call
     * @param url: address of the server
     * @param token: authentication code
     * @return (String) response
     * @throws Exception
     */
    public static String get(String url, String token) throws Exception {
        // Part 1: setup
        HttpURLConnection conn = setupConnection(url, "GET", token);

        // Part 2: read servers response

        return readResponse(conn);
    }

    /**
     * POST method function call
     * @param url: address of the server
     * @param data: data sent to server (JSON)
     * @param token: authentication code
     * @return response (String)
     * @throws IOException
     */
    public static String post(String url, String data, String token) throws IOException {
        allowMethods("PATCH");

        LOGGER.info("Creating connection " + "\n");

        HttpURLConnection conn = setupConnection(url, "PATCH", token);
        LOGGER.info("Connection: " + conn);
        Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(data);
        writer.close();
        LOGGER.info("Reading server response");
        return readResponse(conn);

    }

    /**
     * other supported methods function call
     * @param methods: supported methods PATCH, PUT,...
     */
    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            try {
                methodsField.setAccessible(true);
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            try {
                modifiersField.setAccessible(true);
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }

            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/* static field */, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * establish connection to the server
     *
     * @param url: address of the server
     * @param method: api protocol
     * @param token: authentication code
     * @return connection
     * @throws IOException
     */
    private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
        LOGGER.info("Request URL: " + url + "\n");
        URL line_api_url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);

        return conn;
    }

    /**
     * data received from the server
     * @param conn: connection to the server
     * @return response
     * @throws IOException
     */
    private static String readResponse(HttpURLConnection conn) throws IOException {

        BufferedReader in;
        String inputLine;
        System.out.println("Response code: " + conn.getResponseCode());
        if (conn.getResponseCode() / 100 == 2) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();
        LOGGER.info("Response Info: " + response.substring(0, response.length()).toString());
        return response.toString();
    }
}
