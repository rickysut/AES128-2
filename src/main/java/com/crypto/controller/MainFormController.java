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
import com.crypto.utility.DbHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private MenuItem admLog;
    @FXML
    private Menu admUserid;
    
    
    
    Prefs pref = new Prefs();
    
    int count = 0;
    static DetailLelangController DetailControllerHandle;
    DbHandler objDBHandler;
    Connection con; 
    private PreparedStatement pst;
    
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
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        
        
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
                String SQL = "Insert into logging (log_date, log_kode, log_nama, log_value) values (now(), '"+
                           pref.getPrefs("loged_kode") + "', '" +
                           pref.getPrefs("loged_user") + "', 'Logout from system')";
                con.createStatement().executeUpdate(SQL);
                
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
            content, true);
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
            "INPUT DATA",
            content, true);
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
            "INPUT DATA",
            content, true);
        //Set MDI Size
        mdiWindow.setMinSize(650, 520);
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
            "INPUT DATA",
            content, true);
        //Set MDI Size
        mdiWindow.setMinSize(680, 590);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow);
    }
    
     @FXML protected void admLelangClick(){
        Node content = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetailLelang.fxml"));
            content = loader.load();
            DetailControllerHandle = (DetailLelangController)loader.getController();
        } 
            catch (Exception e) {
        }
        count++;
        //Create a Default MDI Withou Icon
        
        
        MDIWindow mdiWindow = new MDIWindow("Lelang",
            new ImageView("/assets/auction.png"),
            "TRANSAKSI",
            content, true);
        //Set MDI Size
        mdiWindow.setMinSize(655, 510);
        mdiWindow.setMaxDisable(true);
        //Add it to the container
        mdiCanvas.addMDIWindow(mdiWindow); 
     }
     
     @FXML protected void admLogClick(){
        Node content = null;
        if (pref.getPrefs("loged_kode").equals("ADM001")){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogForm.fxml"));
                content = loader.load();
            } 
                catch (Exception e) {
            }
            count++;
            //Create a Default MDI Withou Icon


            MDIWindow mdiWindow = new MDIWindow("Logging",
                new ImageView("/assets/history.png"),
                "LOG AKTIFITAS",
                content, true);
            //Set MDI Size
            mdiWindow.setMinSize(680, 450);
            //mdiWindow.setMaxDisable(true);
            //Add it to the container
            mdiCanvas.addMDIWindow(mdiWindow);
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Otorisasi");
            alert2.setHeaderText("Akses menu tidak diijinkan!");
            alert2.show();
        } 
         
     }
     
     
     public static void setDetailKodeBarang(String kode){
         DetailControllerHandle.setKodeBarang(kode);
     }
     
     public static void setDetailKodeCustomer(String kode){
         DetailControllerHandle.setKodeCustomer(kode);
     }
     
    
}
