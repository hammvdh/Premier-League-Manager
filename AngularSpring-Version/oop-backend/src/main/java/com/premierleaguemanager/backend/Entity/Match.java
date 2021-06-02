//Hammadh Arquil
// W1761780 / 2018128

package com.premierleaguemanager.backend.Entity;

import java.io.Serializable;
import java.time.LocalDate;


public class Match implements Serializable {

    //creating variables to store match data

    private FootballClub homeClub;
    private FootballClub awayClub;
    private int homeClubGoalsScored;
    private int awayClubGoalsScored;
    private LocalDate date;

    //getters and setters for the variables

    public FootballClub getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(FootballClub homeClub) {
        this.homeClub = homeClub;
    }

    public FootballClub getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(FootballClub awayClub) {
        this.awayClub = awayClub;
    }

    public int getHomeClubGoalsScored() {
        return homeClubGoalsScored;
    }

    public void setHomeClubGoalsScored(int homeClubGoalsScored) {
        this.homeClubGoalsScored = homeClubGoalsScored;
    }

    public int getAwayClubGoalsScored() {
        return awayClubGoalsScored;
    }

    public void setAwayClubGoalsScored(int awayClubGoalsScored) {
        this.awayClubGoalsScored = awayClubGoalsScored;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "homeClub=" + homeClub +
                ", awayClub=" + awayClub +
                ", homeClubGoalsScored=" + homeClubGoalsScored +
                ", awayClubGoalsScored=" + awayClubGoalsScored +
                ", date=" + date +
                '}';
    }
}
