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
public class Logging {
    public SimpleStringProperty logdate  =  new SimpleStringProperty();
    public SimpleStringProperty logkode =  new SimpleStringProperty();
    public SimpleStringProperty loguser  =  new SimpleStringProperty();
    public SimpleStringProperty logstate =  new SimpleStringProperty();
    
    
    public String getLogDate(){
        return logdate.get();
    }
    public String getLogKode(){
        return logkode.get();
    }
    
    public String getLogUser(){
        return loguser.get();
    }
    
    public String getLogState(){
        return logstate.get();
    }
}
