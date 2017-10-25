package com.triolabs.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
* ListProgram contiene lista de programas y capitulos asi como el ultimo capitulo y programa
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ListProgram {

	private static ArrayList<Program> listProgram = new ArrayList<Program>();
	private static ArrayList<Chapter> listChapter = new ArrayList<Chapter>();
	private static Program currentProgram;
	private static Chapter currentChapter;
	private static boolean chapterExists;
	private static HashMap<Integer,ArrayList<String>> programmationId;
	private static HashMap<Integer,ArrayList<String>> programmation;
	private static HashMap<Integer,ArrayList<String>> programmationHour;
	private static HashMap<Integer,ArrayList<String>> programmationHourEnd;
	
	/** Se crea una instancia de ListProgram 
 	 */
	public ListProgram(){
		
	}

	/** @return ArrayList<Program> listProgram: devueleve la lista de programas
 	 */
	public ArrayList<Program> getListProgram() {
		return listProgram;
	}

	/** @param  listProgram asigna la lista de programas
 	 */
	public void setListProgram(ArrayList<Program> listProgram) {
		this.listProgram = listProgram;
	}

	public void setListProgramPush(Program program) {
		this.listProgram.add(program);
	}

	/** @return Program currentProgram: devueleve el programa actual
 	 */
	public Program getCurrentProgram() {
		return currentProgram;
	}

	/** @param  currentProgram asigna el programa actual
 	 */
	public void setCurrentProgram(Program currentProgram) {
		this.currentProgram = currentProgram;
	}

	/** @return Chapter currentChapter: devueleve el capitulo actual
 	 */
	public static Chapter getCurrentChapter() {
		return currentChapter;
	}

	/** @param  currentChapter asigna el capitulo actual
 	 */
	public static void setCurrentChapter(Chapter currentChapter) {
		ListProgram.currentChapter = currentChapter;
	}

	/** @return ArrayList<Chapter> listChapter: devueleve la lista de capitulos
 	 */
	public static ArrayList<Chapter> getListChapter() {
		return listChapter;
	}

	/** @param  listChapter asigna la lista de capitulos
 	 */
	public static void setListChapter(ArrayList<Chapter> listChapter) {
		ListProgram.listChapter = listChapter;
	}

	/** @return boolean chapterExists: TRUE si existe un capitulo actual FALSE si no existe
 	 */
	public static boolean isChapterExists() {
		return chapterExists;
	}

	/** @param  chapterExists asigna TRUE si existe un capitulo actual o FALSE si no existe
 	 */
	public static void setChapterExists(boolean chapterExists) {
		ListProgram.chapterExists = chapterExists;
	}

	/**
	 * @return the programmationId
	 */
	public static HashMap<Integer,ArrayList<String>> getProgrammationId() {
		return programmationId;
	}

	/**
	 * @param programmationId the programmationId to set
	 */
	public static void setProgrammationId(HashMap<Integer,ArrayList<String>> programmationId) {
		ListProgram.programmationId = programmationId;
	}

	/**
	 * @return the programmation
	 */
	public static HashMap<Integer,ArrayList<String>> getProgrammation() {
		return programmation;
	}

	/**
	 * @param programmation the programmation to set
	 */
	public static void setProgrammation(HashMap<Integer,ArrayList<String>> programmation) {
		ListProgram.programmation = programmation;
	}

	/**
	 * @return the programmationHour
	 */
	public static HashMap<Integer,ArrayList<String>> getProgrammationHour() {
		return programmationHour;
	}

	/**
	 * @param programmationHour the programmationHour to set
	 */
	public static void setProgrammationHour(HashMap<Integer,ArrayList<String>> programmationHour) {
		ListProgram.programmationHour = programmationHour;
	}

	/**
	 * @return the programmationHourEnd
	 */
	public static HashMap<Integer,ArrayList<String>> getProgrammationHourEnd() {
		return programmationHourEnd;
	}

	/**
	 * @param programmationHourEnd the programmationHourEnd to set
	 */
	public static void setProgrammationHourEnd(HashMap<Integer,ArrayList<String>> programmationHourEnd) {
		ListProgram.programmationHourEnd = programmationHourEnd;
	}	
}
