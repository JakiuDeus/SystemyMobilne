package me.jorlowski;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean done;
    private Category category;

    public Task() {
        id = UUID.randomUUID();
        date = new Date();
        category = Category.HOME;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean isChecked) {
        done = isChecked;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setDate(Date time) {
        this.date = time;
    }
}
