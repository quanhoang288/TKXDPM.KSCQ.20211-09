package ecobike.view;

import ecobike.controller.BikeInfoController;
import ecobike.controller.RentBikeController;
import ecobike.controller.base.BaseController;
import ecobike.entity.Bike;
import ecobike.utils.Configs;
import ecobike.view.base.BaseScreenHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RentBikeHandler<T extends BaseController> extends BaseScreenHandler<T> {


    @FXML
    private Pagination pagination;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchBtn;


    public RentBikeHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        initialize();

    }


    private void initialize() {
        searchBtn.setOnMouseClicked((MouseEvent e) -> {
            String searchTxt = searchText.getText();
            RentBikeController ctrl = ((RentBikeController) getBController());
            String bikeId = ctrl.convertBarcodeToId(searchTxt);
            Bike bike = null;
            try {
                bike = ctrl.findById(bikeId);
                initBikeInfoScreen(bike);
            } catch (NoResultException exception) {

                PopupScreen.error("Cannot find any bike that matches provided barcode");

            }

        });
    }

    private void initBikeInfoScreen(Bike bike) {
        BikeInfoHandler bikeInfoHandler = null;
        try {
            bikeInfoHandler = new BikeInfoHandler(this.stage, Configs.BIKE_DETAIL_PATH);
        } catch (IOException e) {
            System.out.println("Sth went wrong");
            return;
        }
        bikeInfoHandler.setPreviousScreen(this);
        bikeInfoHandler.setBController(new BikeInfoController(bike));
        bikeInfoHandler.initializeInfo();
        bikeInfoHandler.show();


    }


    private GridPane createGridTemplate(int rows, int cols, int gap) {
        GridPane grid = new GridPane();
        grid.setHgap(gap);
        grid.setVgap(gap);
        grid.setPadding(new Insets(0, gap, 0, gap));


        for (int i = 0; i < cols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / cols);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            grid.getRowConstraints().add(rowConst);
        }
        return grid;

    }

    private Node createGridItem(Bike bike) {
        StackPane container = new StackPane();
        container.setStyle("-fx-background-color:lightgrey");

        Label label = new Label("Item Nr. " + bike.getType());
        label.setOnMouseClicked((MouseEvent e) -> {
            initBikeInfoScreen(bike);
        });
        container.getChildren().add(label);
        return container;
    }

    private void populateGridItems(List<Node> items, GridPane gridPane) {
        int rows = gridPane.getRowCount();
        int cols = gridPane.getColumnCount();
        int offset = 0;
        int total = items.size();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (offset >= total)
                    break;
                Node container = items.get(offset);
                GridPane.setRowIndex(container, row);
                GridPane.setColumnIndex(container, col);
                GridPane.setHgrow(container, Priority.ALWAYS);
                GridPane.setVgrow(container, Priority.ALWAYS);

                gridPane.getChildren().add(container);
                offset++;
            }
        }
    }

    public void initializeBikes() {
        this.pagination.setPageFactory((Integer index) -> {
            int rows = 3;
            int cols = 3;
            int count = rows * cols;

            List<Bike> bikes = ((RentBikeController) getBController()).loadBikeFromDB(count * index, count);
            GridPane grid = createGridTemplate(rows, cols, 10);
            populateGridItems(bikes.stream().map((item) -> createGridItem(item)).collect(Collectors.toList()), grid);


            return grid;

        });
    }
}
