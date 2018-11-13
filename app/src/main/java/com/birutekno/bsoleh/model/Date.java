package com.birutekno.bsoleh.model;

public class Date {
    private String timestamp;

    private String readable;

    private Gregorian gregorian;

    private Hijri hijri;

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

    public Hijri getHijri ()
    {
        return hijri;
    }

    public void setHijri (Hijri hijri)
    {
        this.hijri = hijri;
    }

}
