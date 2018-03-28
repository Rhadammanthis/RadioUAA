package com.triolabs.fragment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.kaltura.Constant;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.radio_uaa_android.MainActivity;
import com.triolabs.radio_uaa_android.R;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;
import com.triolabs.util.LoaderView;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RadioOnlineFragment extends Fragment implements OnErrorListener,OnAudioFocusChangeListener{

	static RadioOnlineFragment self;
	ImageView playAudio;
	ImageView backAudio;
	ImageView nextAudio;
	ImageView downloadAudio;
	String urlAudio;
	MediaPlayer mPlayer = null;
	boolean play;
	TextView textStart;
	TextView textDuration;
	SeekBar audioSeekBar;
	private final Handler handler = new Handler();
	private int mediaFileLengthInMilliseconds;
	ImageView imageProgram;
	ProgressBar progressBar;
	TextView nameChapter;
	int limitList=-1;
	int currentList=0;
	ArrayList<Chapter> listChapter;
	AlertDialog.Builder builder;
	private AudioManager audioManager;
	private boolean isFocusGranted, isFocusChanged;
	ProgressDialog progress;
	TextView textRigth;

	public RadioOnlineFragment() {
		self=this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_radio_online, container,
				false);
		progress = new ProgressDialog(self.getActivity());
		progress.setMessage("Conectando...");
		progress.setCancelable(false);
		progress.show();
		initAudioManager();
		play=false;
		builder=null;
		MainActivity.ansyncTask=true;
		imageProgram =(ImageView)rootView.findViewById(R.id.radio_online_image);
		progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar1);
		nameChapter =(TextView)rootView.findViewById(R.id.radio_online_label_text);
		audioSeekBar =(SeekBar)rootView.findViewById(R.id.seekBar1);
		textDuration=(TextView)rootView.findViewById(R.id.textView2);
		textStart=(TextView)rootView.findViewById(R.id.textView1);
		audioSeekBar.getProgressDrawable().setColorFilter(this.getResources().getColor(R.color.green_l), Mode.SRC_IN);
		playAudio=(ImageView)rootView.findViewById(R.id.radio_online_play);
		backAudio=(ImageView)rootView.findViewById(R.id.radio_online_back);
		nextAudio=(ImageView)rootView.findViewById(R.id.radio_online_next);
		audioSeekBar.setEnabled(false);
		downloadAudio=(ImageView)rootView.findViewById(R.id.radio_online_download);
		backAudio.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentList>0)
					back();
			}});
		nextAudio.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentList<limitList)
					next();
			}});
		downloadAudio.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downloadAudio();
			}});
		Font font = new Font();
		textRigth=(TextView)rootView.findViewById(R.id.radio_online_title_rigth);
		TextView textleft=(TextView)rootView.findViewById(R.id.radio_online_title_left);
		TextView textSlogan=(TextView)rootView.findViewById(R.id.radio_online_label_text_slogan);
		TextView textSloganOne=(TextView)rootView.findViewById(R.id.radio_online_label_text_slogan_one);
		font.changeFontIntro(self.getActivity(), textSloganOne);
		font.changeFontHelvetica(self.getActivity(), textSlogan);
		font.changeFontHelvetica(self.getActivity(), nameChapter);
		font.changeFontHelvetica(self.getActivity(), textDuration);
		font.changeFontHelvetica(self.getActivity(), textStart);
		font.changeFontIntro(self.getActivity(), textleft);
		font.changeFontHelvetica(self.getActivity(), textRigth);
		audioSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				mPlayer.seekTo(seekBar.getProgress());
			}});
		limitList=-1;
		currentList=0;
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initAudioManager();

	}

	private void downloadAudio(){
		ListProgram listProgram = new ListProgram();
		Chapter chapter = listProgram.getCurrentChapter();
		Intent intentDownload = new Intent(Intent.ACTION_VIEW);
		intentDownload.setData(Uri.parse(chapter.getDownloadChapter()));
		startActivity(intentDownload);
	}



	private void getInfoList(){
		ListProgram listProgram = new ListProgram();
		listChapter= new ArrayList<Chapter>();
		listChapter=listProgram.getListChapter();
		limitList=listChapter.size()-1;
		Log.i("limite", ""+limitList);
		getCurrentList();
	}

	private void getCurrentList(){
		ListProgram listProgram = new ListProgram();
		Chapter chapter = listProgram.getCurrentChapter();
		for(int i=0;i<=limitList;i++){
			if(listChapter.get(i).getId().equals(chapter.getId())){
				currentList=i;
				Log.i("current", ""+currentList);
			}
		}
		setChapter();
		hideControllers();
	}

	private void checkLimitList(){
		if(currentList==0){
			backAudio.setEnabled(false);
			backAudio.setAlpha(0.5f);
			if(currentList==limitList){
				nextAudio.setEnabled(false);
				nextAudio.setAlpha(0.5f);
			}
		}else if(currentList==limitList){
			nextAudio.setEnabled(false);
			nextAudio.setAlpha(0.5f);
		}else{
			backAudio.setEnabled(true);
			backAudio.setAlpha(1.0f);
			nextAudio.setEnabled(true);
			nextAudio.setAlpha(1.0f);
		}
	}





	private void setChapter(){
		ListProgram listProgram = new ListProgram();
		if(listProgram.isChapterExists()){
			Chapter chapter = listProgram.getCurrentChapter();
			if(chapter.getNameChapter().length()<30)
				nameChapter.setText(chapter.getNameChapter());
			else
				nameChapter.setText(chapter.getNameChapter().subSequence(0, 27)+"...");

			urlAudio=chapter.getUrlChapter();

			textRigth.setText("Podcast");
			Image image = new Image();
			Device device = new Device();
			Bitmap bmChapter = image.getBitmapFromMemCache(chapter.getPhotoChapter(),device.getHeigth(),device.getHeigth());
			if(bmChapter==null){
				ImageAsyncTask task = new ImageAsyncTask(getActivity(),imageProgram,progressBar,chapter.getPhotoChapter(),device.getWidth(),device.getHeigth(),false);
				task.execute();
			}else{
				imageProgram.setImageBitmap(bmChapter);
			}


		}
		checkLimitList();
		try {
			initMediaPlayer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hideControllers(){
		if(urlAudio.equals(Constant.KALTURA_STREAMING)){
			backAudio.setEnabled(false);
			nextAudio.setEnabled(false);
			downloadAudio.setEnabled(false);
//			playAudio.setEnabled(false);
//			playAudio.setAlpha(0.5f);
			backAudio.setAlpha(0.5f);
			nextAudio.setAlpha(0.5f);
			downloadAudio.setAlpha(0.5f);
		}

		playAudio.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!play){
					if(urlAudio.equals(Constant.KALTURA_STREAMING)){
						try {
							initMediaPlayer();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else
						play();
				}else{
					if(urlAudio.equals(Constant.KALTURA_STREAMING))
						stop();
					else
						pause();
				}
			}

		});
	}

	private void initMediaPlayer() throws IOException{
		textStart.setText("00:00");
		textDuration.setText("00:00");
		playAudio.setEnabled(false);
		audioSeekBar.setProgress(0);
		audioSeekBar.setEnabled(false);

		mPlayer = new MediaPlayer();
		mPlayer.setOnErrorListener(self);
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			Log.d("DEATH", "Stream URL: " + urlAudio);
			mPlayer.setDataSource(getActivity(), Uri.parse(urlAudio));
			mPlayer.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					playAudio.setEnabled(true);
					if(!urlAudio.equals(Constant.KALTURA_STREAMING))
						audioSeekBar.setEnabled(true);
					play();
				}
			});
			mPlayer.prepareAsync();
		} catch (IllegalArgumentException e) {
			Log.d("DEATH", "Illegal arguments");
			e.printStackTrace();
		} catch (SecurityException e) {
			Log.d("DEATH", "Security");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.d("DEATH", "Illegal state");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("DEATH", "IO error");
			e.printStackTrace();
		}



	}

	private void play(){
		play=true;
		Drawable pauseImage = getResources().getDrawable(R.drawable.pause);
		playAudio.setImageDrawable(pauseImage);
		mPlayer.start();
		audioSeekBar.setEnabled(true);
		if(urlAudio.equals(Constant.KALTURA_STREAMING)){
			playAudio.setEnabled(true);
			audioSeekBar.setEnabled(false);
		}
		handler.post(seekBarProgressUpdater);
	}

	private void pause(){

		Drawable playImage = getResources().getDrawable(R.drawable.play);
		play=false;
		playAudio.setImageDrawable(playImage);
		mPlayer.pause();
		audioSeekBar.setEnabled(false);
	}

	private void stop(){
		handler.removeCallbacks(seekBarProgressUpdater);
		Drawable playImage = getResources().getDrawable(R.drawable.play);
		play=false;
		playAudio.setImageDrawable(playImage);
		mPlayer.stop();
		mPlayer.reset();
		mPlayer.release();
	}

	private void next(){
		stop();
		if(currentList<limitList)
			currentList++;
		ListProgram listProgram = new ListProgram();
		listProgram.setCurrentChapter(listChapter.get(currentList));
		setChapter();
	}

	private void back(){
		stop();
		if(currentList>0)
			currentList--;
		ListProgram listProgram = new ListProgram();
		listProgram.setCurrentChapter(listChapter.get(currentList));
		setChapter();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
			MainActivity.ansyncTask=false;
			handler.removeCallbacks(seekBarProgressUpdater);
			audioManager.abandonAudioFocus(self);
			stop();
		}catch(NullPointerException e){
			e.printStackTrace();
		}

	};



	private final Runnable seekBarProgressUpdater = new Runnable(){
		public void run(){
			try {
				//prepare and send the data here..
				mediaFileLengthInMilliseconds = mPlayer.getDuration();
				textDuration.setText(convertMilliseconds(mediaFileLengthInMilliseconds));
				audioSeekBar.setMax(mediaFileLengthInMilliseconds);
				if(mPlayer.isPlaying()){
					audioSeekBar.setProgress(mPlayer.getCurrentPosition());
					textStart.setText(convertMilliseconds(mPlayer.getCurrentPosition()));
				}
				if(!textStart.getText().toString().equals("00:00")){
					progress.dismiss();

				}
				handler.postDelayed(this, 1000);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private String convertMilliseconds(int milliseconds){
		String time="00:00";
		int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		DecimalFormat formatter = new DecimalFormat("00");
		if(hours!=0){
			time=formatter.format(hours)+":"+formatter.format(minutes)+":"+formatter.format(seconds);
		}else{
			time=formatter.format(minutes)+":"+formatter.format(seconds);
		}
		//if(!time.equals("00:00"))
		//progressBar.setVisibility(View.GONE);
		return time;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getInfoList();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		try{
			if(builder==null){

				builder = new AlertDialog.Builder(self.getActivity());
				builder.setMessage("Lo sentimos, no se puede reproducir este audio ")
						.setTitle("Error")
						.setCancelable(false)
						.setPositiveButton(self.getResources().getString(R.string.alert_acept),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
										self.getActivity().finish();
									}

								});
				AlertDialog alert = builder.create();
				alert.show();

			}
		}catch(NullPointerException e){

		}
		return true;
	}

	private void initAudioManager(){
		audioManager = (AudioManager) self.getActivity().getSystemService(Context.AUDIO_SERVICE);
		int result = audioManager.requestAudioFocus(self,
				AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

		switch (result) {
			case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
				isFocusGranted = true;
				break;
			case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
				isFocusChanged = false;
				break;
		}

		String message = "Focus request " + (isFocusGranted ? "granted" : "failed");
		Log.i("RADIOUAA", message);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
				Log.i("RADIOUAA", "AUDIOFOCUS_GAIN");
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				Log.i("RADIOUAA", "AUDIOFOCUS_LOSS");
				if(urlAudio.equals(Constant.KALTURA_STREAMING))
					stop();
				else
					pause();
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				Log.i("RADIOUAA", "AUDIOFOCUS_LOSS_TRANSIENT");
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				Log.i("RADIOUAA", "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
				break;
		}
	}




}
