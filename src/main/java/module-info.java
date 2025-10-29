module com.example.join_cafeteria {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.join_cafeteria to javafx.fxml;
    exports com.example.join_cafeteria;
}