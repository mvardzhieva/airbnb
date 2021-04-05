package airbnb.util;

import airbnb.exceptions.user.CompromisedPasswordException;
import airbnb.exceptions.user.InvalidUserInputException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Validator {
    private static final int STATUS_CODE_COMPROMISED = 200;
    private static final String API_URL = "https://api.enzoic.com";
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_SECRET = System.getenv("API_SECRET");

    public void validatePassword(String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = API_URL
                + "/passwords?sha1=" + new ShaPasswordEncoder().encodePassword(password, null)
                + "&md5=" + new Md5PasswordEncoder().encodePassword(password, null)
                + "&sha256=" + new ShaPasswordEncoder(256).encodePassword(password, null);
        String encodedKeys = new String(Base64.encodeBase64((API_KEY + ":" + API_SECRET).getBytes()));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("authorization", "basic " + encodedKeys)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == STATUS_CODE_COMPROMISED) {
            throw new CompromisedPasswordException("This password is compromised. Choose a stronger one.");
        }
    }

    public void validateUserInput(String firstName, String lastName, String phone) {
        validateName(firstName);
        validateName(lastName);
        validatePhoneNumber(phone);
    }

    private void validateName(String name) {
        String nameValidationRegex = "([A-Z][a-z]+([ -][A-Z][a-z]+)*){1,99}$";
        if (!name.matches(nameValidationRegex)) {
            throw new InvalidUserInputException("Invalid name syntax.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        String phoneValidationRegex = "^\\d{10}$";
        if (!phoneNumber.matches(phoneValidationRegex)) {
            throw new InvalidUserInputException("Invalid phone number.");
        }
    }

}
