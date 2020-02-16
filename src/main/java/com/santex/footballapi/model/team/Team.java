package com.santex.footballapi.model.team;

import com.santex.footballapi.model.competition.Competition;
import com.santex.footballapi.model.player.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.Data;

import java.util.HashSet;
import javax.persistence.*;
import java.util.Set;

@Data

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "team_player",
        joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
    private Set<Player> players;
}
