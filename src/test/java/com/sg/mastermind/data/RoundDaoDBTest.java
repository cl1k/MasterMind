/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Alexm
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    public RoundDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        games.forEach((game) -> {
            gameDao.deleteGameById(game.getId());
        });
    
        List<Round> roundList = roundDao.getAllRounds();
        roundList.forEach((rounds) -> {
            roundDao.deleteRoundsById(rounds.getId());
        });
    
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllRounds method, of class RoundDaoDB.
     */
    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        
        gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("7896");
        round.setTimestamp(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        round.setExactMatch(0);
        round.setPartMatch(0);
        round.setGameId(game.getId());
        roundDao.addRound(round);
        
        Round round2 = new Round();
        round2.setGuess("3742");
        round2.setTimestamp(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        round2.setExactMatch(1);
        round2.setPartMatch(1);
        round2.setGameId(game.getId());
        roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
        
    }

    /**
     * Test of getRoundsById method, of class RoundDaoDB.
     */
    @Test
    public void testGetRoundsByGameId() {
    }

    /**
     * Test of addRounds method, of class RoundDaoDB.
     */
    @Test
    public void testAddRounds() {
    }
    
}
