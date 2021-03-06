/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.AES128;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import com.crypto.MainClass;
import com.crypto.Prefs;
import com.crypto.utility.DbHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 */
public class LoginFormController implements Initializable {

    @FXML private javafx.scene.control.Button loginButton;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    Prefs prefs = new Prefs();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       crypt = new AES128();
       objDBHandler = new DbHandler();
       con = objDBHandler.getConnection();
       txtUsername.requestFocus();
       createMasterAdmin();
    }    
    
   public void clearLogin(){
       txtPassword.setText("");
       txtUsername.setText("");
       txtUsername.requestFocus();
   }     
    
    @FXML protected void butCloseAction(ActionEvent event){
        Platform.exit();
    }
    
    @FXML protected void handleLoginButtonAction(ActionEvent event) {
        try {
            String SQL = "Select * from admin where username = '" + crypt.encrypt(txtUsername.getText().toString().toLowerCase()) +
                    "' and password = '" + crypt.encrypt(txtPassword.getText().toString()) + "'";       
            //System.out.println(SQL);            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if (rs.next()){
                prefs.setPrefs("loged_user", txtUsername.getText().toString().toUpperCase());
                prefs.setPrefs("loged_kode", rs.getString("kode").toString());

                try {
                    SQL = "Insert into logging (log_date, log_kode, log_nama, log_value) values (now(), '"+
                           rs.getString("kode").toString() + "', '" +
                           txtUsername.getText().toString().toUpperCase() + "', 'Login to system')";
                    con.createStatement().executeUpdate(SQL); 
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainClass mainForm = new MainClass();
                mainForm.start(MainClass.classStage);

                Stage loginStage = (Stage) loginButton.getScene().getWindow();
                loginStage.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Login");
                alert.setHeaderText("Username / password salah!");
                alert.show();
                txtPassword.setText("");
                txtUsername.requestFocus();
            }
            
        } catch(Exception e) {
           e.printStackTrace();
        }  
        
    }

    private void createMasterAdmin() {
        try {
            PreparedStatement pst;
            String SQL = "Select * from admin where kode = 'ADM001'";       
            //System.out.println(SQL);            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if (!rs.next()){
                try{
                    String insert = "INSERT INTO admin(kode,nama,alamat,email,telp,username,password,status,creator) "
                         +  "VALUES (?,?,?,?,?,?,?,?,?)"; 

                     pst = con.prepareStatement(insert);
                     pst.setString(1, "ADM001");
                     pst.setString(2, crypt.encrypt("MASTER ADMIN"));
                     pst.setString(3, crypt.encrypt("nowhere"));
                     pst.setString(4, crypt.encrypt("dont@ask.com"));
                     pst.setString(5, crypt.encrypt("nophone"));
                     pst.setString(6, crypt.encrypt("admin"));
                     pst.setString(7, crypt.encrypt("admin"));
                     pst.setString(8, "Aktif");
                     pst.setString(9, "SYSTEM");

                     int success = pst.executeUpdate();
                     if (success == 1) {
                          
                     }
               } finally {
                   
               }

            } 
            
        } catch(Exception e) {
           e.printStackTrace();
        }
    }
        
}
