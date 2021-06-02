//Hammadh Arquil
// W1761780 / 2018128

package com.premierleaguemanager.backend;

//importing modules

import com.premierleaguemanager.backend.Entity.FootballClub;
import com.premierleaguemanager.backend.Entity.Match;
import com.premierleaguemanager.backend.Interface.LeagueManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class PremierLeagueManager implements LeagueManager {
    // creating private variables to be used within the program
    private static int clubCount = 0; // private variable to store number of clubs
    private static int count; // to be used to when user saves on exit
    private ArrayList<FootballClub> football; // Arraylist that stores football clubs
    private ArrayList<Match> footballMatches; // Arraylist that stores football Matches
    private ArrayList<Match> playedMatchArray = new ArrayList<>(); //Arraylist to store a played Match for GUI
    private final Scanner sc;
    private static boolean save = true; // to make sure user saves the data if new data has been added
    private static boolean stringValidation; //boolean variable used for string validation
    private static boolean clubCheck;

    //constructor
    public PremierLeagueManager() {
        football = new ArrayList<>();
        footballMatches = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    // Football Arraylist Getter to be used to return arraylist for GUI
    public ArrayList<FootballClub> getFootball() {
        football.sort(new ComparePointValues());
        return football;
    }

    // Match Arraylist Getter to be used to return arraylist for GUI
    public ArrayList<Match> getFootballMatches() {
        footballMatches.sort(new CompareDateValues());
        return footballMatches;
    }

    // Getter used to return the arraylist containing a random generated match
    public ArrayList<Match> getPlayedMatchArray() {

        return playedMatchArray;

    }

    public static void main(String[] args) {
        PremierLeagueManager start = new PremierLeagueManager();
        start.retrieveData();
        start.menu();
    }

    //menu containing a command line menu which directs the user to a specific option
    public void menu() {
        count = 0;
        System.out.println("\n/============================================================\\");
        System.out.println("|------------------Premier League Manager--------------------|");
        System.out.println("\\============================================================/\n");

        System.out.println("--> Enter '1' to add a football club to the premier league");
        System.out.println("--> Enter '2' to remove a football club from the premier league");
        System.out.println("--> Enter '3' to display statistics of a selected club");
        System.out.println("--> Enter '4' to display all team in the premier league");
        System.out.println("--> Enter '5' to add a played match among two clubs in the premier league");
        System.out.println("--> Enter '6' to Display GUI");
        System.out.println("--> Enter '7' to save all premier league data");
        System.out.println("--> Enter '8' to exit the program");

        System.out.print("Enter value: ");
        String input = sc.nextLine();
        int option = 0;

        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n Invalid input!!! please re-enter selection... \n ");
            menu();
        }

        switch (option) {
            case 1:
                save = false;
                addClub();
                break;

            case 2:
                save = false;
                deleteClub();
                break;

            case 3:
                displayStats();
                break;

            case 4:
                displayAllTeams();
                break;

            case 5:
                save = false;
                addMatch();
                break;

            case 6:
                displayGUI();
                break;

            case 7:
                save = true;
                saveData();
                menu();
                break;

            case 8:
                if (!save) {
                    System.out.println("You have un-saved changes!!!..");
                    System.out.println("Enter '1' to save and exit");
                    System.out.println("Enter '2' to exit without saving");
                    String option2 = sc.next();
                    switch (option2) {
                        case "1":
                            count++;
                            System.out.println("Saving Program Data...\n");
                            saveData();
                            menu();

                        case "2":
                            System.out.println("Exiting Program...\n");
                            System.exit(0);

                        default:
                            System.out.println("\n Invalid input!!! Going back to menu...\n ");
                            menu();
                    }

                } else {
                    System.out.println("Exiting Program...\n");
                    System.exit(0);
                }

            default:
                System.out.println("\n Invalid input!!! please re-enter selection... \n ");
                menu();

        }
    }

    //method used to add a club to the premier league
    @Override
    public void addClub() {
        FootballClub club = new FootballClub();
        String name;
        // doing a string validation to user input
        do {
            stringValidation = false;
            System.out.print("Enter name of the football club: ");
            name = sc.nextLine();
            if ((name != null) && name.matches("^[a-z A-Z]*$")) {
                stringValidation = true;
            } else {
                System.out.println("\nYou have entered an invalid name input!! Please re-enter in the correct format!\n");
            }
        } while (!stringValidation);
        if (clubCount == 0) {
            club.setClubName(name);  //setting club name if there are no clubs in the PL
        }
        for (FootballClub check : football) {
            if (check.getClubName().equals(name)) {
                System.out.println("\nClub " + check.getClubName() + " is already in the Premier League!! Going back to menu... \n");
                menu();
            } else {
                club.setClubName(name); // setting values
                break;
            }

        }
        String location;
        // doing a string validation to user input
        do {
            stringValidation = false;
            System.out.print("Enter location of the football club: ");
            location = sc.nextLine();
            if ((location != null) && location.matches("^[a-z A-Z]*$")) {
                stringValidation = true;
            } else {
                System.out.println("\nYou have entered an invalid location input!! Please re-enter in the correct format!\n");
            }
        } while (!stringValidation);
        club.setClubLocation(location); // setting values

        int wins;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of wins this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of wins this season: ");
                sc.next();
            }
            wins = sc.nextInt();
        } while (wins <= 0);
        club.setNumberOfWins(wins); // setting values

        int loss;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of losses this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of losses this season: ");
                sc.next();
            }
            loss = sc.nextInt();
        } while (loss <= 0);
        club.setNumberOfLosses(loss); // setting values

        int draws;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of draws this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of draws this season: ");
                sc.next();
            }
            draws = sc.nextInt();
        } while (draws <= 0);
        club.setNumberOfDraws(draws); // setting values

        int matches;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of matches played this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of matches played this season: ");
                sc.next();
            }
            matches = sc.nextInt();
        } while (matches <= 0);
        club.setNumberOfMatchesPlayed(matches); // setting values

        int scored;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of goals scored this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of goals scored this season: ");
                sc.next();
            }
            scored = sc.nextInt();
        } while (scored <= 0);
        club.setNumberOfGoalsScored(scored); // setting values

        int received;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of goals received this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of goals received this season: ");
                sc.next();
            }
            received = sc.nextInt();
        } while (received <= 0);
        club.setNumberOfGoalsReceived(received); // setting values

        int points;
        // doing an integer validation to user input
        do {
            System.out.print("Enter number of points this season: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!");
                System.out.print("Enter number of points this season: ");
                sc.next();
            }
            points = sc.nextInt();
        } while (points <= 0);
        club.setNumberOfClubPoints(points); // setting values
        sc.nextLine();
        football.add(club); //adding club to the football arraylist
        clubCount++; //increasing club count by 1
        saveData();
        System.out.println("\nClub added to Premier League!\n");
        menu();
    }

    //method used to delete a club from the premier league
    @Override
    public void deleteClub() {
        //allowing user to only delete clubs when there are clubs in the league
        if (clubCount > 0) {
            System.out.print("Enter Name of the club: ");
            String input = sc.nextLine();
            boolean deleted = true;
            for (FootballClub club : football) {
                if (club.getClubName().equals(input)) {
                    deleted = true;
                    football.remove(club); //removing club from club arraylist
                    clubCount--;
                    System.out.println("\nClub " + club.getClubName() + " has been removed from the Premier League.\n");
                    break;
                } else {
                    deleted = false;
                }
            }
            if (!deleted) {
                System.out.println("\nClub not found in league!\n");
            }
        } else {
            System.out.println("There are no clubs in the premier league!");
        }
        menu();
    }

    //method used to display statistics for a specific club
    @Override
    public void displayStats() {
        if (clubCount > 0) {
            System.out.print("Insert Club Name: ");
            String input = sc.nextLine();
            boolean clubFound = true;
            //using string formatter to help with producing the club statistics table
            String format = "|%1$-20s|%2$-10s|%3$-10s|%4$-10s|%5$-14s|%6$-16s|%7$-10s|%8$-16s|\n";
            for (FootballClub club : football) {
                if (club.getClubName().equals(input)) {
                    clubFound = true;
                    System.out.println("+-----------------------------------------------------------------------------------------------------------------+");
                    System.out.println("|__________________________________________Premier League Club Statistics_________________________________________|");
                    System.out.println("+--------------------+----------+----------+----------+--------------+----------------+----------+----------------+");
                    System.out.format(format, " Club Name", " Wins", " Losses", " Draws", " Goals Scored", " Goals Received", " Points", " Matches Played");
                    System.out.println("+--------------------+----------+----------+----------+--------------+----------------+----------+----------------+");
                    System.out.format(format, " " + club.getClubName(), " " + club.getNumberOfWins(), " " + club.getNumberOfLosses(), " " + club.getNumberOfDraws(), " " + club.getNumberOfGoalsScored(), " " + club.getNumberOfGoalsReceived(), " " + club.getNumberOfClubPoints(), " " + club.getNumberOfMatchesPlayed());
                    System.out.println("+-----------------------------------------------------------------------------------------------------------------+\n");
                    break;
                } else {
                    clubFound = false;
                }
            }
            if (!clubFound) {
                System.out.println("\nClub not found in league!\n");
            }
        } else {
            System.out.println("There are no clubs in the premier league!");
        }
        menu();
    }

    //method used to display all clubs in premier league
    @Override
    public void displayAllTeams() {
        if (clubCount > 0) {
            // using the comparePointValues inner class to sort through each of the values in the football arraylist
            football.sort(new ComparePointValues());
            //using string formatter to help with producing the premier league table
            String format = "|%1$-20s|%2$-10s|%3$-10s|%4$-10s|%5$-14s|%6$-16s|%7$-10s|%8$-16s|\n";
            System.out.println("+-----------------------------------------------------------------------------------------------------------------+");
            System.out.println("|_______________________________________________Premier League Table______________________________________________|");
            System.out.println("+--------------------+----------+----------+----------+--------------+----------------+----------+----------------+");
            System.out.format(format, " Club Name", " Wins", " Losses", " Draws", " Goals Scored", " Goals Received", " Points", " Matches Played");
            System.out.println("+--------------------+----------+----------+----------+--------------+----------------+----------+----------------+");
            for (FootballClub club : football) {
                System.out.format(format, " " + club.getClubName(), " " + club.getNumberOfWins(), " " + club.getNumberOfLosses(), " " + club.getNumberOfDraws(), " " + club.getNumberOfGoalsScored(), " " + club.getNumberOfGoalsReceived(), " " + club.getNumberOfClubPoints(), " " + club.getNumberOfMatchesPlayed());
            }
            System.out.println("+-----------------------------------------------------------------------------------------------------------------+\n");
        } else {
            System.out.println("There are no clubs in the premier league!");
        }
        menu();
    }

    //method used to add a played match
    @Override
    public void addMatch() {
        System.out.println("\n+----------------------------------------------------+");
        System.out.println("|__________________Add Played Match__________________|");
        System.out.println("+----------------------------------------------------+\n");
        // Getting Date input
        System.out.print("Enter date ( Format: DD-MM-YYYY ) : ");
        String input = sc.nextLine();
        LocalDate date = null;
        try {
            date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (Exception e) {
            System.out.println("Please enter a valid date input in the correct format!!");
            addMatch();
        }

        // Selecting Home team
        FootballClub home = null;
        clubCheck=false;
        do {
            stringValidation = false;
            System.out.print("Enter Name of Home Team: ");
            input = sc.nextLine();
            if ((input != null) && input.matches("^[a-z A-Z]*$")) {
                stringValidation = true;
            } else {
                System.out.println("\nYou have entered an invalid input!! Please re-enter in the correct format!\n");
            }
        } while (!stringValidation);

        for (FootballClub club : football) {
            if (club.getClubName().equalsIgnoreCase(input)) {
                home = club;
                clubCheck=true;
            }
        }
        if(!clubCheck){
            System.out.println("Club not found! Returning to menu..");
            menu();
        }


        //Selecting Away team-++++++-----------------------------------------
        clubCheck=false;
        do {
            stringValidation = false;
            System.out.print("Enter Name of Away Team: ");
            input = sc.nextLine();
            if ((input != null) && input.matches("^[a-z A-Z]*$")) {
                stringValidation = true;
            } else {
                System.out.println("\nYou have entered an invalid input!! Please re-enter in the correct format!\n");
            }
        } while (!stringValidation);

        FootballClub away = null;
        for (FootballClub club : football) {
            if (club.getClubName().equalsIgnoreCase(input)) {
                away = club;
            }
        }
        if(!clubCheck){
            System.out.println("Club not found! Returning to menu..");
            menu();
        }

        // Entering number of goals by home team
        int homeClubGoals;
        do {
            System.out.print("Enter number of goals by Home Team: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!\n");
                System.out.print("Enter number of goals by Home Team: ");
                sc.next();
            }
            homeClubGoals = sc.nextInt();
        } while (homeClubGoals <= 0);

        // Entering number of goals by away team
        int awayClubGoals;
        do {
            System.out.print("Enter number of goals by Away Team: ");
            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input! Please re-enter input using integer values only!\n");
                System.out.print("Enter number of goals by Away Team: ");
                sc.next();
            }
            awayClubGoals = sc.nextInt();
        } while (awayClubGoals <= 0);

        Match match = new Match(); //creating an instance of match class to store the variables
        match.setDate(date);
        match.setHomeClub(home);    // setting match values
        match.setAwayClub(away);
        match.setHomeClubGoalsScored(homeClubGoals);
        match.setAwayClubGoalsScored(awayClubGoals);
        footballMatches.add(match); // adding the match to the football matches arraylist

        // setting goals scored
        home.setNumberOfGoalsScored(home.getNumberOfGoalsScored() + homeClubGoals);
        away.setNumberOfGoalsScored(away.getNumberOfGoalsScored() + awayClubGoals);
        // setting goals received
        home.setNumberOfGoalsReceived(home.getNumberOfGoalsReceived() + awayClubGoals);
        away.setNumberOfGoalsReceived(away.getNumberOfGoalsReceived() + homeClubGoals);
        // setting matches played
        home.setNumberOfMatchesPlayed(home.getNumberOfMatchesPlayed() + 1);
        away.setNumberOfMatchesPlayed(away.getNumberOfMatchesPlayed() + 1);

        // if home club has more goals than away club, therefore home club wins the match
        if (homeClubGoals > awayClubGoals) {
            home.setNumberOfClubPoints(home.getNumberOfClubPoints() + 3);
            home.setNumberOfWins(home.getNumberOfWins() + 1);
            away.setNumberOfLosses(away.getNumberOfLosses() + 1);
        }
        // if away club has more goals than home club, therefore away club wins the match
        else if (homeClubGoals < awayClubGoals) {
            away.setNumberOfClubPoints(away.getNumberOfClubPoints() + 3);
            away.setNumberOfWins(away.getNumberOfWins() + 1);
            home.setNumberOfLosses(home.getNumberOfLosses() + 1);
        } else { // if both clubs have equal number of goals scored, then it counts as a draw
            home.setNumberOfClubPoints(home.getNumberOfClubPoints() + 1);
            away.setNumberOfClubPoints(away.getNumberOfClubPoints() + 1);
            home.setNumberOfDraws(home.getNumberOfDraws() + 1);
            away.setNumberOfDraws(away.getNumberOfDraws() + 1);
        }
        saveData();
        System.out.println("\nMatch has been added successfully! Club Statistics have been Updated.\n");
        menu();
    }

    //method used to serialize and save the arraylist data
    @Override
    public void saveData() {
        //  Saving in a file of all the information entered by the user.
        try {
            FileOutputStream clubFileSerialize = new FileOutputStream("leaguesave.json");
            ObjectOutputStream save = new ObjectOutputStream(clubFileSerialize);
            save.writeObject(football); // writing arraylist to the file
            save.close();
            clubFileSerialize.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream matchFileSerialize = new FileOutputStream("leagueMatchesSave.json");
            ObjectOutputStream save = new ObjectOutputStream(matchFileSerialize);
            save.writeObject(footballMatches);

            save.close();
            matchFileSerialize.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //To be used when user decides to save on exit
        if (count > 0) {
            System.out.println("Exiting Program...\n");
            System.exit(0);
        }
    }

    //method to be used at the beginning of the program to retrieve previous saved data
    @Override
    public void retrieveData() {
        //read all the information saved in the previous file
        try {
            FileInputStream clubFileDeserialize = new FileInputStream("leaguesave.json");
            ObjectInputStream retrieve = new ObjectInputStream(clubFileDeserialize);
            football = (ArrayList<FootballClub>) retrieve.readObject();
            System.out.println("Previously saved club data Loaded!");
            retrieve.close();
            clubFileDeserialize.close();
        } catch (FileNotFoundException f) {

            System.out.println("Previously saved data file not found!");
        } catch (EOFException f) {
            System.out.println("No club data found in file!");
        } catch (Exception e) {
            System.out.println("Previously saved club data could not be loaded into the program!");
        }
        try {
            FileInputStream matchFileDeserialize = new FileInputStream("leagueMatchesSave.json");
            ObjectInputStream retrieveMatches = new ObjectInputStream(matchFileDeserialize);
            footballMatches = (ArrayList<Match>) retrieveMatches.readObject();
            System.out.println("Previously saved match data Loaded!");
            retrieveMatches.close();
            matchFileDeserialize.close();
        } catch (FileNotFoundException f) {
            System.out.println("Previously saved match data file not found!");
        } catch (EOFException f) {
            System.out.println("No match data found in file!");
        } catch (Exception e) {
            System.out.println("Previously saved match data could not be loaded into the program!");
        }

        //printing all clubs in the premier league
        System.out.println("\n-------------------------------------");
        System.out.println("Clubs in the Premier League");
        System.out.println("-------------------------------------");
        for (FootballClub club : football) {
            System.out.println(club.getClubName());
            clubCount++;
        }
        System.out.println("-------------------------------------\n");

    }

    //method to start the spring boot backend of the application
    @Override
    public void displayGUI() {
        try {
            String url = "http://localhost:4200";
            Desktop.getDesktop().browse(new URL(url).toURI());
        }catch (Exception e){
            System.out.println("Unable to launch browser. ");
        }
        SpringApplication.run(PremierLeagueManager.class);
    }

    // method used to clear played match arraylist before the user generates a new played match
    public void clearPlayedMatch(){
        playedMatchArray.clear();
    }

    //method used to sort the football arraylist by wins and return the arraylist, used for the gui
    public ArrayList<FootballClub> sortByWins(){
        football.sort(new CompareWinValues());
        return football;
    }

    //method used to sort the football arraylist by points and return the arraylist, used for the gui
    public ArrayList<FootballClub> sortByPoints(){
        football.sort(new ComparePointValues());
        return football;
    }

    //method used to sort the football arraylist by goals and return the arraylist, used for the gui
    public ArrayList<FootballClub> sortByGoals(){
        football.sort(new CompareGoalValues());
        return football;
    }

    // public method to generate a played match and add to playedmatch arraylist
    public void playedMatch(){

        Random random = new Random();
        String day = "";
        String month = "";
        String year ="";
        int dayRandom = (int) ((Math.random() * (31 - 1)) + 1);
        if (dayRandom <= 9) {
            day = "0" + dayRandom;
        } else {
            day = String.valueOf(dayRandom);

        }
        int monthRandom = (int) ((Math.random() * (12 - 1)) + 1);
        if (monthRandom <= 9) {
            month = "0" + monthRandom;
        } else {
            month = String.valueOf(monthRandom);
        }

        int yearRandom = (int) ((Math.random() * (2021 - 2000)) + 2000);
        year = String.valueOf(yearRandom);

        String input = (day + "-" + month + "-" + year);
        LocalDate date = null;

        // parsing string input to date variable in the given format
        date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        FootballClub home = null;
        String index = football.get(random.nextInt(football.size())).getClubName();
        for (FootballClub club : football) {
            if (club.getClubName().equals(index)) {
                home = club; //setting club to home variable
            }
        }

        FootballClub away = null;
        String index2 = football.get(random.nextInt(football.size())).getClubName();
        while (index2.equals(index)) {
            index2 = football.get(random.nextInt(football.size())).getClubName();
        }

        for (FootballClub club : football) {
            if (club.getClubName().equals(index2)) {
                away = club;
            }
        }

        int homeClubGoals = 0;
        int homeGoalsRandom = (int) ((Math.random() * (9)) + 0);
        homeClubGoals = homeGoalsRandom;

        int awayClubGoals = 0;
        int awayGoalsRandom = (int) ((Math.random() * (9)) + 0);
        awayClubGoals = awayGoalsRandom;

        Match match = new Match(); //creating an instance of match class to store the variables
        match.setDate(date);
        match.setHomeClub(home);    // setting match values
        match.setAwayClub(away);
        match.setHomeClubGoalsScored(homeClubGoals);
        match.setAwayClubGoalsScored(awayClubGoals);
        footballMatches.add(match); // adding the match to the football matches arraylist
        playedMatchArray.add(match);
        // setting goals scored
        home.setNumberOfGoalsScored(home.getNumberOfGoalsScored() + homeClubGoals);
        away.setNumberOfGoalsScored(away.getNumberOfGoalsScored() + awayClubGoals);
        // setting goals received
        home.setNumberOfGoalsReceived(home.getNumberOfGoalsReceived() + awayClubGoals);
        away.setNumberOfGoalsReceived(away.getNumberOfGoalsReceived() + homeClubGoals);
        // setting matches played
        home.setNumberOfMatchesPlayed(home.getNumberOfMatchesPlayed() + 1);
        away.setNumberOfMatchesPlayed(away.getNumberOfMatchesPlayed() + 1);

        // if home club has more goals than away club, therefore home club wins the match
        if (homeClubGoals > awayClubGoals) {
            home.setNumberOfClubPoints(home.getNumberOfClubPoints() + 3);
            home.setNumberOfWins(home.getNumberOfWins() + 1);
            away.setNumberOfLosses(away.getNumberOfLosses() + 1);
        }
        // if away club has more goals than home club, therefore away club wins the match
        else if (homeClubGoals < awayClubGoals) {
            away.setNumberOfClubPoints(away.getNumberOfClubPoints() + 3);
            away.setNumberOfWins(away.getNumberOfWins() + 1);
            home.setNumberOfLosses(home.getNumberOfLosses() + 1);
        } else { // if both clubs have equal number of goals scored, then it counts as a draw
            home.setNumberOfClubPoints(home.getNumberOfClubPoints() + 1);
            away.setNumberOfClubPoints(away.getNumberOfClubPoints() + 1);
            home.setNumberOfDraws(home.getNumberOfDraws() + 1);
            away.setNumberOfDraws(away.getNumberOfDraws() + 1);
        }
    }

    //Inner class to sort clubs according to points in descending order
    public static class ComparePointValues implements Comparator<FootballClub> {
        public int compare(FootballClub club1, FootballClub club2) {
            if (club1.getNumberOfClubPoints() > club2.getNumberOfClubPoints())
                return -1;
            else if (club1.getNumberOfClubPoints() < club2.getNumberOfClubPoints())
                return 1;
            else {
                // If the club points are the same in both clubs, it compares using the difference in goals
                int goalDifference1 = club1.getNumberOfGoalsScored() - club1.getNumberOfGoalsReceived();
                int goalDifference2 = club2.getNumberOfGoalsScored() - club2.getNumberOfGoalsReceived();

                //returning the compared goal difference using .compare method
                return Integer.compare(goalDifference2, goalDifference1);
            }
        }
    }

    //Inner class to sort clubs according to goals scored in descending order
    public static class CompareGoalValues implements Comparator<FootballClub> {
        public int compare(FootballClub club1, FootballClub club2) {
            return Integer.compare(club2.getNumberOfGoalsScored(), club1.getNumberOfGoalsScored());
        }
    }

    //Inner class to sort clubs according to wins in descending order
    public static class CompareWinValues implements Comparator<FootballClub> {
        public int compare(FootballClub club1, FootballClub club2) {
            return Integer.compare(club2.getNumberOfWins(), club1.getNumberOfWins());
        }
    }

    //Inner class created to be used to sort all matches in ascending order of their date played
    public static class CompareDateValues implements Comparator<Match> {
        public int compare(Match match1, Match match2) {
            return match1.getDate().compareTo(match2.getDate());
        }
    }
}