/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.service;

import com.mthree.guessthenumberrestservice.App;
import com.mthree.guessthenumberrestservice.models.Game;
import com.mthree.guessthenumberrestservice.models.Guess;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author umairsheikh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class guessTheNumberServiceTest {
    
    @Autowired
    guessTheNumberService service;
    public guessTheNumberServiceTest() {
    }
  
    @Before
    public void setUp() {
    }
    
    @Test
    public void testGenerateInitialAnswerForGameAndIfAnAnswerIsValid(){ 
        String generatedAnswer = service.generateInitialAnswerForGame();
        Guess guess = new Guess();
        guess.setGuess(generatedAnswer);
        assertTrue(service.isValidGuess(guess));
    }
    
    @Test 
    public void testCalculateTheResultOfTheGuess(){
        String generatedAnswer = service.generateInitialAnswerForGame();
        Guess guess = new Guess();
        guess.setGuess(generatedAnswer);//making the guess the generatedNumber which is the answer
        
        Game game = new Game();
        game.setGeneratedNumber(generatedAnswer);
        int [] result = service.calculateTheResultOfTheGuess(guess,game.getGeneratedNumber());
        assertEquals(4,result[0]);
        assertEquals(0, result[1]);
    }
    
    
}
