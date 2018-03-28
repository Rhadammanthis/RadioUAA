package com.triolabs.kaltura;

/**
* Constant constantes que se utilizan en la app
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class Constant {
	//User kaltura
	//user local 
	//pass local Vict0r.b4rcen@
	public static String USER_KALTURA="xhuaafm@correo.uaa.mx";
	public static String PASSWORD_KALTURA="k4ltur4.Adm!n";
	//token kaltura
	public static String KS_KALTURA="";
	//Services 
	public static String FORMAT_KALTURA="1";
	public static String SERVICE_KALTURA_LOGIN="user";
	public static String ACTION_KALTURA_LOGIN="loginByLoginId";
	public static String SERVICE_KALTURA_PLAYLIST="playlist";
	public static String ACTION_KALTURA_LIST="list";
	public static String SERVICE_KALTURA_MEDIA="media";
	public static String ACTION_KALTURA_GET="get";
	public static int KALTURA_MEDIATYPE=5;
	public static String ID_BANNER_KALTURA="0_k6acs1yw";
	//host http://172.16.9.2
	public static String HOST_KALTURA="https://kaltura.uaa.mx";
//	public static String KALTURA_STREAMING="rtsp://148.211.120.48:1935/videowhisper/RADIOPODCAST";
//	public static String KALTURA_STREAMING="https://tv.uaa.mx:8443/videowhisper/TV-UAA/playlist.m3u8";
	public static String KALTURA_STREAMING="https://radio.uaa.mx:8443/videowhisper/RADIOPODCAST/playlist.m3u8";
    public static String URL_GRILL="https://tv.uaa.mx/parrilla/api/controller.php";
	//grill http://172.16.9.10/kaltura/Admin_API/index.php?action=get_programmation&db=radio
    /**@param  ks asigna el ks del login
 	 */
	public static void setKSKaltura(String ks){
		Constant.KS_KALTURA=ks;
	};
	
}
