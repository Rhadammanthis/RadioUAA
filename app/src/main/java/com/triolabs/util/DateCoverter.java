package com.triolabs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Esta clase DateCoverter realiza cosas refentes ala fecha
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class DateCoverter {
	
	Long longDate;
	Calendar calendar;
	
	/** Se crea una instancia de DateCoverter con la fecha actual 
 	 */
	public DateCoverter(){
		this.calendar = Calendar.getInstance();
	}
	
	/** Se crea una instancia de DateCoverter con una fecha establecida
	 * @param  longDate es el long de la fecha con la que se quiere iniciar
 	 */
	public DateCoverter(Long longDate){
		this.longDate = longDate*1000;
		this.calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.longDate);
	}
	
	/** Se crea una instancia de DateCoverter con una fecha establecida
	 * @param  stringDate es la fecha con la que se quiere iniciar en formato dd-mm-yyyy
 	 */
	public DateCoverter(String stringDate){
		this.calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sdf.parse(stringDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Metodo getDayMouth regresa el dia del mes
	 * @return int Calendar.DAY_OF_MONTH es el dia del mes
	 */
	public int getDayMouth(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/** Metodo getDay regresa el dia de la semana
	 * @return int Calendar.DAY_OF_WEEK es el dia de la semana
	 */
	public int getDay(){
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/** Metodo getNameDay regresa el dia de la semana
	 * @return String Calendar.DAY_OF_WEEK es el dia de la semana en string
	 */
	public String getNameDay(){
		int day=getDay()-1;
	    String[] dayNames = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
	    return dayNames[day];
	}
	
	/** Metodo getMonth regresa el mes
	 * @return int Calendar.MONTH es el numero del mes
	 */
	public int getMonth(){
		return calendar.get(Calendar.MONTH);
	}
	
	/** Metodo getNameMonth regresa el mes
	 * @return String Calendar.MONTH es el mes en string
	 */
	public String getNameMonth(){
		int month=getMonth();
	    String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	    return monthNames[month];
	}
	
	/** Metodo getYear regresa el year
	 * @return int Calendar.YEAR: es el year
	 */
	public int getYear(){
		return calendar.get(Calendar.YEAR);
	}
	
	/** Metodo getDate regresa el calendario
	 * @return calendar: es el calendario asigando
	 */
	public Calendar getDate(){
		return calendar;
	}
	
	/** Metodo getHour regresa el calendario
	 * @return int: regresa la hora del dia
	 */
	public int getHour(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/** Metodo getDate regresa el calendario
	 * @return int: regresa los minutos de la fecha
	 */
	public int getMinute(){
		return calendar.get(Calendar.MINUTE);
	}

}
