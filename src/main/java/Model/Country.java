package Model;

/**
 * @author Patrick Kell
 */

/**
 * The Country class which defines and manages countries
 */
public class Country {

    private int countryID;
    private String name;

    /**
     * Creates a new country object with the provided arguments
     *
     * @param countryID The id of the country
     * @param name      The name of the country
     */
    public Country(int countryID, String name) {
        this.countryID = countryID;
        this.name = name;
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
    public String getName() {
        return name;
    }

    /**
     * @param name set the country's name
     */
    public void setName(String name) {
        this.name = name;
    }
}
