package com.triolabs.util;

import java.util.Comparator;

import com.triolabs.model.Program;

/**
 * Esta clase AlfaComparatorProgram realiza la comparacion entre objetos de program por orden alfabetico
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class AlfaComparatorProgram implements Comparator<Program> {

	@Override
	public int compare(Program programOne, Program programTwo) {
		// TODO Auto-generated method stub
		return programOne.getNameProgram().compareTo(programTwo.getNameProgram());
	}

}
