package by.academy.pharmacy2;

import by.academy.pharmacy2.dto.ProducerDTO;
import by.academy.pharmacy2.entity.Country;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.ProducerEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import static by.academy.pharmacy2.TestConstant.CLEAR_PRODUCERS_TABLE_SQL;
import static by.academy.pharmacy2.TestConstant.COMPANY_NAME_COLUMN_TITLE;
import static by.academy.pharmacy2.TestConstant.COUNTRY_CODE_COLUMN_TITLE;
import static by.academy.pharmacy2.TestConstant.CREATION_DATE_COLUMN_TITLE;
import static by.academy.pharmacy2.TestConstant.FIRST_INDEX;
import static by.academy.pharmacy2.TestConstant.ID_COLUMN_TITLE;
import static by.academy.pharmacy2.TestConstant.INSERT_PRODUCER_SQL;
import static by.academy.pharmacy2.TestConstant.PASSWORD;
import static by.academy.pharmacy2.TestConstant.SECOND_INDEX;
import static by.academy.pharmacy2.TestConstant.SELECT_PRODUCER_BY_ID_SQL;
import static by.academy.pharmacy2.TestConstant.TEST_COMPANY_NAME1;
import static by.academy.pharmacy2.TestConstant.TEST_COUNTRY1;
import static by.academy.pharmacy2.TestConstant.TEST_CREATION_DATE1;
import static by.academy.pharmacy2.TestConstant.TEST_PROPERTIES_URL;
import static by.academy.pharmacy2.TestConstant.THIRD_INDEX;
import static by.academy.pharmacy2.TestConstant.URL;
import static by.academy.pharmacy2.TestConstant.USERNAME;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;

public final class MockUtil {
    private static final ResourceBundle PROPERTIES = ResourceBundle.getBundle(
            TEST_PROPERTIES_URL);
    public static final String URL_STRING = PROPERTIES.getString(URL);
    public static final String USER = PROPERTIES.getString(USERNAME);
    public static final String PASS = PROPERTIES.getString(PASSWORD);

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL_STRING, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProducerEntity createTestProducer() {
        ProducerEntity producer = new ProducerEntity();
        producer.setCompanyName(TEST_COMPANY_NAME1);
        producer.setCountry(TEST_COUNTRY1);
        producer.setCreationDate(TEST_CREATION_DATE1);
        return producer;
    }

    public static ProducerDTO createTestProducerDTO() {
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setId(1L);
        producerDTO.setCompanyName(TEST_COMPANY_NAME1);
        producerDTO.setCountry(TEST_COUNTRY1);
        producerDTO.setCreationDate(TEST_CREATION_DATE1);
        return producerDTO;
    }

    public static PaginationObject<ProducerDTO> createTestPaginationObject() {
        PaginationObject<ProducerDTO> paginationObject = new PaginationObject<>();
        paginationObject.setCurrentPage(FIRST_INDEX);
        paginationObject.setRecordsPerPage(DEFAULT_RECORDS_PER_PAGE);
        paginationObject.setPagesNum(FIRST_INDEX);
        paginationObject.setRecords(List.of(createTestProducerDTO()));
        return paginationObject;
    }

    public static void clearProducersTable(final Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CLEAR_PRODUCERS_TABLE_SQL)) {
            statement.executeUpdate();
        }
    }

    public static ProducerEntity selectProducerById(final long id, final Connection connection)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCER_BY_ID_SQL)) {
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultset = statement.executeQuery();
            ProducerEntity producer = null;
            while (resultset.next()) {
                producer = createProducer(resultset);
            }
            return producer;
        }
    }

    public static void insertProducer(final ProducerEntity producer, final Connection connection)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCER_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX, producer.getCompanyName());
            statement.setString(SECOND_INDEX, producer.getCountry().getCode());
            statement.setDate(THIRD_INDEX, new java.sql.Date(producer.getCreationDate().getTime()));
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                producer.setId(statement.getGeneratedKeys().getLong(FIRST_INDEX));
            }
        }
    }

    private static ProducerEntity createProducer(final ResultSet resultset) throws SQLException {
        ProducerEntity producer = new ProducerEntity();
        producer.setId(resultset.getLong(ID_COLUMN_TITLE));
        producer.setCompanyName(resultset.getString(COMPANY_NAME_COLUMN_TITLE));
        producer.setCountry(Country.valueOfCode(resultset.getString(COUNTRY_CODE_COLUMN_TITLE)));
        producer.setCreationDate(resultset.getDate(CREATION_DATE_COLUMN_TITLE));
        return producer;
    }
}
