//Hammadh Arquil
// W1761780 / 2018128
package com.premierleaguemanager.backend.Entity;

import java.io.Serializable;

public class SchoolFootballClubs extends FootballClub implements Serializable {
    private String schoolName;

    public SchoolFootballClubs() {

    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
