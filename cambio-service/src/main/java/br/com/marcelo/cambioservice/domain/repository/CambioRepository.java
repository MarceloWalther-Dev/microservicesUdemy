package br.com.marcelo.cambioservice.domain.repository;

import br.com.marcelo.cambioservice.domain.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long>{

    //busca as colunas from e to
    Cambio findByFromAndTo(String from, String to);

}