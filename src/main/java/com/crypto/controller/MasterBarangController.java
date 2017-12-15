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
import com.crypto.utility.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;


public class MasterBarangController implements Initializable {
    
    @FXML private AnchorPane paneBarang;
    @FXML private TableView<Barang> brg_tableview;
    @FXML private TableColumn<Barang, String> col_brgnama;
    @FXML private TableColumn<Barang, String> col_brgkategori;
    @FXML private TableColumn<Barang, String> col_brgharga;
    @FXML private TableColumn<Barang, String> col_brgstock;
    @FXML private TableColumn<Barang, String> col_brgstatus;
    @FXML private JFXButton brg_closebut;
    @FXML private JFXButton brg_savebut;
    @FXML private JFXButton brg_deletebut;
    @FXML private JFXButton brg_newbut;
    @FXML private JFXTextField brg_kode;
    @FXML private JFXTextField brg_nama;
    @FXML private JFXTextField brg_harga;
    @FXML private JFXComboBox brg_kategori;
    @FXML private JFXComboBox brg_status;
    @FXML private JFXTextField brg_stok;
    @FXML private JFXToggleButton btnSwitch;
    AES128 crypt;
    
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<Barang> dataBarang;
    private int mode = 1;
    private int LastClick = -1;
    private Locale locale = Locale.getDefault();
    private final NumberFormat nf = DecimalFormat.getInstance();
    private ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>();
   
    
    UnaryOperator<Change> filter = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    
    UnaryOperator<Change> curr = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };
    TextFormatter<String> currFormatter = new TextFormatter<>(curr);
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        crypt = new AES128("abcdefghijuklmno0123456789012345", "1234567890abcdef" );
        assert brg_tableview != null : "fx:id=\"brg_tableview\" was not injected: check your FXML file 'MasterAdmin.fxml'.";
        
        col_brgnama.setCellValueFactory(new PropertyValueFactory<Barang, String>("nama"));
        col_brgkategori.setCellValueFactory(new PropertyValueFactory<Barang, String>("kategori"));
        col_brgharga.setCellValueFactory(new PropertyValueFactory<Barang, String>("harga"));
        col_brgstock.setCellValueFactory(new PropertyValueFactory<Barang, String>("stock"));
        col_brgstatus.setCellValueFactory(new PropertyValueFactory<Barang, String>("status"));
            
        
        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        
        brg_status.getItems().addAll(
                "Aktif",
                "Non-Aktif");
        brg_stok.setTextFormatter(textFormatter);
       // brg_harga.textProperty().addListener(new ChangeListener<String>() {
        //    @Override
        //    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //        newValue = nf.format(oldValue); //brg_harga.setText(oldValue);
        //    }
        //});
        buildData();
        clearFields();
    } 
    
    @FXML protected void brg_newbutclick(){
        
        clearFields();
    }
    
    @FXML protected void brg_closebutclick(){
        MDIWindow myMDI = Utility.getMDIWindow(paneBarang);
        myMDI.closeMdiWindow();
    }
    
    @FXML protected void btnSwitchClick(){
        LastClick = brg_tableview.getSelectionModel().getSelectedIndex();
        buildData();
        brg_tableviewclick();
        brg_tableview.requestFocus();
    }
    
    @FXML protected void brg_deleteclick(){
        Barang selectedData = brg_tableview.getSelectionModel().getSelectedItem();
        if (selectedData!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus data");
            alert.setHeaderText("Hapus data barang " + selectedData.getNama() + " ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    String delet = "DELETE FROM barang where kode  = ?"; 
                    pst = con.prepareStatement(delet);
                    pst.setString(1, selectedData.getKode());
                    int success = pst.executeUpdate();
                    if (success == 1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Hapus data");
                        alert2.setHeaderText("Sukses menghapus barang");
                        alert2.show();
                        buildData();
                        clearFields();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error on Building Data");   
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Hapus data");
                    alert2.setHeaderText("Gagal menghapus barang! " + e.getMessage().toString());
                    alert2.show();
                }
            } 
        }
    }
    
    @FXML protected void brg_saveclick(ActionEvent event) throws SQLException {
        if (mode == 1){ //new record
           String insert = "INSERT INTO barang(kode,nama,harga,kategori,stock,status) "
                +  "VALUES (?,?,?,?,?,?)"; 
           
            pst = con.prepareStatement(insert);
            pst.setString(1, brg_kode.getText().toUpperCase());
            pst.setString(2, crypt.encrypt(brg_nama.getText()));
            pst.setString(3, crypt.encrypt(brg_harga.getText()));
            pst.setString(4, brg_kategori.getSelectionModel().getSelectedItem().toString());
            pst.setString(5, crypt.encrypt(brg_stok.getText()));
            pst.setString(6, brg_status.getSelectionModel().getSelectedItem().toString());

            int success = pst.executeUpdate();
            if (success == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tambah data");
                alert.setHeaderText("Sukses menambah barang");
                alert.show();
                buildData();
                clearFields();
            }
        } else if (mode == 2) {
            String updat = "UPDATE  barang set nama = ?, harga = ?, kategori = ?, stock = ?, status = ?" + 
                           " WHERE kode = ?";
           
            pst = con.prepareStatement(updat);
            pst.setString(1, crypt.encrypt(brg_nama.getText()));
            pst.setString(2, crypt.encrypt(brg_harga.getText()));
            pst.setString(3, brg_kategori.getSelectionModel().getSelectedItem().toString());
            pst.setString(4, crypt.encrypt(brg_stok.getText()));
            pst.setString(5, brg_status.getSelectionModel().getSelectedItem().toString());
            pst.setString(6, brg_kode.getText());    
            
            int success = pst.executeUpdate();
            if (success == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Simpan data");
                alert.setHeaderText("Sukses menyimpan barang");
                alert.show();
                buildData();
                clearFields();
            }
        }
        
    }

    private void buildData() {
        dataBarang = FXCollections.observableArrayList();
        try{      
            String SQL = "Select * from barang Order By kode";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            while(rs.next()){  
                BooleanProperty isOn = btnSwitch.selectedProperty();
                if (isOn.get()==true){
                
                    Barang cm = new Barang();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(crypt.decrypt(rs.getString("nama")));
                    cm.kategori.set(crypt.decrypt(rs.getString("kategori")));
                    cm.harga.set(crypt.decrypt(rs.getString("harga")));
                    cm.stock.set(crypt.decrypt(rs.getString("stock")));
                    cm.status.set(rs.getString("status"));
                    dataBarang.add(cm); 
                } else {
                    Barang cm = new Barang();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(rs.getString("nama"));
                    cm.kategori.set(rs.getString("kategori"));
                    cm.harga.set(rs.getString("harga"));
                    cm.stock.set(rs.getString("stock"));
                    cm.status.set(rs.getString("status"));
                    dataBarang.add(cm);
                }
            }
            brg_tableview.setItems(dataBarang);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
        }
    }
    
    
    private void clearFields() {
        mode = 1;
        brg_kode.setText(getNewKode());
        brg_kode.setEditable(false);
        brg_nama.setText(null);
        brg_stok.setText(null);
        brg_kategori.getSelectionModel().clearSelection();
        brg_harga.setText(null);
        brg_status.getSelectionModel().clearSelection();
    }

    private String getNewKode() {
        String res = "";
        try {
            String SQL = "Select max(kode) from barang ";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if( (rs!=null) &&(rs.next()) ){
                String dres = rs.getString(1);
                if (dres!=null){
                    dres = String.valueOf(Integer.parseInt(dres.substring(3)) + 1);
                    res = "BRG" + "000".substring(dres.length()) + dres;
                } else res = "BRG001";
            } else {
                res = "BRG001";
            }
                
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on getNewKode");            
        }
        return res;
    }

    @FXML protected void brg_tableviewclick() {
        //System.out.println("Cell Click");
       Barang selectedData = brg_tableview.getSelectionModel().getSelectedItem();
       
       if ((selectedData==null)&&(LastClick!=-1)) {
           brg_tableview.getSelectionModel().select(LastClick);
           selectedData = brg_tableview.getSelectionModel().getSelectedItem();
       } 
       
       if (selectedData!=null){
            mode = 2;
            brg_kode.setText(selectedData.getKode());
            brg_kode.setEditable(false);
            brg_nama.setText(selectedData.getNama());
            brg_stok.setText(selectedData.getStock());
            brg_kategori.getSelectionModel().select(selectedData.getKategori());
            brg_harga.setText(selectedData.getHarga());
            brg_status.getSelectionModel().select(selectedData.getStatus());
       }
    }
    
    @FXML protected void brg_tableviewpressed(KeyEvent event){
       if (event.getCode().toString().equals("ENTER")){
            Barang selectedData = brg_tableview.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                mode = 2;
                brg_kode.setText(selectedData.getKode());
                brg_kode.setEditable(false);
                brg_nama.setText(selectedData.getNama());
                brg_stok.setText(selectedData.getStock());
                brg_kategori.getSelectionModel().select(selectedData.getKategori());
                brg_harga.setText(selectedData.getHarga());
                brg_status.getSelectionModel().select(selectedData.getStatus());
            }
        } 
    }
            
   @FXML protected void brg_tblviewreleased(KeyEvent event){
       if ((event.getCode().toString().equals("UP")) || 
            (event.getCode().toString().equals("DOWN")) ){
            Barang selectedData = brg_tableview.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                brg_kode.setText(selectedData.getKode());
                brg_kode.setEditable(false);
                brg_nama.setText(selectedData.getNama());
                brg_stok.setText(selectedData.getStock());
                brg_kategori.getSelectionModel().select(selectedData.getKategori());
                brg_harga.setText(selectedData.getHarga());
                brg_status.getSelectionModel().select(selectedData.getStatus());
            }
       }
   }
   
   @FXML protected void brg_stokpressed(KeyEvent event){
       
   }
            
}

