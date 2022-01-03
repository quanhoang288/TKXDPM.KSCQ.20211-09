package ecobike.view.base;

import java.io.IOException;
import java.util.Hashtable;

import ecobike.controller.DockListController;
import ecobike.controller.ReturnBikeController;
import ecobike.controller.base.BaseController;
import ecobike.utils.Configs;
import ecobike.view.BikeRentalInfoHandler;
import ecobike.view.DockListHandler;
import ecobike.view.ReturnBikeHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import views.screen.home.HomeScreenHandler;

public class BaseScreenHandler<T extends BaseController> extends FXMLScreenHandler {

    private Scene scene;
    private BaseScreenHandler prev;
    protected final Stage stage;
    protected Hashtable<String, String> messages;
    private T bController;




    private BaseScreenHandler(String screenPath) throws IOException {
        super(screenPath);
        this.stage = new Stage();
    }

    public void setPreviousScreen(BaseScreenHandler prev) {
        this.prev = prev;
    }

    public BaseScreenHandler getPreviousScreen() {
        return this.prev;
    }

    public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
        super(screenPath);
        this.stage = stage;
    }

    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        System.out.println("run show");
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void setScreenTitle(String string) {
        this.stage.setTitle(string);
    }

    public void setBController(T bController){
        this.bController = bController;
    }

    public T getBController(){
        return this.bController;
    }

    public void goBack() {
        if (this.prev != null) {
            this.prev.show();
        }
    }

    public void redirectToHome() throws IOException {
        DockListHandler dockListHandler = new DockListHandler(this.stage, Configs.DOCK_LIST_PATH);
        dockListHandler.setBController(new DockListController());
        dockListHandler.initDockList();
        dockListHandler.setScreenTitle("Home Screen");
        dockListHandler.show();
    }

    public void redirectToRentBike() throws IOException {
        //TODO: ignore button

    }

    public void redirectToReturnBike() throws IOException {
        //TODO: init controller
        ReturnBikeHandler returnBikeHandler = new ReturnBikeHandler(this.stage, Configs.RETURN_BIKE_PATH);
        returnBikeHandler.setBController(new ReturnBikeController(null));
        returnBikeHandler.initializeDockListView();
        returnBikeHandler.setScreenTitle("Return Bike Screen");
        returnBikeHandler.show();
    }

    public void redirectToRentalInfo() throws IOException {
        //TODO: init controller
        BaseScreenHandler bikeRentalInfoHandler = new BikeRentalInfoHandler(this.stage, Configs.RENTAL_STATUS_PATH);

        bikeRentalInfoHandler.setScreenTitle("Bike Rental Info Screen");
        bikeRentalInfoHandler.show();
    }


}
