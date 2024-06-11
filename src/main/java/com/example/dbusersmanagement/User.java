package com.example.dbusersmanagement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final IntegerProperty id;
    private final StringProperty company;
    private final StringProperty name;
    private final StringProperty score;

    public User(int id, String company, String name, String score) {
        this.id = new SimpleIntegerProperty(id);
        this.company = new SimpleStringProperty(company);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(score);
    }

    public int getId() {
        return id.get();
    }

    public StringProperty nameProperty() {
        return name;
    }
    public String getName() {
        return name.get();
    }

    public StringProperty companyProperty() {
        return company;
    }
    public String getCompany() {
        return company.get();
    }

    public String getScore() {
        return score.get();
    }

    public StringProperty scoreProperty() {
        return score;
    }
}