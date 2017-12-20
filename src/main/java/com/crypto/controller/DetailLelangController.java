/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.controller;

import com.crypto.AES128;
import com.crypto.model.DetailLelang;
import com.crypto.model.Lelang;
import com.crypto.model.TrxLelang;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

/**
 */
public class DetailLelangController implements Initializable {
    @FXML private AnchorPane paneLelang; 
    @FXML private TableView<TrxLelang> lelang_table;
    @FXML private TableColumn<TrxLelang, String> col_kodelelang;
    @FXML private TableColumn<TrxLelang, String> col_kodecustomer;
    @FXML private TableColumn<TrxLelang, String> col_tgllelang;
    @FXML private TableColumn<TrxLelang, String> col_kodebarang;
    @FXML private TableColumn<TrxLelang, String> col_total;
    
    @FXML private TableView<DetailLelang> viewTable;
    @FXML private TableColumn<DetailLelang, String> colViewKode;
    @FXML private TableColumn<DetailLelang, String> colViewNama;
    @FXML private TableColumn<DetailLelang, String> colViewHarga;
    @FXML private JFXButton but_ok;
    @FXML private JFXButton but_batal;
    @FXML private JFXButton but_update;
    @FXML private JFXButton but_delete;
    @FXML private TextField kodeLelang;
    @FXML private TextField kodeCustomer;
    @FXML private DatePicker tglLelang;
    @FXML private TextField kodeBarang;
    @FXML private JFXToggleButton btn_switch;
    @FXML private Button but_searchcust;
    @FXML private Button but_searchbarang;
    @FXML private Button but_add; 
    @FXML private Button but_searchclear;
    @FXML private TextField txtSearch;
    @FXML private Label txtTotal;
    
    
    
    
    AES128 crypt;
    
    DbHandler objDBHandler;
    Connection con;
    private PreparedStatement pst;
    private ObservableList<TrxLelang> dataTrxLelang;
    private ObservableList<DetailLelang> dataDetailLelang;
    private int mode = 1;
    private int LastClick = -1;
    //private Lelang buffLelang = new Lelang();
    //private DetailLelang buffDetail = new DetailLelang();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crypt = new AES128();
        assert lelang_table != null : "fx:id=\"lelang_table\" was not injected: check your FXML file 'SetupLelang.fxml'.";
        
        col_kodecustomer.setCellValueFactory(new PropertyValueFactory<TrxLelang, String>("KodeCustomer"));
        col_kodelelang.setCellValueFactory(new PropertyValueFactory<TrxLelang, String>("KodeLelang"));
        col_tgllelang.setCellValueFactory(new PropertyValueFactory<TrxLelang, String>("TglLelang"));
        col_kodebarang.setCellValueFactory(new PropertyValueFactory<TrxLelang, String>("KodeBarang"));
        col_total.setCellValueFactory(new PropertyValueFactory<TrxLelang, String>("Total"));
        
        colViewKode.setCellValueFactory(new PropertyValueFactory<DetailLelang, String>("KodeBarang"));
        colViewNama.setCellValueFactory(new PropertyValueFactory<DetailLelang, String>("NamaBarang"));
        colViewHarga.setCellValueFactory(new PropertyValueFactory<DetailLelang, String>("Harga"));
        
        Image imageSearch = new Image(getClass().getResourceAsStream("/assets/search.png"));
        but_searchcust.setGraphic(new ImageView(imageSearch));
        but_searchbarang.setGraphic(new ImageView(imageSearch));
        
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
        dataTrxLelang = FXCollections.observableArrayList();
        dataDetailLelang = FXCollections.observableArrayList();
        TrxLelang cm = null;
        try{      
            String SQL = "Select t1.kode_lelang, t1.kode_customer, t1.tgl_lelang, t2.kode_barang, t2.harga from lelang t1, detail_lelang t2 where t1.kode_lelang = t2.kode_lelang Order By t1.kode_lelang";            
            ResultSet rs = con.createStatement().executeQuery(SQL);  
            String LastKodeLelang = "";
            while(rs.next()){  
                String kd_lelang = rs.getString("kode_lelang");
                BooleanProperty isOn = btn_switch.selectedProperty();
                if (isOn.get()==true){
                    if (!kd_lelang.equals(LastKodeLelang)) {
                        if (cm!=null) {
                            dataTrxLelang.add(cm);
                        } // should be new , then we must add to list
                        //INITIALIZE cm
                        cm = new TrxLelang();
                        cm.total.set("0");
                        cm.kode_barang.set("");

                        cm.kode_lelang.set(kd_lelang);
                        cm.kode_customer.set(crypt.decrypt(rs.getString("kode_customer")));
                        cm.tgl_lelang.set(crypt.decrypt(rs.getString("tgl_lelang")));
                        cm.kode_barang.set(cm.getKodeBarang() + crypt.decrypt(rs.getString("kode_barang")) + ";" ) ; 
                        String tempHarga = StringUtils.remove(crypt.decrypt(rs.getString("harga")), ".") ;
                        int total = Integer.parseInt(cm.getTotal()) + Integer.parseInt(tempHarga);
                        cm.total.set(Integer.toString(total));
                        LastKodeLelang = kd_lelang;
                    } else {
                        if (cm!=null){
                            cm.kode_barang.set(cm.getKodeBarang() + crypt.decrypt(rs.getString("kode_barang")) + ";" ) ; 
                            String tempHarga = StringUtils.remove(crypt.decrypt(rs.getString("harga")), ".") ;
                            int total = Integer.parseInt(cm.getTotal()) + Integer.parseInt(tempHarga);
                            cm.total.set(Integer.toString(total));
                        }
                    }

                } else {
                    if (!kd_lelang.equals(LastKodeLelang)) {
                        if (cm!=null) {
                            dataTrxLelang.add(cm);
                        } // should be new , then we must add to list
                        //INITIALIZE cm
                        cm = new TrxLelang();
                        cm.total.set("0");
                        cm.kode_barang.set("");

                        cm.kode_lelang.set(kd_lelang);
                        cm.kode_customer.set(rs.getString("kode_customer"));
                        cm.tgl_lelang.set(rs.getString("tgl_lelang"));
                        cm.kode_barang.set(cm.getKodeBarang() + rs.getString("kode_barang") + ";" ) ; 
                        String tempHarga = StringUtils.remove(rs.getString("harga"), ".") ;
                        int total = Integer.parseInt(cm.getTotal()) + Integer.parseInt(tempHarga);
                        cm.total.set(Integer.toString(total));
                        LastKodeLelang = kd_lelang;
                    } else {
                        if (cm!=null){
                            cm.kode_barang.set(cm.getKodeBarang() + rs.getString("kode_barang") + ";" ) ; 
                            String tempHarga = StringUtils.remove(rs.getString("harga"), ".") ;
                            int total = Integer.parseInt(cm.getTotal()) + Integer.parseInt(tempHarga);
                            cm.total.set(Integer.toString(total));
                        }
                    }
                }
            } 
            
            if (cm!=null){
                dataTrxLelang.add(cm);
            }
            lelang_table.setItems(dataTrxLelang);
            //viewTable set clear
            viewTable.setItems(dataDetailLelang);
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
        kodeCustomer.setText("");
        tglLelang.setValue(LocalDate.now());
        kodeBarang.setText("");
        kodeCustomer.requestFocus();
        
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
                //insert table lelang
                String insert = "INSERT INTO lelang(kode_lelang,kode_customer,tgl_lelang) VALUES (?,?,?)"; 

                pst = con.prepareStatement(insert);
                pst.setString(1, kodeLelang.getText().toUpperCase());
                pst.setString(2, crypt.encrypt(kodeCustomer.getText()));
                pst.setString(3, crypt.encrypt(tglLelang.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

                int success = pst.executeUpdate();
                if (success == 1) {
                    //insert table detail_lelang
                    /*insert = "INSERT INTO detail_lelang(kode_lelang,kode_barang,harga) VALUES (?,?,?)"; 

                    pst = con.prepareStatement(insert);
                    pst.setString(1, kodeLelang.getText().toUpperCase());
                    pst.setString(2, crypt.encrypt(kodeBarang.getText()));
                    pst.setString(3, crypt.encrypt(tglLelang.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    */
                    
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
        clearFields(); 
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
        TrxLelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
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
                        delet = "DELETE FROM detail_lelang where kode_lelang  = ?"; 
                        pst = con.prepareStatement(delet);
                        pst.setString(1, selectedData.getKodeLelang());
                        success = pst.executeUpdate();
                        if (success == 1) {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Hapus data");
                            alert2.setHeaderText("Sukses menghapus lelang");
                            alert2.show();
                            buildData();
                            clearFields();
                        }
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
            TrxLelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
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
            TrxLelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
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
       TrxLelang selectedData = lelang_table.getSelectionModel().getSelectedItem();
       
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
    
    
    
    @FXML protected void butSearchCustClick() throws Exception{
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Parent popup = FXMLLoader.load(getClass().getResource("/fxml/PopupCustomer.fxml"));
        Scene dialogScene = new Scene(popup);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    @FXML protected void butSearchBarangClick() throws Exception{
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Parent popup = FXMLLoader.load(getClass().getResource("/fxml/PopupBarang.fxml"));
        Scene dialogScene = new Scene(popup);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    @FXML protected void butSearchClearClick(){
       txtSearch.setText("");
       txtSearch.requestFocus();
       lelang_table.setItems(dataTrxLelang);
    }
    
    
    @FXML protected void txtSearchReleased(){
        if(txtSearch.getText().isEmpty()) {
            lelang_table.setItems(dataTrxLelang);
            return;
        }
        ObservableList<TrxLelang> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<TrxLelang, ?>> cols = lelang_table.getColumns();
        for(int i=0; i<dataTrxLelang.size(); i++) {

            for(int j=0; j<cols.size(); j++) {
                TableColumn col = cols.get(j);
                String cellValue = col.getCellData(dataTrxLelang.get(i)).toString();
                cellValue = cellValue.toLowerCase();
                if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                    tableItems.add(dataTrxLelang.get(i));
                    break;
                }                        
            }

        }
        lelang_table.setItems(tableItems);
    }
    
    @FXML protected void but_addClick(){
        if ((!kodeLelang.getText().isEmpty())&&(!kodeBarang.getText().isEmpty()) ){
            try 
            {   
                DetailLelang cm = new DetailLelang();
                String SQL = "Select nama, harga from barang where kode = ?";
                pst = con.prepareStatement(SQL);
                pst.setString(1, kodeBarang.getText());
                ResultSet rs = pst.executeQuery();  
                if(rs.next()){  
                    cm.nama_barang.set(crypt.decrypt(rs.getString("nama")));
                    cm.harga.set(crypt.decrypt(rs.getString("harga")));
                    cm.kode_lelang.set(kodeLelang.getText());
                    cm.kode_barang.set(kodeBarang.getText());
                    dataDetailLelang.add(cm);
                }
                
                viewTable.setItems(dataDetailLelang);
                
                
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Error on add detail data");            
            }
        }
    }
    

    void setKodeBarang(String kode) {
        kodeBarang.setText(kode);
    }
    
    void setKodeCustomer(String kode) {
        kodeCustomer.setText(kode);
    }
    
}
    
    

