/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.models;

import java.sql.Timestamp;
import java.util.Objects;


/**
 *
 * @author Alexm
 */
public class Round {
    
    private int id;
    private Timestamp timestamp;
    private String guess;
    private int exactMatch;
    private int partMatch;
    private int gameId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public int getExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(int exactMatch) {
        this.exactMatch = exactMatch;
    }

    public int getPartMatch() {
        return partMatch;
    }

    public void setPartMatch(int partMatch) {
        this.partMatch = partMatch;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.timestamp);
        hash = 89 * hash + Objects.hashCode(this.guess);
        hash = 89 * hash + this.exactMatch;
        hash = 89 * hash + this.partMatch;
        hash = 89 * hash + this.gameId;
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
        final Round other = (Round) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.exactMatch != other.exactMatch) {
            return false;
        }
        if (this.partMatch != other.partMatch) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return true;
    }

    

}