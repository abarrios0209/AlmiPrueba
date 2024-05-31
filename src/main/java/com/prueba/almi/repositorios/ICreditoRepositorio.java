package com.prueba.almi.repositorios;

import com.prueba.almi.modelos.Creditos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICreditoRepositorio extends JpaRepository<Creditos, Long> {

    List<Creditos> findByNumeroIdentificacion(String numeroIdentificacion);

    Creditos findByNumeroIdentificacionAndId(String numeroIdentificacion, Long idCredito);


}
