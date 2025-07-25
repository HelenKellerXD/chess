package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import request.*;
import result.CreateGameResult;
import result.ListGamesResult;
import result.LoginResult;
import result.RegisterResult;
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
        Spark.post("/session", this::loginHandler);
        // Logout| /session DELETE
        Spark.delete("/session", this::logoutHandler);
        // List Games| /game GET
        Spark.get("/game", this::listGamesHandler);
        // Create Game| /game POST
        Spark.post("/game", this::createGameHandler);
        // Join Game| /game PUT
        Spark.put("/game", this::joinGameHandler);
        // Clear| /db DELETE
        Spark.delete("/db", this::clearHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }


    private Object generalError (DataAccessException e, Response res){
        Gson gson = new Gson();
        if (e.getCause() instanceof java.sql.SQLException){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }
        // if not server error, then throw 401 error
        res.status(401);
        return gson.toJson(Map.of("message", "Error: unauthorized"));
    }

    private Object joinGameHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        String username;

        //validate auth token
        try {
            username = userService.getUsername(authToken);
        } catch (DataAccessException e) {
            return generalError(e, res);
        }


        // collect gameID from the request body
        JoinGameRequest userInfo = (gson.fromJson(req.body(), JoinGameRequest.class));
        JoinGameRequest joinGameRequest = new JoinGameRequest(userInfo.playerColor(), userInfo.gameID(), username);
        try {
            gameService.joinGame(joinGameRequest);
            res.status(200);
            return gson.toJson(null);
        } catch (DataAccessException e) {
            if (e.getCause() instanceof java.sql.SQLException){
                res.status(500);
                return gson.toJson(Map.of("message", "Error: server error"));
            }
            // if e = bad join request -> 400, if e = game doesn't exist -> 403
            String error = e.getMessage();
            if (error.equalsIgnoreCase("Error: already taken")){
                res.status(403);
                return gson.toJson(Map.of("message", error));

            }
            else {
                res.status(400);
                return gson.toJson(Map.of("message", "Error: bad request"));
            }
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }
    }

    private Object createGameHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        //validate auth token
        try {
            userService.validateToken(authToken);
        } catch (DataAccessException e) {
            return generalError(e, res);
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
            return generalError(e, res);
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }

    }

    private Object listGamesHandler(Request req, Response res) {
        // create Gson object and collect authToken from the request header
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);

        //validate auth token
        try {
            userService.validateToken(authToken);
        } catch (DataAccessException e) {
            return generalError(e, res);
        }


        try{
            //list games, return ListGamesResult result, and return code 200
            ListGamesResult result = gameService.listGames();
            res.status(200);
            return  gson.toJson(result);

        } catch(DataAccessException e){
            return generalError(e, res);
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }

    }

    private Object logoutHandler(Request req, Response res) {
        Gson gson = new Gson();
        String authToken= req.headers("authorization");
        LogoutRequest logoutRequest = new LogoutRequest(authToken);

        try{
            //logout, and return code 200
            userService.logout(logoutRequest);
            res.status(200);
            return  gson.toJson(null);

        } catch(DataAccessException e){
            return generalError(e, res);
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }
    }

    private Object loginHandler(Request req, Response res) {
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
            return generalError(e, res);
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }
    }

    private Object clearHandler(Request req, Response res) {
        Gson gson = new Gson();
        try {
            userService.clear();
            gameService.clear();

        } catch (DataAccessException e) {
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
        }
        res.status(200);
        return  gson.toJson(null);
    }


    private Object registerHandler(Request req, Response res) {
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
            if (e.getCause() instanceof java.sql.SQLException){
                res.status(500);
                return gson.toJson(Map.of("message", "Error: server error"));
            }
            // if register is not successful, then throw 403 error
            res.status(403);
            return gson.toJson(Map.of("message", "Error: already taken"));
        } catch (Exception e){
            res.status(500);
            return gson.toJson(Map.of("message", "Error: server error"));
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
