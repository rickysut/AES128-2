/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.model.Customer;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *

 */
public class PopupBarangController implements Initializable {
    @FXML private AnchorPane panePopup;
    @FXML private JFXButton but_pilih;
    @FXML private TableView pop_table;
    @FXML private TableColumn<Customer, String> colKode;
    @FXML private TableColumn<Customer, String> colNama;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML protected void butPilihClick(){
        
    }
    
}
