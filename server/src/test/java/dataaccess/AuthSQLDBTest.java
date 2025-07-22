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

public class AuthSQLDBTest {
    AuthDAO authDAO;

    @BeforeEach
    void initiate(){
        Assertions.assertDoesNotThrow(() ->
        {
            authDAO = new MySQLAuthDAO();
        });
    }

    @BeforeEach
    void wipe(){
        Assertions.assertDoesNotThrow(() ->
        {
            authDAO.clear();
        });
    }

    @Test
    @Order(1)
    void createAuthPass(){}
    void createAuthFail(){}


}
