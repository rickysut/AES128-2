/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crypto.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Asus-Ricky
 */
public class DetailLelang {
    public SimpleStringProperty kode_lelang     =  new SimpleStringProperty();
    public SimpleStringProperty kode_barang     =  new SimpleStringProperty();
    public SimpleStringProperty harga           =  new SimpleStringProperty();
    public SimpleStringProperty nama_barang     =  new SimpleStringProperty();
    
    
    public String getKodeLelang(){
        return kode_lelang.get();
    }
    
    public String getHarga(){
        return harga.get();
    }
    public String getKodeBarang(){
        return kode_barang.get();
    }
    
    public String getNamaBarang(){
        return nama_barang.get();
    }
    

    
}
