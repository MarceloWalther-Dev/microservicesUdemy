package br.com.marcelo.cambioservice.domain.controller;

import br.com.marcelo.cambioservice.api.controller.CambioController;
import br.com.marcelo.cambioservice.api.dto.CambioDto;
import br.com.marcelo.cambioservice.domain.mapper.CambioMapper;
import br.com.marcelo.cambioservice.domain.mapper.EntityMapper;
import br.com.marcelo.cambioservice.domain.model.Cambio;
import br.com.marcelo.cambioservice.domain.service.CambioService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
public class CambioControllerTest {
    private static final String ENDPOINT_URL = "/api/cambio;@InjectMocks
    private CambioController cambioController;
    @Mock
    private CambioService cambioService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(cambioController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<CambioDto> page = new PageImpl<>(Collections.singletonList(CambioBuilder.getDto()));

        Mockito.when(cambioService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(cambioService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(cambioService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(cambioService.findById(ArgumentMatchers.anyLong())).thenReturn(CambioBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(cambioService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(cambioService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(cambioService.save(ArgumentMatchers.any(CambioDto.class))).thenReturn(CambioBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CambioBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(cambioService, Mockito.times(1)).save(ArgumentMatchers.any(CambioDto.class));
        Mockito.verifyNoMoreInteractions(cambioService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(cambioService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(CambioBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(CambioBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(cambioService, Mockito.times(1)).update(ArgumentMatchers.any(CambioDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(cambioService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(cambioService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(CambioBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(cambioService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(cambioService);
    }
}