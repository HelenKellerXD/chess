package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    ArrayList<AuthData> userData = new ArrayList<>();


    @Override
    public void createAuth(String userName) {
        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(token, userName);
    }

    @Override
    public void getAuth(String authToken) {

    }

    @Override
    public void deleteAuth(String authToken) {

    }
}
