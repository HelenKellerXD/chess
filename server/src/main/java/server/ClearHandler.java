package server;

import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    public Object handle (Request req, Response res) throws DataAccessException {
        return null;
    }
}
