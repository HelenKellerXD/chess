package ui;

import java.util.Arrays;

import request.LoginRequest;
import request.RegisterRequest;

import static ui.EscapeSequences.*;

public class PreLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final Repl repl;


    /*** list all the possible PreLogin actions
     * -
     *
     */
    public PreLoginClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.repl = repl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "help" -> help();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params){
        if (params.length != 2) {
            return SET_TEXT_COLOR_RED + "please enter all fields: (username, password)";

        }
        var userName = params[0];
        var password = params[1];
        LoginRequest login = new LoginRequest(userName, password);



        try {
            server.login(login);
            //return SET_TEXT_COLOR_BLUE + "login successful";
            return "login successful";
        } catch (Exception e) {
            return SET_TEXT_COLOR_RED + "Username or Password was incorrect";
        }

    }

    public String register(String... params) {

        if (params.length != 3) {
            return SET_TEXT_COLOR_RED + "please enter all fields: (username, password, email)";
        }
        var userName = params[0];
        var password = params[1];
        var email = params[2];
        RegisterRequest request = new RegisterRequest(userName, password, email);

        try{
            server.register(request);
            return SET_TEXT_COLOR_BLUE + "registration successful";

        } catch (Exception e) {
            return SET_TEXT_COLOR_RED + "registration failed: " + e.getMessage();
        }
    }



    public String help() {

        return """
                    [Options] : [what to type]
                    - Register: "register" <username> <password> <email>
                    - Login: "login" <username> <password>
                    - Quit: "quit"
                    - Help: "help"
                    
                    """;


    }

}
