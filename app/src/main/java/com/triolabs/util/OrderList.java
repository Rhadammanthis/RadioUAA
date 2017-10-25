package com.triolabs.util;

import java.util.ArrayList;
import java.util.Collections;

import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.model.Chapter;
import com.triolabs.model.Program;

/**
 * Esta clase OrderList ordena los programas y los capitulos
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Jose Maria Armendariz, Alex Campos, Ivan
 * @version 1.0
 */
public class OrderList {
	
	/** Se crea una instancia de OrderList para utlizar sus metodos de ordenamiento
 	 */
	public OrderList(){
		
	}
	
	/** Metodo isProgramming checa si es la vista de programas
	 * @return boolean: FALSE si no es la vista, TRUE si es la vista
 	 */
	public boolean isProgramming(){
		return ProgrammingFragment.isProgramming();
	}
	
	/** Metodo byAlfaProgram ordena por orden alfabetico los programas
	 * @param listProgram lista de los programas a ordenar
 	 */
	public void byAlfaProgram(ArrayList<Program> listProgram){
		Collections.sort(listProgram, new AlfaComparatorProgram());
	}
	
	/** Metodo byDateProgram ordena por fecha los programas
	 * @param ArrayList<Program> listProgram: lista de los programas a ordenar
 	 */
	public void byDateProgram(ArrayList<Program> listProgram){
		Collections.sort(listProgram, new DateComparatorProgram());
	}
	
	/** Metodo byAlfaChapter ordena por orden alfabetico los capitulos
	 * @param listChapter lista de los capitulos a ordenar
 	 */
	public void byAlfaChapter(ArrayList<Chapter> listChapter){
		Collections.sort(listChapter, new AlfaComparatorChapter());
	}
	
	/** Metodo byAlfaChapter ordena por fecha los capitulos
	 * @param listChapter lista de los capitulos a ordenar
 	 */
	public void byDateChapter(ArrayList<Chapter> listChapter){
		Collections.sort(listChapter, new DateComparatorChapter());
	}

}
