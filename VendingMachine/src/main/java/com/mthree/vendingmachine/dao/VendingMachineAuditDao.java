/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

/**
 *
 * @author umairsheikh
 */
public interface VendingMachineAuditDao {//used to write audit logs

    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
    
}
