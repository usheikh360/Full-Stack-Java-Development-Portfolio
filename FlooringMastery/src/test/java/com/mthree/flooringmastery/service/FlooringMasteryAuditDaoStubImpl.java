/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringMasteryAduitDao;
import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryAuditDaoStubImpl implements FlooringMasteryAduitDao{
    public FlooringMasteryAuditDaoStubImpl() {
    }
    
    @Override
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException {
        
    }
}
