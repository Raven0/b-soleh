package com.birutekno.bsoleh.model;

class Date {
    private String timestamp;

    private String readable;

    private Gregorian gregorian;

    public String getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getReadable ()
    {
        return readable;
    }

    public void setReadable (String readable)
    {
        this.readable = readable;
    }

    public Gregorian getGregorian ()
    {
        return gregorian;
    }

    public void setGregorian (Gregorian gregorian)
    {
        this.gregorian = gregorian;
    }

}
