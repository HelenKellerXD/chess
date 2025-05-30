package server;

import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler extends TemplateAuthTokenHandler implements Route {
    public Object handle (Request req, Response res) throws DataAccessException {
        return null;
    }
}
