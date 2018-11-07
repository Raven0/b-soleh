package com.birutekno.bsoleh.model;

public class PrayerObject {
    private String status;

    private DataPrayer data;

    private String code;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public DataPrayer getDataPrayer ()
    {
        return data;
    }

    public void setDataPrayer (DataPrayer data)
    {
        this.data = data;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }
}
