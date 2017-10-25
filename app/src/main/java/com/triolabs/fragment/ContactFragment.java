package com.triolabs.fragment;

import com.facebook.share.widget.LikeView;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Font;
import com.triolabs.util.LoaderView;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* ContactFragment es el fragmento muestra la seccion del contacto
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ContactFragment extends Fragment implements OnClickListener{
	
	LoaderView loader;
	static ContactFragment self;
	
	/** Se crea una instancia de ContactFragment
 	 */
	public ContactFragment(){
		self=this;
	}
	
	/**
	 *  Metodo onCreateView inicializa los componentes de la vista
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contact, container,
				false);
//		getActivity().getActionBar().show();
		setMenuVisibility(false);
		MainActivity.changeWhiteActionBar();
		Font font = new Font();
		TextView textLeft = (TextView)rootView.findViewById(R.id.contact_title_left);
		TextView textRigth = (TextView)rootView.findViewById(R.id.contact_title_rigth);
		TextView textAddress = (TextView)rootView.findViewById(R.id.contact_text_address);
		TextView textPhone = (TextView)rootView.findViewById(R.id.contact_text_phone);
		TextView textUaa = (TextView)rootView.findViewById(R.id.contact_text_copyright);
		TextView textDep = (TextView)rootView.findViewById(R.id.contact_text_dep);
		TextView textSlogan = (TextView)rootView.findViewById(R.id.contact_slogan);
		RelativeLayout contactFacebook = (RelativeLayout)rootView.findViewById(R.id.contact_facebook);
		RelativeLayout contactTwitter = (RelativeLayout)rootView.findViewById(R.id.contact_twitter);
		RelativeLayout contactEmail = (RelativeLayout)rootView.findViewById(R.id.contact_email);
		RelativeLayout contactPhone = (RelativeLayout)rootView.findViewById(R.id.contact_phone);
		LinearLayout layoutTvOnline =(LinearLayout)rootView.findViewById(R.id.contact_label);
		layoutTvOnline.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.sendRadioOnline();
			}});
		font.changeFontIntro(getActivity(), textLeft);
		font.changeFontIntro(getActivity(), textRigth);
		font.changeFontHelvetica(getActivity(), textAddress);
		font.changeFontHelvetica(getActivity(), textPhone);
		font.changeFontIntro(getActivity(), textSlogan);
		font.changeFontIntro(getActivity(), textUaa);
		font.changeFontHelvetica(getActivity(), textDep);
		contactFacebook.setOnClickListener(this);
		contactTwitter.setOnClickListener(this);
		contactEmail.setOnClickListener(this);
		contactPhone.setOnClickListener(this);
		
		LikeView likeView = (LikeView) rootView.findViewById(R.id.like_button);
		likeView.setObjectIdAndType(
		    "https://www.facebook.com/tvuaaoficial",
		    LikeView.ObjectType.PAGE);
		
		return rootView;
	}
	
	/**
	 *  Metodo getOpenFacebookIntent manda abrir el facebook de TV UAA
     */
	public void getOpenFacebookIntent() {
		   try {
			    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/270053706369750"));
			    this.getActivity().startActivity(intent);
			} catch(Exception e) {
				this.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RadioUAA")));
			}
	}
	
	/**
	 *  Metodo callRadio manda una intencion para llamar 
	 *  @params tel es el numero de telefono al que se va llamar
     */
	public void callRadio(String tel){
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
		this.getActivity().startActivity(callIntent);
	};

	/**
	 *  Metodo alertDialogWithNumbers muesta una alerta con lo numeros para que se eliga a cual llamar
     */
	public void alertDialogWithNumbers(){
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(this.getActivity());
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selecciona un numero:");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("4499121588");
        arrayAdapter.add("4499107455");
        arrayAdapter.add("4499107459");
        builderSingle.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strNumber = arrayAdapter.getItem(which);
                        callRadio(strNumber);
                    }
                });
        builderSingle.show();
	};
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.contact_email:
				loader= new LoaderView(self.getActivity());
				loader.show();
				loader.hideDelayed();
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
		            "mailto","xhuaa@correo.uaa.mx", null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RADIO UAA");
				this.getActivity().startActivity(emailIntent);
			break;
		case R.id.contact_phone:
			    alertDialogWithNumbers();
			break;
		case R.id.contact_facebook:
				loader= new LoaderView(self.getActivity());
				loader.show();
				loader.hideDelayed();
				getOpenFacebookIntent();
			break;
		case R.id.contact_twitter:
				loader= new LoaderView(self.getActivity());
				loader.show();
				loader.hideDelayed();
				Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/radiouaa"));
				this.getActivity().startActivity(twitterIntent);
			break;
		default:
			return;
		}
		
	}	
	
	
	

}
