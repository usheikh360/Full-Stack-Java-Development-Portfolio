/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao; 

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryAduitDaoFileImpl implements FlooringMasteryAduitDao{
    
    public static String AUDIT_FILE = "audit.txt";
    PrintWriter write;
    public FlooringMasteryAduitDaoFileImpl() throws FlooringMasteryPersistenceException {
        try {
            write = new PrintWriter(new FileWriter(AUDIT_FILE), true);
        }catch(IOException ex){
            throw new FlooringMasteryPersistenceException("Not able to persist the audit information.", ex);
        }
    }
    
    @Override
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException{
        LocalDateTime dateTimeStamp = LocalDateTime.now();
        write.println(dateTimeStamp.toString() + " : " + entry);
        write.flush();
    }
}
