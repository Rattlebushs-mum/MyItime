package com.data;

import java.io.Serializable;

public class SetBg implements Serializable {
    int cred;
    int cgreen;
    int cblue;

    public int getCred() {
        return cred;
    }

    public void setCred(int cred) {
        this.cred = cred;
    }

    public int getCgreen() {
        return cgreen;
    }

    public void setCgreen(int cgreen) {
        this.cgreen = cgreen;
    }

    public int getCblue() {
        return cblue;
    }

    public void setCblue(int cblue) {
        this.cblue = cblue;
    }
}
