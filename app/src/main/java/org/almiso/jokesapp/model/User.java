package org.almiso.jokesapp.model;

public class User {

    private CharSequence firstName;
    private CharSequence lastName;

    public User(CharSequence firstName, CharSequence lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CharSequence getFirstName() {
        return firstName;
    }

    public CharSequence getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
