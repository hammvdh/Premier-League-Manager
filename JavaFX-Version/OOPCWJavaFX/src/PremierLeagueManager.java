
//importing modules from javafx library
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
//importing modules from java library
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PremierLeagueManager extends Application implements LeagueManager {
    // creating private variables to be used within the program

    private static int clubCount = 0; // private variable to store number of clubs
    private static int count; // to be used to when user saves on exit

    private ArrayList<FootballClub> football; // Arraylist that stores football clubs
    private ArrayList<Match> footballMatches; // Arraylist that stores football Matches

    private final Scanner sc;
    Stage matchStage = new Stage(); //JavaFX stage created to be used when user generates a played match
    Stage window; // JavaFX stage to be used as Main window when GUI is displayed

    private TableView premierLeagueTable = new TableView(); // table that will store club details
    private TableView matchesTable = new TableView(); // table that will store match details
    private TableView playedMatchTable = new TableView();

    private static boolean columnCount = true; //boolean variable to make sure already added columns don't get created
    private static boolean matchColumnCount = true; //boolean variable to make sure already added columns don't get created
    private static boolean playedMatchColumnCount = true;//boolean variable to make sure already added columns don't get created
    private static boolean save = true; // to make sure user saves the data if new data has been added
    private static boolean stringValidation; //boolean variable used for string validation

    //constructor
    public PremierLeagueManager() {
        football = new ArrayList<>();
        footballMatches = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage; //assigning stage to window
        retrieveData(); // calling retrieve data method to retrieve previously saved data
        menu();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //menu containing a command line menu which directs the user to a specific option
    private void menu() {
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
                columnCount = false;
                break;

            case 7:
                save = true;
                saveData();
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
        sc.nextLine();
        club.setNumberOfClubPoints(points); // setting values

        football.add(club); //adding club to the football arraylist
        clubCount++; //increasing club count by 1
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

        FootballClub home = null;
        for (FootballClub club : football) {
            if (club.getClubName().toLowerCase().equals(input.toLowerCase())) {
                home = club;
            }else{
                System.out.println("Club not found! Returning back to menu..");
                menu();
            }
        }

        //Selecting Away team
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
            if (club.getClubName().toLowerCase().equals(input.toLowerCase())) {
                away = club;
            }else{
                System.out.println("Club not found! Returning back to menu..");
                menu();
            }
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
        sc.nextLine();
        System.out.println("\nMatch has been added successfully! Club Statistics have been Updated.\n");
        menu();
    }

    //method used to serialize and save the arraylist data
    @Override
    public void saveData() {
        //  Saving in a file of all the information entered by the user.
        try {
            FileOutputStream clubFileSerialize = new FileOutputStream("leaguesave");
            ObjectOutputStream save = new ObjectOutputStream(clubFileSerialize);
             save.writeObject(football); // writing arraylist to the file
            save.close();
            clubFileSerialize.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream matchFileSerialize = new FileOutputStream("leagueMatchesSave");
            ObjectOutputStream save = new ObjectOutputStream(matchFileSerialize);
            save.writeObject(footballMatches);

            save.close();
            matchFileSerialize.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nData has been saved!\n");
        //To be used when user decides to save on exit
        if (count > 0) {
            System.out.println("Exiting Program...\n");
            System.exit(0);
        } else {
            menu();
        }
    }

    //method to be used at the beginning of the program to retrieve previous saved data
    @Override
    public void retrieveData() {
        //read all the information saved in the previous file
        try {
            FileInputStream clubFileDeserialize = new FileInputStream("leaguesave");
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
            FileInputStream matchFileDeserialize = new FileInputStream("leagueMatchesSave");
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

    //method to display the graphical user interface
    @Override
    public void displayGUI() {
        Scene mainScene; //Scene for main window
        Scene allMatches; // Scene for view all matches window
        /*
         * Creating and setting cell values for the Clubs table
         */
        TableColumn<FootballClub, String> clubNameColumn = new TableColumn<>("Club Name"); // column heading
        clubNameColumn.setMinWidth(200);
        clubNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getClubName()))
        );

        TableColumn<FootballClub, String> clubWinsColumn = new TableColumn<>("Wins"); // column heading
        clubWinsColumn.setMinWidth(100);
        clubWinsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfWins()))
        );

        TableColumn<FootballClub, String> clubLossColumn = new TableColumn<>("Losses"); // column heading
        clubLossColumn.setMinWidth(100);
        clubLossColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfLosses()))
        );

        TableColumn<FootballClub, String> clubDrawColumn = new TableColumn<>("Draws"); // column heading
        clubDrawColumn.setMinWidth(100);
        clubDrawColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfDraws()))
        );

        TableColumn<FootballClub, String> clubScoredColumn = new TableColumn<>("Goals Scored"); // column heading
        clubScoredColumn.setMinWidth(150);
        clubScoredColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfGoalsScored()))
        );

        TableColumn<FootballClub, String> clubReceivedColumn = new TableColumn<>("Goals Received"); // column heading
        clubReceivedColumn.setMinWidth(150);
        clubReceivedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfGoalsReceived()))
        );

        TableColumn<FootballClub, String> clubPointsColumn = new TableColumn<>("Points"); // column heading
        clubPointsColumn.setMinWidth(100);
        clubPointsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfClubPoints()))
        );

        TableColumn<FootballClub, String> clubMatchesPlayedColumn = new TableColumn<>("Matches Played"); // column heading
        clubMatchesPlayedColumn.setMinWidth(200);
        clubMatchesPlayedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfMatchesPlayed()))
        );
    /*
     * Creating and setting cell values for the Matches table
     */
        TableColumn<Match, String> homeClubColumn = new TableColumn<>("Home Club"); // column heading
        homeClubColumn.setMinWidth(200);
        homeClubColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getHomeClub().getClubName()))
        );

        TableColumn<Match, String> homeClubGoalsColumn = new TableColumn<>("Home Club Goals"); // column heading
        homeClubGoalsColumn.setMinWidth(150);
        homeClubGoalsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getHomeClubGoalsScored()))
        );

        TableColumn<Match, String> datePlayed = new TableColumn<>("Date Played (YYYY-MM-DD)"); // column heading
        datePlayed.setMinWidth(200);
        datePlayed.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDate()))
        );

        TableColumn<Match, String> awayClubGoalsColumn = new TableColumn<>("Away Club Goals"); // column heading
        awayClubGoalsColumn.setMinWidth(150);
        awayClubGoalsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getAwayClubGoalsScored()))
        );

        TableColumn<Match, String> awayClubColumn = new TableColumn<>("Away Club"); // column heading
        awayClubColumn.setMinWidth(200);
        awayClubColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getAwayClub().getClubName()))
        );
    /*
     * Creating and setting cell values for the Played Matches table
     */
        TableColumn<Match, String> homeClubPlayedColumn = new TableColumn<>("Home Club"); // column heading
        homeClubPlayedColumn.setMinWidth(200);
        homeClubPlayedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getHomeClub().getClubName()))
        );

        TableColumn<Match, String> homeClubGoalsPlayedColumn = new TableColumn<>("Home Club Goals"); // column heading
        homeClubGoalsPlayedColumn.setMinWidth(150);
        homeClubGoalsPlayedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getHomeClubGoalsScored()))
        );

        TableColumn<Match, String> datePlayedMatch = new TableColumn<>("Date Played (YYYY-MM-DD)"); // column heading
        datePlayedMatch.setMinWidth(200);
        datePlayedMatch.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDate()))
        );

        TableColumn<Match, String> awayClubGoalsPlayedColumn = new TableColumn<>("Away Club Goals"); // column heading
        awayClubGoalsPlayedColumn.setMinWidth(150);
        awayClubGoalsPlayedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getAwayClubGoalsScored()))
        );

        TableColumn<Match, String> awayClubPlayedColumn = new TableColumn<>("Away Club"); // column heading
        awayClubPlayedColumn.setMinWidth(200);
        awayClubPlayedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getAwayClub().getClubName()))
        );

        if (columnCount) { // making sure the columns don't get added to the table multiple times
            premierLeagueTable.getColumns().addAll(clubNameColumn, clubWinsColumn, clubLossColumn, clubDrawColumn, clubScoredColumn, clubReceivedColumn, clubPointsColumn, clubMatchesPlayedColumn);
        }
        football.sort(new ComparePointValues());
        for (FootballClub club : football) {
            premierLeagueTable.getItems().add(club);
            premierLeagueTable.setStyle("-fx-alignment: CENTER-RIGHT");
        }

        Button closeWindow = new Button("Close");
        closeWindow.setOnAction(event -> {
            premierLeagueTable.getItems().clear(); //clearing premier league table when user decides to close
            window.close();
            menu(); // calling menu in cli
        });
        closeWindow.setLayoutX(40);
        closeWindow.setLayoutY(16);

        ComboBox yearDropdown = new ComboBox(); // creating a dropdown menu for user to select year of played match
        yearDropdown.getItems().addAll( // adding dropdown options
                "2020",
                "2019",
                "2018",
                "2017",
                "2016",
                "2015",
                "2014",
                "2013",
                "2012",
                "2011",
                "2010"
        );
        yearDropdown.setValue("2020");
        yearDropdown.setLayoutX(1000);
        yearDropdown.setLayoutY(16);

        Button addMatch = new Button("Play a Match");
        addMatch.setOnAction(event -> {
            playedMatchTable.getItems().clear();
            String year = (String) yearDropdown.getValue();
            Random random = new Random();
            String day = "";
            String month = "";
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
            if (playedMatchColumnCount) {
                playedMatchTable.getColumns().addAll(homeClubPlayedColumn, homeClubGoalsPlayedColumn, datePlayedMatch, awayClubGoalsPlayedColumn, awayClubPlayedColumn);
                playedMatchColumnCount = false;
            }
            playedMatchTable.getItems().addAll(match);
            footballMatches.sort(new CompareDateValues());

            playedMatchTable.setLayoutX(15);
            playedMatchTable.setLayoutY(60);
            playedMatchTable.setPrefHeight(50);
            Label header3 = new Label("Played Match"); // heading
            header3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            header3.setStyle("-fx-font-weight:500;");
            header3.setLayoutX(400);
            header3.setLayoutY(20);

            Button closeMatch = new Button("Close");
            closeMatch.setOnAction(e -> {
                playedMatchTable.getItems().clear();
                matchStage.close();
            });
            closeMatch.setLayoutX(430);
            closeMatch.setLayoutY(130);

            AnchorPane playedPane = new AnchorPane(closeMatch, playedMatchTable, header3);
            playedPane.setStyle("-fx-background-color:#a9d9d9");
            matchStage.setTitle("Played Match");
            matchStage.setScene(new Scene(playedPane, 935, 200));
            matchStage.setResizable(true);
            matchStage.show(); // displaying window

            premierLeagueTable.getItems().clear();
            for (FootballClub club : football) {
                premierLeagueTable.getItems().add(club);
            }

            //SAVING MATCH FILE DATA + CLUB STATISTIC CHANGES AUTOMATICALLY
            try {
                FileOutputStream matchFileSerialize = new FileOutputStream("leagueMatchesSave");
                ObjectOutputStream save = new ObjectOutputStream(matchFileSerialize);
                save.writeObject(footballMatches);
                save.close();
                matchFileSerialize.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream clubFileSerialize = new FileOutputStream("leaguesave");
                ObjectOutputStream save = new ObjectOutputStream(clubFileSerialize);
                save.writeObject(football);
                save.close();
                clubFileSerialize.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addMatch.setLayoutX(900);
        addMatch.setLayoutY(16);

        Label header1 = new Label("Premier League Manager"); // heading
        header1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        header1.setStyle("-fx-font-weight:500;");
        header1.setLayoutX(15);
        header1.setLayoutY(19);

        premierLeagueTable.setLayoutX(15);
        premierLeagueTable.setLayoutY(60);

        AnchorPane anchorPane = new AnchorPane(); // pane to keep waiting room table
        anchorPane.setPrefWidth(335); // setting width of pane
        anchorPane.setStyle("-fx-background-color:#a9d9d9"); // adding background color
        // adding items to pane

        AnchorPane bottomPane = new AnchorPane(); //creating anchor pane
        bottomPane.setStyle("-fx-background-color:#ffffff; -fx-text-align:center"); //background color
        // adding items to be displayed in window
        bottomPane.setPrefHeight(60);

        BorderPane borderPane = new BorderPane(); // used to align panes
        borderPane.setStyle("-fx-background-color:#bfbfbf");// adding background color
        borderPane.setBottom(bottomPane);
        borderPane.setCenter(anchorPane);

        // gui elements for all matches scene

        Label header2 = new Label("All Matches Played"); // heading
        header2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        header2.setStyle("-fx-font-weight:500;");
        header2.setLayoutX(15);
        header2.setLayoutY(19);

        matchesTable.setLayoutX(15);
        matchesTable.setLayoutY(60);

        AnchorPane anchorPane2 = new AnchorPane(); // pane to keep waiting room table
        anchorPane2.setPrefWidth(335); // setting width of pane
        anchorPane2.setStyle("-fx-background-color:#a9d9d9"); // adding background color

        AnchorPane bottomPane2 = new AnchorPane(); //creating anchor pane
        bottomPane2.setStyle("-fx-background-color:white; -fx-text-align:center"); //background color
        bottomPane2.setPrefHeight(60);

        BorderPane borderPane2 = new BorderPane(); // used to align panes
        borderPane2.setStyle("-fx-background-color:#bfbfbf");// adding background color
        borderPane2.setBottom(bottomPane2);
        borderPane2.setCenter(anchorPane2);

        mainScene = new Scene(borderPane, 1135, 550);
        allMatches = new Scene(borderPane2, 1135, 550);

        Button backBtn = new Button("Back to Home");
        backBtn.setOnAction(event -> {
            matchesTable.getItems().clear();
            window.setWidth(1150);
            window.setScene(mainScene);
        });
        backBtn.setLayoutX(15);
        backBtn.setLayoutY(15);

        TextField searchDateInput = new TextField();
        searchDateInput.setPromptText("YYYY-MM-DD");

        Button searchMatch = new Button("Search by Date");
        searchMatch.setOnAction(event -> {
            matchesTable.getItems().clear();
            for (Match match : footballMatches) {
                if (String.valueOf(match.getDate()).equals(searchDateInput.getText())) {
                    matchesTable.getItems().add(match);
                }
            }
        });

        searchMatch.setLayoutY(19);
        searchMatch.setLayoutX(665);
        searchDateInput.setLayoutX(765);
        searchDateInput.setLayoutY(19);

        Button viewMatches = new Button("View All Matches");
        viewMatches.setOnAction(event -> {
            footballMatches.sort(new CompareDateValues());
            if (matchColumnCount) {
                matchesTable.getColumns().addAll(homeClubColumn, homeClubGoalsColumn, datePlayed, awayClubGoalsColumn, awayClubColumn);
                matchColumnCount = false;
            }
            matchesTable.getItems().clear();
            for (Match match : footballMatches) {
                matchesTable.getItems().add(match);
            }
            anchorPane2.getChildren().clear();
            anchorPane2.getChildren().addAll(matchesTable, header2, searchMatch, searchDateInput); // adding items to pane
            window.setWidth(950);
            window.setScene(allMatches);
        });
        viewMatches.setLayoutX(1000);
        viewMatches.setLayoutY(18);

        Button pointSort = new Button("Sort by Points");
        pointSort.setOnAction(event -> {
            football.sort(new ComparePointValues());
            premierLeagueTable.getItems().clear();
            for (FootballClub club : football) {
                premierLeagueTable.getItems().add(club);
            }
        });
        pointSort.setLayoutX(400);
        pointSort.setLayoutY(16);

        Button goalSort = new Button("Sort by Goals");
        goalSort.setOnAction(event -> {
            football.sort(new CompareGoalValues());
            premierLeagueTable.getItems().clear();
            for (FootballClub club : football) {
                premierLeagueTable.getItems().add(club);
            }
        });
        goalSort.setLayoutX(500);
        goalSort.setLayoutY(16);

        Button winSort = new Button("Sort by Wins");
        winSort.setOnAction(event -> {
            football.sort(new CompareWinValues());
            premierLeagueTable.getItems().clear();
            for (FootballClub club : football) {
                premierLeagueTable.getItems().add(club);
            }
        });
        winSort.setLayoutX(598);
        winSort.setLayoutY(16);

        anchorPane.getChildren().addAll(premierLeagueTable, header1, viewMatches);
        bottomPane.getChildren().addAll(closeWindow, addMatch, yearDropdown, pointSort, winSort, goalSort);
        bottomPane2.getChildren().addAll(backBtn); // adding items to be displayed in window

        window.setScene(mainScene);
        window.setTitle("Premier League Manager");
        window.setResizable(false); // making sure window cannot be resized
        window.show(); // displaying window
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