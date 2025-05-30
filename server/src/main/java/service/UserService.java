package service;

import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;

public class UserService {
    UserDAO userDAO = new MemoryUserDAO();

    public RegisterResult register(RegisterRequest registerRequest) {
        return null;
    }
    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }
    public void logout(LogoutRequest logoutRequest) {}
}
