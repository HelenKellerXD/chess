package ui;

public class ServerFacade {
    //3 repl loops,
    // - before logging in
    // - after logging in
    // - after joining game
    String serverURL;
    String authToken;

    public ServerFacade(String serverURL){
        this.serverURL = serverURL;
    }
}
