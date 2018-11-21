package com.birutekno.bsoleh.constant;

public class Constant {

    public static final String TAG = "TAG";
    //API Endpoint
    public static final String BASE_URL_API = "http://api.aladhan.com/v1/";

    //PREFERENCES NAME
    public static final String PREFS_INTRO = "INTRO";
    public static final String PREFS_GPERMISSION = "GPERMISSION";
    public static final String PREFS_LOCATION = "LOCATION";
    public static final String PREFS_LATLNG = "LATLNG";
    public static final String PREFS_PRAYER = "PRAYER";
    public static final String PREFS_CACHE = "DATA_CACHE";

    //LATLONG QIBLA
    public static final double KAABA_LATITUDE = 21.422487;
    public static final double KAABA_LONGITUDE = 39.826206;
    //Bunderan GranSharon
    //public static final double KAABA_LATITUDE = -6.951746;
    //public static final double KAABA_LONGITUDE = 107.672390;

    //Array Method
    public static final String[] METHOD_ID = {"1","2","3","4","5","7","8","9","10","11","12","13"};
    public static final String[] METHOD_NAME = {
            "1. University of Islamic Sciences, Karachi",
            "2. Islamic Society of North America",
            "3. Muslim World League",
            "4. Umm Al-Qura University, Makkah ",
            "5. Egyptian General Authority of Survey",
            "6. Institute of Geophysics, University of Tehran",
            "7. Gulf Region",
            "8. Kuwait",
            "9. Qatar",
            "10. Indonesia, Malaysia & Singapore",
            "11. Union Organization islamic de France",
            "12. Diyanet İşleri Başkanlığı, Turkey"};

    //Array School
    public static final String[] SCHOOL_ID = {"0","1"};
    public static final String[] SCHOOL_NAME = {"1. Standard(Shafi, Maliki, Hanbali)", "2. Hanafi"};

    //Array latitudeAdjustmentMethod
    public static final String[] LAT_ID = {"1","2","3"};
    public static final String[] LAT_NAME = {"1. Middle of the Night", "2. One Seventh", "3. Angle Based"};

    //Zakat Constant
    public static final float RICE_KG = (float) 2.5;

}
