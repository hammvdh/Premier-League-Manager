//Hammadh Arquil
// W1761780 / 2018128

package com.premierleaguemanager.backend.Controller;

import com.premierleaguemanager.backend.Entity.FootballClub;
import com.premierleaguemanager.backend.Entity.Match;
import com.premierleaguemanager.backend.Service.ClubService;
import com.premierleaguemanager.backend.Service.MatchService;
import com.premierleaguemanager.backend.Service.PlayedMatchService;
import com.premierleaguemanager.backend.Service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LeagueController {

    @Autowired
    private ClubService clubService; // initializing clb service variable to access methods within

    @Autowired
    private MatchService matchService; // initializing match service variable to access methods within

    @Autowired
    private PlayedMatchService playedMatchService; // initializing played match service variable to access methods within

    @Autowired
    private SortService sortService;// initializing sort service variable to access methods within

    @GetMapping("/allclubs") // mapping the values to the localhost link
    public ArrayList<FootballClub> getClubs(){
        return clubService.findAllClubs();
    }

    @GetMapping("/allmatches") // mapping the values to the localhost link
    public ArrayList<Match> getMatches(){
        return matchService.findAllMatches();
    }

    @GetMapping("/playedmatch") // mapping the values to the localhost link
    public ArrayList<Match> getPlayedMatch(){
        return playedMatchService.findPlayedMatch();
    }

    @GetMapping("/allclubs/sortbywins") // mapping the values to the localhost link
    public ArrayList<FootballClub> getWinSort(){
        return sortService.winSort();
    }

    @GetMapping("/allclubs/sortbypoints") // mapping the values to the localhost link
    public ArrayList<FootballClub> getPointSort(){
        return sortService.pointSort();
    }

    @GetMapping("/allclubs/sortbygoals") // mapping the values to the localhost link
    public ArrayList<FootballClub> getGoalSort(){
        return sortService.goalSort();
    }

}
