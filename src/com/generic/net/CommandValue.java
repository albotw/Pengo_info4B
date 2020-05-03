package com.generic.net;

public enum CommandValue {
    ACTION("ACTION"),
    PUSH_SCORE("GET SCORE"),
    PULL_SCORE("SET SCORE");

    private String value = "";

    CommandValue(String value)
    {
        this.value = value;
    }

    public String val()
    {
        return value;
    }
}
