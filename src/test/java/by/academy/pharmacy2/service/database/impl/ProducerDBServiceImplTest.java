package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.ProducerDTO;
import by.academy.pharmacy2.entity.ProducerEntity;
import by.academy.pharmacy2.service.database.ProducerDBService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static by.academy.pharmacy2.MockUtil.clearProducersTable;
import static by.academy.pharmacy2.MockUtil.createTestProducer;
import static by.academy.pharmacy2.MockUtil.getConnection;
import static by.academy.pharmacy2.MockUtil.insertProducer;
import static by.academy.pharmacy2.MockUtil.selectProducerById;
import static by.academy.pharmacy2.TestConstant.EQUALS_ALL_FIELDS;
import static by.academy.pharmacy2.TestConstant.UPDATED_COUNTRY;
import static by.academy.pharmacy2.TestConstant.UPDATED_DATE;
import static by.academy.pharmacy2.TestConstant.UPDATED_STRING;
import static by.academy.pharmacy2.TestConstant.ZERO_INDEX;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ProducerDBServiceImplTest {
    @Autowired
    ProducerDBService producerDBServiceImpl;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void testCreate() throws SQLException {
        ProducerEntity producer = createTestProducer();
        ProducerDTO producerDTO = modelMapper.map(producer, ProducerDTO.class);
        ProducerDTO createdProducerDTO = producerDBServiceImpl.create(producerDTO);
        ProducerEntity expectedProducer = modelMapper.map(createdProducerDTO, ProducerEntity.class);
        ProducerEntity actualProducer = selectProducerById(expectedProducer.getId(),
                getConnection());
        Calendar expectedDate = Calendar.getInstance();
        Calendar actualDate = Calendar.getInstance();
        expectedDate.setTime(expectedProducer.getCreationDate());
        actualDate.setTime(actualProducer.getCreationDate());
        assertAll(EQUALS_ALL_FIELDS,
                () -> assertEquals(expectedProducer.getId(), actualProducer.getId()),
                () -> assertEquals(expectedProducer.getCompanyName(),
                        actualProducer.getCompanyName()),
                () -> assertEquals(expectedProducer.getCountry(), actualProducer.getCountry()),
                () -> assertEquals(expectedDate.get(Calendar.YEAR), actualDate.get(Calendar.YEAR)),
                () -> assertEquals(expectedDate.get(Calendar.DAY_OF_YEAR),
                        actualDate.get(Calendar.DAY_OF_YEAR)));
    }

    @Test
    public void testReadById() throws SQLException {
        ProducerEntity actualProducer = createTestProducer();
        insertProducer(actualProducer, getConnection());
        ProducerDTO producerDTO = producerDBServiceImpl.readById(actualProducer.getId());
        ProducerEntity expectedProducer = modelMapper.map(producerDTO, ProducerEntity.class);
        Calendar expectedDate = Calendar.getInstance();
        Calendar actualDate = Calendar.getInstance();
        expectedDate.setTime(expectedProducer.getCreationDate());
        actualDate.setTime(actualProducer.getCreationDate());
        assertAll(EQUALS_ALL_FIELDS,
                () -> assertEquals(expectedProducer.getId(), actualProducer.getId()),
                () -> assertEquals(expectedProducer.getCompanyName(),
                        actualProducer.getCompanyName()),
                () -> assertEquals(expectedProducer.getCountry(), actualProducer.getCountry()),
                () -> assertEquals(expectedDate.get(Calendar.YEAR), actualDate.get(Calendar.YEAR)),
                () -> assertEquals(expectedDate.get(Calendar.DAY_OF_YEAR),
                        actualDate.get(Calendar.DAY_OF_YEAR)));
    }

    @Test
    public void testUpdate() throws SQLException {
        ProducerEntity expectedProducer = createTestProducer();
        insertProducer(expectedProducer, getConnection());
        expectedProducer.setCompanyName(expectedProducer.getCompanyName() + UPDATED_STRING);
        expectedProducer.setCountry(UPDATED_COUNTRY);
        expectedProducer.setCreationDate(UPDATED_DATE);
        ProducerDTO producerDTO = modelMapper.map(expectedProducer, ProducerDTO.class);
        producerDBServiceImpl.update(producerDTO);
        ProducerEntity actualProducer = selectProducerById(expectedProducer.getId(),
                getConnection());
        Calendar expectedDate = Calendar.getInstance();
        Calendar actualDate = Calendar.getInstance();
        expectedDate.setTime(expectedProducer.getCreationDate());
        actualDate.setTime(actualProducer.getCreationDate());
        assertAll(EQUALS_ALL_FIELDS,
                () -> assertEquals(expectedProducer.getId(), actualProducer.getId()),
                () -> assertEquals(expectedProducer.getCompanyName(),
                        actualProducer.getCompanyName()),
                () -> assertEquals(expectedProducer.getCountry(), actualProducer.getCountry()),
                () -> assertEquals(expectedDate.get(Calendar.YEAR), actualDate.get(Calendar.YEAR)),
                () -> assertEquals(expectedDate.get(Calendar.DAY_OF_YEAR),
                        actualDate.get(Calendar.DAY_OF_YEAR)));
    }

    @Test
    public void testDeleteById() throws SQLException {
        ProducerEntity initialProducer = createTestProducer();
        insertProducer(initialProducer, getConnection());
        producerDBServiceImpl.deleteById(initialProducer.getId());
        ProducerEntity deletedProducer = selectProducerById(initialProducer.getId(),
                getConnection());
        assertAll(EQUALS_ALL_FIELDS,
                () -> assertNotNull(initialProducer.getId()),
                () -> assertNotNull(initialProducer.getCompanyName()),
                () -> assertNotNull(initialProducer.getCountry()),
                () -> assertNotNull(initialProducer.getCreationDate()),
                () -> assertNull(deletedProducer));
    }

    @Test
    public void testReadAll() throws SQLException {
        List<ProducerEntity> actualProducers = List.of(createTestProducer());
        for (ProducerEntity producer : actualProducers) {
            insertProducer(producer, getConnection());
        }
        List<ProducerEntity> expectedProducers = producerDBServiceImpl.readAll()
                .stream().map(x -> modelMapper.map(x, ProducerEntity.class)).toList();
        Calendar expectedDate = Calendar.getInstance();
        Calendar actualDate = Calendar.getInstance();
        expectedDate.setTime(expectedProducers.get(ZERO_INDEX).getCreationDate());
        actualDate.setTime(actualProducers.get(ZERO_INDEX).getCreationDate());
        assertAll(EQUALS_ALL_FIELDS,
                () -> assertEquals(expectedProducers.get(ZERO_INDEX).getId(),
                        actualProducers.get(ZERO_INDEX).getId()),
                () -> assertEquals(expectedProducers.get(ZERO_INDEX).getCompanyName(),
                        actualProducers.get(ZERO_INDEX).getCompanyName()),
                () -> assertEquals(expectedProducers.get(ZERO_INDEX).getCountry(),
                        actualProducers.get(ZERO_INDEX).getCountry()),
                () -> assertEquals(expectedDate.get(Calendar.YEAR), actualDate.get(Calendar.YEAR)),
                () -> assertEquals(expectedDate.get(Calendar.DAY_OF_YEAR),
                        actualDate.get(Calendar.DAY_OF_YEAR)));
    }

    @AfterEach
    public void clearTable() throws SQLException {
        clearProducersTable(Objects.requireNonNull(getConnection()));
    }
}