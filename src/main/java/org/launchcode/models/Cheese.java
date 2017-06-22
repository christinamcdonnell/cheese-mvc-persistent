package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity
public class Cheese {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;

    // private CheeseType type;
    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "cheeses")
    private List<Menu> menus;

    public Cheese() { }

    public Cheese(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // the cheese TABLE stores the categoryid for the type of cheese
    // But The cheese object stores the category
    public Category getCategory() {
        return this.category;
    } // returns the category object (id & name) stored in this cheese object

    public void setCategory(Category cat) {
        this.category = cat;
    }
}
