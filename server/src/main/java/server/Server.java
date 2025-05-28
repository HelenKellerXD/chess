package server;

import service.RegisterRequest;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Register|  /user POST
        Spark.post("/user", new Register);
        // Login| /session POST
        // Logout| /session DELETE
        // List Games| /game GET
        // Create Game| /game POST
        // Join Game| /game PUT


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
