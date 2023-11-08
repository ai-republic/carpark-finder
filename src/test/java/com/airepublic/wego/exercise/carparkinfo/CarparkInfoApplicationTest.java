package com.airepublic.wego.exercise.carparkinfo;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityDTO;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkService;

@SpringBootTest
@AutoConfigureMockMvc
class CarparkInfoApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarparkService carparkService;

    @Test
    void contextLoads() {
    }


    @Test
    void testResultOk() throws Exception {
        // prepare
        final List<CarparkAvailabilityDTO> dtos = new ArrayList<>();
        dtos.add(new CarparkAvailabilityDTO("BLK 364 / 365 UPP SERANGOON RD", 1.37011, 103.897, 471, 324));
        when(carparkService.getAvailableCarparks(anyDouble(), anyDouble(), anyInt(), anyInt())).thenReturn(dtos);

        // perform
        mockMvc.perform(get("/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"address\":\"BLK 364 / 365 UPP SERANGOON RD\",\"latitude\":1.37011,\"longitude\":103.897,\"totalLots\":471,\"availableLots\":324}]"));
    }


    @Test
    void testResultBadRequest() throws Exception {
        // prepare
        when(carparkService.getAvailableCarparks(anyDouble(), anyDouble(), anyInt(), anyInt())).thenThrow(new RuntimeException());

        // perform
        mockMvc.perform(get("/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10")).andDo(print()).andExpect(status().isBadRequest());
    }

}
