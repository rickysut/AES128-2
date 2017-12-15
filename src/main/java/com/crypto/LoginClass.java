
package com.crypto;

import com.crypto.controller.LoginFormController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginClass extends Application{
    
    public static Stage classStage = new Stage();

    static LoginFormController myControllerHandle;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        classStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginForm.fxml"));
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        
        myControllerHandle = (LoginFormController)loader.getController();
        
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setAlwaysOnTop(true);
    
        primaryStage.show();
    }
    
    public static void showLogin() {
        classStage.show();
        myControllerHandle.clearLogin();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
