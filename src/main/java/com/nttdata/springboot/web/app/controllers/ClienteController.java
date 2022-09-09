package com.nttdata.springboot.web.app.controllers;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.springboot.web.app.models.transfers.ClienteOutputModel;
import com.nttdata.springboot.web.app.models.documents.Cliente;
import com.nttdata.springboot.web.app.services.ClienteService;
import com.nttdata.springboot.web.app.services.ClienteServiceImpl;


@RestController
@RequestMapping(value = "/", headers = "Accept=application/json")
public class ClienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
    ClienteServiceImpl clienteService;
	
	@GetMapping
	 public ResponseEntity<?> getClientData(@Valid @RequestBody Cliente cliente) {
        logger.info("-- ClienteController.getClientData: ");
        Map<String, Object> respuesta = new HashMap<String, Object>();
        Properties prop = new Properties();
        InputStream input;
        String typeCedula = "";
        String typePassport = "";
        Optional<Cliente> clienteConsulta;
        Cliente clienteResponse = new Cliente();
        ClienteOutputModel model = new ClienteOutputModel();
        
        try {
   
        	input = ClienteService.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            
            typeCedula = prop.getProperty("type.document.cedula");
            typePassport = prop.getProperty("type.document.passport");
           
            if(cliente.getTipoDocumento().equalsIgnoreCase(typeCedula) 
            		|| cliente.getTipoDocumento().equalsIgnoreCase(typePassport)) {
            	
            	clienteConsulta = clienteService.findById(cliente.getNumeroDocumento());
            	
            	if(clienteConsulta.isPresent()) {
            		
            		clienteResponse = clienteConsulta.get();
            		
            		if(clienteResponse.getTipoDocumento().equalsIgnoreCase(cliente.getTipoDocumento())) {
            			
            			model.setPrimerNombre(clienteResponse.getPrimerNombre());
            			model.setSegundoNombre(clienteResponse.getSegundoApellido());
            			model.setPrimerApellido(clienteResponse.getPrimerApellido());
            			model.setSegundoApellido(clienteResponse.getSegundoApellido());
            			model.setTelefono(clienteResponse.getTelefono());
            			model.setDireccion(clienteResponse.getDireccion());
            			model.setCiudadDeResidencia(clienteResponse.getCiudadDeResidencia());
            			
            			respuesta.put("timestamp", new Date());
            			respuesta.put("status", HttpStatus.OK.value());
            			respuesta.put("message", "OK");
            			respuesta.put("path","/");
            			respuesta.put("cliente", model);
        				
        				logger.info("Cliente consultado con exito");
            			
            			return ResponseEntity.ok()
            					.contentType(MediaType.APPLICATION_JSON)
        						.body(respuesta);
            			
            		}else {
            			logger.info("Data no encontrada");
            			respuesta.put("timestamp", new Date());
                    	respuesta.put("error", "Not Found");
        				respuesta.put("status", HttpStatus.NOT_FOUND.value());
        				respuesta.put("path","/");
            			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            		}
            		
            	}else {
            		logger.info("Data no encontrada");
            		respuesta.put("timestamp", new Date());
                	respuesta.put("error", "Not Found");
    				respuesta.put("status", HttpStatus.NOT_FOUND.value());
    				respuesta.put("path","/");
        			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            	}
            	
            	
            }else {
            	logger.info("Error request invalido");
            	respuesta.put("timestamp", new Date());
            	respuesta.put("error", "Bad Request");
				respuesta.put("status", HttpStatus.BAD_REQUEST.value());
				respuesta.put("path","/");
            	return ResponseEntity.badRequest().body(respuesta);
            }
            
        }catch (Exception e) {
        	logger.info("Error general en el servidor");
        	respuesta.put("timestamp", new Date());
        	respuesta.put("error", "Internal Server Error");
			respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			respuesta.put("path","/");
        	return ResponseEntity.internalServerError().body(respuesta);

		}finally {
			logger.info("Consulta terminada");
		}
    }

}
