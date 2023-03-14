package Model;

/**
 * @author Patrick Kell
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Customer class which defines and manages customers
 */
public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int divisionId;
    private String country;
    private String division;

    /**
     * Creates a new customer object with the provided arguments
     * <p>
     *
     * @param customerId The id of the customer
     * @param name       The name of the customer
     * @param address    The address of the customer
     * @param postal     The postal code of the customer
     * @param phone      The phone number of the customer
     * @param divisionId The division id of the customer
     * @param country    The country of the customer
     * @param division   The division of the customer
     */
    public Customer(int customerId, String name, String address, String postal, String phone, int divisionId, String country, String division) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = country;
        this.division = division;
    }

    /**
     * @return the customer's id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId sets the customer's id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address sets the customer's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customer's postal code
     */
    public String getPostal() {
        return postal;
    }

    /**
     * @param postal sets the customer's postal code
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * @return the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone sets the customer's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the customer's division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId sets the customer's division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return a string representation of the customer's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country sets the customer's country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return a string representation of the customer's division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division sets the customer's division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return a string representation of the customer's id and name
     */
    @Override
    public String toString() {
        return this.customerId + ": " + this.name;
    }
}