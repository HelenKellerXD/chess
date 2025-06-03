package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import service.*;
import spark.*;

import java.util.Map;
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
        Spark.post("/session", this::LoginHandler);
        // Logout| /session DELETE
        Spark.delete("/session", this::LogoutHandler);
        // List Games| /game GET
        Spark.get("/game", this::ListGamesHandler);
        // Create Game| /game POST
        Spark.post("/game", this::CreateGameHandler);
        // Join Game| /game PUT
        Spark.put("/game", this::JoinGameHandler);
        // Clear| /db DELETE
        Spark.delete("/db", this::clearHandler);


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object JoinGameHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        String username;

        //validate auth token
        try {
            username = userService.getUsername(authToken);
        } catch (DataAccessException e) {
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }


        // collect gameID from the request body
        JoinGameRequest userInfo = (gson.fromJson(req.body(), JoinGameRequest.class));
        JoinGameRequest joinGameRequest = new JoinGameRequest(userInfo.playerColor(), userInfo.gameID(), username);
        try {
            gameService.joinGame(joinGameRequest);
            res.status(200);
            return gson.toJson(null);
        } catch (DataAccessException e) {
            // if e = bad join request -> 400, if e = game doesn't exist -> 403
            String error = e.getMessage();
            if (error.equalsIgnoreCase("Error: already taken")){
                res.status(403);
                return gson.toJson(Map.of("message", error));

            }
            else {
                res.status(400);
                return gson.toJson(Map.of("message", "Error: unauthorized"));
            }
        }
    }

    private Object CreateGameHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        //validate auth token
        try {
            userService.validateToken(authToken);
        } catch (DataAccessException e) {
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }


        // collect gameName from the request body
        CreateGameRequest userInfo = gson.fromJson(req.body(), CreateGameRequest.class);

        //check to see if body contains game name
        if(userInfo.gameName() == null){
            res.status(400);
            return gson.toJson(Map.of("message", "Error: bad request"));
        }

        try {
            CreateGameResult result = gameService.createGame(userInfo);
            res.status(200);
            return gson.toJson(result);

        } catch (DataAccessException e) {
            res.status(400);
            return gson.toJson(Map.of("message", "Error: bad request"));
        }

    }

    private Object ListGamesHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        //validate auth token
        try {
            userService.validateToken(authToken);
        } catch (DataAccessException e) {
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }


        try{
            //list games, return ListGamesResult result, and return code 200
            ListGamesResult result = gameService.listGames(listGamesRequest);
            res.status(200);
            return  gson.toJson(result);

        } catch(DataAccessException e){
            // if login is not successful, then throw 401 error
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }

    }

    private Object LogoutHandler(Request req, Response res) {
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        LogoutRequest logoutRequest = new LogoutRequest(authToken);

        try{
            //logout, and return code 200
            userService.logout(logoutRequest);
            res.status(200);
            return  gson.toJson(null);

        } catch(DataAccessException e){
            // if login is not successful, then throw 401 error
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }
    }

    private Object LoginHandler(Request req, Response res) {
        Gson gson = new Gson();
        LoginRequest userInfo = gson.fromJson(req.body(), LoginRequest.class);
        LoginResult executionResult;

        if(userInfo.username() == null || userInfo.password() == null){
            res.status(400);
            return gson.toJson(Map.of("message", "Error: bad request"));
        }

        try{
            //login user, create authToken, and return code 200
            executionResult = userService.login(userInfo);

            res.status(200);
            return  gson.toJson(executionResult);


        } catch(DataAccessException e){
            // if login is not successful, then throw 401 error
            res.status(401);
            return gson.toJson(Map.of("message", "Error: unauthorized"));
        }
    }

    private Object clearHandler(Request req, Response res) {
        Gson gson = new Gson();
        userService.clear();
        gameService.clear();
        res.status(200);
        return  gson.toJson(null);
    }


    private Object registerHandler(Request req, Response res) throws DataAccessException {
        Gson gson = new Gson();
        RegisterRequest userInfo = gson.fromJson(req.body(), RegisterRequest.class);
        RegisterResult executionResult;
        // check to see if all fields were properly entered
        if(userInfo.username() == null || userInfo.password() == null || userInfo.email() == null){
            res.status(400);
            return gson.toJson(Map.of("message", "Error: bad request"));
        }


        try{
            //register user, create authToken, and return code 200
            executionResult = userService.register(userInfo);

            res.status(200);
            return  gson.toJson(executionResult);


        } catch(DataAccessException e){
            // if register is not successful, then throw 403 error
            res.status(403);
            return gson.toJson(Map.of("message", "Error: already taken"));
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
