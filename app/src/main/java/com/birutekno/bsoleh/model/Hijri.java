package com.birutekno.bsoleh.model;

public class Hijri {
    private Weekday weekday;

    private Month month;

    private String[] holidays;

    private String year;

    private String day;

    private String format;

    private String date;

    public Weekday getWeekday ()
    {
        return weekday;
    }

    public void setWeekday (Weekday weekday)
    {
        this.weekday = weekday;
    }

    public Month getMonth ()
    {
        return month;
    }

    public void setMonth (Month month)
    {
        this.month = month;
    }

    public String[] getHolidays ()
    {
        return holidays;
    }

    public void setHolidays (String[] holidays)
    {
        this.holidays = holidays;
    }

    public String getYear ()
    {
        return year;
    }

    public void setYear (String year)
    {
        this.year = year;
    }

    public String getDay ()
    {
        return day;
    }

    public void setDay (String day)
    {
        this.day = day;
    }

    public String getFormat ()
    {
        return format;
    }

    public void setFormat (String format)
    {
        this.format = format;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }
}
