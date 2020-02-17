package com.santex.footballapi.model.competition;

import com.santex.footballapi.model.team.Team;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "teams")

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "competition")
public class Competition {
    @Id
    private Long id;
    private String name;
    private String code;
    private String areaName;
    @Transient
    private Area area;

     
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "competition_team",
        joinColumns = @JoinColumn(name = "competition_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Team> teams;

}
