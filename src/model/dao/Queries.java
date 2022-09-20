package model.dao;

import exceptions.FileElaborationException;
import model.Constants;

import model.ConstantsExceptions;
import model.db.DbHelper;
import model.order.Order;
import model.order.Payment;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Queries {
    private static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    private static final String SELECT_DISTINCT_ALL_FROM_SHOP = "SELECT DISTINCT * FROM shop ";
    private static final String AND_TYPE ="AND type = ?";
    private static final String TWO_VALUES = "VALUES (?, ?)";
    private static final String WHERE_USERNAME = "WHERE username = ?";
    private static final String AND_TIME = "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? ";
    private static final Connection conn = DbHelper.getInstance().getConnection();

    private Queries(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

}
