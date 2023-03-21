package com.fesc.apipartidos.Data.Repositorios;

import org.springframework.data.repository.CrudRepository;

import com.fesc.apipartidos.Data.Entidades.EquipoEntity;

public interface IEquipoRepository extends CrudRepository<EquipoEntity, Long>{
    
    EquipoEntity findById(long id);
}
