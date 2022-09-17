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
    opens view.view1;
    opens view.view2;
    opens model;
    opens model.product;
    opens model.user;
    opens model.shop;
    opens model.department;
    opens model.db;
    opens model.dao;
    opens main;
    opens model.order;
    opens model.address;
    exports control;
    exports test;
    exports model;
    exports view.view1;
    exports view.view2;
}