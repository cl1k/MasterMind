/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.service;

import com.sg.mastermind.data.GameDao;
import com.sg.mastermind.data.RoundDao;
import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alexm
 */
@Component
public class MasterMindServiceImpl implements MasterMindService {

    @Autowired
    private RoundDao roundDao;
    @Autowired
    private GameDao gameDao;

    private String generateAnswer() {
        List<Character> n = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Collections.shuffle(n);
        ArrayList<Character> a = new ArrayList<>(Arrays.asList(
                n.get(0), n.get(1), n.get(2), n.get(3)));

        String answer = "";
        for (int i = 0; i < a.size(); i++) {
            answer += a.get(i);
        }
        return answer;
    }

    private String hideAnswer(Game game) {
        if (game.isInProgress()) {
            String s = game.getAnswer();
            String hidden = s.replaceAll(s, "####");
            game.setAnswer(hidden);
            return game.getAnswer();
        } else {
            return game.getAnswer();
        }
    }

    public Round getGuess(int gameId, String guess) {
        Round round = new Round();
        round.setGameId(gameId);
        round.setGuess(guess);
        round.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        checkGameState(round);
        return round;
    }

    private void checkGameState(Round round) {
        Game game = gameDao.getGameById(round.getGameId());
        String answer = game.getAnswer();
        String guess = round.getGuess();

        int partial = 0;
        int exact = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                exact += 1;
            } else if(answer.contains(guess.substring(i, i +1))) {
                    partial += 1;
            }
        }

        round.setExactMatch(exact);
        round.setPartMatch(partial);

        if (exact == 4) {
            game.setInProgress(false);
            gameDao.updateGame(game);
        }
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> gameList = gameDao.getAllGames();
        for (Game game : gameList) {
            hideAnswer(game);
        }
        return gameList;
    }

    @Override
    public Game getGameById(int id) {
        Game game = gameDao.getGameById(id);
        hideAnswer(game);
        return game;
    }

    @Override
    public Game createGame() {
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game.setInProgress(true);
        gameDao.addGame(game);
        hideAnswer(game);
        return game;
    }

    @Override
    public boolean updateGame(Game game) {
        return gameDao.updateGame(game);
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        return roundDao.getAllRounds();
    }

    @Override
    public Round addRounds(Round round) {
        return roundDao.addRound(round);
    }

    @Override
    public List<Round> getRoundsForGame(int id) {
        return roundDao.getRoundsForGame(gameDao.getGameById(id));
    }

}
