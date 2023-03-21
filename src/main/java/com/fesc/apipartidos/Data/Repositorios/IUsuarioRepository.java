package com.fesc.apipartidos.Data.Repositorios;

import org.springframework.data.repository.CrudRepository;
import com.fesc.apipartidos.Data.Entidades.UsuarioEntity;

public interface IUsuarioRepository extends CrudRepository<UsuarioEntity, Long>{
    
    UsuarioEntity findByEmail(String email);
    UsuarioEntity findByUsername(String username);
}
