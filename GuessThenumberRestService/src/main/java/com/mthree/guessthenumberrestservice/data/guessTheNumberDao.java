/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.data;

import com.mthree.guessthenumberrestservice.models.Game;
import com.mthree.guessthenumberrestservice.models.Guess;
import com.mthree.guessthenumberrestservice.models.Round;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author umairsheikh
 */
//@Service
public interface guessTheNumberDao {

    public List<Game> getAllGames();

    public Game getGameById(int gameId);
    
    public List<Round> getListOfRoundsByGameId(int gameId);
    
    public int createGame(Game game);
    
    public String getAnswerForGame(int gameId);

    public Round addGuess(int gameId, int [] resultOfTheGuess, String guessedValue);

    public void deleteRoundsByGameId(int gameId);

    public void deleteGameByGameId(int gameId);
    
    public void insertIntoRoundForTest(Round round);

}
