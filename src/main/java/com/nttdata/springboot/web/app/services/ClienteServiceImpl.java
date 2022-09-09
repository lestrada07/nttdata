package com.nttdata.springboot.web.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nttdata.springboot.web.app.models.dao.ClienteDao;
import com.nttdata.springboot.web.app.models.documents.Cliente;


@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteDao dao;

	@Override
	public List<Cliente> findAll() {
		return dao.findAll();
	}


	@Override
	public void save(Cliente cliente) {
		dao.save(cliente);
		
	}

	@Override
	public Optional<Cliente> findById(String documento) {
		
		return dao.findById(documento);
	}
}
