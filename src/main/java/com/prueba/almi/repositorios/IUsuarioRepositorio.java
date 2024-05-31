package com.prueba.almi.repositorios;

import com.prueba.almi.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Boolean existsByEmailAndContraseña(String email, String password);
}
