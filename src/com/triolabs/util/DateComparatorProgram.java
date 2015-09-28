package com.triolabs.util;

import java.util.Comparator;

import com.triolabs.model.Program;

/**
 * Esta clase DateComparatorProgram realiza la comparacion entre objetos de program por fecha
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class DateComparatorProgram implements Comparator<Program>{

	@Override
	public int compare(Program programOne, Program programTwo) {
		// TODO Auto-generated method stub
		return programOne.getDateProgram().compareTo(programTwo.getDateProgram());
	}

}
