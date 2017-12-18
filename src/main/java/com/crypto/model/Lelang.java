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
public class Lelang {
    public SimpleStringProperty kode_lelang =  new SimpleStringProperty();
    public SimpleStringProperty kode_customer =  new SimpleStringProperty();
    public SimpleStringProperty tgl_lelang  =  new SimpleStringProperty();
    
    public String getKodeLelang(){
        return kode_lelang.get();
    }
    public String getKodeCustomer(){
        return kode_customer.get();
    }
    
    public String getTglLelang(){
        return tgl_lelang.get();
    }

    
}
