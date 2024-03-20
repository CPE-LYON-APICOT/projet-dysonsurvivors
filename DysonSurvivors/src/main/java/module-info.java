module com.dysonsurvivors.dysonsurvivors {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.dysonsurvivors.dysonsurvivors to javafx.fxml;
    exports com.dysonsurvivors.dysonsurvivors;
}