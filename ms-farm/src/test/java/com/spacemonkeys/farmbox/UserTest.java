package com.spacemonkeys.farmbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacemonkeys.farmbox.Models.Users;
import com.spacemonkeys.farmbox.dto.UserDto;
import com.spacemonkeys.farmbox.services.userservice.UserService;
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

public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    public Users userTest(){

        UserDto tester =  UserDto.builder()
                .name("John Doe")
                .password("password")
                .build();

        return tester.toUser();
    }



    @Test
    public void createTest() throws Exception {

        Users userTest = this.userTest();

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(userTest))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name").value(userTest.getName()))
                .andExpect(jsonPath("$.password").value(userTest.getPassword()))
                .andExpect(jsonPath("$.id").isNumber())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertFalse(userService.findAll().isEmpty());
    }

    @Test
    public void deleteTest() throws Exception {
        Users userTest = this.userTest();
        Users deleter = userService.save(userTest);
        Long id = deleter.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + deleter.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Optional<Users> check = userService.findById(id);
        Assertions.assertFalse(check.isPresent());
    }

    @Test
    public void updateUserTest() throws  Exception{
        Users userTest = this.userTest();
        Users updater = userService.save(userTest);
        userTest.setName("John Lennon");

        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + updater.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(updater))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value(userTest.getName()))
                .andExpect(jsonPath("$.password").value(userTest.getPassword()))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void badRequestUpdateTest() throws Exception{
        Users userTest = this.userTest();
        Users updater = userService.save(userTest);
        userTest.setName("John Lennon");

        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + "1000")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(updater))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Register not found"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void badRequestNameCreateTest() throws Exception{

        Users userTest = this.userTest();
        userTest.setName(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(userTest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The field name cannot be null"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void badRequestPasswordCreateTest() throws Exception {
        Users userTest = this.userTest();
        userTest.setPassword(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content( new ObjectMapper().writeValueAsString(userTest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The field password cannot be null"))
                .andDo(MockMvcResultHandlers.print());
    }
}
