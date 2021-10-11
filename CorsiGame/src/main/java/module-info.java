module com.jaredmaione.corsigame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jaredmaione.corsigame to javafx.fxml;
    exports com.jaredmaione.corsigame;
}