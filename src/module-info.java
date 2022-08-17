module Supermarket {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires java.sql;
    requires mail;
    requires activation;
    opens view;
    opens model;
    opens model.Product;
    opens model.User;
    opens model.Shop;
    opens model.Department;
    opens model.Db;
    opens model.Dao;
    opens main;
}