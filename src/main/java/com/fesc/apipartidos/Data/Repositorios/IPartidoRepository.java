package com.fesc.apipartidos.Data.Repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fesc.apipartidos.Data.Entidades.PartidoEntity;

public interface IPartidoRepository extends PagingAndSortingRepository<PartidoEntity, Long>{
    
    PartidoEntity save(PartidoEntity partidoEntity);
}
