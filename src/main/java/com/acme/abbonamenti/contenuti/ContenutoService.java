package com.acme.abbonamenti.contenuti;

import java.util.List;

public interface ContenutoService {
	
	public void inserisciContenuto (ContenutoDTO dto) throws AlreadyInsertedException;
	public ContenutoImp find(long id);
	public List<ContenutoImp> getListaContenuti();
	
}
