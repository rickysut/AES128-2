package com.crypto.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.supremeforever.mdi.MDIWindow;
import br.com.supremeforever.mdi.Utility;
import com.crypto.AES128;
import com.crypto.model.Admin;
import com.crypto.model.Customer;
import com.crypto.utility.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class

 */
public class MasterCustomerController implements Initializable {

    @FXML private AnchorPane paneCustomer;
    @FXML private TextField cust_kode;
    @FXML private TextField cust_nama;
    @FXML private TextField cust_namafb;
    @FXML private TextArea cust_alamat;
    @FXML private TextField cust_kota;
    @FXML private TextField cust_provinsi;
    @FXML private TextField cust_kodepos;
    @FXML private TextField cust_telp1;
    @FXML private TextField cust_telp2;
    @FXML private TextField cust_urlfb;
    @FXML private TextField cust_email;
    @FXML private JFXButton but_ok;
    @FXML private JFXButton but_cancel;
    @FXML private JFXButton but_update;
    @FXML private JFXButton but_delete;
    @FXML private JFXToggleButton btn_switch;
    
    @FXML private TableView<Customer> cust_table;
    @FXML private TableColumn<Customer, String> col_kode;
    @FXML private TableColumn<Customer, String> col_nama;
    @FXML private TableColumn<Customer, String> col_namafb;
    @FXML private TableColumn<Customer, String> col_alamat;
    @FXML private TableColumn<Customer, String> col_kota;
    @FXML private TableColumn<Customer, String> col_provinsi;
    @FXML private TableColumn<Customer, String> col_kodepos;
    @FXML private TableColumn<Customer, String> col_telp1;
    @FXML private TableColumn<Customer, String> col_telp2;
    @FXML private TableColumn<Customer, String> col_urlfb;
    @FXML private TableColumn<Customer, String> col_email;
    @FXML private Button but_searchclear;
    @FXML private TextField txtSearch;
    
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<Customer> dataCustomer;
    private int mode = 1;
    private int LastClick = -1;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crypt = new AES128();
        assert cust_table != null : "fx:id=\"cust_table\" was not injected: check your FXML file 'MasterCustomer.fxml'.";
        
        col_kode.setCellValueFactory(new PropertyValueFactory<Customer, String>("Kode"));
        col_nama.setCellValueFactory(new PropertyValueFactory<Customer, String>("Nama"));
        col_namafb.setCellValueFactory(new PropertyValueFactory<Customer, String>("NamaFB"));
        col_alamat.setCellValueFactory(new PropertyValueFactory<Customer, String>("Alamat"));
        col_kota.setCellValueFactory(new PropertyValueFactory<Customer, String>("Kota"));
        col_provinsi.setCellValueFactory(new PropertyValueFactory<Customer, String>("Provinsi"));
        col_kodepos.setCellValueFactory(new PropertyValueFactory<Customer, String>("KodePos"));
        col_telp1.setCellValueFactory(new PropertyValueFactory<Customer, String>("NoTelp1"));
        col_telp2.setCellValueFactory(new PropertyValueFactory<Customer, String>("NoTelp2"));
        col_urlfb.setCellValueFactory(new PropertyValueFactory<Customer, String>("UrlFB"));
        col_email.setCellValueFactory(new PropertyValueFactory<Customer, String>("Email"));

        objDBHandler = new DbHandler();
        con = objDBHandler.getConnection();
        
        
       
        buildData();
        clearFields();
    }    

    private void buildData() {
        dataCustomer = FXCollections.observableArrayList();
        try{      
            String SQL = "Select * from customer Order By kode";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            while(rs.next()){  
                BooleanProperty isOn = btn_switch.selectedProperty();
                if (isOn.get()==true){
                
                    Customer cm = new Customer();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(crypt.decrypt(rs.getString("nama")));
                    cm.namafb.set(crypt.decrypt(rs.getString("nama_fb")));
                    cm.alamat.set(crypt.decrypt(rs.getString("alamat")));
                    cm.kota.set(crypt.decrypt(rs.getString("kota")));
                    cm.provinsi.set(crypt.decrypt(rs.getString("provinsi")));
                    cm.kodepos.set(crypt.decrypt(rs.getString("kode_pos")));
                    cm.notelp1.set(crypt.decrypt(rs.getString("no_telp1")));
                    cm.notelp2.set(crypt.decrypt(rs.getString("no_telp2")));
                    cm.urlfb.set(crypt.decrypt(rs.getString("url_fb")));
                    cm.email.set(crypt.decrypt(rs.getString("email")));    
                    dataCustomer.add(cm); 
                } else {
                    Customer cm = new Customer();
                    cm.kode.set(rs.getString("kode"));
                    cm.nama.set(rs.getString("nama"));
                    cm.namafb.set(rs.getString("nama_fb"));
                    cm.alamat.set(rs.getString("alamat"));
                    cm.kota.set(rs.getString("kota"));
                    cm.provinsi.set(rs.getString("provinsi"));
                    cm.kodepos.set(rs.getString("kode_pos"));
                    cm.notelp1.set(rs.getString("no_telp1"));
                    cm.notelp2.set(rs.getString("no_telp2"));
                    cm.urlfb.set(rs.getString("url_fb"));
                    cm.email.set(rs.getString("email"));
                    dataCustomer.add(cm);
                }
            }
            cust_table.setItems(dataCustomer);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on Building Data");            
        }
    }

    private void clearFields() {
       mode = 1;
       cust_kode.setText(getNewKode());
       cust_kode.setEditable(false);
       cust_nama.setText(null);
       cust_namafb.setText(null);
       cust_alamat.setText(null);
       cust_kota.setText(null);
       cust_provinsi.setText(null);
       cust_kodepos.setText(null);
       cust_telp1.setText(null);
       cust_telp2.setText(null);
       cust_urlfb.setText(null);
       cust_email.setText(null);
    }
    
    @FXML protected void but_okclick() throws SQLException{
        if (mode == 1){ //new record
            try {
                String insert = "INSERT INTO customer(kode,nama,nama_fb,alamat,kota,provinsi,kode_pos,no_telp1,no_telp2,url_fb,email) "
                    +  "VALUES (?,?,?,?,?,?,?,?,?,?,?)"; 

                pst = con.prepareStatement(insert);
                pst.setString(1, cust_kode.getText().toUpperCase());
                pst.setString(2, crypt.encrypt(cust_nama.getText()));
                pst.setString(3, crypt.encrypt(cust_namafb.getText()));
                pst.setString(4, crypt.encrypt(cust_alamat.getText()));
                pst.setString(5, crypt.encrypt(cust_kota.getText()));
                pst.setString(6, crypt.encrypt(cust_provinsi.getText()));
                pst.setString(7, crypt.encrypt(cust_kodepos.getText()));
                pst.setString(8, crypt.encrypt(cust_telp1.getText()));
                pst.setString(9, crypt.encrypt(cust_telp2.getText()));
                pst.setString(10, crypt.encrypt(cust_urlfb.getText()));
                pst.setString(11, crypt.encrypt(cust_email.getText()));


                int success = pst.executeUpdate();
                if (success == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tambah data");
                    alert.setHeaderText("Sukses menambah customer");
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
    @FXML protected void but_cancelclick(){
       //MDIWindow myMDI = Utility.getMDIWindow(paneCustomer);
       //myMDI.closeMdiWindow(); 
       clearFields();
    }
    
    @FXML protected void but_updateclick() throws SQLException{
       if (mode == 2) {
            String updat = "UPDATE  customer set nama = ?, nama_fb = ?, alamat = ?, "+
                           " kota = ?, provinsi = ?, kode_pos = ?, no_telp1 = ?, no_telp2 = ?, url_fb = ?, email = ? " + 
                           " WHERE kode = ?";
           
            pst = con.prepareStatement(updat);
            BooleanProperty isOn = btn_switch.selectedProperty();
            if (isOn.get()==true){
                pst.setString(1, crypt.encrypt(cust_nama.getText()));
                pst.setString(2, crypt.encrypt(cust_namafb.getText()));
                pst.setString(3, crypt.encrypt(cust_alamat.getText()));
                pst.setString(4, crypt.encrypt(cust_kota.getText()));
                pst.setString(5, crypt.encrypt(cust_provinsi.getText()));
                pst.setString(6, crypt.encrypt(cust_kodepos.getText()));
                pst.setString(7, crypt.encrypt(cust_telp1.getText()));
                pst.setString(8, crypt.encrypt(cust_telp2.getText()));
                pst.setString(9, crypt.encrypt(cust_urlfb.getText()));
                pst.setString(10, crypt.encrypt(cust_email.getText()));   
                pst.setString(11, cust_kode.getText().toUpperCase());
            } else {
                pst.setString(1, cust_nama.getText());
                pst.setString(2, cust_namafb.getText());
                pst.setString(3, cust_alamat.getText());
                pst.setString(4, cust_kota.getText());
                pst.setString(5, cust_provinsi.getText());
                pst.setString(6, cust_kodepos.getText());
                pst.setString(7, cust_telp1.getText());
                pst.setString(8, cust_telp2.getText());
                pst.setString(9, cust_urlfb.getText());
                pst.setString(10, cust_email.getText());   
                pst.setString(11, cust_kode.getText().toUpperCase());
            }

            int success = pst.executeUpdate();
            if (success == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Simpan data");
                alert.setHeaderText("Sukses menyimpan customer");
                alert.show();
                buildData();
                clearFields();
            } 
       }
    }
    
    @FXML protected void but_deleteclick(){
        Customer selectedData = cust_table.getSelectionModel().getSelectedItem();
        if (selectedData!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus data");
            alert.setHeaderText("Hapus data customer " + selectedData.getNama() + " ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    String delet = "DELETE FROM customer where kode  = ?"; 
                    pst = con.prepareStatement(delet);
                    pst.setString(1, selectedData.getKode());
                    int success = pst.executeUpdate();
                    if (success == 1) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Hapus data");
                        alert2.setHeaderText("Sukses menghapus customer");
                        alert2.show();
                        buildData();
                        clearFields();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Error on Building Data");   
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Hapus data");
                    alert2.setHeaderText("Gagal menghapus customer! " + e.getMessage().toString());
                    alert2.show();
                }
            } 
        } 
    }
    @FXML protected void btn_switchclick(){
        LastClick = cust_table.getSelectionModel().getSelectedIndex();
        buildData();
        cust_tableclick();
        cust_table.requestFocus();
    }
                            
    @FXML protected void cust_tableclick(){
        //System.out.println("Cell Click");
       Customer selectedData = cust_table.getSelectionModel().getSelectedItem();
       
       if ((selectedData==null)&&(LastClick!=-1)) {
           cust_table.getSelectionModel().select(LastClick);
           selectedData = cust_table.getSelectionModel().getSelectedItem();
       } 
       
       if (selectedData!=null){
            mode = 2;
            cust_kode.setText(selectedData.getKode());
            cust_kode.setEditable(false);
            cust_nama.setText(selectedData.getNama());
            cust_namafb.setText(selectedData.getNamaFB());
            cust_alamat.setText(selectedData.getAlamat());
            cust_kota.setText(selectedData.getKota());
            cust_provinsi.setText(selectedData.getProvinsi());
            cust_kodepos.setText(selectedData.getKodePos());
            cust_telp1.setText(selectedData.getNoTelp1());
            cust_telp2.setText(selectedData.getNoTelp2());
            cust_urlfb.setText(selectedData.getUrlFB());
            cust_email.setText(selectedData.getEmail());
       } 
    }
    
    @FXML protected void cust_tablepressed(KeyEvent event){
       if (event.getCode().toString().equals("ENTER")){
            Customer selectedData = cust_table.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                mode = 2;
                cust_kode.setText(selectedData.getKode());
                cust_kode.setEditable(false);
                cust_nama.setText(selectedData.getNama());
                cust_namafb.setText(selectedData.getNamaFB());
                cust_alamat.setText(selectedData.getAlamat());
                cust_kota.setText(selectedData.getKota());
                cust_provinsi.setText(selectedData.getProvinsi());
                cust_kodepos.setText(selectedData.getKodePos());
                cust_telp1.setText(selectedData.getNoTelp1());
                cust_telp2.setText(selectedData.getNoTelp2());
                cust_urlfb.setText(selectedData.getUrlFB());
                cust_email.setText(selectedData.getEmail());
            }
        } 
    }
    
    @FXML protected void cust_tablereleased(KeyEvent event){
       if ((event.getCode().toString().equals("UP")) || 
            (event.getCode().toString().equals("DOWN")) ){
            Customer selectedData = cust_table.getSelectionModel().getSelectedItem();
            //if ((selectedData==null)&&(LastClick!=null)) selectedData = LastClick;
            if (selectedData!=null){
                cust_kode.setText(selectedData.getKode());
                cust_kode.setEditable(false);
                cust_nama.setText(selectedData.getNama());
                cust_namafb.setText(selectedData.getNamaFB());
                cust_alamat.setText(selectedData.getAlamat());
                cust_kota.setText(selectedData.getKota());
                cust_provinsi.setText(selectedData.getProvinsi());
                cust_kodepos.setText(selectedData.getKodePos());
                cust_telp1.setText(selectedData.getNoTelp1());
                cust_telp2.setText(selectedData.getNoTelp2());
                cust_urlfb.setText(selectedData.getUrlFB());
                cust_email.setText(selectedData.getEmail());
            }
       }
    }

    private String getNewKode() {
        String res = "";
        try {
            String SQL = "Select max(kode) from customer ";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            if( (rs!=null) &&(rs.next()) ){
                String dres = rs.getString(1);
                if (dres!=null){
                    dres = String.valueOf(Integer.parseInt(dres.substring(3)) + 1);
                    res = "CST" + "000".substring(dres.length()) + dres;
                } else res = "CST001";
            } else {
                res = "CST001";
            }
                
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error on getNewKode");            
        }
        return res;   
    }
    
    @FXML protected void butSearchClearClick(){
       txtSearch.setText("");
       txtSearch.requestFocus();
       cust_table.setItems(dataCustomer);
    }
    
    
    @FXML protected void txtSearchReleased(){
        if(txtSearch.getText().isEmpty()) {
            cust_table.setItems(dataCustomer);
            return;
        }
        ObservableList<Customer> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<Customer, ?>> cols = cust_table.getColumns();
        for(int i=0; i<dataCustomer.size(); i++) {

            for(int j=0; j<cols.size(); j++) {
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(dataCustomer.get(i)).toString();
                cellValue = cellValue.toLowerCase();
                if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                    tableItems.add(dataCustomer.get(i));
                    break;
                }                        
            }

        }
        cust_table.setItems(tableItems);
    }
}
