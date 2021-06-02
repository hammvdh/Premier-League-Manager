//Hammadh Arquil
// W1761780 / 2018128

package com.premierleaguemanager.backend.Entity;

import java.io.Serializable;


public class FootballClub extends SportsClub implements Serializable {

    //creating variables to store club data
    private int numberOfWins;
    private int numberOfDraws;
    private int numberOfLosses;
    private int numberOfGoalsScored;
    private int numberOfGoalsReceived;
    private int numberOfClubPoints;
    private int numberOfMatchesPlayed;

    // empty constructor
    public FootballClub() {
    }

    //getters and setters of the variables

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }

    public int getNumberOfLosses() {
        return numberOfLosses;
    }

    public void setNumberOfLosses(int numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public int getNumberOfGoalsScored() {
        return numberOfGoalsScored;
    }

    public void setNumberOfGoalsScored(int numberOfGoalsScored) {
        this.numberOfGoalsScored = numberOfGoalsScored;
    }

    public int getNumberOfGoalsReceived() {
        return numberOfGoalsReceived;
    }

    public void setNumberOfGoalsReceived(int numberOfGoalsReceived) {
        this.numberOfGoalsReceived = numberOfGoalsReceived;
    }

    public int getNumberOfClubPoints() {
        return numberOfClubPoints;
    }

    public void setNumberOfClubPoints(int numberOfClubPoints) {
        this.numberOfClubPoints = numberOfClubPoints;
    }

    public int getNumberOfMatchesPlayed() {
        return numberOfMatchesPlayed;
    }

    public void setNumberOfMatchesPlayed(int numberOfMatchesPlayed) {
        this.numberOfMatchesPlayed = numberOfMatchesPlayed;
    }

    // toString method that returns the values.

    @Override
    public String toString() {
        return "FootballClub{" +
                "numberOfWins=" + numberOfWins +
                ", numberOfDraws=" + numberOfDraws +
                ", numberOfLosses=" + numberOfLosses +
                ", numberOfGoalsScored=" + numberOfGoalsScored +
                ", numberOfGoalsReceived=" + numberOfGoalsReceived +
                ", numberOfClubPoints=" + numberOfClubPoints +
                ", numberOfMatchesPlayed=" + numberOfMatchesPlayed +
                '}';
    }
}
