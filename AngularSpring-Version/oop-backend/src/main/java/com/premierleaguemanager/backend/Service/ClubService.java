//Hammadh Arquil
// W1761780 / 2018128
package com.premierleaguemanager.backend.Service;

import com.premierleaguemanager.backend.Entity.FootballClub;
import com.premierleaguemanager.backend.PremierLeagueManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClubService {
    PremierLeagueManager plm = new PremierLeagueManager();
    public ArrayList<FootballClub> findAllClubs(){
        plm.retrieveData();
        return plm.getFootball();
    }

}
