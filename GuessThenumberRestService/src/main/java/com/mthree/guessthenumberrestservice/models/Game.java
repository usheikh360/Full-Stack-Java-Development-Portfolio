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
public class Game {
 int gameId;
 String generatedNumber;
 String status;
 String guessed;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGeneratedNumber() {
        return generatedNumber;
    }

    public void setGeneratedNumber(String generatedNumber) {
        this.generatedNumber = generatedNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuessed() {
        return guessed;
    }

    public void setGuessed(String guessed) {
        this.guessed = guessed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.gameId;
        hash = 43 * hash + Objects.hashCode(this.generatedNumber);
        hash = 43 * hash + Objects.hashCode(this.status);
        hash = 43 * hash + Objects.hashCode(this.guessed);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.generatedNumber != other.generatedNumber) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.guessed, other.guessed)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", generatedNumber=" + generatedNumber + ", status=" + status + ", guessed=" + guessed + '}';
    }
}
