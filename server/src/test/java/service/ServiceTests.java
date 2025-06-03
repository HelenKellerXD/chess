package service;

import dataaccess.DataAccessException;
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
    public GameService gameService;
    public UserService userService;

    // ### TESTING SETUP/CLEANUP ###

    @AfterEach
    public void clear() {
        gameService.clear();
        userService.clear();
    }


    @BeforeEach
    public void setup() {
        userService = new UserService();
        gameService = new GameService();
    }

    // ### SERVICE-LEVEL TESTS ###

    /**
     * Register tests - Pass and Fail
     */

    @Test
    @Order(1)
    @DisplayName("User Service - Register PASS")
    public void registerSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

        Assertions.assertEquals(registerRequest.username(), registerResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(registerResult.authToken(), "Response did not return authentication String");
    }

    @Test
    @Order(2)
    @DisplayName("User Service - Register FAIL (user already exists)")
    public void registerFail() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "username is already in use");
        Assertions.assertThrows(DataAccessException.class, ()-> userService.register(registerRequest), "double register exception was not thrown");

        Assertions.assertEquals(registerRequest.username(), registerResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(registerResult.authToken(), "Response did not return authentication String");
    }

    /**
     * Login tests - Pass and Fail
     */
    @Test
    @Order(3)
    @DisplayName("User Service - Login PASS")
    public void loginSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

        LoginRequest loginRequest = new LoginRequest("dave", "dave's password");
        LoginResult loginResult = Assertions.assertDoesNotThrow(()-> userService.login(loginRequest), "login() threw an exception");

        Assertions.assertEquals(loginRequest.username(), loginResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(loginResult.authToken(), "Response did not return authentication String");
    }

    @Test
    @Order(4)
    @DisplayName("User Service - Login FAIL (incorrect password)")
    public void loginFail() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

        LoginRequest loginRequest = new LoginRequest("dave", "NOT dave's password");
        Assertions.assertThrows(DataAccessException.class, ()-> userService.login(loginRequest), "password did not throw exception despite being incorrect");

        Assertions.assertEquals(registerRequest.username(), registerResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(registerResult.authToken(), "Response did not return authentication String");
    }



    /**
     * Logout tests - Pass and Fail
     */
    @Test
    @Order(5)
    @DisplayName("User Service - Logout PASS")
    public void logoutSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

        LoginRequest loginRequest = new LoginRequest("dave", "dave's password");
        LoginResult loginResult = Assertions.assertDoesNotThrow(()-> userService.login(loginRequest), "login() threw an exception");

        LogoutRequest logoutRequest = new LogoutRequest(loginResult.authToken());
        Assertions.assertDoesNotThrow(()-> userService.login(loginRequest), "logout() threw an exception");

        Assertions.assertEquals(loginRequest.username(), loginResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(loginResult.authToken(), "Response did not return authentication String");
    }

    @Test
    @Order(5)
    @DisplayName("User Service - Logout FAIL")
    public void logoutFail() {
        //register
        RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
        RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

        //login
        LoginRequest loginRequest = new LoginRequest("dave", "dave's password");
        LoginResult loginResult = Assertions.assertDoesNotThrow(()-> userService.login(loginRequest), "login() threw an exception");

        //logout
        LogoutRequest logoutRequest = new LogoutRequest(loginResult.authToken());
        Assertions.assertDoesNotThrow(()-> userService.logout(logoutRequest), "logout() threw an exception");
        Assertions.assertThrows(DataAccessException.class, ()->userService.logout(logoutRequest), "logout() threw an exception");


        Assertions.assertEquals(loginRequest.username(), loginResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(userService.userDAO.getUser("dave"), "User was not found in User Database");
        Assertions.assertNotNull(loginResult.authToken(), "Response did not return authentication String");
    }

    /**
     * Create Game tests - Pass and Fail
     */

    //register
    RegisterRequest registerRequest = new RegisterRequest("dave", "dave's password", "dave's email");
    RegisterResult registerResult = Assertions.assertDoesNotThrow(()-> userService.register(registerRequest), "register() threw an exception");

    //login
    LoginRequest loginRequest = new LoginRequest("dave", "dave's password");
    LoginResult loginResult = Assertions.assertDoesNotThrow(()-> userService.login(loginRequest), "login() threw an exception");

    //create game
    CreateGameRequest createGameRequest = new CreateGameRequest("dave's game", loginResult.authToken());


    /**
     * Join Game tests - Pass and Fail
     */

    /**
     * List Games tests - Pass and Fail
     */
}

