package dataaccess;

import model.AuthData;

import java.util.UUID;

/**
 * run CRUD on this
 *  - create
 *  - read (get/find)
 *  - update
 *  - delete
 */
public class AuthDataDAO {

    /**
     * createAuth: Create a new authorization.
     */
    void createAuth(String userName){
        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(token, userName);
    }


    /**
     * getAuth: Retrieve an authorization given an authToken.
     * deleteAuth: Delete an authorization so that it is no longer valid.
     */
}
