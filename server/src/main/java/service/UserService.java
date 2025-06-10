package service;

import dataaccess.*;

public class UserService {
    UserDAO userDAO = new MySQLUserDAO();
    AuthDAO authDAO = new MySQLAuthDAO();

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {

        if (userDAO.getUser(registerRequest.username()) != null){
            // if user already exists, throw 403 error
            throw new DataAccessException("Username: " + registerRequest.username() + " already exists.");
        } else {
            // if user doesn't exist then create user and token
            userDAO.createUser(registerRequest.username(),registerRequest.password(), registerRequest.email());
            String authToken = authDAO.createAuth(registerRequest.username());
            // return username, token combo
            return new RegisterResult(registerRequest.username(), authToken);
        }
    }
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{
        //Check to see if username exists in userDB
        if (userDAO.getUser(loginRequest.username()) == null){
            //if user doesn't exist -> throw exception (username not found)
            throw new DataAccessException("Username: " + loginRequest.username() + " doesn't exist.");
        }
        //Check to see if login password matches database password
        else if (!loginRequest.password().equals(userDAO.getUser(loginRequest.username()).password())){
            //if passwords don't match -> throw exception (password incorrect)
            throw new DataAccessException("Password: " + loginRequest.password() + " incorrect");
        }
        //create auth token
        String token = authDAO.createAuth(loginRequest.username());
        //return loginResult (username, authToken)
        return new LoginResult(loginRequest.username(), token);

    }


    public void logout(LogoutRequest logoutRequest) throws DataAccessException{
        // check to see if user authToken is even present in the authDAO
        if(authDAO.getAuth(logoutRequest.authToken()) == null){
            // if token not present -> throw exception
            throw new DataAccessException("User is already logged out");
        }
        // delete auth token from authDAO
        authDAO.deleteAuth(logoutRequest.authToken());
    }

    public void validateToken(String authToken) throws DataAccessException{
        // check to see if user authToken is even present in the authDAO
        if(authDAO.getAuth(authToken) == null){
            // if token not present -> throw exception
            throw new DataAccessException("invalid token");
        }
    }

    public String getUsername(String authToken) throws DataAccessException{
        // check to see if user authToken is even present in the authDAO
        if(authDAO.getAuth(authToken) == null){
            // if token not present -> throw exception
            throw new DataAccessException("invalid token");
        }
        return authDAO.getAuth(authToken).username();
    }

    public void clear(){
        userDAO.clear();
        authDAO.clear();
    }
}
