package Model;

/**
 * @author Patrick Kell
 */

/**
 * The Country class which defines and manages countries
 */
public class Country {

    private int countryID;
    private String countryName;

    /**
     * Creates a new country object with the provided arguments
     *
     * @param countryID The id of the country
     * @param countryName      The name of the country
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return the country's id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID set the country's id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return the country's name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName set the country's name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return a string representation of the country's id and name
     */
    @Override
    public String toString() {
        return this.countryID + " " + this.countryName;
    }
}