package com.acme.abbonamenti.abbonati;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbbonatoRepository extends PagingAndSortingRepository<Abbonato, Long> {
	public Abbonato findByCodiceFiscale(String cf);
	public boolean existsByCodiceFiscale(String cf);

}
