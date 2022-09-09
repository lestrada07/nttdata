package com.nttdata.springboot.web.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.nttdata.springboot.web.app.models.documents.Cliente;

@Repository
public interface ClienteDao extends MongoRepository<Cliente, String>{
	
	

}
