package com.exercise.apirest.service;

import com.exercise.apirest.model.dto.ClienteDto;
import com.exercise.apirest.model.entity.Cliente;

public interface IClientService {
    Cliente save(ClienteDto clienteDto);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);

}
