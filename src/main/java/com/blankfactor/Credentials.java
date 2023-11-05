/* BlankFactor (C)2023 */
package com.blankfactor;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Credentials {

    private static Credentials instance = null;
    private final String username;
    private final String password;
    private final String bfBankPublicUrl;
    private final String bfMarqetaPublicUrl;

    private Credentials() {

        try {
            Dotenv dotenv = Dotenv.configure().load();
            dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        } catch (Exception ignored) {
            // ignored
        }

        username = getEnv("MARQETA_USERNAME");
        password = getEnv("MARQETA_PASSWORD");
        bfBankPublicUrl = getEnvUrl("BF_CORE_PUBLIC_URL");
        bfMarqetaPublicUrl = getEnvUrl("BF_MARQETA_PUBLIC_URL");
    }

    public static synchronized Credentials getInstance() {
        if (instance == null) instance = new Credentials();

        return instance;
    }

    private static String getEnv(String key) {
        String value =
                !StringUtils.isEmpty(System.getProperty(key))
                        ? System.getProperty(key)
                        : System.getenv(key);

        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException(
                    "No system variable found for "
                            + key
                            + ". You must set it at .env file or in system variables");
        }

        return value;
    }

    private static String getEnvUrl(String key) {
        String value = getEnv(key);
        isValidUrl(key, value);
        return value;
    }

    public static boolean isValidUrl(String variableName, String urlString) {
        try {
            URL url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException("%s is not a valid public URL".formatted(variableName));
        }

        if (urlString.endsWith("/")) {
            throw new RuntimeException("%s must NOT end with /".formatted(variableName));
        }

        if (variableName.equals("BF_MARQETA_PUBLIC_URL") && !urlString.startsWith("https://")) {
            throw new RuntimeException(
                    "%s must be a public url that starts with 'https://'".formatted(variableName));
        }

        if (variableName.equals("BF_CORE_PUBLIC_URL")
                && !urlString.startsWith("https://")
                && !urlString.startsWith("http://")) {
            throw new RuntimeException(
                    "%s must be a url that starts with 'https://' or 'http://'"
                            .formatted(variableName));
        }

        return true;
    }
}
