package com.fredtm.exportar;

import java.util.List;

public interface Exportavel<T> {

	void exportar(T t,String caminho) throws ErroDeExportacaoExcetion;
	
	void exportar(List<T> t,String caminho) throws ErroDeExportacaoExcetion;
}
