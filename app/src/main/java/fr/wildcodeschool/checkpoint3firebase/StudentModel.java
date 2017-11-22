package fr.wildcodeschool.checkpoint3firebase;

/**
 * Created by bastienwcs on 20/11/17.
 */

public class StudentModel {

    private String firstname;
    private String lastname;
    private String average;

    public StudentModel(String firstname, String lastname, String average) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.average = average;
    }

    public StudentModel() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

}
