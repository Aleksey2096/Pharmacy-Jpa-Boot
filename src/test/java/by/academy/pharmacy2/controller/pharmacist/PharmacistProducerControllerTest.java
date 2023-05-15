package by.academy.pharmacy2.controller.pharmacist;

import by.academy.pharmacy2.dto.ProducerDTO;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.service.database.ProducerDBService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static by.academy.pharmacy2.MockUtil.createTestPaginationObject;
import static by.academy.pharmacy2.MockUtil.createTestProducerDTO;
import static by.academy.pharmacy2.TestConstant.FORMAT;
import static by.academy.pharmacy2.TestConstant.ID_COLUMN_TITLE;
import static by.academy.pharmacy2.TestConstant.ID_ONE;
import static by.academy.pharmacy2.TestConstant.ID_ONE_EDIT;
import static by.academy.pharmacy2.TestConstant.PAGE_CONTENT1;
import static by.academy.pharmacy2.TestConstant.PAGE_CONTENT2;
import static by.academy.pharmacy2.TestConstant.PAGE_CONTENT3;
import static by.academy.pharmacy2.entity.Constant.COMPANY_NAME;
import static by.academy.pharmacy2.entity.Constant.COUNTRY;
import static by.academy.pharmacy2.entity.Constant.CREATION_DATE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.NEW;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRODUCERS;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRODUCERS_EDIT;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRODUCERS_INDEX;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRODUCERS_NEW;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.PRODUCER;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = PharmacistProducerController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class PharmacistProducerControllerTest {
    @MockBean
    private ProducerDBService producerDBServiceImpl;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndex() throws Exception {
        PaginationObject<ProducerDTO> paginationObject = createTestPaginationObject();
        when(producerDBServiceImpl.readPaginated(anyInt(), anyInt(), anyString(), anyString(),
                anyString())).thenReturn(paginationObject);
        mockMvc.perform(get(PHARMACIST_PRODUCERS))
                .andExpect(status().isOk())
                .andExpect(view().name(PHARMACIST_PRODUCERS_INDEX))
                .andExpect(model().attributeExists(SEARCH_VALUE))
                .andExpect(model().attribute(SEARCH_VALUE, EMPTY))
                .andExpect(model().attributeExists(ORDER_FIELD))
                .andExpect(model().attribute(ORDER_FIELD, ID_COLUMN_TITLE))
                .andExpect(model().attributeExists(ORDER_TYPE))
                .andExpect(model().attribute(ORDER_TYPE, DEFAULT_ORDER_TYPE))
                .andExpect(model().attributeExists(PAGINATION))
                .andExpect(model().attribute(PAGINATION, paginationObject))
                .andExpect(content().string(containsString(PAGE_CONTENT1)));
    }

    @Test
    public void testNewObject() throws Exception {
        mockMvc.perform(get(PHARMACIST_PRODUCERS + NEW)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(status().isOk())
                .andExpect(view().name(PHARMACIST_PRODUCERS_NEW))
                .andExpect(model().attributeExists(PREVIOUS_REQUEST_LINK))
                .andExpect(model().attribute(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(content().string(containsString(PAGE_CONTENT2)));
    }

    @Test
    public void testCreate() throws Exception {
        ProducerDTO producerDTO = createTestProducerDTO();
        when(producerDBServiceImpl.create(any(ProducerDTO.class))).thenReturn(producerDTO);
        mockMvc.perform(post(PHARMACIST_PRODUCERS)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS)
                        .param(COMPANY_NAME, producerDTO.getCompanyName())
                        .param(COUNTRY, producerDTO.getCountry().toString())
                        .param(CREATION_DATE, FORMAT.format(producerDTO.getCreationDate())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + PHARMACIST_PRODUCERS))
                .andExpect(model().attributeDoesNotExist(PREVIOUS_REQUEST_LINK));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        mockMvc.perform(post(PHARMACIST_PRODUCERS)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(status().isOk())
                .andExpect(view().name(PHARMACIST_PRODUCERS_NEW))
                .andExpect(model().attributeExists(PREVIOUS_REQUEST_LINK))
                .andExpect(model().attribute(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(content().string(containsString(PAGE_CONTENT2)));
    }

    @Test
    public void testEdit() throws Exception {
        ProducerDTO producerDTO = createTestProducerDTO();
        when(producerDBServiceImpl.readById(anyLong())).thenReturn(producerDTO);
        mockMvc.perform(get(PHARMACIST_PRODUCERS + ID_ONE_EDIT)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(status().isOk())
                .andExpect(view().name(PHARMACIST_PRODUCERS_EDIT))
                .andExpect(model().attributeExists(PREVIOUS_REQUEST_LINK))
                .andExpect(model().attribute(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(model().attributeExists(PRODUCER))
                .andExpect(model().attribute(PRODUCER, producerDTO))
                .andExpect(content().string(containsString(PAGE_CONTENT3)));
    }

    @Test
    public void testUpdate() throws Exception {
        ProducerDTO producerDTO = createTestProducerDTO();
        mockMvc.perform(patch(PHARMACIST_PRODUCERS + ID_ONE)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS)
                        .param(ID_COLUMN_TITLE, producerDTO.getId().toString())
                        .param(COMPANY_NAME, producerDTO.getCompanyName())
                        .param(COUNTRY, producerDTO.getCountry().toString())
                        .param(CREATION_DATE, FORMAT.format(producerDTO.getCreationDate())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + PHARMACIST_PRODUCERS))
                .andExpect(model().attributeDoesNotExist(PREVIOUS_REQUEST_LINK));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        mockMvc.perform(patch(PHARMACIST_PRODUCERS + ID_ONE)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(status().isOk())
                .andExpect(view().name(PHARMACIST_PRODUCERS_EDIT))
                .andExpect(model().attributeExists(PREVIOUS_REQUEST_LINK))
                .andExpect(model().attribute(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(content().string(containsString(PAGE_CONTENT3)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(PHARMACIST_PRODUCERS + ID_ONE)
                        .param(PREVIOUS_REQUEST_LINK, PHARMACIST_PRODUCERS))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT + PHARMACIST_PRODUCERS));
    }
}
