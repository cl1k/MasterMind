/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.controllers;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import com.sg.mastermind.service.MasterMindServiceImpl;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alexm
 */
@RestController
@RequestMapping("/api")
public class MasterMindController {

    private final MasterMindServiceImpl service;

    public MasterMindController(MasterMindServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/game")
    public List<Game> all() {
        return service.getAllGames();
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create() {
        return service.createGame();
    }

    @GetMapping("game/{id}")
    public ResponseEntity<Game> getById(@PathVariable int id) {
        Game result = service.getGameById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Round> guess(int gameId, String guess) {
        Round round = service.getGuess(gameId, guess);

        return ResponseEntity.ok(round);
    }

    @GetMapping("/rounds/{id}")
    public ResponseEntity<List<Round>> getByGameId(@PathVariable int id) {
        List<Round> result = service.getRoundsForGame(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
