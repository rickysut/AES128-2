/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import br.com.supremeforever.mdi.MDIWindow;
import br.com.supremeforever.mdi.Utility;
import com.crypto.AES128;
import com.crypto.model.Barang;
import com.crypto.model.Lelang;
import com.crypto.utility.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 */
public class SetupLelangController implements Initializable {
    @FXML private AnchorPane paneLelang; 
    @FXML private TableView<Lelang> lelang_table;
    @FXML private TableColumn<Lelang, String> col_kodelelang;
    @FXML private TableColumn<Lelang, String> col_kodecustomer;
    @FXML private TableColumn<Lelang, String> col_tgllelang;
    @FXML private JFXButton but_ok;
    @FXML private JFXButton but_batal;
    @FXML private JFXButton but_update;
    @FXML private JFXButton but_delete;
    @FXML private TextField kodeLelang;
    @FXML private TextField kodeCustomer;
    @FXML private DatePicker tglLelang;
    @FXML private JFXToggleButton btn_switch;
    
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<Lelang> dataLelang;
    private int mode = 1;
    private int LastClick = -1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crypt = new AES128();
        assert lelang_table != null : "fx:id=\"lelang_table\" was not injected: check your FXML file 'SetupLelang.fxml'.";
        
        col_kodelelang.setCellValueFactory(new PropertyValueFactory<Lelang, String>("KodeLelang"));
        col_kodecustomer.setCellValueFactory(new PropertyValueFactory<Lelang, String>("KodeCustomer"));
        col_tgllelang.setCellValueFactory(new PropertyValueFactory<Lelang, String>("TglLelang"));
            
        
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        
        // Converter
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };   
        tglLelang.setConverter(converter);
        tglLelang.setPromptText("dd-mm-yyyy");
        
        buildData();
        clearFields();
    }    

    private void buildData() {
        dataLelang = FXCollections.observableArrayList();
        try{      
            String SQL = "Select * from lelang Order By kode_lelang";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            while(rs.next()){  
                BooleanProperty isOn = btn_switch.selectedProperty();
                if (isOn.get()==true){
                
                    Lelang cm = new Lelang();
                    cm.kode_lelang.set(rs.getString("kode_lelang"));
                    cm.kode_customer.set(crypt.decrypt(rs.getString("kode_customer")));
                    cm.tgl_lelang.set(rs.getString("tgl_lelang"));
                    
                    dataLelang.add(cm); 
                } else {
                    Lelang cm = new Lelang();
                    cm.kode_lelang.set(rs.getString("kode_lelang"));
                    cm.kode_customer.set(rs.getString("kode_customer"));
                    cm.tgl_lelang.set(rs.getString("tgl_lelang"));
                    dataLelang.add(cm);
                }
            }
            lelang_table.setItems(dataLelang);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
        }
    }

    private void clearFields() {
        mode = 1;
        kodeLelang.setText(getNewKode());
        kodeLelang.setEditable(false);
        kodeCustomer.setText(null);
        tglLelang.setValue(LocalDate.now());
        
    }

    private String getNewKode() {
        String res = "";
        try {
            String SQL = "Select max(kode_lelang) from lelang ";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if( (rs!=null) &&(rs.next()) ){
                String dres = rs.getString(1);
                if (dres!=null){
                    dres = String.valueOf(Integer.parseInt(dres.substring(3)) + 1);
                    res = "AUC" + "000".substring(dres.length()) + dres;
                } else res = "AUC001";
            } else {
                res = "AUC001";
            }
                
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on getNewKode");            
        }
        return res;
    }
    
    @FXML protected void butOkClick() throws SQLException{
       if (mode == 1){ //new record
            try{
                String insert = "INSERT INTO lelang(kode_lelang,kode_customer,tgl_lelang) "
                    +  "VALUES (?,?,?)"; 

                pst = con.prepareStatement(insert);
                pst.setString(1, kodeLelang.getText().toUpperCase());
                pst.setString(2, crypt.encrypt(kodeCustomer.getText()));
                pst.setString(3, tglLelang.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                int success = pst.executeUpdate();
                if (success == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tambah data");
                    alert.setHeaderText("Sukses menambah lelang");
                    alert.show();
                    buildData();
                    clearFields();
                }
            } catch (NullPointerException np){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tambah data");
                alert.setHeaderText("Semua field harus diisi dahulu");
                alert.show();
            }
        } else {
            clearFields();
        } 
    }
    @FXML protected void butBatalClick(){
        MDIWindow myMDI = Utility.getMDIWindow(paneLelang);
        myMDI.closeMdiWindow(); 
    }
    
    @FXML protected void butUpdateClick() throws SQLException{
        if (mode == 2) {
            BooleanProperty isOn = btn_switch.selectedProperty();
            String updat = "UPDATE  lelang set kode_customer = ?, tgl_lelang = ?" + 
                           " WHERE kode_lelang = ?";
           
            pst = con.prepareStatement(updat);
            if (isOn.get()==true){
                pst.setString(1, crypt.encrypt(kodeCustomer.getText()));
                pst.setString(2, tglLelang.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                pst.setString(3, kodeLelang.getText());
            } else {
                pst.setString(1, kodeCustomer.getText());
                pst.setString(2, tglLelang.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                pst.setString(3, kodeLelang.getText());
            }
                
            
            int success = pst.executeUpdate();
            if (success == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Simpan data");
                alert.setHeaderText("Sukses menyimpan lelang");
                alert.show();
                buildData();
                clearFields();
            }
        }
    }
    
    @FXML protected void butDeleteClick(){
        Lelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
        if (selectedData!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus data");
            alert.setHeaderText("Hapus data lelang " + selectedData.getKodeLelang() + " ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    String delet = "DELETE FROM lelang where kode_lelang  = ?"; 
                    pst = con.prepareStatement(delet);
                    pst.setString(1, selectedData.getKodeLelang());
                    int success = pst.executeUpdate();
                    if (success == 1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Hapus data");
                        alert2.setHeaderText("Sukses menghapus lelang");
                        alert2.show();
                        buildData();
                        clearFields();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error on Building Data");   
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Hapus data");
                    alert2.setHeaderText("Gagal menghapus lelang! " + e.getMessage().toString());
                    alert2.show();
                }
            } 
        }
    }
    
    @FXML protected void btnSwitchClick(){
        LastClick = lelang_table.getSelectionModel().getSelectedIndex();
        buildData();
        lelangTableClick();
        lelang_table.requestFocus(); 
    }
    
    @FXML protected void lelangTableKeyPressed (KeyEvent event){
        if (event.getCode().toString().equals("ENTER")){
            Lelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                mode = 2;
                kodeLelang.setText(selectedData.getKodeLelang());
                kodeLelang.setEditable(false);
                kodeCustomer.setText(selectedData.getKodeCustomer());
                tglLelang.setValue(LOCAL_DATE(selectedData.getTglLelang()));
                
            }
        }
    }
    
    @FXML protected void lelangTableKeyReleased(KeyEvent event){
       if ((event.getCode().toString().equals("UP")) || 
            (event.getCode().toString().equals("DOWN")) ){
            Lelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                kodeLelang.setText(selectedData.getKodeLelang());
                kodeLelang.setEditable(false);
                kodeCustomer.setText(selectedData.getKodeCustomer());
                tglLelang.setValue(LOCAL_DATE(selectedData.getTglLelang()));
            }
       } 
    }
    
    @FXML protected void lelangTableClick(){
       Lelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
       
       if ((selectedData==null)&&(LastClick!=-1)) {
           lelang_table.getSelectionModel().select(LastClick);
           selectedData = lelang_table.getSelectionModel().getSelectedItem();
       } 
       
       if (selectedData!=null){
            mode = 2;
            kodeLelang.setText(selectedData.getKodeLelang());
            kodeLelang.setEditable(false);
            kodeCustomer.setText(selectedData.getKodeCustomer());
            BooleanProperty isOn = btn_switch.selectedProperty();
            tglLelang.setValue(LOCAL_DATE(selectedData.getTglLelang()));
       } 
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    
    @FXML protected void butSearchClick(){
        
    }
}
    
    

