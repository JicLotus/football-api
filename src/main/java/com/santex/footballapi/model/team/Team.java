package com.santex.footballapi.model.team;

import com.santex.footballapi.model.competition.Area;
import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.player.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"players"})

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "team")
public class Team {
    @Id
    private Long id;
    private String name;
    private String tla;
    private String shortName;
    private String areaName;
    private String email;

    @ManyToMany(mappedBy = "teams")
    private Set<Competition> competitions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "team_player",
        joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
    private Set<Player> players;
}
