package com.santex.footballapi.model.player;

import com.santex.footballapi.model.team.Team;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Data

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "player")
public class Player {
    @Id
    private Long id;
    private String name;
    private String position;
    private String dateOfBirth;
    private String countryOfBirth;
    private String nationality;
    @Transient
    private String role;

    @ManyToMany(mappedBy = "players")
    private Set<Team> teams = new HashSet<>();
}
