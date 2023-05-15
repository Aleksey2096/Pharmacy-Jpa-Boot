package by.academy.pharmacy2.entity;

/**
 * constants storage.
 */
public final class Constant {
    /**
     * SuppressWarnings type.
     */
    public static final String UNCHECKED = "unchecked";
    /**
     * table title.
     */
    public static final String PRODUCER = "producer";
    /**
     * session UserDTO attribute title.
     */
    public static final String USER = "user";
    /**
     * session attribute or request parameter holding link to previous request.
     */
    public static final String PREVIOUS_REQUEST_LINK = "previousRequestLink";
    public static final String PREVIOUS_REQUEST_LINK_APPROVED = "previousRequestLinkApproved";

    /**
     * initial number of page.
     */
    public static final Integer DEFAULT_PAGE_NUMBER = 1;
    /**
     * initial number of records displayed on single page.
     */
    public static final Integer DEFAULT_RECORDS_PER_PAGE = 5;
    /**
     * initial order type.
     */
    public static final String DEFAULT_ORDER_TYPE = "asc";
    /**
     * request parameter title.
     */
    public static final String ORDER_TYPE = "orderType";
    /**
     * request parameter title.
     */
    public static final String ORDER_FIELD = "orderField";
    /**
     * request parameter title.
     */
    public static final String SEARCH_VALUE = "searchValue";
    /**
     * name of html input with type="file".
     */
    public static final String PRODUCT = "product";
    public static final String MEDICINE = "medicine";
    public static final String MEDICINE_PRODUCT = "medicineProduct";
    public static final String MEDICINE_PRODUCTS = "medicineProducts";

    // Fields and database properties
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DOSAGE = "dosage";
    public static final String FORM = "form";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String DATE = "date";
    public static final String ROLE = "role";
    public static final String ROLE_ = "ROLE_";

    public static final String HEALTH_CARE_CARD_NUMBER = "healthCareCardNumber";
    public static final String DATE_JOINED = "joinedDate";
    public static final String AVATAR_IMAGE_PATH = "avatarImagePath";
    public static final String PRESCRIPTION_SCAN_PATH = "prescriptionScanPath";
    public static final String UPLOAD_DATE_TIME = "uploadDateTime";
    public static final String PRESCRIPTION_REQUEST_STATUS = "prescriptionRequestStatus";
    public static final String PRESCRIPTION_REQUEST = "prescriptionRequest";
    public static final String CART_ORDER = "cart_order";
    /**
     * table column.
     */
    public static final String LOGIN = "login";
    /**
     * table column.
     */
    public static final String COMPANY_NAME = "companyName";
    public static final String COUNTRY = "country";
    public static final String CREATION_DATE = "creationDate";

    // database properties

    public static final String HEALTH_CARE_CARD_NUMBER_DB = "health_care_card_number";
    public static final String PRODUCER_ID_DB = "producer_id";
    public static final String MEDICINE_ID_DB = "medicine_id";
    public static final String MEDICINE_PRODUCT_ID_DB = "medicine_product_id";
    public static final String IS_NONPRESCRIPTION_DB = "is_nonprescription";
    public static final String APPROVAL_DATE_DB = "approval_date";
    public static final String MEDICINE_IMAGE_PATH_DB = "medicine_image_path";
    public static final String MEDICINE_IMAGE_PATH = "medicineImagePath";
    public static final String DATE_TIME_DB = "date_time";
    public static final String PAYMENT_CARD_NUMBER_DB = "payment_card_number";
    public static final String CONTACT_PHONE_DB = "contact_phone";
    public static final String DELIVERY_ADDRESS_DB = "delivery_address";
    public static final String DATE_OF_BIRTH_DB = "date_of_birth";
    public static final String PERSONAL_ACCOUNT_DB = "personal_account";
    public static final String COMPANY_NAME_DB = "company_name";
    public static final String COUNTRY_CODE_DB = "country_code";
    public static final String CREATION_DATE_DB = "creation_date";
    public static final String DATE_JOINED_DB = "date_joined";
    public static final String AVATAR_IMAGE_PATH_DB = "avatar_image_path";
    public static final String PRESCRIPTION_SCAN_PATH_DB = "prescription_scan_path";
    public static final String UPLOAD_DATE_TIME_DB = "upload_date_time";
    public static final String PRESCRIPTION_REQUEST_STATUS_DB = "prescription_request_status";

    // database table titles

    public static final String ADDRESSES = "addresses";
    public static final String MEDICINES = "medicines";
    public static final String MEDICINE_PRODUCTS_DB = "medicine_products";
    public static final String ORDERS = "orders";
    public static final String PERSONAL_INFO_DB = "personal_info";
    public static final String PRESCRIPTIONS = "prescriptions";
    public static final String PRESCRIPTION = "prescription";
    public static final String USERS = "users";
    public static final String PRODUCERS = "producers";
    public static final String PRESCRIPTION_REQUESTS_DB = "prescription_requests";
    public static final String PRESCRIPTION_REQUESTS_ID = "prescriptionRequestId";
    // hibernate variable properties

    public static final String ADDRESS = "address";
    public static final String CART = "cart";
    public static final String PERSONAL_INFO = "personalInfo";
    public static final String CARTS = "carts";
    public static final String LOCAL_DATE_TIME = "localDateTime";
    public static final String PAYMENT_CARD_NUMBER = "paymentCardNumber";
    public static final String CONTACT_PHONE = "contactPhone";
    public static final String DELIVERY_ADDRESS = "deliveryAddress";
    public static final String IS_NONPRESCRIPTION = "isNonprescription";
    public static final String APPROVAL_DATE = "approvalDate";

    // URL properties
    public static final String LOCALE = "locale";
    public static final String PAGINATION = "pagination";
    public static final String REQUEST = "request";
    public static final String IMG_MEDICINE = "/img/medicine/";
    public static final String JPG = ".jpg";
    public static final String REDIRECT = "redirect:";
    public static final String EMPTY = "";
    public static final String SLASH = "/";
    public static final String COLON = ":";
    public static final String SPACE = " ";
    public static final String IMG_PRESCRIPTION_SCAN = "/img/prescriptionScan/";
    public static final String IMG_USER = "/img/user/";
    public static final String STATIC_PATH = "src/main/resources/static";
    public static final String LANGUAGES_TEXT = "languages/text";
    public static final String US = "US";
    public static final String MESSAGE_SOURCE = "messageSource";
    public static final String UTF_8 = "UTF-8";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String PHARMACIST = "PHARMACIST";
    public static final String CLIENT = "CLIENT";
    public static final String PHARMACIST1 = "/pharmacist/**";
    public static final String CLIENT_MEDICINE_PRODUCTS_ADD_TO_CART
            = "/client/medicineProducts/*/addToCart";
    public static final String ADMINISTRATOR1 = "/administrator/**";
    public static final String LOGOUT = "/logout";
    public static final String PROCESS_LOGIN = "/process_login";
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String JS = "/js/**";
    public static final String CSS = "/css/**";
    public static final String IMG = "/img/**";
    public static final String AUTH_REGISTRATION = "/auth/registration";
    public static final String CLIENT_MEDICINE = "/client/medicine*/**";
    public static final String ERROR = "/error";
    public static final String AUTH_LOGIN_ERROR = "/auth/login?error";
    public static final String ADMINISTRATOR_ORDERS = "/administrator/orders";
    public static final String PAGE_CURRENT_PAGE = "/page/{currentPage}";
    public static final String ADMINISTRATOR_ORDERS_INDEX = "administrator/orders/index";
    public static final String ADMINISTRATOR_PRESCRIPTION_REQUESTS
            = "/administrator/prescriptionRequests";
    public static final String ADMINISTRATOR_PRESCRIPTION_REQUESTS_INDEX
            = "administrator/prescriptionRequests/index";
    public static final String ADMINISTRATOR_USERS = "/administrator/users";
    public static final String ADMINISTRATOR_USERS_INDEX = "administrator/users/index";
    public static final String ADMINISTRATOR_USERS_NEW = "administrator/users/new";
    public static final String ID_EDIT = "/{id}/edit";
    public static final String ADMINISTRATOR_USERS_EDIT = "administrator/users/edit";
    public static final String NEW = "/new";
    public static final String ID_PARAM = "/{id}";
    public static final String CLIENT_CART = "/client/cart";
    public static final String CLIENT_CART_REST = "/client/cart/rest";
    public static final String CLIENT_CART_INDEX = "client/cart/index";
    public static final String MAKE_ORDER = "/makeOrder";
    public static final String CLIENT_MEDICINES = "/client/medicines";
    public static final String ID_ADD_TO_CART = "/{id}/addToCart";
    public static final String ID_DELETE_FROM_CART = "/{id}/deleteFromCart";
    public static final String VALIDATE_ORDER = "/validateOrder";
    public static final String RESULT_NOT_ENOUGH_FUNDS = "{\"result\" : \"notEnoughFunds\"}";
    public static final String RESULT_PRESCRIPTION_REQUIRED
            = "{\"result\" : \"prescriptionRequired\"}";
    public static final String RESULT_OK = "{\"result\" : \"ok\"}";
    public static final String CLIENT_MEDICINES_INDEX = "client/medicines/index";
    public static final String CLIENT_MEDICINE_PRODUCTS = "/client/medicineProducts";
    public static final String MEDICINE_ID = "/{medicineId}";
    public static final String MEDICINE_ID_PAGE_CURRENT_PAGE = "/{medicineId}/page/{currentPage}";
    public static final String CLIENT_MEDICINE_PRODUCTS_INDEX = "client/medicineProducts/index";
    public static final String CLIENT_ORDERS = "/client/orders";
    public static final String CLIENT_ORDERS_INDEX = "client/orders/index";
    public static final String CLIENT_PRESCRIPTIONS = "/client/prescriptions";
    public static final String CLIENT_PRESCRIPTIONS_INDEX = "client/prescriptions/index";
    public static final String CLIENT_PRESCRIPTION_REQUESTS = "/client/prescriptionRequests";
    public static final String CLIENT_PRESCRIPTION_REQUESTS_INDEX
            = "client/prescriptionRequests/index";
    public static final String CLIENT_PRESCRIPTION_REQUESTS_NEW = "client/prescriptionRequests/new";
    public static final String CLIENT_USERS = "/client/users";
    public static final String EDIT = "/edit";
    public static final String CLIENT_USERS_EDIT = "client/users/edit";
    public static final String PHARMACIST_MEDICINES = "/pharmacist/medicines";
    public static final String PHARMACIST_MEDICINES_INDEX = "pharmacist/medicines/index";
    public static final String PHARMACIST_MEDICINES_NEW = "pharmacist/medicines/new";
    public static final String PHARMACIST_MEDICINES_EDIT = "pharmacist/medicines/edit";
    public static final String PHARMACIST_MEDICINE_PRODUCTS = "/pharmacist/medicineProducts";
    public static final String PHARMACIST_MEDICINE_PRODUCTS_INDEX
            = "pharmacist/medicineProducts/index";
    public static final String PHARMACIST_MEDICINE_PRODUCTS_NEW = "pharmacist/medicineProducts/new";
    public static final String PHARMACIST_MEDICINE_PRODUCTS_EDIT
            = "pharmacist/medicineProducts/edit";
    public static final String PHARMACIST_PRESCRIPTIONS = "/pharmacist/prescriptions";
    public static final String PRESCRIPTION_REQUEST_ID_NEW = "/{prescriptionRequestId}/new";
    public static final String PHARMACIST_PRESCRIPTIONS_NEW = "pharmacist/prescriptions/new";
    public static final String PRESCRIPTION_REQUEST_ID = "/{prescriptionRequestId}";
    public static final String PHARMACIST_PRESCRIPTION_REQUESTS
            = "/pharmacist/prescriptionRequests";
    public static final String PHARMACIST_PRESCRIPTION_REQUESTS_INDEX
            = "pharmacist/prescriptionRequests/index";
    public static final String PHARMACIST_PRODUCERS = "/pharmacist/producers";
    public static final String PHARMACIST_PRODUCERS_INDEX = "pharmacist/producers/index";
    public static final String PHARMACIST_PRODUCERS_NEW = "pharmacist/producers/new";
    public static final String PHARMACIST_PRODUCERS_EDIT = "pharmacist/producers/edit";
    public static final String AUTH = "/auth";
    public static final String LOGIN_PATH = "/login";
    public static final String AUTH_LOGIN1 = "auth/login";
    public static final String REGISTRATION = "/registration";
    public static final String AUTH_REGISTRATION1 = "auth/registration";
    public static final String ERR = "err";
    public static final String ERROR1 = "error";
    public static final String ERROR_CONSTRAINT_VIOLATION = "error.constraintViolation";
    public static final String ERROR_POSTCODE = "error.postcode";
    public static final String A_ZA_Z_$ = "^[A-Za-z]+$";
    public static final String ERROR_CITY = "error.city";
    public static final String ERROR_STREET = "error.street";
    public static final String ERROR_HOUSE = "error.house";
    public static final String ERROR_APARTMENT = "error.apartment";
    public static final String ERROR_TITLE = "error.title";
    public static final String ERROR_APPROVAL_DATE = "error.approvalDate";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ERROR_DOSAGE = "error.dosage";
    public static final String ERROR_PRICE = "error.price";
    public static final String ERROR_AMOUNT = "error.amount";
    public static final int INT_TWO = 2;
    public static final int INT_SIX = 6;
    public static final int INT_ZERO = 0;
    public static final int INT_SIXTEEN = 16;
    public static final int INT_TEN = 10;
    public static final long PAYMENT_CARD_NUMBER_MIN_VALUE = 1000000000000000L;
    public static final String ERROR_SURNAME = "error.surname";
    public static final String ERROR_NAME = "error.name";
    public static final String ERROR_BIRTH_DATE = "error.birthDate";
    public static final String ERROR_PHONE = "error.phone";
    public static final String PHONE_PATTERN = "^\\+([0-9]{11})$";
    public static final String ERROR_EMAIL = "error.email";
    public static final String ERROR_PERSONAL_ACCOUNT = "error.personalAccount";
    public static final String ERROR_PAYMENT_CARD_NUMBER = "error.paymentCardNumber";
    public static final String ERROR_DATE = "error.date";
    public static final String ERROR_COMPANY_NAME = "error.companyName";
    public static final String ERROR_CREATION_DATE = "error.creationDate";
    public static final String ERROR_PASSWORD = "error.password";
    public static final String ERROR_HEALTH_CARE_CARD_NUMBER = "error.healthCareCardNumber";
    public static final String ERROR_LOGIN = "error.login";
    public static final long HEALTH_CARE_CARD_NUMBER_MIN_VALUE = 1000000000000000L;
    public static final String LOGIN_PATTERN
            = "^[a-zA-Z\\d]([._-](?![._-])|[a-zA-Z\\d]){3,18}[a-zA-Z\\d]$";
    public static final String PASSWORD_PATTERN
            = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    public static final String AUSTRALIA1 = "Australia";
    public static final String AU = "AU";
    public static final String AUSTRIA1 = "Austria";
    public static final String AT = "AT";
    public static final String BELGIUM1 = "Belgium";
    public static final String BE = "BE";
    public static final String CANADA1 = "Canada";
    public static final String CA = "CA";
    public static final String CROATIA1 = "Croatia";
    public static final String HR = "HR";
    public static final String JAPAN1 = "Japan";
    public static final String JP = "JP";
    public static final String UNITED_STATES1 = "United States";
    public static final String POLAND1 = "Poland";
    public static final String PL = "PL";
    public static final String SOUTH_KOREA1 = "South Korea";
    public static final String KR = "KR";
    public static final String SWEDEN1 = "Sweden";
    public static final String SE = "SE";
    public static final String USER_WITH_THIS_LOGIN_NOT_EXIST
            = "User with this login doesn't exist!";
    public static final String ERROR_MEDICINE_IMAGE_PATH = "error.medicineImagePath";
    public static final String ERROR_PRESCRIPTION_SCAN_PATH = "error.prescriptionScanPath";
    public static final String USER_HEALTH_CARE_CARD_NUMBER = "user.healthCareCardNumber";
    public static final String MEDICINE_PRODUCT_ID = "medicineProduct.id";
    public static final String ERROR_MEDICINE_PRODUCT_DTO = "error.medicineProductDTO";
    public static final String ERROR_SAME_HEALTH_CARE_CARD_NUMBER
            = "error.sameHealthCareCardNumber";
    public static final String ERROR_AVATAR_IMAGE_PATH = "error.avatarImagePath";
    public static final String ERROR_SAME_LOGIN = "error.sameLogin";
    public static final String ENTER_WITH_ARGUMENT_S = "Enter: {}.{}() with argument[s] = {}";
    public static final String EXIT_WITH_RESULT = "Exit: {}.{}() with result = {}";
    public static final String EXCEPTION_IN_WITH_CAUSE = "Exception in {}.{}() with cause = {}";
    public static final String E = "e";
    public static final String JOIN_POINT_E = "joinPoint,e";
    public static final String PERCENTAGE_VALUE = "%%%s%%";

    /**
     * default constructor.
     */
    private Constant() {
    }
}
