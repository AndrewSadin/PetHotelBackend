package com.example.demo;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void list() throws Exception {
        List<Map<String,String>> clients = new ArrayList<Map<String,String>>(){{
            add(new HashMap<String,String>(){{put("id","1");put("name", "Ivan");}});
            add(new HashMap<String,String>(){{put("id","2");put("name", "Petya");}});
            add(new HashMap<String,String>(){{put("id","3");put("name", "Semen");}});
        }};

        this.mockMvc.perform(get("/client")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.clients",hasSize(3)))
                .andExpect(jsonPath("$.clients", containsInAnyOrder(clients.toArray())));
    }

    @Test
    public void getClient() throws Exception {
        Map<String,String> client = new HashMap<String,String>(){{put("id","1");put("name", "Ivan");}};

        this.mockMvc.perform(get("/client/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", is(client)));
    }

    @Test
    public void add() throws Exception {
        Map<String,String> client = new HashMap<String,String>(){{put("id","4");put("name", "John Doe");}};

        this.mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"John Doe\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(client)));
    }
}