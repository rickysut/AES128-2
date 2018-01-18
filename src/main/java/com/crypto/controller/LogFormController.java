/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.model.Logging;
import com.crypto.utility.DbHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 */
 
public class LogFormController implements Initializable {

    @FXML private AnchorPane formLog;
    @FXML private TableView<Logging> log_tableview;
    @FXML private Button but_searchclear;
    @FXML private TextField txtSearch;
    @FXML private TableColumn<Logging, String> col_logdate;
    @FXML private TableColumn<Logging, String> col_logkode;
    @FXML private TableColumn<Logging, String> col_logname;
    @FXML private TableColumn<Logging, String> col_logstate;
    
    
    DbHandler objDBHandler;
    Connection con;
    private ObservableList<Logging> dataLogging;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_logdate.setCellValueFactory(new PropertyValueFactory<Logging, String>("LogDate"));
        col_logkode.setCellValueFactory(new PropertyValueFactory<Logging, String>("LogKode"));
        col_logname.setCellValueFactory(new PropertyValueFactory<Logging, String>("LogUser"));
        col_logstate.setCellValueFactory(new PropertyValueFactory<Logging, String>("LogState"));
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        buildData();
    } 
    
    @FXML protected void butSearchClearClick(){
       txtSearch.setText("");
       txtSearch.requestFocus();
       log_tableview.setItems(dataLogging);
    }
    
    public void buildData(){        
        dataLogging = FXCollections.observableArrayList();
        try{      
            String SQL = "Select * from logging  Order By log_id";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            while(rs.next()){  
                
                Logging cm = new Logging();
                cm.logdate.set(rs.getTimestamp("log_date").toString());
                cm.logkode.set(rs.getString("log_kode"));
                cm.loguser.set(rs.getString("log_nama"));
                cm.logstate.set(rs.getString("log_value"));
                dataLogging.add(cm); 
                
            }
            log_tableview.setItems(dataLogging);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
        }
    }
    
    @FXML protected void txtSearchReleased(){
        if(txtSearch.getText().isEmpty()) {
            log_tableview.setItems(dataLogging);
            return;
        }
        ObservableList<Logging> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<Logging, ?>> cols = log_tableview.getColumns();
        for(int i=0; i<dataLogging.size(); i++) {

            for(int j=0; j<cols.size(); j++) {
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(dataLogging.get(i)).toString();
                cellValue = cellValue.toLowerCase();
                if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                    tableItems.add(dataLogging.get(i));
                    break;
                }                        
            }

        }
       log_tableview.setItems(tableItems);
    }
    
}
