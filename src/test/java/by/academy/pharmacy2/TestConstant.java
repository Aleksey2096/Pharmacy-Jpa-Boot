package by.academy.pharmacy2;

import by.academy.pharmacy2.entity.Country;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.DATE_FORMAT;

public final class TestConstant {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);
    public static final String TEST_PROPERTIES_URL = "application";
    public static final String URL = "spring.datasource.url";
    public static final String USERNAME = "spring.datasource.username";
    public static final String PASSWORD = "spring.datasource.password";
    public static final String SELECT_PRODUCER_BY_ID_SQL
            = "SELECT id, company_name, country_code, creation_date FROM producers WHERE id = ?";
    public static final String CLEAR_PRODUCERS_TABLE_SQL = "DELETE FROM producers";
    public static final String INSERT_PRODUCER_SQL
            = "INSERT INTO producers (company_name, country_code, creation_date) VALUES (?,?,?)";
    public static final String ID_COLUMN_TITLE = "id";
    public static final String COMPANY_NAME_COLUMN_TITLE = "company_name";
    public static final String COUNTRY_CODE_COLUMN_TITLE = "country_code";
    public static final String CREATION_DATE_COLUMN_TITLE = "creation_date";
    public static final int ZERO_INDEX = 0;
    public static final int FIRST_INDEX = 1;
    public static final int SECOND_INDEX = 2;
    public static final int THIRD_INDEX = 3;
    public static final String TEST_COMPANY_NAME1 = "testCompanyName1";
    public static final Country TEST_COUNTRY1 = Country.SWEDEN;
    public static final Date TEST_CREATION_DATE1 = new Date();
    public static final String EQUALS_ALL_FIELDS = "Test equality of all Producer fields.";
    public static final String UPDATED_STRING = "_Updated";
    public static final Country UPDATED_COUNTRY = Country.CROATIA;
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String TEST_STRING_DATE = "26-09-1989";
    public static Date UPDATED_DATE;
    public static final String STRONG_PASSWORD = "qwerty";
    public static final String ID_ONE_EDIT = "/1/edit";
    public static final String ID_ONE = "/1";
    public static final String PAGE_CONTENT1 = "Company name";
    public static final String PAGE_CONTENT2 = "Add producer";
    public static final String PAGE_CONTENT3 = "Edit producer";

    static {
        try {
            UPDATED_DATE = new SimpleDateFormat(DATE_PATTERN).parse(TEST_STRING_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
