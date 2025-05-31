package service;

import dataaccess.*;

public class UserService {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

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
    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }
    public void logout(LogoutRequest logoutRequest) {}

    public void clear(){
        userDAO.clear();
        authDAO.clear();
    }
}
