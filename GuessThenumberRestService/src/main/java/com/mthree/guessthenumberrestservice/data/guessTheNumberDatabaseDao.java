/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.data;

import com.mthree.guessthenumberrestservice.models.Game;
import com.mthree.guessthenumberrestservice.models.Guess;
import com.mthree.guessthenumberrestservice.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author umairsheikh 
 */ 
@Repository
public class guessTheNumberDatabaseDao implements guessTheNumberDao{
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public guessTheNumberDatabaseDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game";
              return jdbcTemplate.query(sql, new GameMapper(true));
    } 

    @Override
    public Game getGameById(int gameId) {
        final String sql = "SELECT * FROM game WHERE gameId = ?";
                return jdbcTemplate.queryForObject(sql, new GameMapper(true), gameId);
    }

    @Override
    public List<Round> getListOfRoundsByGameId(int gameId) {
        final String sql = "SELECT * FROM round WHERE gameId = ? ORDER BY dateTime DESC";//sorting the order of rounds by dateTime in descending order
        return jdbcTemplate.query(sql, new RoundMapper(), gameId);
    }

    @Override
    public int createGame(Game game) {
        final String sql = "INSERT INTO game (generatedNumber, status, guessed) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, game.getGeneratedNumber());
            preparedStatement.setString(2, game.getStatus());
            preparedStatement.setString(3, game.getGuessed());
            return preparedStatement;   
        }, keyHolder);
        
        int gameId = keyHolder.getKey().intValue();
        return gameId;
    }
    
    @Override
    public String getAnswerForGame(int gameId){//used in the service layer in the addGuess method 
        String sql = "SELECT * FROM game WHERE gameId = ?";
        Game game = jdbcTemplate.queryForObject(sql, new GameMapper(false), gameId);
        return game.getGeneratedNumber();
    }
    
    @Override
    public Round addGuess(int gameId, int [] resultOfTheGuess, String guessedValue) {

        String sql;
        if (resultOfTheGuess[0] == 4){ //index 0 is EXACT MATCHES
            sql = "UPDATE game SET status = 'finished', guessed ='yes' WHERE gameId = ?";
            jdbcTemplate.update(sql, gameId);
        }else{
            sql = "UPDATE game SET status = 'in progress', guessed ='no' WHERE gameId = ?";
            jdbcTemplate.update(sql, gameId);
        }
        
        //Below I am checking how many rounds there are currently for that game and what should be the round number for the next game 
        List<Round> roundByGameId = getListOfRoundsByGameId(gameId);
        int currentNumberOfRounds = roundByGameId.size();
        int newRoundNumberForGame = currentNumberOfRounds + 1;
        
        String result = "e:" + String.valueOf(resultOfTheGuess[0]) + "p:" + String.valueOf(resultOfTheGuess[1]); //index 0 is EXACT MATCHES and index 1 is PARTIAL MATCHES
        
        sql =  "INSERT INTO round (roundNumber, gameId, numberGuessed, result) VALUES (?,?,?,?)";
        
        jdbcTemplate.update(sql, newRoundNumberForGame, gameId, guessedValue, result);
        
        return getRoundWithResult(gameId, newRoundNumberForGame);//using to return the round object as required       
    }
      
    private Round getRoundWithResult(int gameId, int roundNumber) {
          String sql = "SELECT * FROM round WHERE gameId = ? AND roundNumber = ?";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), gameId, roundNumber);
    }

    @Override
    public void deleteRoundsByGameId(int gameId) {
        final String sql = "DELETE FROM round WHERE gameId = ?";
        jdbcTemplate.update(sql, gameId);
    }

    @Override
    public void deleteGameByGameId(int gameId) {
        final String sql = "DELETE FROM game WHERE gameId = ?";
        jdbcTemplate.update(sql, gameId);
    }
    
    
    //Below the mappers are used so that when a row is retrieved from the database it can be sent back as a JSON response

    private static class GameMapper implements RowMapper<Game> {
        
        private Boolean forSearchingGame;
        public GameMapper(Boolean forSearchingGame) {
            this.forSearchingGame = forSearchingGame;
        }
 
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException{
            
            if (forSearchingGame == false){
              Game game = new Game();
                game.setGameId(rs.getInt("gameId"));               
                game.setGeneratedNumber(rs.getString("generatedNumber"));
                game.setStatus(rs.getString("status"));
                game.setGuessed(rs.getString("guessed"));
                return game;  
            }
                Game game = new Game();
                game.setGameId(rs.getInt("gameId"));
                if (rs.getString("status").equals("finished")){
                    game.setGeneratedNumber(rs.getString("generatedNumber"));                
                }else{
                    game.setGeneratedNumber("Answer unavailable finish the game to see the answer!");
                }
                game.setStatus(rs.getString("status"));
                game.setGuessed(rs.getString("guessed"));
                return game;     
        }
    }
    
    private static class RoundMapper implements RowMapper<Round> {
        
        public RoundMapper(){
            
        }
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException{
            Round round = new Round();
            round.setRoundNumber(rs.getInt("roundNumber"));
            round.setGameId(rs.getInt("gameId"));
            round.setDate(rs.getTimestamp("dateTime").toLocalDateTime());
            round.setResult(rs.getString("result"));
            return round;
        }     
    }
    
    private static class GuessMapper implements RowMapper<Guess>{

        public GuessMapper() {
        }
        
        @Override
        public Guess mapRow(ResultSet rs, int index) throws SQLException{
            Guess guess = new Guess();
            guess.setGuessId(rs.getInt("guessId"));
            guess.setGameId(rs.getInt("gameId"));
            guess.setGuess(rs.getString("numberGuessed"));
            return guess;
        }
    }
    
    @Override
    public void insertIntoRoundForTest(Round round){//THIS METHOD IS ONLY USED FOR TESTING PURPOSES
        final String sql =  "INSERT INTO round (roundNumber, gameId, numberGuessed, result) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, round.getRoundNumber(), round.getGameId(), "1234", round.getResult());
    }
}