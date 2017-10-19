package com.pentavalue.tvquran.network.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Utility class to hold all data layer helper methods
 *
 * @author Mahmoud Turki
 */
public class DataHelper {

    public static final String URL_SEPARATOR = "/";
    public static final String URL_AMPERSAND = "&";
    public static final String URL_QUESTION_MARK = "?";
    public static final String URL_EQUAL = "=";
    public static final String FLAG_TRUE = "true";
    public static final String FLAG_FALSE = "false";

    /**
     * Appends a resource or target to a URL, for example adding if the URL is
     * <code>https://www.google.com.eg</code> and the appended part is
     * <code>images</code>, the result would be
     * <code>https://www.google.com.eg/images</code>
     *
     * @param url
     * @param appended
     * @return
     */
    public static String appendToUrl(String url, String appended)
            throws ApplicationException {
        if (url == null)
            throw new ApplicationException(
                    ApplicationException.OBJECT_INSTANTIATION_EXCEPTION);

        if (appended == null)
            return url;

        if (!url.endsWith(URL_SEPARATOR) && !appended.startsWith(URL_SEPARATOR))
            return url + URL_SEPARATOR + appended;

        if (url.endsWith(URL_SEPARATOR) && appended.startsWith(URL_SEPARATOR)) {
            // Removing the extra URL separators
            String editedBaseUrl = url.replace(URL_SEPARATOR, "");
            return editedBaseUrl + appended;
        }

        return url + appended;
    }

    /**
     * Appends a resource or target to a URL, for example adding if the URL is
     * <code>https://www.google.com.eg</code> and the appended part is
     * <code>images</code>, the result would be
     * <code>https://www.google.com.eg/images</code>
     *
     * @param url
     * @param appended
     * @param lang
     *
     * @return
     * @throws ApplicationException
     */
    public static String appendToUrl(String url, String appended, String lang)
            throws ApplicationException {
        if (url == null)
            throw new ApplicationException(
                    ApplicationException.NO_DATA_EXCEPTION);


        if (appended != null && !url.endsWith(URL_SEPARATOR) && !appended.startsWith(URL_SEPARATOR)){
            url = url + URL_SEPARATOR + appended;
        }

        if (url.endsWith(URL_SEPARATOR) && appended.startsWith(URL_SEPARATOR)) {
            // Removing the extra URL separators
            String editedBaseUrl = url.replace(URL_SEPARATOR, "");
            url = editedBaseUrl + appended;
        }

        if(lang == null || lang.equals(""))
            return url;

        if (!url.endsWith(URL_SEPARATOR) && !lang.startsWith(URL_SEPARATOR))
            return url + URL_SEPARATOR + lang;

        if (url.endsWith(URL_SEPARATOR) || lang.startsWith(URL_SEPARATOR)) {
            // Removing the extra URL separators
            String editedBaseUrl = url.replace(URL_SEPARATOR, "");
            return editedBaseUrl + lang;
        }

        return url + appended;
    }

    /**
     * Custom {@linkplain Boolean} serializer/deserializer for Gson to map ints
     * to booleans and vice versa
     */
    private static TypeAdapter<Boolean> booleanAsStringAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value)
                out.value(FLAG_TRUE);
            else
                out.value(FLAG_FALSE);
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NUMBER:
                    return in.nextInt() == 1;
                case STRING:
                    String value = in.nextString();
                    int numberVal = getNumber(value);
                    if (numberVal != -1)
                        return numberVal == 1;
                    else
                        return value.equals(FLAG_TRUE);
                default:
                    throw new IllegalStateException(
                            "Expected BOOLEAN or String but was " + peek);
            }
        }
    };

    private static int getNumber(String txt) {
        int number;
        try {
            number = Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            number = -1;
        }
        return number;
    }

    private static TypeAdapter<Integer> numberAsStringAdapter = new TypeAdapter<Integer>() {
        @Override
        public void write(JsonWriter out, Integer value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public Integer read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case NUMBER:
                    return in.nextInt();
                case STRING:
                    String s = in.nextString();
                    return Integer.parseInt(s.length() == 0 ? "0" : s);
                default:
                    throw new IllegalStateException(
                            "Expected Integer or String but was " + peek);
            }
        }
    };

    /**
     * Returns a configured instance of {@linkplain Gson} ready for use.
     *
     * @return
     */
    private static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("MMM dd, yyyy")
                .serializeNulls()
                .registerTypeAdapter(Boolean.class,
                        booleanAsStringAdapter.nullSafe())
                .registerTypeAdapter(boolean.class,
                        booleanAsStringAdapter.nullSafe())
                .registerTypeAdapter(Integer.class,
                        numberAsStringAdapter.nullSafe())
                .registerTypeAdapter(int.class,
                        numberAsStringAdapter.nullSafe());
        return builder.create();
    }

    public static <T> T deserialize(String data, Class<T> clazz) {
        return getGson().fromJson(data, clazz);
    }

    public static <T> T deserialize(String data, Type type) {
        return getGson().fromJson(data, type);
    }

    public static <T> String serialize(T obj, Class<T> clazz) {
        return getGson().toJson(obj, clazz);
    }

    public static <T> String serialize(T obj, Type type) {
        return getGson().toJson(obj, type);
    }


}
