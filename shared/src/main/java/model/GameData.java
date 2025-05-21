package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
    GameData addBlackPlayer(String newBlackUsername){
        return new GameData(gameID, whiteUsername,newBlackUsername,gameName,game);
    }
    GameData addWhitePlayer(String newWhiteUsername){
        return new GameData(gameID, newWhiteUsername,blackUsername,gameName,game);
    }
}
