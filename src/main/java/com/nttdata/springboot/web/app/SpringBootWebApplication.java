package com.nttdata.springboot.web.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.nttdata.springboot.web.app.models.documents.Cliente;
import com.nttdata.springboot.web.app.services.ClienteService;
import com.nttdata.springboot.web.app.services.ClienteServiceImpl;

@SpringBootApplication
//@ComponentScan
//@EnableMongoRepositories
//@ComponentScan("com.nttdata.springboot.web.app.models.dao") //to scan packages mentioned
//@EnableMongoRepositories("com.nttdata.springboot.web.app.models.dao") //to activate MongoDB repositories
//@EnableMongoRepositories({ "ups.mongo.repository" })
public class SpringBootWebApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebApplication.class);
	
	
	@Autowired
	private ClienteServiceImpl service;
	
	@Autowired
	private MongoTemplate mongoTemplate;	

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("SpringBootWebApplication.run: ");
		
		mongoTemplate.dropCollection("clientes");
		
		
		Cliente cliente = new Cliente();
		
		cliente.setTipoDocumento("C");
		cliente.setNumeroDocumento("23445322");
		cliente.setPrimerNombre("Luis");
		cliente.setSegundoNombre("Alfredo");
		cliente.setPrimerApellido("Estrada");
		cliente.setSegundoApellido("Ramos");
		cliente.setTelefono("+58426286943");
		cliente.setDireccion("Sabana Grande");
		cliente.setCiudadDeResidencia("Caracas");
		
		service.save(cliente);
		
		log.info("Se guardo el cliente con Tipo de documento: " + cliente.getTipoDocumento() + " Numero de documento: " + cliente.getNumeroDocumento());
		
		
	}

}
