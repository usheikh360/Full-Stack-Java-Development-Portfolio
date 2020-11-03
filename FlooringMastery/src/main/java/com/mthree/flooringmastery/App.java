/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery;

import com.mthree.flooringmastery.dao.FlooringMasteryAduitDao;
import com.mthree.flooringmastery.dao.FlooringMasteryAduitDaoFileImpl;
import com.mthree.flooringmastery.dao.FlooringMasteryDao;
import com.mthree.flooringmastery.dao.FlooringMasteryDaoFileImpl;
import com.mthree.flooringmastery.service.FlooringMasteryServiceLayer;
import com.mthree.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.mthree.flooringmastery.ui.FlooringMasteryView;
import com.mthree.flooringmastery.ui.UserIO;
import com.mthree.flooringmastery.ui.UserIOConsoleImpl;
import com.mthree.flooringmastery.controller.FlooringMasteryController;
import com.mthree.flooringmastery.service.OrderDateException;
import com.mthree.flooringmastery.service.OrderNumberException;
import com.mthree.flooringmastery.service.StateException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author umairsheikh
 */
public class App {
    public static void main(String [] args) throws OrderDateException, OrderNumberException, StateException{

//Please note I have kept the below commented code for refrence purposes
//        UserIO io = new UserIOConsoleImpl();
//        FlooringMasteryDao dao = new FlooringMasteryDaoFileImpl();
//        FlooringMasteryView view = new FlooringMasteryView(io);
//        FlooringMasteryAduitDao audit = new FlooringMasteryAduitDaoFileImpl();
//        FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerImpl(dao, audit);
//        FlooringMasteryController controller = new FlooringMasteryController(service, view);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
}
   
}
