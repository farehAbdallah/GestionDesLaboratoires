//package net.chabab.gestionanalyse.web;
//
//import net.chabab.gestionanalyse.dtos.AnalyseDTO;
//import net.chabab.gestionanalyse.service.AnalyseService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class AnalyseControllerTest {
//
//    @Mock
//    private AnalyseService analyseService;
//
//    @InjectMocks
//    private AnalyseController analyseController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(analyseController).build();
//    }
//
//    @Test
//    void testCreateAnalyse() throws Exception {
//        AnalyseDTO analyseDTO = new AnalyseDTO(1L, "Analyse 1", "Description 1", null);
//        when(analyseService.createAnalyse(any(AnalyseDTO.class))).thenReturn(analyseDTO);
//
//        mockMvc.perform(post("/analyses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nom\":\"Analyse 1\",\"description\":\"Description 1\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.nom").value("Analyse 1"))
//                .andExpect(jsonPath("$.description").value("Description 1"));
//
//        verify(analyseService, times(1)).createAnalyse(any(AnalyseDTO.class));
//    }
//
//    @Test
//    void testGetById() throws Exception {
//        AnalyseDTO analyseDTO = new AnalyseDTO(1L, "Analyse 1", "Description 1", null);
//        when(analyseService.getAnalyseById(1L)).thenReturn(analyseDTO);
//
//        mockMvc.perform(get("/analyses/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.nom").value("Analyse 1"))
//                .andExpect(jsonPath("$.description").value("Description 1"));
//
//        verify(analyseService, times(1)).getAnalyseById(1L);
//    }
//
//    @Test
//    void testGetAllAnalyses() throws Exception {
//        List<AnalyseDTO> analyses = Arrays.asList(
//                new AnalyseDTO(1L, "Analyse 1", "Description 1", null),
//                new AnalyseDTO(2L, "Analyse 2", "Description 2", null)
//        );
//        when(analyseService.getAllAnalyses()).thenReturn(analyses);
//
//        mockMvc.perform(get("/analyses"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].nom").value("Analyse 1"))
//                .andExpect(jsonPath("$[1].nom").value("Analyse 2"));
//
//        verify(analyseService, times(1)).getAllAnalyses();
//    }
//
//    @Test
//    void testUpdateAnalyse() throws Exception {
//        AnalyseDTO updatedAnalyse = new AnalyseDTO(1L, "Analyse Updated", "Updated Description", null);
//        when(analyseService.updateAnalyse(eq(1L), any(AnalyseDTO.class))).thenReturn(updatedAnalyse);
//
//        mockMvc.perform(put("/analyses/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nom\":\"Analyse Updated\",\"description\":\"Updated Description\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.nom").value("Analyse Updated"))
//                .andExpect(jsonPath("$.description").value("Updated Description"));
//
//        verify(analyseService, times(1)).updateAnalyse(eq(1L), any(AnalyseDTO.class));
//    }
//
//    @Test
//    void testDeleteAnalyse() throws Exception {
//        doNothing().when(analyseService).deleteAnalyse(1L);
//
//        mockMvc.perform(delete("/analyses/1"))
//                .andExpect(status().isOk());
//
//        verify(analyseService, times(1)).deleteAnalyse(1L);
//    }
//}
