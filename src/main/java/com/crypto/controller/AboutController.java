/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import br.com.supremeforever.mdi.MDIWindow;
import br.com.supremeforever.mdi.Utility;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Asus-Ricky
 */
public class AboutController implements Initializable {

    @FXML private AnchorPane paneAbout;
    @FXML private JFXButton about_okbut;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML protected void about_okbutclick(){
        MDIWindow myMDI = Utility.getMDIWindow(paneAbout);
        myMDI.closeMdiWindow();
    }
    
}
