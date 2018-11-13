package com.birutekno.bsoleh.model;

public class Timings {
    private String Fajr;

    private String Isha;

    private String Asr;

    private String Dhuhr;

    private String Midnight;

    private String Sunset;

    private String Sunrise;

    private String Imsak;

    private String Maghrib;

    public String getFajr () {
        return splitter(Fajr);
    }

    public void setFajr (String Fajr)
    {
        this.Fajr = Fajr;
    }

    public String getIsha () {
        return splitter(Isha);
    }

    public void setIsha (String Isha)
    {
        this.Isha = Isha;
    }

    public String getAsr () {
        return splitter(Asr);
    }

    public void setAsr (String Asr)
    {
        this.Asr = Asr;
    }

    public String getDhuhr () {
        return splitter(Dhuhr);
    }

    public void setDhuhr (String Dhuhr)
    {
        this.Dhuhr = Dhuhr;
    }

    public String getMidnight () {
        return splitter(Midnight);
    }

    public void setMidnight (String Midnight)
    {
        this.Midnight = Midnight;
    }

    public String getSunset () {
        return splitter(Sunset);
    }

    public void setSunset (String Sunset)
    {
        this.Sunset = Sunset;
    }

    public String getSunrise () {
        return splitter(Sunrise);
    }

    public void setSunrise (String Sunrise)
    {
        this.Sunrise = Sunrise;
    }

    public String getImsak () {
        return splitter(Imsak);
    }

    public void setImsak (String Imsak)
    {
        this.Imsak = Imsak;
    }

    public String getMaghrib () {
        return splitter(Maghrib);
    }

    public void setMaghrib (String Maghrib)
    {
        this.Maghrib = Maghrib;
    }

    private String splitter(String args){
        String[] parts = args.split(" ");
        return parts[0];
    }

    @Override
    public String toString() {
        return  "Imsak = "+Imsak+
                "\nFajr = "+Fajr+
                "\nSunrise = "+Sunrise+
                "\nDhuhr = "+Dhuhr+
                "\nAsr = "+Asr+
                "\nSunset = "+Sunset+
                "\nMaghrib = "+Maghrib+
                "\nIsha = "+Isha+
                "\nMidnight = "+Midnight;
    }
}
