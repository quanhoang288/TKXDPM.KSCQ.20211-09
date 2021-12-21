//package ecobike;
//
//import ecobike.utils.Configs;
//import ecobike.view.DockListHandler;
//import javafx.application.Application;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class App extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        DockListHandler dockListHandler = new DockListHandler(stage, Configs.DOCK_LIST_PATH);
//        dockListHandler.setScreenTitle("Home Screen");
//        dockListHandler.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}