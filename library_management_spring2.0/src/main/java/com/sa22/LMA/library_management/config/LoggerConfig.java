package com.sa22.LMA.library_management.config;


import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoggerConfig {

    public static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void logSqlException(SQLException sqlException) {
        logger.severe("Message: " + sqlException.getMessage());
        logger.severe("SQL State: " + sqlException.getSQLState());
        logger.severe("SQL Code: " + sqlException.getErrorCode());
        logger.severe("Next Exception: " + sqlException.getNextException());
    }
}
