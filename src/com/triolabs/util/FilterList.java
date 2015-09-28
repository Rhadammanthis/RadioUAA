package com.triolabs.util;

import java.util.ArrayList;
import java.util.Locale;

import com.triolabs.fragment.GrillFragment;
import com.triolabs.fragment.LastChapterFragment;
import com.triolabs.fragment.ProgramDetailFragment;
import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;


/**
 * Esta clase FilterList realiza filtro por la busqueda
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class FilterList {
	
	static ArrayList<Program> listProgram;
	static ArrayList<Chapter> listChapter;
	static String idProgram;
	
	/** Se crea una instancia de FilterList se inicializa la lista de programas 
 	 */
	public FilterList(){
		ListProgram listProgram = new ListProgram();
		FilterList.listProgram=listProgram.getListProgram();
	}
	
	/** Metodo inicializa la lista de capitulos
	 */
	public void initChapter(){
		ListProgram listProgram = new ListProgram();
		FilterList.listProgram=listProgram.getListProgram();
		Program program = listProgram.getCurrentProgram();
		listChapter=ProgramDetailFragment.listChapter;
		idProgram=program.getIdProgram();
	}
	

	/** Metodo byProgram hace un filtro por programa
	 * @param charText es el caracter que se va buscar en las lista
 	 */
	public void byProgram(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ArrayList<Program> listProgramTemp = new ArrayList<Program>();
        if (charText.length() != 0) {
        	for (int i=0;i<listProgram.size();i++) {
                if (listProgram.get(i).getNameProgram().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	listProgramTemp.add(listProgram.get(i));
                }
            }
        	setAdapterProgram(listProgramTemp);
        } else {
        	setAdapterProgram(listProgram);
        }
    }
	
	/** Metodo setAdapterProgram actualiza la lista de los programas
	 * @param listProgram es la lista con la que se va actualizar
 	 */
	public void setAdapterProgram(ArrayList<Program> listProgram){
		ProgrammingFragment.updateListView(listProgram);
	}
	
	/** Metodo byChapter hace un filtro por capitulos
	 * @param charText es el caracter que se va buscar en las lista
 	 */
	public void byChapter(String charText){
		charText = charText.toLowerCase(Locale.getDefault());
        ArrayList<Chapter> listChapterTemp = new ArrayList<Chapter>();
        if (charText.length() != 0) {
        	ProgramDetailFragment.hideDetail();
        	for (int i=0;i<listChapter.size();i++) {
                if (listChapter.get(i).getNameChapter().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	listChapterTemp.add(listChapter.get(i));
                }
            }
        	setAdapterChapter(listChapterTemp);
        } else {
        	setAdapterChapter(listChapter);
        }
	}
	
	/** Metodo setAdapterChapter actualiza la lista de los capitulos
	 * @param listChapter es la lista con la que se va actializar
 	 */
	public void setAdapterChapter(ArrayList<Chapter> listChapter){
		ProgramDetailFragment.updateListView(listChapter,idProgram);
	}
	
	/** Metodo isProgramming checa si es la vista de programas
	 * @return FALSE si no es la vista o TRUE si es la vista
 	 */
	public boolean isProgramming(){
		return ProgrammingFragment.isProgramming();
	}
	
	/** Metodo isLastChapter checa si es la vista de los utlimos capitulos
	 * @return FALSE si no es la vista o TRUE si es la vista
 	 */
	public boolean isLastChapter(){
		return LastChapterFragment.isLastChapter();
	}
	
	/** Metodo isGrill checa si es la vista de la parrilla
	 * @return FALSE si no es la vista o TRUE si es la vista
 	 */
	public boolean isGrill(){
		return GrillFragment.isGrill();
	}
	
	/** Metodo isProgramDetail checa si es la vista de los detalles de programa
	 * @return FALSE si no es la vista o TRUE si es la vista
 	 */
	public boolean isProgramDetail(){
		return ProgramDetailFragment.isProgramDetail();
	}
}
