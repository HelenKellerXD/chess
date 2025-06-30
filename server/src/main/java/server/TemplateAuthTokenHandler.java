package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

// this class should had "fromJson" and "toJson" methods built in so handlers dont have to call Gson directly

public class TemplateAuthTokenHandler implements Route {
    public Object handle (Request req, Response res) throws DataAccessException {
        Gson gson = new Gson();
        // Request

        //deserialization step at beginning

        // video code
        //LoginRequest request = (LoginRequest)gson.fromJson(req, LoginRequest.class);

        //LoginService service = new LoginService();

        // serialization step at the end
        return gson.toJson(res);




    }
}
