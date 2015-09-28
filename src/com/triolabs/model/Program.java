package com.triolabs.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *Program es el objeto de programa en ella se podra instaciar un nuevo programa y extraer sus campos
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class Program {
	
	private String idProgram;
	private String nameProgram;
	private String photoProgram;
	private String typeProgram;
	private String scheduleProgram;
	private String producerProgram;
	private String periodicityProgram;
	private String stringScheduleRepeatProgram;
	private String synopsisProgram;
	private Calendar dateProgram;
	private String[] idChapter;
	private ArrayList<String> scheduleRepeatProgram;
	private ArrayList<Chapter> listChapter;
	
	/** Se crea una instancia de Program 
 	 */
	public Program(){
		this.scheduleRepeatProgram = new ArrayList<String>();
		this.listChapter = new ArrayList<Chapter>();
	}
	
	/** @return String nameProgram: devueleve el nombre del programa
 	 */
	public String getNameProgram() {
		return nameProgram;
	}
	
	/** @param nameProgram se asigna el nombre del programa
 	 */
	public void setNameProgram(String nameProgram) {
		this.nameProgram = nameProgram;
	}
	
	/** @return String photoProgram: devueleve el url de la foto del programa
 	 */
	public String getPhotoProgram() {
		return photoProgram;
	}
	
	/** @param photoProgram se asigna el url de la foto del programa
 	 */
	public void setPhotoProgram(String photoProgram) {
		this.photoProgram = photoProgram;
	}
	
	/** @return String photoProgram: devueleve el tipo del programa
 	 */
	public String getTypeProgram() {
		return typeProgram;
	}
	
	/** @param typeProgram se asigna el tipo del programa
 	 */
	public void setTypeProgram(String typeProgram) {
		this.typeProgram = typeProgram;
	}
	
	/** @return String scheduleProgram: devueleve el horario del programa
 	 */
	public String getScheduleProgram() {
		return scheduleProgram;
	}
	
	/** @param scheduleProgram se asigna el horario del programa
 	 */
	public void setScheduleProgram(String scheduleProgram) {
		this.scheduleProgram = scheduleProgram;
	}
	
	/** @return String producerProgram: devueleve el productor del programa
 	 */
	public String getProducerProgram() {
		return producerProgram;
	}
	
	/** @param producerProgram se asigna el productor del programa
 	 */
	public void setProducerProgram(String producerProgram) {
		this.producerProgram = producerProgram;
	}
	
	/** @return ArrayList<String> scheduleRepeatProgram: devueleve las repeticiones del programa
 	 */
	public ArrayList<String> getScheduleRepeatProgram() {
		return scheduleRepeatProgram;
	}
	
	/** @param scheduleRepeatProgram se asigna las repeticiones del programa
 	 */
	public void setScheduleRepeatProgram(ArrayList<String> scheduleRepeatProgram) {
		this.scheduleRepeatProgram = scheduleRepeatProgram;
	}
	
	/** @return String periodicityProgram: devueleve la periocidad del programa
 	 */
	public String getPeriodicityProgram() {
		return periodicityProgram;
	}
	
	/** @param periodicityProgram se asigna la periocidad del programa
 	 */
	public void setPeriodicityProgram(String periodicityProgram) {
		this.periodicityProgram = periodicityProgram;
	}
	
	/** @return String stringScheduleRepeatProgram: devueleve las repeticiones del programa en un string
 	 */
	public String getStringScheduleRepeatProgram() {
		return stringScheduleRepeatProgram;
	}
	
	/** @param stringScheduleRepeatProgram se asigna las repeticiones del programa en un string
 	 */
	public void setStringScheduleRepeatProgram(
			String stringScheduleRepeatProgram) {
		this.stringScheduleRepeatProgram = stringScheduleRepeatProgram;
	}	
	
	private String makeStringScheduleRepeatProgram(){
		this.stringScheduleRepeatProgram="";
		for(int i=0;i<this.scheduleRepeatProgram.size();i++){
			if(i!=0)
				this.stringScheduleRepeatProgram=this.stringScheduleRepeatProgram+","+this.scheduleRepeatProgram.get(i);
			else
				this.stringScheduleRepeatProgram=this.scheduleRepeatProgram.get(i);
		}
		return stringScheduleRepeatProgram;
		
	}

	/** @return String synopsisProgram: devueleve la sipnosis del programa
 	 */
	public String getSynopsisProgram() {
		return synopsisProgram;
	}

	/** @param synopsisProgram se asigna la sipnosis del programa
 	 */
	public void setSynopsisProgram(String synopsisProgram) {
		this.synopsisProgram = synopsisProgram;
	}

	/** @return ArrayList<Chapter> listChapter: devueleve la lista de los capitulos del programa
 	 */
	public ArrayList<Chapter> getListChapter() {
		return listChapter;
	}

	/** @param listChapter se asigna la lista de los capitulos del programa
 	 */
	public void setListChapter(ArrayList<Chapter> listChapter) {
		this.listChapter = listChapter;
	}

	/** @return String[] idChapter: devueleve la lista de los id capitulos del programa
 	 */
	public String[] getIdChapter() {
		return idChapter;
	}

	/** @param String[] idChapter: asigna la lista de los id capitulos del programa
 	 */
	public void setIdChapter(String[] idChapter) {
		this.idChapter = idChapter;
	}

	/** @return Calendar dateProgram: devueleve la fecha del programa
 	 */
	public Calendar getDateProgram() {
		return dateProgram;
	}

	/** @param dateProgram se asigna la fecha del programa
 	 */
	public void setDateProgram(Calendar dateProgram) {
		this.dateProgram = dateProgram;
	}

	/**
	 * @return the idProgram
	 */
	public String getIdProgram() {
		return idProgram;
	}

	/**
	 * @param idProgram the idProgram to set
	 */
	public void setIdProgram(String idProgram) {
		this.idProgram = idProgram;
	}

}
