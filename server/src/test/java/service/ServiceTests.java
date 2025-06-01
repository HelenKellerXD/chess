package service;

import org.junit.jupiter.api.*;
import passoff.model.TestAuthResult;
import passoff.model.TestCreateRequest;
import passoff.model.TestUser;
import passoff.server.TestServerFacade;
import server.Server;

import java.net.HttpURLConnection;


// do one positive and one negative test case

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {

    private static TestUser existingUser;
    private static TestUser newUser;
    private static TestCreateRequest createRequest;
    private String existingAuth;
    public static GameService gameService;
    public static UserService userService;

    // ### TESTING SETUP/CLEANUP ###

    @AfterAll
    static void clear() {
        gameService.clear();
        userService.clear();
    }


    @BeforeEach
    public void setup() {
        gameService = new GameService();
        userService = new UserService();
        clear();
    }

    // ### SERVICE-LEVEL TESTS ###

    @Test
    @Order(1)
    @DisplayName("User Service - Register PASS")
    public void registerSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = userService.register(registerRequest);

        Assertions.assertEquals(registerRequest.username(), registerResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(registerResult.authToken(), "Response did not return authentication String");
    }

    @Test
    @Order(2)
    @DisplayName("User Service - Register FAIL (user already exists")
    public void registerFail() {
        TestUser[] incompleteLoginRequests = {
                new TestUser(null, existingUser.getPassword(), existingUser.getEmail()),
                new TestUser(existingUser.getUsername(), null, existingUser.getEmail()),
        };

        for (TestUser incompleteLoginRequest : incompleteLoginRequests) {
            TestAuthResult loginResult = serverFacade.login(incompleteLoginRequest);

            assertHttpBadRequest(loginResult);
            assertAuthFieldsMissing(loginResult);
        }
    }

}
