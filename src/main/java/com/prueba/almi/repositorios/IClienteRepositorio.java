package com.prueba.almi.repositorios;

import com.prueba.almi.modelos.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepositorio extends JpaRepository<Clientes, Long> {

    Clientes findByNumeroIdentificacion(String numeroIdentificacion);

}
