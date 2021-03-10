package com.spacemonkeys.farmbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacemonkeys.farmbox.Models.Plant;
import com.spacemonkeys.farmbox.Models.Users;
import com.spacemonkeys.farmbox.dto.PlantDto;
import com.spacemonkeys.farmbox.dto.UserDto;
import com.spacemonkeys.farmbox.repository.UsersRepository;
import com.spacemonkeys.farmbox.services.plantservice.PlantService;
import com.spacemonkeys.farmbox.services.userservice.UserService;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc

public class PlantTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlantService plantService;
    @Autowired
    private UserService userService;


    public Plant plantTest(){

        UserDto userTeste = UserDto.builder()
                .name("John Doe")
                .password("123456")
                .build();

        Users user  = userService.save(userTeste.toUser());

        PlantDto dataTest  = PlantDto.builder()
                .age(1L)
                .cicle("bimestre")
                .type("vegatable")
                .user(user.getId())
                .build();

        return dataTest.dtoToPlant();
    }


    @Test
    public void createPlantTest() throws Exception{

        Plant plant = this.plantTest();
        mockMvc.perform(MockMvcRequestBuilders.post("/plant")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(plant))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.age").value(plant.getAge()))
                .andExpect(jsonPath("$.cicle").value(plant.getCicle()))
                .andExpect(jsonPath("$.type").value(plant.getType()))
                .andExpect(jsonPath("$.user").value(plant.getUser()))
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertFalse(plantService.findAll().isEmpty());

    }

    @Test
    public void updatePlantTest() throws Exception{

        Plant plant = this.plantTest();
        Plant updater = plantService.save(plant);
        plant.setAge(4L);

        mockMvc.perform(MockMvcRequestBuilders.put("/plant/" + updater.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(plant))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.age").value(4L))
                .andExpect(jsonPath("$.cicle").value(plant.getCicle()))
                .andExpect(jsonPath("$.type").value(plant.getType()))
                .andExpect(jsonPath("$.user").value(plant.getUser()))
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertFalse(plantService.findById(plant.getId()).isEmpty());

    }

    @Test
    public void deleteTest() throws Exception{
        Plant plant = this.plantTest();
        Plant deleter = plantService.save(plant);
        Long id = deleter.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/plant/" + deleter.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Optional<Plant> check = plantService.findById(id);
        Assertions.assertFalse(check.isPresent());
    }

    @Test
    public void badRequestDeleteTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/plant/" + 100))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Register not found"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void badRequestCreatePlantTest() throws Exception{

        Plant plant = this.plantTest();
        plant.setUser(100L);

        mockMvc.perform(MockMvcRequestBuilders.post("/plant")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(plant))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Register not found"))
                .andDo(MockMvcResultHandlers.print());
    }


}
