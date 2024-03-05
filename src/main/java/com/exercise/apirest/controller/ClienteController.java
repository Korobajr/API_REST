package com.exercise.apirest.controller;

import com.exercise.apirest.model.dto.ClienteDto;
import com.exercise.apirest.model.entity.Cliente;
import com.exercise.apirest.model.payload.MensajeResponse;
import com.exercise.apirest.service.IClientService;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {
    @Autowired
    private IClientService clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){
        Cliente clienteSave = null;
        try{
            clienteSave = clienteService.save(clienteDto);
            clienteDto =  ClienteDto.builder().idCliente(clienteSave.getIdCliente()).nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();

            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Guardado correctamente").object(clienteDto).build(), HttpStatus.CREATED);
        }catch (DataException eDt) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(eDt.getMessage())
                    .object(null)
                    .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto){
        Cliente clienteUpdate = null;
        try{
            if (clienteService.existsById(id)) {
                clienteUpdate.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                clienteDto =  ClienteDto.builder().idCliente(clienteUpdate.getIdCliente()).nombre(clienteUpdate.getNombre())
                        .apellido(clienteUpdate.getApellido())
                        .correo(clienteUpdate.getCorreo())
                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                        .build();

                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Modificado correctamente").object(clienteDto).build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("El registro que intenta actualizar no existe")
                        .object(null)
                        .build()
                        , HttpStatus.NOT_FOUND);
            }

        }catch (DataException eDt) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(eDt.getMessage())
                    .object(null)
                    .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Cliente clienteId = clienteService.findById(id);
            clienteService.delete(clienteId);
            return new ResponseEntity<>(clienteId,HttpStatus.NO_CONTENT);
        }catch (DataException eDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje(eDt.getMessage())
                        .object(null)
                        .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Cliente clienteFound = clienteService.findById(id);
        if (clienteFound == null) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El registro que intenta buscar no existe")
                    .object(null)
                    .build()
                    , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje(null)
                .object(ClienteDto.builder().idCliente(clienteFound.getIdCliente()).nombre(clienteFound.getNombre())
                        .apellido(clienteFound.getApellido())
                        .correo(clienteFound.getCorreo())
                        .fechaRegistro(clienteFound.getFechaRegistro())
                        .build())
                .build()
                , HttpStatus.OK);
    }
}
