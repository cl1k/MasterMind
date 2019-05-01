/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import java.util.List;

/**
 *
 * @author Alexm
 */
public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int id);
    Game addGame(Game game);
    boolean updateGame(Game game);
    void deleteGameById(int id);
//    List<Round> getRoundsForGame(Round rounds);
    
}
