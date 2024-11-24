/**
 * Module of the program (based on Java 9's modules)
 */
open module edu.uoc.locuocomotive{
    requires  javafx.controls;
    requires  javafx.fxml;
    requires java.desktop;
    exports edu.uoc.locuocomotive.view;
}