/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
@RunWith (SpringRunner.class)
@SpringBootTest
public class GameDaoDBTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public GameDaoDBTest() {
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
     * Test of getAllGames method, of class GameDaoDB.
     */
//    @Test
    public void testGetAllGames() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        gameDao.addGame(game);
        
        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setInProgress(true);
        gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
        
    }

    /**
     * Test of getGameById method, of class GameDaoDB.
     */
    @Test
    public void testGetGameById() {
    }

    /**
     * Test of addGame method, of class GameDaoDB.
     */
//    @Test
    public void testAddGetGame() {
        
        Game game = new Game();
        game.setAnswer("9876");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testUpdateGame() {
        
        Game game = new Game();
        game.setAnswer("3456");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
        
        game.setAnswer("7895");
        
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
        
    }

    /**
     * Test of getRoundsForGame method, of class GameDaoDB.
     */
    @Test
    public void testGetRoundsForGame() {
    }
    
}
