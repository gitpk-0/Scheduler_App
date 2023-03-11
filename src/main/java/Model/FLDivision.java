package Model;

/**
 * @author Patrick Kell
 */

/**
 * The FL(First-Level)Division class which defines and manages first-level divisions
 */
public class FLDivision {

    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * Creates a new first-level division object with the provided arguments
     *
     * @param divisionId The division id of the first-level division
     * @param divisionName       The name of the first-level division
     * @param countryId  The country id of the first-level division
     */
    public FLDivision(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * @return the first-level division's id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId set the first-level division's division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the first-level division's name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName set the first-level division's name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the first-level division's country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId set the first-level division's country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return a string representation of the first-level division's division id and name
     */
    @Override
    public String toString() {
        return this.divisionId + " " + this.divisionName;
    }

}
