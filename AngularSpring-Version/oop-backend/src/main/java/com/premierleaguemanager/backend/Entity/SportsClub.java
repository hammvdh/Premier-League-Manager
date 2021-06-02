//Hammadh Arquil
// W1761780 / 2018128
package com.premierleaguemanager.backend.Entity;

import java.io.Serializable;

public class SportsClub implements Serializable {
    // private variables
    private String clubName;
    private String clubLocation;

    // constructors
    public SportsClub() {
        super();
    }
    public SportsClub(String clubName, String clubLocation) {
        this.clubName = clubName;
        this.clubLocation = clubLocation;
    }

    // getters and setters
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLocation() {
        return clubLocation;
    }

    public void setClubLocation(String clubLocation) {
        this.clubLocation = clubLocation;
    }

}
