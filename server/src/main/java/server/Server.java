package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.GameService;
import service.RegisterRequest;
import service.RegisterResult;
import service.UserService;
import spark.*;

import java.util.Objects;

public class Server {
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

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
        RegisterResult executionResult;
        try{
            //register user, create authToken, and return code 200
            executionResult = userService.register(userInfo);

            res.status(200);
            return  gson.toJson(executionResult);


        } catch(DataAccessException e){
            // if register is not successful, then throw 403 error
            res.status(403);
            return gson.toJson(new RegisterResult(null, e.getLocalizedMessage()));
        }
    }



    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Server server = (Server) o;
        return Objects.equals(userService, server.userService) && Objects.equals(gameService, server.gameService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userService, gameService);
    }
}
