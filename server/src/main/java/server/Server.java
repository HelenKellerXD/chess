package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.RegisterRequest;
import service.RegisterResult;
import service.UserService;
import spark.*;

public class Server {
    private UserService userService = new UserService();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Register|  /user POST
        Spark.post("/user", this::registerHandler);
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


    private Object registerHandler(Request req, Response res) throws DataAccessException {
        Gson gson = new Gson();
        RegisterRequest userInfo = gson.fromJson(req.body(), RegisterRequest.class);
        return gson.toJson(userService.register(userInfo));
    }



    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
