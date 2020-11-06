package com.mfour.OrderBook.controller;

import com.mfour.OrderBook.entities.*;
import com.mfour.OrderBook.service.*;
import com.mfour.OrderBook.service.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    OrderService orderService;

    @Autowired
    StockService stockService;

    @Autowired
    TradeService tradeService;

    @Autowired
    UserService userService;

    @PostMapping("/addOrder/{buyorsell}")
    public String addOrder(@RequestBody Order order, @PathVariable String buyorsell) throws ExceptionHandler{
        String response = orderService.createOrder(order, buyorsell);
        return response;
    }

    @GetMapping("/buyAll")
    public List<Order> getBuy(){
        return orderService.getBuyAll();
    }

    @GetMapping("/sellAll")
    public List<Order> getSell(){
        return orderService.getSellAll();
    }

    @GetMapping("/getSpread/{stockid}")
    public Double getSpread(@PathVariable int stockid) throws ExceptionHandler{
        try {
            return orderService.getSpread(stockid).doubleValue();
        }
        catch(NoSuchElementException ex){
            throw new ExceptionHandler("No such Symbol");
        }
    }

    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestBody Order order) throws ExceptionHandler{
        try{
            return orderService.deleteOrder(order);
        }
        catch(NoSuchElementException ex){
            throw new ExceptionHandler("Order does not exist");
        }
    }

    @GetMapping("/getMpid")
    public List<MpidInfo> getMpid(){
        return orderService.allMpid();
    }

    @GetMapping("/getIncompleteStocks")
    public List<String> getIncompleteStocks() {
        return stockService.getIncompleteStocks();
    }

    @GetMapping("/getAllStocks")
    public List<Stock> getAllStocks(){
        return stockService.getAllStocks();
    }

    @GetMapping("/getStock")
    public Stock getStock(@RequestBody String stockSymbol) throws ExceptionHandler{
        try {
            return stockService.getStockBySymbol(stockSymbol);
        }
        catch(NoSuchElementException ex){
            throw new ExceptionHandler("No such Symbol");
        }
    }

    @GetMapping("/getAllTrades")
    public List<Trades> getAllTrades() {
        return tradeService.getAllTrades();
    }

    @PostMapping("/addTrade/{buyId}/{sellId}/{userId}")
    public String addTrade(@PathVariable int buyId,@PathVariable int sellId,@PathVariable int userId) throws ExceptionHandler {
        return tradeService.createTrade(buyId, sellId, userId);
    }

    @GetMapping("/login/{username}/{password}")
    public UserInfo login(@PathVariable String username, @PathVariable String password) throws ExceptionHandler {
        return userService.login(username, password);
    }

    @PostMapping("/createnewuser/{username}/{password}")
    public String createUser(@PathVariable String username,@PathVariable String password) throws ExceptionHandler {
        return userService.createNewUser(username, password);
    }
}