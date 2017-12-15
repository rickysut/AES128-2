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
public class Winner {
    public SimpleStringProperty tgl_lelang      =  new SimpleStringProperty();
    public SimpleStringProperty kode_barang     =  new SimpleStringProperty();
    public SimpleStringProperty kode_pelanggan  =  new SimpleStringProperty();
    public SimpleStringProperty harga_final     =  new SimpleStringProperty();
    
    public String getTglLelang(){
        return tgl_lelang.get();
    }
    public String getKodeBarang(){
        return kode_barang.get();
    }
    public String getKodePelanggan(){
        return kode_pelanggan.get();
    }
    public String getHargaFinal(){
        return harga_final.get();
    }


    
}
