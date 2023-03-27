package com.fesc.apipartidos.Data.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fesc.apipartidos.Data.Entidades.PartidoEntity;

public interface IPartidoRepository extends PagingAndSortingRepository<PartidoEntity, Long>{
    
    PartidoEntity save(PartidoEntity partidoEntity);

    void delete(PartidoEntity partidoEntity);
    
    List<PartidoEntity> getByUsuarioEntityIdOrderByCreadoDesc(Long usuarioEntityId);

    @Query(nativeQuery = true, value = "SELECT * FROM partido ORDER BY creado DESC LIMIT 10")
    List<PartidoEntity> partidosCreados();

    PartidoEntity findByIdPartido(String id);
}
