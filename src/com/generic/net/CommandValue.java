package com.generic.net;

public enum CommandValue {
    PUSH_SCORE("GET SCORE", "", "SERVER"),
    PULL_SCORE("SET SCORE", "", "SERVER");

    private String value = "";
    private String param = "";
    private String source = "";
    //private String

    CommandValue(String value, String param, String source)
    {
        this.value = value;
    }

    public String val()
    {
        return value;
    }

    public String param()
    {
        return param;
    }

    public String source()
    {
        return source;
    }
}
