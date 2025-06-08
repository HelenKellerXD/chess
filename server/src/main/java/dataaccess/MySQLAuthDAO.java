package dataaccess;

import model.AuthData;

public class MySQLAuthDAO implements AuthDAO{
    @Override
    public String createAuth(String userName) {
        return "";
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clear() {

    }
}
