/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import br.com.supremeforever.mdi.MDICanvas;
import br.com.supremeforever.mdi.MDIWindow;
import com.crypto.LoginClass;
import com.crypto.Prefs;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class MainFormController implements Initializable {

    MDICanvas mdiCanvas = null;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private MenuItem adminMenu;
    @FXML
    private MenuItem admLogout;
    @FXML
    private MenuItem admAbout;
    @FXML
    private Menu admUserid;
    
    Prefs pref = new Prefs();
    
    int count = 0;
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mdiCanvas = new MDICanvas(MDICanvas.Theme.DARK);
        AnchorPane.setBottomAnchor(mdiCanvas, 0d);
        AnchorPane.setLeftAnchor(mdiCanvas, 0d);
        AnchorPane.setTopAnchor(mdiCanvas, 25d);//Button place
        AnchorPane.setRightAnchor(mdiCanvas, 0d);
        mainPane.getChildren().add(mdiCanvas);
        admUserid.setText(pref.getPrefs("loged_user"));
        
    }    
    
    @FXML protected void onMenuClose(ActionEvent event) {
        try {
            System.out.println("Close...");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Keluar");
            alert.setHeaderText("Keluar program?");
            //alert.setContentText("Logout dari layar utama?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            } 
        } catch (Exception e) {
           e.printStackTrace();
        }  
    }
    
    @FXML protected void admLogoutClick(ActionEvent event) {
        try {
            System.out.println("Logout...");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("LOGOUT");
            alert.setHeaderText("Logout dari layar utama?");
            //alert.setContentText("Logout dari layar utama?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                LoginClass.showLogin();
                Stage mainStage = (Stage) mainPane.getScene().getWindow();
                mainStage.close();
            } 
            
            
        } catch(Exception e) {
           e.printStackTrace();
        }  
        
    }
    
    @FXML protected void admAboutClick(){
        Node content = null;
        try {
            //FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            content = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"));
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("Tentang",
            new ImageView("/assets/about.png"),
            "About",
            content, false, false);
        //Set MDI Size
        mdiWindow.maxHeight(250);
        mdiWindow.maxWidth(340);
        mdiWindow.setMinSize(340, 250);
        mdiWindow.setCloseDisable(true);
        mdiWindow.setMaxDisable(true);
        mdiWindow.setMinDisable(true);
        
        
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow);
        
       
    }
    
    @FXML protected void onAdminMenuAction(ActionEvent event) {
        Node content = null;
        try {
            //FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            content = FXMLLoader.load(getClass().getResource("/fxml/MasterAdmin.fxml"));
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("AdminWindow",
            new ImageView("/assets/admin.png"),
            "Admin Management",
            content);
        //Set MDI Size
        mdiWindow.setMinSize(690, 630);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow);
        
        
    }
    
    @FXML protected void admBarangClick(ActionEvent event) {
        Node content = null;
        try {
            //FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            content = FXMLLoader.load(getClass().getResource("/fxml/MasterBarang.fxml"));
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("AdminBarang",
            new ImageView("/assets/box.png"),
            "Master Barang",
            content);
        //Set MDI Size
        mdiWindow.setMinSize(650, 500);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow);
    }
    
     @FXML protected void admCustClick(ActionEvent event) {
        Node content = null;
        try {
            //FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            content = FXMLLoader.load(getClass().getResource("/fxml/MasterCustomer.fxml"));
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("Customer",
            new ImageView("/assets/customer.png"),
            "Customer",
            content);
        //Set MDI Size
        mdiWindow.setMinSize(680, 553);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow);
    }
    
     @FXML protected void admLelangClick(){
        Node content = null;
        try {
            //FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            content = FXMLLoader.load(getClass().getResource("/fxml/SetupLelang.fxml"));
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("Lelang",
            new ImageView("/assets/auction.png"),
            "Lelang",
            content);
        //Set MDI Size
        mdiWindow.setMinSize(480, 375);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow); 
     }
     
     @FXML protected void admDetailBidClick(){
         
     }
    
}
