/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;

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
public class RoundDaoDB implements RoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT id, timestamp, guess, exactMatch, partMatch, gameId FROM rounds;";
        return jdbcTemplate.query(sql, new RoundsMapper());
    }

    @Override
    public Round getRoundsById(int id) {
        final String sql = "SELECT id, timestamp, guess, exactMatch, partMatch, gameId FROM rounds WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new RoundsMapper(), id);
    }

    @Override
    public Round addRound(Round round) {

        final String sql = "INSERT INTO round(timestamp, guess, exactMatch, partMatch, gameId) VALUES(?,?,?,?,?);";
        GeneratedKeyHolder hodor = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, round.getTimestamp());
            statement.setString(2, round.getGuess());
            statement.setInt(3, round.getExactMatch());
            statement.setInt(4, round.getPartMatch());
            statement.setInt(5, round.getGameId());
            return statement;

        }, hodor);

        round.setId(hodor.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getRoundsForGame(Game game) {
        final String SELECT_ROUNDS_FOR_GAME = "SELECT r.* FROM rounds r "
                + "JOIN game_rounds gr ON r.id = gr.roundId WHERE gr.gameId = ?";
        return jdbcTemplate.query(SELECT_ROUNDS_FOR_GAME, new RoundsMapper(),
                game.getId());
    }

    @Override
    public void deleteRoundsById(int id) {
        final String DELETE_ROUNDS = "DELETE FROM rounds WHERE id = ?";
        jdbcTemplate.update(DELETE_ROUNDS, id);
    }

    public static final class RoundsMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setId(rs.getInt("id"));
            r.setTimestamp(rs.getTimestamp("timestamp"));
            r.setGuess(rs.getString("guess"));
            r.setExactMatch(rs.getInt("exactMatch"));
            r.setPartMatch(rs.getInt("partMatch"));
            r.setGameId(rs.getInt("gameId"));
            return r;
        }

    }

}
