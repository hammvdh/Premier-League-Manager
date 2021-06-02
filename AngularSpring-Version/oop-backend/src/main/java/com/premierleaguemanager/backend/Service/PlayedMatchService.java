//Hammadh Arquil
// W1761780 / 2018128
package com.premierleaguemanager.backend.Service;

import com.premierleaguemanager.backend.Entity.Match;
import com.premierleaguemanager.backend.PremierLeagueManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlayedMatchService {
    PremierLeagueManager plm = new PremierLeagueManager();
    public ArrayList<Match> findPlayedMatch(){
        plm.retrieveData();
        plm.clearPlayedMatch();
        plm.playedMatch();
        plm.saveData();
        return plm.getPlayedMatchArray();
    }
}
