package com.spacemonkeys.farmbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacemonkeys.farmbox.Models.Info;
import com.spacemonkeys.farmbox.dto.InfoDTO;
import com.spacemonkeys.farmbox.services.infoservice.InfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc

public class InfoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InfoService service;

    Date d = new Date();

    InfoDTO dtoTest  = InfoDTO.builder()
            .nutrients("npk")
            .conductivity("media")
            .time(String.valueOf(d.getTime()))
            .ph("medium")
            .water(0.2F)
            .light(true)
            .humidity(0.3F)
            .build();

    @Test
    public void createInfoTest() throws Exception {

        // Create
        mockMvc.perform(MockMvcRequestBuilders.post("/farmbox")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dtoTest))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.conductivity").value(dtoTest.getConductivity()))
                .andExpect(jsonPath("$.water").value(dtoTest.getWater()))
                .andExpect(jsonPath("$.light").value(dtoTest.getLight()))
                .andExpect(jsonPath("$.ph").value(dtoTest.getPh()))
                .andExpect(jsonPath("$.nutrients").value(dtoTest.getNutrients()))
                .andExpect(jsonPath("$.humidity").value(dtoTest.getHumidity()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test

    public void deleteTest() throws Exception {

        // Delete

        Info dataTest = service.save(dtoTest.toInfo());

        mockMvc.perform(MockMvcRequestBuilders.delete("/farmbox/" + dataTest.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Optional<Info> check = service.findById(dataTest.getId());
        Assertions.assertFalse(check.isPresent());
    }

    @Test

    public void updateTest() throws Exception {

        // Update

        Info dataTest = service.save(dtoTest.toInfo());
        dataTest.setPh("neutro");

        mockMvc.perform(MockMvcRequestBuilders.put("/farmbox/" + dataTest.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(dataTest))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.conductivity").value(dataTest.getConductivity()))
                .andExpect(jsonPath("$.water").value(dataTest.getWater()))
                .andExpect(jsonPath("$.light").value(dataTest.getLight()))
                .andExpect(jsonPath("$.ph").value("neutro"))
                .andExpect(jsonPath("$.nutrients").value(dataTest.getNutrients()))
                .andExpect(jsonPath("$.humidity").value(dataTest.getHumidity()))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void badRequestUpdateTest() throws Exception {

        Info dataTest = service.save(dtoTest.toInfo());
        mockMvc.perform(MockMvcRequestBuilders.put("/farmbox/" + "100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(dataTest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").isNumber())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Register not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void badRequestDeleteTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/farmbox/" + "100"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").isNumber())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Register not found"))
                .andDo(MockMvcResultHandlers.print());

    }
}
