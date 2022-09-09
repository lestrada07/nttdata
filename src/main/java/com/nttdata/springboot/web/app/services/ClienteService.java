package com.nttdata.springboot.web.app.services;

import java.util.List;
import java.util.Optional;


import com.nttdata.springboot.web.app.models.documents.Cliente;

public interface ClienteService {
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Optional<Cliente> findById(String documento);

}
