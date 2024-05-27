module com.fast_food.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens model to javafx.base;
    opens com.fast_food.demo to javafx.fxml;

    exports com.fast_food.demo;
}