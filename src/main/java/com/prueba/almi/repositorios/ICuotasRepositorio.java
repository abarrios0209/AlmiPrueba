package com.prueba.almi.repositorios;

import com.prueba.almi.modelos.Cuotas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICuotasRepositorio extends JpaRepository<Cuotas, Long> {
    List<Cuotas> findByNumeroIdentificacionAndIdCredito(String numeroIdentificacion, Long idCredito);
    Cuotas findByNumeroIdentificacionAndIdCreditoAndId(String numeroIdentificacion,Long idCredito, Long idCuota);
}
