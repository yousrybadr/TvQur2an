package com.pentavalue.tvquran.data.constants;

/**
 * Define all constant values that is related to com.pentavalue.tvquran.network operation.
 *
 * @author Ahmed Rabie
 */
public class NetworkConstants {
    /**
     * The base url of the web service.
     */
    public static final String BASE_URL = "http://api.tvquran.com"; /*"http://penta-test.com/tvquran/public";*/
    /**
     * homePage
     * http://penta-test.com/tvquran/public/home
     */
    public static final String HOME_URL =BASE_URL +"/home";
    /**
     * profile of reader
     * http://penta-test.com/tvquran/public/reciterProfile
     */
    public static final String PROFILE_URL =BASE_URL +"/reciterProfile";
    /**
     * profile of reader
     * http://penta-test.com/tvquran/public/getCategoryDetails
     */
    public static final String GET_CATOGRY_URL =BASE_URL +"/getCategoryDetails";
    /**
     * getAlphabeticalHolyQuran
     * http://penta-test.com/tvquran/public/getAlphabeticalHolyQuran
     */
    public static final String GET_AlphabeticalHolyQuran_URL =BASE_URL +"/getAlphabeticalHolyQuran";
    /**
     * profile of reader
     * http://penta-test.com/tvquran/public/getCollectionHolyQuran
     */
    public static final String GET_CollectionHolyQuran_URL =BASE_URL +"/getCollectionHolyQuran";
    /**
     * /getMostListenedReciters
     * http://penta-test.com/tvquran/public//getMostListenedReciters
     */
    public static final String GET_MostListenedReciters_URL =BASE_URL +"/getMostListenedReciters";
    /**
     * getRecentlyModifiedHolyQuran
     * http://penta-test.com/tvquran/public/getRecentlyModifiedHolyQuran
     */
    public static final String GET_RecentlyModifiedHolyQuran_URL =BASE_URL +"/getRecentlyModifiedHolyQuran";
    /**
     * getVideosAndLiveBroadcast
     * http://penta-test.com/tvquran/public/getVideosAndLiveBroadcast
     */
    public static final String GET_VideosAndLiveBroadcast_URL =BASE_URL +"/getVideosAndLiveBroadcast";
    /**
     * autoCompleteReciterName
     * http://penta-test.com/tvquran/public/autoCompleteReciterName
     */
    public static final String GET_AutoCompleteReciterName_URL =BASE_URL +"/autoCompleteReciterName";
    /**
     * contactUs
     * http://penta-test.com/tvquran/public/contactUs
     */
    public static final String GET_CONTACTUS_URL =BASE_URL +"/contactUs";
    public static final String GET_SEARCH_RESULT=BASE_URL+"/searchResult";
    public static final String GET_CATEGORIES=BASE_URL+"/categories";
    // issue Rebuild this URls
    /*
    About Us
    GET http://api.tvquran.com/about_us?lang=ar
    Terms and Conditions
    GET http://api.tvquran.com/terms?lang=ar
    Advertise with us
    GET http://api.tvquran.com/advertise?lang=ar

    */
    public static String ABOUT_URL="https://www.tvquran.com/en/page/6/about-us";
        public static String TERMS_OF_US="https://www.tvquran.com/en/page/5/tearms-of-use";


    public static String GET_ABOUT_US = "http://api.tvquran.com/about_us";

    public static String GET_Advertise_US = "http://api.tvquran.com/terms?lang=ar";
    public static String GET_TERMS = "http://api.tvquran.com/terms?lang=ar";




    public static final class categories {
        public static final int DUA = 4;
        public static final int RecitationByChildren = 7;
        public static final int Ruqyah = 6;

        public static final int DailyDhikr = 8;
        public static final int AdanAndTakbier = 9;
        public static final int HolyQr2an = 5;

        public static final int VisitorsRecitations = 13;
        public static final int RareRecitations = 22;
        public static final int amazinQuraan = 10;
    }

    public static final class Filters {
        public static final int RANDOM = 1;
        public static final int MOST_LIKED = 2;
        public static final int MOST_LISTEN = 4;
        public static final int RECENT = 3;

    }

}
