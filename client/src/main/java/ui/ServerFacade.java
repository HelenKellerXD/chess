package ui;

import dataaccess.DataAccessException;
import request.*;
import result.CreateGameResult;
import result.ListGamesResult;
import result.LoginResult;
import result.RegisterResult;

import com.google.gson.Gson;

public class ServerFacade {
    /**
     * so this just copies the api endpoint calls and use the info from the http video training
     */

    private final String serverURL;
    private String authToken;
    private String username;
    private final Gson gson = new Gson();
    private ClientCommunicator clientCommunicator;


    public ServerFacade(String serverURL){
        this.serverURL = serverURL;
        clientCommunicator = new ClientCommunicator(serverURL);

    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        var path = "/user";
        return clientCommunicator.makeRequest("POST",path,request,RegisterResult.class, null);

    }
    public LoginResult login(LoginRequest request)throws DataAccessException{
        var path = "/session";
        LoginResult result = clientCommunicator.makeRequest("POST",path, request, LoginResult.class, null);
        this.authToken = result.authToken();
        this.username = request.username();
        return result;

    }
    public void logout(LogoutRequest request)throws DataAccessException{
        var path = "/session";
        clientCommunicator.makeRequest("DELETE",path,request, null, authToken);
        this.authToken = null;

    }
    public ListGamesResult listGames(ListGamesRequest request)throws DataAccessException{
        var path = "/game";
        return clientCommunicator.makeRequest("GET",path, request, ListGamesResult.class, authToken);

    }
    public CreateGameResult createGame(CreateGameRequest request)throws DataAccessException{
        var path = "/game";
        return clientCommunicator.makeRequest("POST",path, request, CreateGameResult.class, authToken);

    }
    public void joinGame(JoinGameRequest request)throws DataAccessException{
        var path = "/game";
        clientCommunicator.makeRequest("PUT",path, request, null, authToken);

    }
    public void clear()throws DataAccessException{
        var path = "/db";
        clientCommunicator.makeRequest("DELETE",path, null, null, null);

    }

    public String getAuthToken(){
        return authToken;
    }

    public String getUsername(){
        return username;
    }

}
