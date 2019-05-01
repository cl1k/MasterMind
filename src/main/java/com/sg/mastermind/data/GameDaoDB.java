/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexm
 */
@Repository
public class GameDaoDB implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT id, answer, inProgress FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game getGameById(int id) {
        final String sql = "Select id, answer, inProgress FROM game WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public Game addGame(Game game) {

        final String sql = "INSERT INTO game(answer, inProgress) VALUES(?,?);";
        GeneratedKeyHolder hodor = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.isInProgress());
            return statement;

        }, hodor);

        game.setId(hodor.getKey().intValue());

        return game;
    }

    @Override
    public boolean updateGame(Game game) {

        final String sql = "UPDATE game SET "
                + "answer = ?, "
                + "inProgress = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                game.getAnswer(),
                game.isInProgress(),
                game.getId()) > 0;
    }

    @Override
    public void deleteGameById(int id) {
        final String DELETE_GAME_ROUNDS = "DELETE FROM rounds "
                + "WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME_ROUNDS, id);

        final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
        jdbcTemplate.update(DELETE_GAME, id);
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setId(rs.getInt("id"));
            g.setAnswer(rs.getString("answer"));
            g.setInProgress(rs.getBoolean("inProgress"));
            return g;
        }

    }
}
