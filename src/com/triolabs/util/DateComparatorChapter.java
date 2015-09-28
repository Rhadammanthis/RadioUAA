package com.triolabs.util;

import java.util.Comparator;

import com.triolabs.model.Chapter;

/**
 * Esta clase DateComparatorChapter realiza la comparacion entre objetos de chapter por fecha
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class DateComparatorChapter implements Comparator<Chapter>{

	@Override
	public int compare(Chapter chapterOne, Chapter chapterTwo) {
		// TODO Auto-generated method stub
		return chapterTwo.getDateChapter().getTime().compareTo(chapterOne.getDateChapter().getTime());
	}

}
