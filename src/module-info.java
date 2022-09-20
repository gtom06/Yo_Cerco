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
    opens model.product;
    opens model.user;
    opens model.shop;
    opens model.department;
    opens model.db;
    opens dao;
    opens main;
    opens model.order;
    opens model.address;
    opens constants;
    exports control;
    exports test;
    exports view.view1;
    exports view.view2;
    exports constants;
}