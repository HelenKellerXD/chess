package dataaccess;


import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoff.model.*;
import server.Server;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UserSQLDBTest {
    UserDAO userDAO;

    @BeforeEach
    void initiate(){
        Assertions.assertDoesNotThrow(() ->
        {
            userDAO = new MySQLUserDAO();
        });
    }

    @BeforeEach
    void wipe(){
        Assertions.assertDoesNotThrow(() ->
        {
            userDAO.clear();
        });
    }

    @Test
    @Order(1)
    void createUserPass(){}
    void createUserFail(){}

}
