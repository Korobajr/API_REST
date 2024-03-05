package com.exercise.apirest.service.impl;

import com.exercise.apirest.model.dao.ClienteDao;
import com.exercise.apirest.model.dto.ClienteDto;
import com.exercise.apirest.model.entity.Cliente;
import com.exercise.apirest.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClienteImpl implements IClientService {
    @Autowired
    private ClienteDao clienteDao;
    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente = Cliente.builder().idCliente(clienteDto.getIdCliente()).nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return clienteDao.save(cliente);
    }
    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }
}
