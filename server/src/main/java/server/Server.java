package server;

import service.RegisterRequest;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Register|  /user POST
        Spark.post("/user", new RegisterHandler());
        // Login| /session POST
        Spark.post("/session", new LoginHandler());
        // Logout| /session DELETE
        Spark.delete("/session", new LogoutHandler());
        // List Games| /game GET
        Spark.get("/game", new ListGamesHandler());
        // Create Game| /game POST
        Spark.post("/game", new CreateGameHandler());
        // Join Game| /game PUT
        Spark.put("/game", new JoinGameHandler());
        // Clear| /db DELETE
        Spark.delete("/db", new ClearHandler());


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    // implementing the port specificity from the video
    public static void main(String[] args){
        try {
            int port = Integer.parseInt(args[0]);
            Spark.port(port);

            createRoutes();

            Spark.awaitInitialization();
            System.out.println("Listening on port " + port);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex ){
            System.err.println("Specifiy the port number as a command line parameter");
        }
        private static void createRoutes(){
            Spark.get("/")
        }
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
