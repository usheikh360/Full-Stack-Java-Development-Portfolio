/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //do nothing 
    }
}
