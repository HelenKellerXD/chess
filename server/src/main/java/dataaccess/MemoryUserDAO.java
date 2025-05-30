package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
    ArrayList<UserData> userData = new ArrayList<>();

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {

    }

    @Override
    public void insertUser(UserData u) throws DataAccessException {

    }
}
