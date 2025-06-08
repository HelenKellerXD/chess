package dataaccess;

import model.UserData;

public class MySQLUserDAO implements UserDAO{
    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String userName) {
        return null;
    }

    @Override
    public void clear() {

    }
}
