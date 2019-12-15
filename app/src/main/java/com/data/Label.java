package com.data;

import java.io.Serializable;

public class Label implements Serializable {
    String title;


    public Label(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
