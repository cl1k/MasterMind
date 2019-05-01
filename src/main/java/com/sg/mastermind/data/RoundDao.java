/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

import java.util.List;

/**
 *
 * @author Alexm
 */
public interface RoundDao {
    List<Round> getAllRounds();
    Round getRoundsById(int id);
    Round addRound(Round round);
    void deleteRoundsById(int id);
    List<Round> getRoundsForGame(Game game);
}
