package com.triolabs.util;

import java.util.Comparator;

import com.triolabs.model.Chapter;

/**
 * Esta clase AlfaComparatorChapter realiza la comparacion entre objetos de chapter por orden alfabetico
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class AlfaComparatorChapter implements Comparator<Chapter>{

	@Override
	public int compare(Chapter chapterOne, Chapter chapterTwo) {
		// TODO Auto-generated method stub
		return chapterOne.getNameChapter().compareTo(chapterTwo.getNameChapter());
	}

}
