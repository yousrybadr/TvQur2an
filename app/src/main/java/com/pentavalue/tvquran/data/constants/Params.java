package com.pentavalue.tvquran.data.constants;

/**
 * @author Mahmoud Turki
 */
public abstract class Params {

    public static final int NO_LAYOUT = 0;
    public static final String STATUS = "eCode";
    public static final String DATA = "eContent";
    public static final String MESSAGE = "EDesc";
    public static final String LANGUAGE = "lang";
    public static final String IMAGE = "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg";

    public static final class Registration {
        public static final String ADDRESS_ID = "Id";
        public static final String FIRST_NAME = "FirstName";
        public static final String LAST_NAME = "LastName";
        public static final String EMAIL = "Email";
        public static final String PASSWORD = "Password";
        public static final String CONFIRM_PASSWORD = "ConfirmPassword";
        public static final String PHONE_NUMBER = "PhoneNumber";
        public static final String GENDER = "Gender";
        public static final String SHIPPING_ADDRESSES = "ShippingAddresses";
        public static final String ADDRESS_NAME = "AddressName";
        public static final String TYPE = "Type";
        public static final String BLOCK_NUMBER = "BlockNumber";
        public static final String STREET_NAME = "StreetName";
        public static final String BUILDING_NUMBER = "BuildingNumber";
        public static final String FLOOR = "Floor";
        public static final String APPARTMENT_NUMBER = "AppartmentNumber";
        public static final String EXTRA_DIRECTIONS = "ExtraDirections";
        public static final String DATE_OF_BIRTH = "DateOfBirth";

    }

    public static final class ChangePassword {
        public static final String OLD_PASSWORD = "OldPassword";
        public static final String NEW_PASSWORD = "NewPassword";
        public static final String CONFIRM_PASSWORD = "ConfirmPassword";
    }

    public static final class Login {
        public static final String GRANT_TYPE = "grant_type";
        public static final String GRANT_VALUE = "password";
        public static final String USERNAME = "username";
        public static final String USER_PASSWORD = "password";


        public static final String ACCESS_TOKEN = "access_token";
        public static final String TOKEN_TYPE = "token_type";
        public static final String EXPIRES_IN = "expires_in";
        public static final String USER_NAME = "userName";
        public static final String ISSUED = ".issued";
        public static final String EXPIRES = ".expires";
        public static final String ERROR = "error";
        public static final String ERROR_MSG = "error_description";

    }

    public static final class BrowseSupermarket {
        public static final String PAGE_INDEX = "pageIndex";
        public static final String PAGE_SIZE = "pageSize";
        public static final String CITY_ID = "cityId";
        public static final String AREA_ID = "areaId";
        public static final int PAGE_SIZE_VALUE = 10;


        public static final String SUPERMARKET_ID = "supermarketId";
        public static final String SUBCATEGORIES_ID = "subCategoryId";
        public static final String BARCODE_ID = "barcode";
    }

    public static final class Supermarket {
        public static final String CLOSED = "Closed";
        public static final String OPEN = "Open";
        public static final String AED = " AED";
        public static final int CASH = 1;
        public static final int DEPIT = 2;
        public static final int CREDIT = 3;

        public static final String CASH_VALUE = "Cash";
        public static final String DEPIT_VALUE = "Depit";
        public static final String CREDIT_VALUE = "Credit";

        public static final int SPECIALS_CATEGORIES = 1;
        public static final int SPECIALS_ITEMS = 2;
        public static final int NORMAL = 3;
        public static final int DEALS = 4;
        public static final int FAVORITE = 5;
        public static final int ARRIVALS = 6;
    }

    public static final class Favourite {
        public static final int FAVOURITE = 1;
        public static final int ALL_ITEMS = 2;
        public static final int NEW_ARRIVALS = 3;
    }

    public static final class Order {
        public static final int ONGOING = 2;
        public static final int DELIVERED = 3;
        public static final int NO_ACTION = 1;
    }

    public static final class Test {
        public static final int SUPERMARKET_ID = 1002;
    }

    public static final class ShoppingCart {
        public static final int ShoppingCart = 112233;
    }
    /**
     * @Http Status Code
     *
     * Informational 1xx
     *          100 Continue
     *          101 Switching Protocols
     *
     * Successful 2xx
     *          200 OK
     *          201 Created
     *          202 Accepted
     *          203 Non-Authoritative Information
     *          204 No Content
     *          205 Reset Content
     *          206 Partial Content
     *
     * Redirection 3xx
     *          300 Multiple Choices
     *          301 Moved Permanently
     *          302 Found
     *          303 See Other
     *          304 Not Modified
     *          305 Use Proxy
     *          306 (Unused)
     *          307 Temporary Redirect
     *
     * Client Error 4xx
     *          400 Bad Request
     *          401 Unauthorized
     *          402 Payment Required
     *          403 Forbidden
     *          404 Not Found
     *          405 Method Not Allowed
     *          406 Not Acceptable
     *          407 Proxy Authentication Required
     *          408 Request Timeout
     *          409 Conflict
     *          410 Gone
     *          411 Length Required
     *          412 Precondition Failed
     *          413 Request Entity Too Large
     *          414 Request-URI Too Long
     *          415 Unsupported Media Type
     *          416 Requested Range Not Satisfiable
     *          417 Expectation Failed
     *
     * Server Error 5xx
     *          500 Internal Server Error
     *          501 Not Implemented
     *          502 Bad Gateway
     *          503 Service Unavailable
     *          504 Gateway Timeout
     *          505 HTTP Version Not Supported
     */
}