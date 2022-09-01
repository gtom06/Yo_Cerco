module Supermarket {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.swt;
    requires java.sql;
    requires gson;
    requires jdk.jsobject;
    requires GMapsFX;
    opens view;
    opens model;
    opens model.Product;
    opens model.User;
    opens model.Shop;
    opens model.Department;
    opens model.Db;
    opens model.Dao;
    opens main;
    opens model.Order;
}