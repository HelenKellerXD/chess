package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        //facade = new ServerFacade(port);

    }

    @BeforeEach
    void resetServer() {
        server.stop();
        server.run(0);
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }


    @Test
    void register() throws Exception {
        //var authData = facade.register("player1", "password", "p1@email.com");
        //assertTrue(authData.authToken().length() > 10);
    }
}
