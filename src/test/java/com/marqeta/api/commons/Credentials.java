/* BlankFactor (C)2023 */
package com.marqeta.api.commons;

import io.github.cdimascio.dotenv.Dotenv;

public class Credentials {

    private static String username;
    private static String password;

    public Credentials() {
        Dotenv dotenv = Dotenv.configure().load();

        username = dotenv.get("MARQETA_USERNAME");
        password = dotenv.get("MARQETA_PASSWORD");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
