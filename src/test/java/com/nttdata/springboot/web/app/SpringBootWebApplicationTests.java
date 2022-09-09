package com.nttdata.springboot.web.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.springboot.web.app.models.documents.Cliente;
import com.nttdata.springboot.web.app.models.transfers.ClienteOutputModel;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootWebApplicationTests {
	

	@Value("${config.base.endpoint}")
	private String url;
	
	@Autowired
    private MockMvc mockMvc;;

	
	@MockBean
	//@Autowired
	private ClienteOutputModel clienteOutput;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	void verCliente() throws Exception {
			
		
		 Cliente cliente = new Cliente("C", "23445322");
		 
		 
		 ResultActions response = mockMvc.perform(get(url)
				 .contentType(MediaType.APPLICATION_JSON)
		         .content(objectMapper.writeValueAsString(cliente)));;

	        response.andExpect(status().isOk())
	                .andDo(print());
	              
	 	}

}
