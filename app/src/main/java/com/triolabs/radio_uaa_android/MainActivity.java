
package com.triolabs.radio_uaa_android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.HashMap;

import com.facebook.FacebookSdk;
import com.triolabs.adapter.MenuAdapter;
import com.triolabs.fragment.CategoriesFragment;
import com.triolabs.fragment.ContactFragment;
import com.triolabs.fragment.GrillFragment;
import com.triolabs.fragment.LastChapterFragment;
import com.triolabs.fragment.ProgramDetailFragment;
import com.triolabs.fragment.ProgrammingFragment;
import com.triolabs.kaltura.Constant;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.model.Program;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.DateCoverter;
import com.triolabs.util.Device;
import com.triolabs.util.FilterList;
import com.triolabs.util.Font;
import com.triolabs.util.OrderList;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * Esta clase es el main de la aplicacion aqui se crea el menu lateral,
 *  asi como la barra de busqueda y sus respectivas funciones.
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class MainActivity extends ActionBarActivity {
	
	//Contexto de la clase
	static MainActivity self;
	
	public static boolean ansyncTask=false;
	//Componentes del menu lateral
	private static DrawerLayout menuLayout;
    private static ListView menuList;
    private static ActionBarDrawerToggle menuToggle;
    private static CharSequence menuTitle;
    private static CharSequence barTitle;
    private static String[] menuOptions;
  
    //Array que contiene el historial de los fragment que se entran
    private static ArrayList<CharSequence> historyNavigation;
    
    //Componentes de la barra de busqueda
    static SearchView searchView;
    static boolean search=true;
    FilterList filterList;

    /**
     * Metodo @Override:onCreate si no existe una instancia salvada 
     * se inicializan los componentes a utilizar
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		self=this;
		
			FacebookSdk.sdkInitialize(getApplicationContext());

			Toolbar toolbar = (Toolbar)findViewById(R.id.page_toolbar);
			toolbar.setNavigationIcon(R.drawable.ic_drawer);
			setSupportActionBar(toolbar);
			
			try {
		        PackageInfo info = getPackageManager().getPackageInfo(
		                "com.facebook.samples.hellofacebook", 
		                PackageManager.GET_SIGNATURES);
		        for (Signature signature : info.signatures) {
		            MessageDigest md = MessageDigest.getInstance("SHA");
		            md.update(signature.toByteArray());
		            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
		            }
		    } catch (NameNotFoundException e) {

		    } catch (NoSuchAlgorithmException e) {

		    }
	
			initDevice();//Inicaliza las opciones del dispositivo
			initActionBar();//Inicaliza la barra
			initSideMenu();//Inicaliza el menu lateral
			selectMenuOption(0);//Por defecto va inciar con la opcion 0 del menu lateral	
	}


	/**
     * Metodo @Override:onCreateOptionsMenu se crean las opciones del menu,
     * aqui realizamos la acciones de la barra de busqueda y sus validaciones
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        
        //Listener para opciones de busqueda
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        	
        	//Busqueda cada vez que se ingrese una caracter
        	@Override
            public boolean onQueryTextChange(String newText) {
        		// TODO Auto-generated method stub
            	filterList = new FilterList();
            	if(filterList.isGrill()){
            		
            	}else if(filterList.isLastChapter()){
            		
            	}else if(filterList.isProgramming()){
            		filterList.byProgram(newText);
            	}else if(filterList.isProgramDetail()){
            		filterList.initChapter();
            		filterList.byChapter(newText);
            	}
            	
                return true;
            }

        	//Busqueda por palabra(opcion desabilitada)
			@Override
			public boolean onQueryTextSubmit(String text) {
				// TODO Auto-generated method stub
				return false;
			}
        };
        searchView.setOnQueryTextListener(queryTextListener);
        
        //Listener cuando se cierra la barra de busqueda
        searchView.setOnCloseListener(new OnCloseListener()
        {

            @Override
            public boolean onClose()
            {
            	 searchView.setQuery("", false);
            	 if(filterList.isProgramDetail())
            		 ProgramDetailFragment.showDetail();
                // TODO Auto-generated method stub
                return false;
            }
        });
        
       //Listener cuando se cambia el foco de la barra de busqueda
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                	searchView.setQuery("", false);
                	searchMenuItem.collapseActionView();
                	if(filterList.isProgramDetail())
                		 ProgramDetailFragment.showDetail();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
	}
	
	/**
     * Metodo @Override:onOptionsItemSelected
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	/**
     * Metodo @Override:onPrepareOptionsMenu aqui se prepara el menu(la barra de busqueda), 
     * se habilita y se desabilita segun sea la vista
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean menuOpen = menuLayout.isDrawerOpen(menuList);
        if(search)
        	menu.findItem(R.id.action_search).setVisible(!menuOpen);
        else
        	menu.findItem(R.id.action_search).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
    
    /**
     * Metodo @Override:onPostCreate se ejecuta despues de que se creo la vista,
     * aqui se sincroniza el menu lateral
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        menuToggle.syncState();
    }
	
    /**
     * Metodo @Override:onConfigurationChanged se ejecuta cuando entra nueva configuracion,
     * aqui se a??ade la nueva configuracion al menu lateral
     */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		menuToggle.onConfigurationChanged(newConfig);
	}
	
	/**
     * Metodo @void:initDevice aqui se crea una instancia de la clase Device para inicializar
     * sus valores y se inicializa el historial de navegacion
     */
	private void initDevice(){
		historyNavigation=new ArrayList<CharSequence>();
		Device device = new Device();
		device.init(self);
	}
	
	/**
     * Metodo @void:initActionBar aqui se le da el estilo ala barra
     */
	private void initActionBar(){
		getSupportActionBar().setIcon(android.R.color.transparent);
		getSupportActionBar().setTitle(barTitle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		changeWhiteActionBar();
	}
	
	/**
     * Metodo estatico @void:changeColorTitleBar en este metodo se cambia de color y de fuente al texto de la barra
     * @param: int recibe el color que se le asigna al titulo
     */
	private static void changeColorTitleBar(int color){
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) self.findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(color);
		        //se crea una intancia Font para cambiar el tipo de letra
		        Font font = new Font();
		        font.changeFontIntro(self, title);
		    }
		}
	}
		
	/**
     * Metodo estatico @void:changeWhiteActionBar en este metodo se cambia de color del fondo de la barra a blanco
     */
	public static void changeWhiteActionBar(){
		int colorgreen =self.getResources().getColor(R.color.green_l);
		self.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(self.getResources().getColor(R.color.white)));
		changeColorTitleBar(colorgreen);
	}
	
	/**
     * Metodo @void:initSideMenu en este metodo se incializan los componentes del menu
     * lateral asi como sus acciones
     */
	private void initSideMenu(){
		
		//inicalizan los componentes
		barTitle="";
		menuTitle=getResources().getString(R.string.action_settings);
		menuOptions = getResources().getStringArray(R.array.menu_options);
		menuLayout = (DrawerLayout) findViewById(R.id.menu_layout);
		menuList = (ListView) findViewById(R.id.left_menu);
		menuLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// se inserta las opciones del menu
		insertListMenu(false);
		
		//se le agrega una accion al menu lateral que llama al metodo selectMenuOption
		//mandandole como paramentro la posicion que se seleciono
		menuList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectMenuOption(position);
			}});
		//accion para el menu lateral para saber si esta abierto o cerrado
		menuToggle = new ActionBarDrawerToggle(this,menuLayout,R.drawable.ic_drawer,R.string.menu_open,R.string.menu_close) 
			{
				@Override 
            	public void onDrawerClosed(View view) {
            		getSupportActionBar().setTitle(barTitle);
            		if(ProgrammingFragment.programType == null)
                		invalidateOptionsMenu();
            	}
				
				@Override 
            	public void onDrawerOpened(View drawerView) {
            		getSupportActionBar().setTitle(menuTitle);
            		if(ProgrammingFragment.programType == null)
                		invalidateOptionsMenu();
            	}
			};
        menuLayout.setDrawerListener(menuToggle);
	}
	
	/**
     * Metodo @void:insertListMenu se crea el adapter para la lista del menu
     * @param: contact(boolean) True si es contacto se quitan las opciones de ordenamiento,
     * 							False se a??aden las opciones de ordenamiento
     */
	public void insertListMenu(boolean contact){
		MenuAdapter menuAdapter = new MenuAdapter(self,menuOptions,contact);
		menuList.setAdapter(menuAdapter);
	}

	/**
     * Metodo @void:selectMenuOption realiza la opcion correspondiente al menu lateral 
     * que se halla seleccionado.
     * @param: position(int)  del 1 al 6 manda la accion correspondiente al menu
     */
	private void selectMenuOption(int position) {

		menuList.setItemChecked(position, true);
		barTitle=menuOptions[position];//cambia el titulo por la opcion selecionada
		if(position!=1)
		ifContactChangeMenu();
		
		switch(position){
		
			case 0:
				search=false;//barra de busqueda se desactiva
				barTitle="Home";
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				displayView(new CategoriesFragment(),"LastChapterView");
				break;
		    //manda a ver la programmacion
			case 1:
				sendRadioOnline();
				break;
			//manda a ver la tv online
			case 2:
				ProgrammingFragment.programType = "1";
				search=true;//barra de busqueda activa
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				LastChapterFragment.setLastChapter(false);
				GrillFragment.setGrill(false);
				ProgramDetailFragment.setProgramDetail(false);
				displayView(new ProgrammingFragment(),"ProgrammingView");
				break;
		    //ordena por orden alfabetico
			case 3:
				search=true;//barra de busqueda activa
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				orderArrayList(false);//false para orden alfabetico
				break;
		    //ordena por fecha
			case 4:
				search=true;//barra de busqueda activa
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				orderArrayList(true);//true para ordenar por fecha
				break;
		    //manda a ver la parrilla de programacion
			case 5:
				search=false;//barra de busqueda activa
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				LastChapterFragment.setLastChapter(false);
				GrillFragment.setGrill(false);
				ProgramDetailFragment.setProgramDetail(false);
				displayView(new GrillFragment(),"GrillFragment");
				break;
			//manda a ver la seccion de contacto
			case 6:
				search=false;//barra de busqueda se desactiva
				changeBarTitle(barTitle);//cambia el titulo por la opcion selecionada
				LastChapterFragment.setLastChapter(false);
				GrillFragment.setGrill(false);
				ProgramDetailFragment.setProgramDetail(false);
				displayView(new ContactFragment(),"ContactFragment");
				break;
			default:
				return;
		
		}
    }
	
	/**
     * Metodo estatico @void:changeBarTitle cambia el titulo de la barra
     * @param: barTitle(CharSequence) es el titulo de la barra que se pondra
     */
	public static void changeBarTitle(CharSequence barTitle){
		self.getSupportActionBar().setTitle(barTitle);
		menuLayout.closeDrawer(menuList);
	}
	
	/**
     * Metodo @void:displayView cambia de fragment
     * @param: fragment(Fragment) es el fragmento que se mostrara,  
     *         backStack(String) es el nombre de fragmento para regresar a el
     */
	private void displayView(Fragment fragment,String backStack){
		historyNavigation.add(barTitle);
		getFragmentManager().beginTransaction()
			.replace(R.id.container, fragment,backStack).addToBackStack(null).commit();
	}
	
	/**
     * Metodo @void:ifContactChangeMenu si es contacto inserta la lista de menu si ordenamiento
     */
	private void ifContactChangeMenu(){
		if(barTitle==menuOptions[5]||barTitle==menuOptions[6]||barTitle==menuOptions[0])
    		insertListMenu(true);
    	else	
    		insertListMenu(false);
	}
	
	/**
     * Metodo @void:getLastElementHistory remueve el ultimo elemento de el historial de navegacion
     * y va cambiando el titulo de la barra
     */
	private void getLastElementHistory(){
		if(ProgrammingFragment.isProgramming())
			self.finish();
		
		if(historyNavigation.size()>0){
			barTitle=historyNavigation.get(historyNavigation.size()-2);
			historyNavigation.remove(historyNavigation.size()-1);
			changeBarTitle(barTitle);
		}else{
			self.finish();
		}
	}
	
	/**
     * Metodo @void:addLastElementHitory a??ade el ultimo elemento de al historial de navegacion
     */
	public static void addLastElementHitory(String name){
		historyNavigation.add(name);
	}
	
	/**
     * Metodo @Override:onBackPressed regresa al fragment anterior
     */
	@Override
	public void onBackPressed(){
		if(ansyncTask){
			Log.i("ansyncTask", "Esta descargando");
			return;
		}
		try{
			FragmentManager fm = getFragmentManager();
		    if (fm.getBackStackEntryCount() > 1) {
		    	getLastElementHistory();
		    	ifContactChangeMenu();	
		        fm.popBackStack();
		    } else {
		        super.onBackPressed();  
		    }
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			self.finish();
		}
	    
	}
	
	public static void sendRadioOnline(){
		//se crea un capitulo para el streaming de tv online
		Chapter chapter =getChapterNow();
		if(chapter==null){
			chapter= new Chapter();
			chapter.setId("1");
			chapter.setUrlChapter(Constant.KALTURA_STREAMING);
			chapter.setNameChapter("Radio UAA");
			chapter.setPhotoChapter("https://radio.uaa.mx/asset/img/logo_radio.png");
		}
		ArrayList<Chapter> listC=new ArrayList<Chapter>();
		listC.add(chapter);
		ListProgram.setChapterExists(true);
		ListProgram.setCurrentChapter(chapter);
		ListProgram.setListChapter(listC);
		Intent intentTvOnline = new Intent(self,RadioOnlineActivity.class);
		self.startActivity(intentTvOnline);
	}
	
	private static Chapter getChapterNow(){
		//se crea un capitulo para el streaming de tv online
		DateCoverter date = new DateCoverter();
		int day = date.getDay()-1;
		int hour = date.getHour();
		int minute = date.getMinute();
		int hourNow=0;
		if(minute<10)
			hourNow=Integer.parseInt(""+hour+"0"+minute);
		else
			hourNow=Integer.parseInt(""+hour+""+minute);
		Log.i("dayNow", "dia: "+day+" hora: "+hour+" minutos: "+minute);
		HashMap<Integer,ArrayList<String>> programmation = ListProgram.getProgrammation();
		HashMap<Integer,ArrayList<String>> programmationId = ListProgram.getProgrammationId();
		HashMap<Integer,ArrayList<String>> programmationHour = ListProgram.getProgrammationHour();
		HashMap<Integer,ArrayList<String>> programmationHourEnd = ListProgram.getProgrammationHourEnd();
			ArrayList<String> programName=programmation.get(day);
			ArrayList<String> programID=programmationId.get(day);
			ArrayList<String> programHour=programmationHour.get(day);
			ArrayList<String> programHourEnd=programmationHourEnd.get(day);
			String urlPhotoProgram="";
			String nameProgram="";
			String idProgram="";
			try{
				for(int i=0;i<programName.size();i++){
					int hourStart;
					int hourEnd;
					String strOne = programHour.get(i);
					String strNext = programHourEnd.get(i);
					hourStart=Integer.parseInt(strOne.replace(":" , ""));
					hourEnd=Integer.parseInt(strNext.replace(":" , ""));
					Log.i("hourNow", ""+hourNow);
					Log.d("horaInicio", strOne);
					Log.e("hourStart", ""+hourStart);
					Log.d("horaTermino", strNext);
					Log.e("hourEnd", ""+hourEnd);
					if(hourNow>=hourStart&&hourNow<hourEnd){
						
							nameProgram=programName.get(i);
							idProgram=programID.get(i);
							urlPhotoProgram =Constant.HOST_KALTURA+"/p/101/sp/10100/thumbnail/entry_id/"+idProgram+"/def_height/480/def_width/640/version/100000/type/1";
							Chapter chapter =new Chapter();
							chapter.setId("1");
							chapter.setUrlChapter(Constant.KALTURA_STREAMING);
							chapter.setNameChapter(nameProgram);
							chapter.setPhotoChapter(urlPhotoProgram);
							return chapter;
					}
				}	
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
		return null;	
	}
	
	/**
     * Metodo @void:orderArrayList ordena la lista de los programas
     * @param: True ordena por fecha,
     *         False ordena por orden alfabetico
     */
	private void orderArrayList(boolean fecha){
		// se instancia OrderList
		OrderList orderList = new OrderList();
		//checa si es la vista de programacion
		if(orderList.isProgramming()){		
			// se instancia ListProgram para obtener la lista de programas
			ListProgram listProgram = new ListProgram();
			if(fecha){
				// se ordenan por fecha y se actuliza la vista
				orderList.byDateProgram(listProgram.getListProgram());
				ProgrammingFragment.updateListView(listProgram.getListProgram());
			}else{
				// se ordenan por alfabetico y se actuliza la vista
				orderList.byAlfaProgram(listProgram.getListProgram());
				ProgrammingFragment.updateListView(listProgram.getListProgram());
			}
		}else{
			// se ordena la lista de capitulos
			if(fecha){
				// se ordenan por fecha y se actuliza la vista
				ListProgram listProgram = new ListProgram();
				Program program =listProgram.getCurrentProgram();
				orderList.byDateChapter(ProgramDetailFragment.listChapter);
				ProgramDetailFragment.updateListView(ProgramDetailFragment.listChapter, program.getNameProgram());
			}else{
				// se ordenan por alfabetico y se actuliza la vista
				ListProgram listProgram = new ListProgram();
				Program program =listProgram.getCurrentProgram();
				orderList.byAlfaChapter(ProgramDetailFragment.listChapter);
				ProgramDetailFragment.updateListView(ProgramDetailFragment.listChapter, program.getNameProgram());
				
			}
		}
	}
}
