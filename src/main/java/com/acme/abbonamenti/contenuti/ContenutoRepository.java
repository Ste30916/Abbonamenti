package com.acme.abbonamenti.contenuti;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContenutoRepository extends PagingAndSortingRepository<ContenutoImp, Long> {
	
}
