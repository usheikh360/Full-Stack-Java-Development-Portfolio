/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessthenumberrestservice.models;

import java.util.Objects;

/**
 *
 * @author umairsheikh
 */
public class Guess {
    int guessId;
    int gameId;
    String guess;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public int getGuessId() {
        return guessId;
    }

    public void setGuessId(int guessId) {
        this.guessId = guessId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.guessId;
        hash = 67 * hash + this.gameId;
        hash = 67 * hash + Objects.hashCode(this.guess);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Guess other = (Guess) obj;
        if (this.guessId != other.guessId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.guess != other.guess) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Guess{" + "guessId=" + guessId + ", gameId=" + gameId + ", guess=" + guess + '}';
    }
}
