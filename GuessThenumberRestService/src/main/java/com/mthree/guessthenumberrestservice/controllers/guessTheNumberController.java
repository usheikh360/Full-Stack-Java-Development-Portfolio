/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.controllers;

import com.mthree.guessthenumberrestservice.data.guessTheNumberDao;
import com.mthree.guessthenumberrestservice.models.Game;
import com.mthree.guessthenumberrestservice.models.Guess;
import com.mthree.guessthenumberrestservice.models.Round;
import com.mthree.guessthenumberrestservice.service.guessTheNumberService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** 
 *
 * @author umairsheikh
 */
@CrossOrigin
@RestController
@RequestMapping()
public class guessTheNumberController {
    private final guessTheNumberDao dao;
    private final guessTheNumberService service;
    
    public guessTheNumberController(guessTheNumberDao dao, guessTheNumberService service){
        this.dao = dao;
        this.service = service;
    }    
    
    @CrossOrigin
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame(){
        return service.callDaoToCreateGame();
    }
   
    
//    @PostMapping("/guess")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Round getUsersGuess(@RequestBody Guess guess){
//        if (service.isValidGuess(guess)){
//            return service.addUsersGuess(guess);
//        }
//        return null;
//    }
    
        @PostMapping("/guess")
    public ResponseEntity<Round> getUsersGuess(@RequestBody Guess guess){
        if (service.isValidGuess(guess)){
            Round round = service.addUsersGuess(guess);
            return ResponseEntity.ok(round);//returns status code 200
        }
        return new ResponseEntity(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);//returns status code 203
    }

    @GetMapping("/game")
    public List<Game> allGameHistory(){
       return service.callDaoToGetAllGames();
    }
    
    @GetMapping("game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId){
        return service.findGameByIdFromDao(gameId);
    }
    
    @CrossOrigin
    @GetMapping("rounds/{gameId}")
    public ResponseEntity<List<Round>> findRoundsById(@PathVariable int gameId){
       return service.findRoundsByIdFromDao(gameId);
    }
    
}
