/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.service;

import com.mthree.guessthenumberrestservice.data.guessTheNumberDao;
import com.mthree.guessthenumberrestservice.models.Game;
import com.mthree.guessthenumberrestservice.models.Guess;
import com.mthree.guessthenumberrestservice.models.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author umairsheikh
 */
@Component
public class guessTheNumberService {
    private final guessTheNumberDao dao;
    
    @Autowired
    public guessTheNumberService(guessTheNumberDao dao) {
        this.dao = dao;
    }
   
    
    public int callDaoToCreateGame(){
        String answer = generateInitialAnswerForGame();
        Game game = new Game();
        game.setGeneratedNumber(answer);
        game.setStatus("in progress");
        game.setGuessed("no");   
        return dao.createGame(game);     
    }
    
    public List<Game> callDaoToGetAllGames(){
       return dao.getAllGames();
    }
    
    
    public boolean isValidGuess(Guess guess){//method used in the controller
        String guessedNumber = guess.getGuess();
        if (guessedNumber.chars().distinct().count() == 4 && guessedNumber.length() == 4){
            return true;
        }return false;
    }
    
    public Round addUsersGuess(Guess guess){
        String answerForGame = dao.getAnswerForGame(guess.getGameId());// gets the answer for that game so that it can be compared
        int[] resultOfTheGuess = calculateTheResultOfTheGuess(guess, answerForGame);// a method that calculates the result in the format e0:p0 where e represents exact matches and p represents partial matches
        return dao.addGuess(guess.getGameId(), resultOfTheGuess, guess.getGuess()); 
    }

    public ResponseEntity<Game> findGameByIdFromDao(int gameId){
        Game game = dao.getGameById(gameId);
        if (game == null){
           return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
   
    public ResponseEntity<List<Round>> findRoundsByIdFromDao(int gameId){
       List<Round> rounds = dao.getListOfRoundsByGameId(gameId);
       if (rounds == null){
           return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);
       }
       return ResponseEntity.ok(rounds);
    }
    
    //generateInitialAnswerForGame IS A METHOD DESIGNED TO HANDLE THE BUSINESS LOGIC OF CREATING A UNIQUE ANSWER WHERE EACH DIGIT IS DIFFERENT
    public String generateInitialAnswerForGame(){//**METHOD CHANGED METHOD TO PUBLIC TO TEST
        Random randomNumber = new Random();
        int digit1, digit2, digit3, digit4;
        
        digit1 = randomNumber.nextInt(10);
        do{
            digit2 = randomNumber.nextInt(10);
        }while (digit2 == digit1);
        do{
            digit3 = randomNumber.nextInt(10);
        }while (digit3 == digit2 || digit3 == digit1);
        do{
            digit4 = randomNumber.nextInt(10);
        }while (digit4 == digit3 || digit4 == digit2 || digit4 == digit1);      
        
        String initialAnswerForGame = String.valueOf(digit1) + String.valueOf(digit2) + String.valueOf(digit3) + String.valueOf(digit4);
        return initialAnswerForGame;
    }
    
    //calculateTheResultOfTheGuess IS A METHOD DESIGNED TO HANDLE THE BUSINESS LOGIC OF CALCULATING THE RESULT OF THE GUESS
    public int[] calculateTheResultOfTheGuess(Guess guess, String answerForGame) {//** METHOD CHANGED TO PUBLIC TO TEST
        int[] resultOfTheGuess = new int[2];//index 0 = EXAACT MATCHES, index 1 = PARTIAL MATCHES
        String userGuessedValue = guess.getGuess();
//        The if else statements below check if the character is at the same location for an exact match
//        otherwise checks if the character at that index is included elsewhere in the string
        if (answerForGame.charAt(0) == userGuessedValue.charAt(0)){
            resultOfTheGuess[0] ++;
        }else if (answerForGame.contains(String.valueOf(userGuessedValue.charAt(0)))){
             resultOfTheGuess[1] ++;
        }
        if (answerForGame.charAt(1) == userGuessedValue.charAt(1)){
            resultOfTheGuess[0] ++;
        }else if (answerForGame.contains(String.valueOf(userGuessedValue.charAt(1)))){
            resultOfTheGuess[1] ++;
        }
        
        if (answerForGame.charAt(2) == userGuessedValue.charAt(2)){
            resultOfTheGuess[0] ++;
        }else if (answerForGame.contains(String.valueOf(userGuessedValue.charAt(2)))){
            resultOfTheGuess[1] ++;
        }
        
        if (answerForGame.charAt(3) == userGuessedValue.charAt(3)){
            resultOfTheGuess[0] ++;
        }else if (answerForGame.contains(String.valueOf(userGuessedValue.charAt(3)))){
            resultOfTheGuess[1] ++;
        }       
        return resultOfTheGuess;
    }

}
    
