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
public interface AuthDAO {

    /**
     * createAuth: Create a new authorization.
     */
    void createAuth(String userName);


    /**
     * getAuth: Retrieve an authorization given an authToken.
     * deleteAuth: Delete an authorization so that it is no longer valid.
     */
    void getAuth(String authToken);
    void deleteAuth(String authToken);
}
