/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.AES128;
import com.crypto.model.Barang;
import com.crypto.utility.DbHandler;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *

 */
public class PopupBarangController implements Initializable {
    @FXML private AnchorPane panePopup;
    @FXML private JFXButton but_pilih;
    @FXML private TableView<Barang> pop_table;
    @FXML private TableColumn<Barang, String> colKode;
    @FXML private TableColumn<Barang, String> colNama;
    @FXML private Button but_searchclear;
    @FXML private TextField txtSearch;    
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<Barang> dataBarang;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crypt = new AES128();
        assert pop_table != null : "fx:id=\"pop_table\" was not injected: check your FXML file 'PopupBarang.fxml'.";
        
        colKode.setCellValueFactory(new PropertyValueFactory<Barang, String>("Kode"));
        colNama.setCellValueFactory(new PropertyValueFactory<Barang, String>("Nama"));
        
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        
        buildData();
    } 
    
    @FXML protected void butPilihClick(){
        Barang selectedData = pop_table.getSelectionModel().getSelectedItem();
        if (selectedData!=null){
            MainFormController.setDetailKodeBarang(selectedData.getKode());
        }
        Stage PopupStage = (Stage) panePopup.getScene().getWindow();
        PopupStage.close(); 
    }

    private void buildData(){
        dataBarang = FXCollections.observableArrayList();
        
        try{      
            String SQL = "Select * from barang Order By kode";            
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while(rs.next()){ 
                Barang cm = new Barang();
                cm.kode.set(rs.getString("kode"));
                cm.nama.set(crypt.decrypt(rs.getString("nama")));
                cm.harga.set(crypt.decrypt(rs.getString("harga")));
                dataBarang.add(cm);
            }
            pop_table.setItems(dataBarang);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");            
        }
    }
    
    @FXML protected void popTableClick(MouseEvent event){
        if (event.getClickCount() == 2) {
            Barang selectedData = pop_table.getSelectionModel().getSelectedItem();
            if (selectedData!=null){
                MainFormController.setDetailKodeBarang(selectedData.getKode());
            }
            Stage PopupStage = (Stage) panePopup.getScene().getWindow();
            PopupStage.close(); 
        }
        
    }
    
    @FXML protected void butSearchClearClick(){
       txtSearch.setText("");
       txtSearch.requestFocus();
       pop_table.setItems(dataBarang);
    }
    
    
    @FXML protected void txtSearchReleased(){
        if(txtSearch.getText().isEmpty()) {
            pop_table.setItems(dataBarang);
            return;
        }
        ObservableList<Barang> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<Barang, ?>> cols = pop_table.getColumns();
        for(int i=0; i<dataBarang.size(); i++) {

            for(int j=0; j<cols.size(); j++) {
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(dataBarang.get(i)).toString();
                cellValue = cellValue.toLowerCase();
                if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                    tableItems.add(dataBarang.get(i));
                    break;
                }                        
            }

        }
        pop_table.setItems(tableItems);
    }
    
    
}
