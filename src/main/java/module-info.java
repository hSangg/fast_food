module com.fast_food.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fast_food.demo to javafx.fxml;
    exports com.fast_food.demo;
}