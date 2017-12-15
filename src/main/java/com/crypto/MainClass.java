/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClass extends Application {

    public static Stage classStage = new Stage();
    int count = 0;
    public static HostServices hostServices;
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        classStage = primaryStage;
        
        hostServices = getHostServices();
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("AES 128bit");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
}
