package com.acme.abbonamenti.contenuti;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContenutoRepository extends PagingAndSortingRepository<Contenuto, Long> {
	public boolean existsByNome(String nome);
	public Contenuto findByNome(String nome);
}
