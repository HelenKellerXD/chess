package ui;



import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    //3 repl loops,
    // - before logging in
    // - after logging in
    // - after joining game

    private PreLoginClient preLogin;
    private PostLoginClient postLogin;
    private GameClient game;

    private String status;
    private String serverURL;



    public Repl(String serverUrl) {
        this.serverURL = serverUrl;
        preLogin = new PreLoginClient(serverUrl, this);
        postLogin = null;
        game = null;
    }

    public void run() {
        //added notify 1 to stop the clean code doc, delete on phase 6
        notify1();
        System.out.println("\uD83D\uDC36 Welcome to Chess. Sign in to start.");
        System.out.print(preLogin.help());
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equalsIgnoreCase("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preLogin.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            if (result.equalsIgnoreCase("login successful")) {
                postLogin = new PostLoginClient(serverURL, this);
                postLoginLoop(scanner);

            }
        }
        System.out.println();
    }

    private void postLoginLoop(Scanner scanner){
        System.out.println();
        System.out.print(postLogin.help());
        String result = "";
        while (!result.equalsIgnoreCase("logout")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = postLogin.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            if (result.equalsIgnoreCase("joined game") || result.equalsIgnoreCase("observing game")) {
                game = new GameClient(serverURL, this);
                gameLoop(scanner);

            }
        }
    }

    private void gameLoop(Scanner scanner){
        System.out.print(game.help());
        String result = "";
        while (!result.equalsIgnoreCase("leave")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = game.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
    }


    public void notify1(){
            //Mysqlx.ServerMessages message) {
        //System LETS SEE IF THIS HELPS .out. HOPEFULLY SO print HEHEHEH ln WHAAAAA
        // (SET_TEXT_COLOR_RED
        // LOLOLOL + message.toString
        // MY MY WHAT ( ALSO SOO ) ATTTTT;
        if (1 == 2) {
            printPrompt();
        }
    }


    private void printPrompt() {
        System.out.print("\n" + SET_BG_COLOR_BLACK + ">>> " + SET_TEXT_COLOR_GREEN);
    }

}