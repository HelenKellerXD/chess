package dataaccess;

import model.UserData;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    private Map<String, UserData> userDB = new HashMap<>();

    @Override
    public void createUser(String username, String password, String email){
        UserData userData = new UserData(username, password, email);
        userDB.put(username, userData);
    }

    @Override
    public UserData getUser(String userName){
        if (userDB.containsKey(userName)){
            return userDB.get(userName);
        }
        else{
            return null;
        }
    }

    @Override
    public void insertUser(UserData u){

    }

    @Override
    public void clear() {

    }
}
