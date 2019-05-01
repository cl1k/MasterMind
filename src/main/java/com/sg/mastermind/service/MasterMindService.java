/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.service;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

import java.util.List;

/**
 *
 * @author Alexm
 */
public interface MasterMindService {
    List<Game> getAllGames();
    Game getGameById(int id);
    Game createGame();
    boolean updateGame(Game game);
    List<Round> getAllRounds(int gameId);
    Round addRounds(Round round);
    List<Round> getRoundsForGame(int gameId);
}
