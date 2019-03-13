package com.emr.emr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.emr.controllers.RecordsRestURIConstants;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmrApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	@Test
    public void AddNewPatientRecordNoTrauma() throws Exception {
    	this.mockMvc.perform(post("/records")
    			.param("age", "18")
    			.param("blood_count", "4.8")
    			.param("gender", "Female"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.age").value("18.0"))
        .andExpect(jsonPath("$.blood_count").value("4.8"))
    	.andExpect(jsonPath("$.gender").value("FEMALE"))
    	.andExpect(jsonPath("$.trauma").value("NONE"));
    	
    	this.mockMvc.perform(post("/records")
    			.param("age", "28")
    			.param("blood_count", "6")
    			.param("gender", "Male")
    			.param("trauma", "Burn"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.age").value("28.0"))
        .andExpect(jsonPath("$.blood_count").value("6.0"))
    	.andExpect(jsonPath("$.gender").value("MALE"))
    	.andExpect(jsonPath("$.trauma").value("BURN"));
    	
    	// checking variable validation 
    	this.mockMvc.perform(post("/records")
    			.param("age", "89")
    			.param("blood_count", "8.7")
    			.param("gender", "Male"))
    	.andExpect(content().string("blood_count must be number between 3.5 and 6"));

    	
    	// traumas
    	this.mockMvc.perform(get(RecordsRestURIConstants.GET_PATIENTS_DIAGNOSIS, "1", "traumas"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("false"));
    	
    	// healthy_blood_count
    	this.mockMvc.perform(get(RecordsRestURIConstants.GET_PATIENTS_DIAGNOSIS, "1", "healthy_blood_count"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("true"));
    	
    	// healthy_blood_count
    	this.mockMvc.perform(get(RecordsRestURIConstants.GET_PATIENTS_DIAGNOSIS, "2", "healthy_blood_count"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("false"));
    }
}

