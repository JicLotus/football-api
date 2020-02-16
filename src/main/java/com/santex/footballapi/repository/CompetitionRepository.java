package com.santex.footballapi.repository;

import com.santex.footballapi.model.team.Team;

import java.util.List;

import com.santex.footballapi.model.competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Query("SELECT count(c) FROM Competition c INNER JOIN c.teams t INNER JOIN t.players WHERE c.code = :code")
    public int getTotalPlayers(@Param("code") String code);
}