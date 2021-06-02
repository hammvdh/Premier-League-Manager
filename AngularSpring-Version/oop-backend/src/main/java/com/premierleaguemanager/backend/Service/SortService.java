//Hammadh Arquil
// W1761780 / 2018128
package com.premierleaguemanager.backend.Service;

import com.premierleaguemanager.backend.Entity.FootballClub;
import com.premierleaguemanager.backend.PremierLeagueManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SortService {
    PremierLeagueManager plm = new PremierLeagueManager();
    public ArrayList<FootballClub> winSort(){
        plm.retrieveData();
        plm.sortByWins();
        plm.saveData();
        return plm.sortByWins();
    }

    public ArrayList<FootballClub> pointSort(){
        plm.retrieveData();
        plm.sortByPoints();
        plm.saveData();
        return plm.sortByPoints();
    }

    public ArrayList<FootballClub> goalSort(){
        plm.retrieveData();
        plm.sortByGoals();
        plm.saveData();
        return plm.sortByGoals();
    }
}
