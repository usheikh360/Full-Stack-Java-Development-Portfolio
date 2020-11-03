/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao{
    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException{
        PrintWriter write;
        
        try {
            write = new PrintWriter(new FileWriter(AUDIT_FILE, true));//true passed as an argument so that it will create the audit file if it doesn't already exist
        } catch (IOException ex) {
            throw new VendingMachinePersistenceException("Not able to persist the audit information.", ex);
        }
        LocalDateTime dateTimeStamp = LocalDateTime.now();
        write.println(dateTimeStamp.toString() + " : " + entry);
        write.flush();
    }
}
