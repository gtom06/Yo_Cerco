module Supermarket {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.base;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.fxml;
    requires javafx.swt;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;
    requires junit;
    exports control;
    opens view.view1;
    opens view.view2;
    opens model;
    opens model.Product;
    opens model.User;
    opens model.Shop;
    opens model.Department;
    opens model.Db;
    opens model.Dao;
    opens main;
    opens model.Order;
    exports test;
}