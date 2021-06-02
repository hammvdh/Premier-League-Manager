import java.io.Serializable;

public class UniversityFootballClub extends FootballClub implements Serializable {

    private String universityName;

    public UniversityFootballClub() {

    }

    public UniversityFootballClub(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
