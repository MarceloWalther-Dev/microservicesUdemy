package br.com.marcelo.bookservice.api.controller;

import br.com.marcelo.bookservice.api.controller.BookController;
import br.com.marcelo.bookservice.domain.dto.BookDto;
import br.com.marcelo.bookservice.domain.mapper.BookMapper;
import br.com.marcelo.bookservice.domain.mapper.EntityMapper;
import br.com.marcelo.bookservice.domain.model.Book;
import br.com.marcelo.bookservice.domain.service.BookService;
import br.com.marcelo.cambioservice.domain.controller.CustomUtils;
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
public class BookControllerTest {
    private static final String ENDPOINT_URL = "/api/book;@InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<BookDto> page = new PageImpl<>(Collections.singletonList(BookBuilder.getDto()));

        Mockito.when(bookService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(bookService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(bookService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(bookService.findById(ArgumentMatchers.anyLong())).thenReturn(BookBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(bookService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(bookService.save(ArgumentMatchers.any(BookDto.class))).thenReturn(BookBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(BookBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(bookService, Mockito.times(1)).save(ArgumentMatchers.any(BookDto.class));
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(bookService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(BookBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(BookBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(bookService, Mockito.times(1)).update(ArgumentMatchers.any(BookDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(bookService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(BookBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(bookService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(bookService);
    }
}