package edu.uoc.locuocomotive.view;

import edu.uoc.locuocomotive.controller.LocUOComotiveController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PlayViewController {

    @FXML
    private Pane playPane;
    @FXML
    private TextField passportField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private TextField emailField;
    @FXML
    private Button submitButton;
    @FXML
    private AnchorPane formPane;
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private Button myTravelsButton;
    @FXML
    private HBox ticketContainer;
    @FXML
    private TableView<String> ticketTable;
    @FXML
    private Button closeTicketButton;
    @FXML
    public Polygon arrowPolygon;
    @FXML
    public HBox tableContainer;
    @FXML
    public TableView<String> stationTable;
    @FXML
    public Button closeTableButton;

    private LocUOComotiveController controller;

    public void initialize() {
        try {
            controller = new LocUOComotiveController("stations.txt", "routes.txt", "trains.txt");
            loadStationButtons();

            tableContainer.setVisible(false);
            closeTableButton.setOnAction(event -> {
                tableContainer.setVisible(false);
            });

            formPane.layoutXProperty().bind(playPane.widthProperty().subtract(formPane.widthProperty()).divide(2));
            formPane.layoutYProperty().bind(playPane.heightProperty().subtract(formPane.heightProperty()).divide(2));

            AnchorPane.setTopAnchor(formPane.getChildren().get(0), 0.0);
            AnchorPane.setBottomAnchor(formPane.getChildren().get(0), 0.0);
            AnchorPane.setLeftAnchor(formPane.getChildren().get(0), 0.0);
            AnchorPane.setRightAnchor(formPane.getChildren().get(0), 0.0);

            String[] seatTypes = controller.getSeatTypes();

            classComboBox.getItems().addAll(seatTypes);
            classComboBox.getSelectionModel().selectFirst();

            myTravelsButton.setOnAction(event -> openMyTravelsWindow());
            closeTicketButton.setOnAction(event -> ticketContainer.setVisible(false));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStationButtons() {
        for (String stationInfo : controller.getStationsInfo()) {
            Button stationButton = new Button();
            double buttonSize = 15;

            // Split the information by |
            String[] parts = stationInfo.split("\\|");
            String id = parts[0];
            String name = parts[1];
            String city = parts[2];
            String image = parts[3];
            String x = parts[4];
            String y = parts[5];

            stationButton.setLayoutX(Integer.parseInt(x) - buttonSize / 2);
            stationButton.setLayoutY(Integer.parseInt(y) - buttonSize / 2);
            stationButton.getStyleClass().add("station-button");

            stationButton.setId(id);

            if (Integer.parseInt(id) == controller.getCurrentStationId()) {
                stationButton.setDisable(true);

                double arrowX = stationButton.getLayoutX();
                double arrowY = stationButton.getLayoutY();

                arrowPolygon.getPoints().setAll(
                        arrowX - 2, arrowY - 18,
                        arrowX + 18, arrowY - 18,
                        arrowX + 8, arrowY + 2
                );
                arrowPolygon.setVisible(true);
            }

            Text stationName = new Text(name);
            stationName.getStyleClass().add("station-name");
            Text cityName = new Text(city);
            cityName.getStyleClass().add("city-name");

            ImageView cityImage = new ImageView();
            cityImage.setImage(new Image("/images/" + image));
            cityImage.setFitWidth(200);
            cityImage.setPreserveRatio(true);

            VBox tooltipVBox = new VBox(cityImage, stationName, cityName);
            tooltipVBox.setAlignment(Pos.CENTER);

            Tooltip stationTooltip = new Tooltip();
            stationTooltip.setGraphic(tooltipVBox);
            stationTooltip.getStyleClass().add("station-tooltip");
            stationTooltip.setShowDelay(Duration.seconds(0));

            stationButton.setOnMouseEntered(event -> {
                stationTooltip.setX(event.getScreenX());
                stationTooltip.setY(event.getScreenY() + 20);
                stationTooltip.show(stationButton, event.getScreenX(), event.getScreenY() + 20);
            });

            stationButton.setOnMouseExited(event -> {
                stationTooltip.hide();
            });

            stationButton.setOnMouseMoved(event -> {
                stationTooltip.setX(event.getScreenX());
                stationTooltip.setY(event.getScreenY() + 20);
            });

            Tooltip.install(stationButton, stationTooltip);

            stationButton.setOnAction(event -> {
                fillStationTable(Integer.parseInt(id));
                tableContainer.setVisible(true);
            });

            playPane.getChildren().add(stationButton);
        }
    }

    private void fillStationTable(int stationId) {
        stationTable.getColumns().clear();
        stationTable.getItems().clear();

        // Define a column for the route name
        TableColumn<String, String> routeColumn = new TableColumn<>("Route");
        routeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[1]));

        // Define a column for the departure time
        TableColumn<String, String> departureColumn = new TableColumn<>("Departure");
        departureColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[0].split("-")[0]));

        // Define a column for the arrival time
        TableColumn<String, String> arrivalColumn = new TableColumn<>("Arrival");
        arrivalColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[0].split("-")[1]));

        // Define a column for the ticket cost
        TableColumn<String, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[2] + " \u20AC"));

        // Define a hidden column for the origin station ID
        TableColumn<String, String> originIdColumn = new TableColumn<>("Origin Station ID");
        originIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[3]));
        originIdColumn.setVisible(false); // This will hide the column

        // Define a hidden column for the destination station ID
        TableColumn<String, String> destinationIdColumn = new TableColumn<>("Destination Station ID");
        destinationIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[4]));
        destinationIdColumn.setVisible(false); // This will hide the column

        // Define a column for the origin station
        TableColumn<String, String> originColumn = new TableColumn<>("Origin Station");
        originColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[5]));

        // Define a column for the destination station
        TableColumn<String, String> destinationColumn = new TableColumn<>("Destination Station");
        destinationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[6]));

        // Define a column for the buy button
        TableColumn<String, Void> buyColumn = new TableColumn<>("Buy");
        buyColumn.setCellFactory(param -> new TableCell<>() {
            private final Button buyButton = new Button("Buy");

            {
                buyButton.setOnAction(event -> {
                    String selectedOption = getTableView().getItems().get(getIndex());
                    stationTable.getSelectionModel().select(selectedOption);

                    formPane.setVisible(true);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        });

        submitButton.setOnAction(event -> {
            String passport = passportField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            LocalDate birthdate = birthdatePicker.getValue();
            String email = emailField.getText();
            String selectedSeatType = classComboBox.getSelectionModel().getSelectedItem();

            String selectedTravel = stationTable.getSelectionModel().getSelectedItem();
            if (selectedTravel == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a travel option before buying a ticket.");
                alert.showAndWait();
                return;
            }

            String[] parts = selectedTravel.split("\\|");
            String routeId = parts[1];
            String departureTime = parts[0].split("-")[0];
            String arrivalTime = parts[0].split("-")[1];
            String cost = parts[2];
            String originStationId = parts[3];
            String destinationStationId = parts[4];

            try {
                controller.buyTicket(passport, name, surname, birthdate, email, routeId, LocalTime.parse(departureTime), LocalTime.parse(arrivalTime), Double.parseDouble(cost), Integer.parseInt(originStationId), Integer.parseInt(destinationStationId), selectedSeatType);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            // Clear the form fields, hide the form pane and the table, and update the station buttons
            passportField.clear();
            nameField.clear();
            surnameField.clear();
            birthdatePicker.setValue(null);
            emailField.clear();

            formPane.setVisible(false);
            tableContainer.setVisible(false);

            updateCurrentStation();
        });

        stationTable.getColumns().add(routeColumn);
        stationTable.getColumns().add(departureColumn);
        stationTable.getColumns().add(arrivalColumn);
        stationTable.getColumns().add(costColumn);
        stationTable.getColumns().add(originColumn);
        stationTable.getColumns().add(destinationColumn);
        stationTable.getColumns().add(buyColumn);
        stationTable.getColumns().add(originIdColumn);
        stationTable.getColumns().add(destinationIdColumn);

        List<String> info = controller.getRoutesByStation(stationId);
        stationTable.getItems().addAll(info);
    }

    private void updateCurrentStation() {
        int currentStationId = controller.getCurrentStationId();

        for (Node node : playPane.getChildren()) {
            if (node instanceof Button stationButton) {
                int stationButtonId = Integer.parseInt(stationButton.getId());

                if (stationButtonId == currentStationId) {
                    stationButton.setDisable(true);

                    double arrowX = stationButton.getLayoutX();
                    double arrowY = stationButton.getLayoutY();

                    arrowPolygon.getPoints().setAll(
                            arrowX - 2, arrowY - 18,
                            arrowX + 18, arrowY - 18,
                            arrowX + 8, arrowY + 2
                    );
                    arrowPolygon.setVisible(true);
                } else if (stationButton.isDisabled()) {
                    stationButton.setDisable(false);
                }
            }
        }
    }

    private void openMyTravelsWindow() {
        ticketTable.getItems().clear();
        ticketTable.getColumns().clear();

        TableColumn<String, String> routeColumn = new TableColumn<>("Route");
        routeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[0]));

        TableColumn<String, String> originTimeColumn = new TableColumn<>("Time");
        originTimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[1]));

        TableColumn<String, String> originStationColumn = new TableColumn<>("Origin");
        originStationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[2]));

        TableColumn<String, String> destinationTimeColumn = new TableColumn<>("Time");
        destinationTimeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[3]));

        TableColumn<String, String> destinationStationColumn = new TableColumn<>("Destination");
        destinationStationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[4]));

        //TODO

        TableColumn<String, String> carriageSeatColumn = new TableColumn<>("Car-Seat");
        carriageSeatColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[5]));

        TableColumn<String, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().split("\\|")[6]+ " \u20AC") );

        //TODO

        ticketTable.getColumns().add(routeColumn);
        ticketTable.getColumns().add(originTimeColumn);
        ticketTable.getColumns().add(originStationColumn);
        ticketTable.getColumns().add(destinationTimeColumn);
        ticketTable.getColumns().add(destinationStationColumn);
        //TODO
        ticketTable.getColumns().add(carriageSeatColumn);
        ticketTable.getColumns().add(costColumn);
        //TODO

        List<String> tickets = controller.getAllTickets();
        ticketTable.getItems().addAll(tickets);

        // Make the ticketContainer visible
        ticketContainer.setVisible(true);
    }
}
