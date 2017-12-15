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
    public SimpleStringProperty kode_pelanggan  =  new SimpleStringProperty();
    public SimpleStringProperty tgl_penawaran   =  new SimpleStringProperty();
    public SimpleStringProperty penawaran       =  new SimpleStringProperty();
    
    public String getKodeLelang(){
        return kode_lelang.get();
    }
    public String getKodePelanggan(){
        return kode_pelanggan.get();
    }
    public String getTglPenawaran(){
        return tgl_penawaran.get();
    }
    public String getPenawaran(){
        return penawaran.get();
    }

    
}
