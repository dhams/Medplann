package com.medplan.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medpan.util.CellInfo_Model;
import com.medpan.util.CommonMethod;
import com.medpan.util.Constant;
import com.medpan.util.GlobalMethods;
import com.medpan.util.Notification_Model;
import com.medpan.util.Picture_Model;
import com.medpan.util.User_Model;
import com.medplan.db.databasehelper;

public class BoxTenActivity extends Activity implements OnClickListener,
		OnInitListener {
	RelativeLayout titleHeadLayout, rlCellA, rlCellB, rlCellC, rlCellD,
			rlCellE, rlCellF, rlCellG, rlCellH, rlCellI, rlCellJ, tmpRl;
	LinearLayout MainBgLayout;
	TextView tvCellA, tvCellB, tvCellC, tvCellD, tvCellE, tvCellF, tvCellG,
			tvCellH, tvCellI, tvCellJ, tvTimeA, tvTimeB, tvTimeC, tvTimeD,
			tvTimeE, tvTimeF, tvTimeG, tvTimeH, tvTimeI, tvTimeJ, tvtmpTime;
	Intent intent;
	ImageView imgCellA, imgCellB, imgCellC, imgCellD, imgCellE, imgCellF,
			imgCellG, imgCellH, imgCellI, imgCellJ, tmpIV;
	Button btnConfirm;
	public static int cell_pos, boxid, userid, loginid, picid, WaytoStop,
			alarmSound;
	databasehelper db;
	public String task = "add", MedName, Desc;
	ArrayList<CellInfo_Model> cellinfolist = new ArrayList<CellInfo_Model>();
	Picture_Model picmodel;
	int a = -1, b = -1, c = -1, d = -1, e = -1, f = -1, g = -1, h = -1, i = -1,
			j = -1;
	Animation anim;
	Handler handler;
	int counter = 1;
	private TextToSpeech mTts;
	boolean blink, miniImg, vibrat, alert, sound, isConfirm, isAlarm;
	private static final int MY_DATA_CHECK_CODE = 1234;
//	public static  Onpause  =false;
	AlertDialog alertMsg;
	public static CellInfo_Model cell;
	ImageView headLogo;
	TextView headerTitle;
	SimpleDateFormat sdf;
	SharedPreferences SP;
    public static  boolean stop ;
    private String fromWhere ;
    private ApplicationClass applicationClass ;
    private String 	activity   ;
    String pendingInterntID ; 
    private MediaPlayer mediaPlayer ;
    private Context context  ;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		context = this ;
		db = new databasehelper(this);
		SP = PreferenceManager.getDefaultSharedPreferences(this);
		Intent intent = getIntent();
		pendingInterntID  = intent.getStringExtra("unique") ;
		boxid = intent.getIntExtra("BoxID", 0);
		userid = intent.getIntExtra("UserID", 0);
		loginid = intent.getIntExtra("LoginID", 0);
		cell_pos = intent.getIntExtra("CellID", -1);
		MedName = intent.getStringExtra("Med");
		WaytoStop = intent.getIntExtra("WayToStop", 0);
		alarmSound = intent.getIntExtra("Sound", 0);
		Desc = intent.getStringExtra("Description");
		fromWhere = intent.getStringExtra("FromWhereActivity");
		 
			applicationClass = (ApplicationClass) this.getApplication();

			if (intent.hasExtra("BoxID")) {
				activity = intent.getStringExtra("Activity") ;
				boxid = intent.getIntExtra("BoxID", 0);
				applicationClass.boxId = boxid;
				userid = intent.getIntExtra("UserID", 0);
				applicationClass.userId = userid;
				loginid = intent.getIntExtra("LoginID", 0);
				applicationClass.loginId = loginid;
				cell_pos = intent.getIntExtra("CellID", -1);
				applicationClass.cellPos = cell_pos;
				MedName = intent.getStringExtra("Med");
				applicationClass.medName = MedName;
				WaytoStop = intent.getIntExtra("WayToStop", 0);
				applicationClass.wayToStop = WaytoStop;
				Desc = intent.getStringExtra("Description");
				applicationClass.desc = Desc;
				alarmSound = intent.getIntExtra("Sound", 0);
				applicationClass.alarmSound = alarmSound;
				fromWhere = intent.getStringExtra("FromWhereActivity");
//				applicationClass.fromWhere = fromWhere;

			} else {
				boxid = applicationClass.boxId;
				userid = applicationClass.userId;
				loginid = applicationClass.loginId;
				cell_pos = applicationClass.cellPos;
				MedName = applicationClass.medName;
				WaytoStop = applicationClass.wayToStop;
				Desc = applicationClass.desc;
				alarmSound = applicationClass.alarmSound;
//				fromWhere = applicationClass.fromWhere;
			}

		 
//		 Window wind;
//		    wind = this.getWindow();
//		    wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
//		    wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//		    wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
		    
//		if (SP.getInt("lock", -1) == 0 && cell_pos != -1) {
//			Log.i("", "finish");
//			stop =  true ;
//			finish();
//		}
//		else if (SP.getString("isLogin", "")=="no"){
//			stop = true ;
//		finish();
//	}
			
			if (pendingInterntID!=null){
				Log.d("*********** Pending intent Id =", "Pending intent Id ="+pendingInterntID) ;
				unlockScren();
				}
			Log.d("!!!!!!!!!!!!!! Pending intent Id =", "Pending intent Id ="+SP.getString("BoxTenPid", "")) ;
			if (SP.getString("BoxTenPid", "").equals(pendingInterntID)){
				finish() ;
				return   ;
			}
		    
		if(((SP.getInt("lock", -1) == 0)&& cell_pos != -1)||(SP.getString("isLogin", "")=="no")||(db.isUserExsist(userid)==false)) {
			Log.e("", "*************  finish  ****	**************");
			stop = true ;
			finish();
			launchHomeActivity();
			return   ;
		}
	else{
		ArrayList<CellInfo_Model> arrCell = db.getCellInfoForBox(loginid,
				userid, boxid, cell_pos);
		if (arrCell.size()==0& fromWhere!=null){
			Log.e("", "*************  finish  ****	**************");
			stop = true ;
			finish();
			launchHomeActivity();
			return  ; 
		}
		else
			if (activity!=null)
		Toast.makeText(BoxTenActivity.this,
				getResources().getString(R.string.cell_edit),
				Toast.LENGTH_LONG).show();
	}
		setContentView(R.layout.cell_mgt_ten_boxe);

		Constant.flag = false;

//		Toast.makeText(BoxTenActivity.this,
//				getResources().getString(R.string.cell_edit),
//				Toast.LENGTH_LONG).show();

		headLogo = (ImageView) findViewById(R.id.img_header_logo);
		headLogo.setBackgroundResource(R.drawable.cell_mgt);

		headerTitle = (TextView) findViewById(R.id.tv_header_title);
		headerTitle.setText(R.string.cell_mgt);

		anim = AnimationUtils.loadAnimation(this, R.anim.blink);
		handler = new Handler();

		tvCellA = (TextView) findViewById(R.id.tv_ten_boxA);
		tvCellB = (TextView) findViewById(R.id.tv_ten_boxB);
		tvCellC = (TextView) findViewById(R.id.tv_ten_boxC);
		tvCellD = (TextView) findViewById(R.id.tv_ten_boxD);
		tvCellE = (TextView) findViewById(R.id.tv_ten_boxE);
		tvCellF = (TextView) findViewById(R.id.tv_ten_boxF);
		tvCellG = (TextView) findViewById(R.id.tv_ten_boxG);
		tvCellH = (TextView) findViewById(R.id.tv_ten_boxH);
		tvCellI = (TextView) findViewById(R.id.tv_ten_boxI);
		tvCellJ = (TextView) findViewById(R.id.tv_ten_boxJ);

		tvTimeA = (TextView) findViewById(R.id.tv_ten_timeA);tvTimeA.setText("");
		tvTimeB = (TextView) findViewById(R.id.tv_ten_timeB);tvTimeB.setText("");
		tvTimeC = (TextView) findViewById(R.id.tv_ten_timeC);tvTimeC.setText("");
		tvTimeD = (TextView) findViewById(R.id.tv_ten_timeD);tvTimeD.setText("");
		tvTimeE = (TextView) findViewById(R.id.tv_ten_timeE);tvTimeE.setText("");
		tvTimeF = (TextView) findViewById(R.id.tv_ten_timeF);tvTimeF.setText("");
		tvTimeG = (TextView) findViewById(R.id.tv_ten_timeG);tvTimeG.setText("");
		tvTimeH = (TextView) findViewById(R.id.tv_ten_timeH);tvTimeH.setText("");	
		tvTimeI = (TextView) findViewById(R.id.tv_ten_timeI);tvTimeI.setText("");
		tvTimeJ = (TextView) findViewById(R.id.tv_ten_timeJ);tvTimeJ.setText("");

		rlCellA = (RelativeLayout) findViewById(R.id.rl_ten_boxA);
		rlCellB = (RelativeLayout) findViewById(R.id.rl_ten_boxB);
		rlCellC = (RelativeLayout) findViewById(R.id.rl_ten_boxC);
		rlCellD = (RelativeLayout) findViewById(R.id.rl_ten_boxD);
		rlCellE = (RelativeLayout) findViewById(R.id.rl_ten_boxE);
		rlCellF = (RelativeLayout) findViewById(R.id.rl_ten_boxF);
		rlCellG = (RelativeLayout) findViewById(R.id.rl_ten_boxG);
		rlCellH = (RelativeLayout) findViewById(R.id.rl_ten_boxH);
		rlCellI = (RelativeLayout) findViewById(R.id.rl_ten_boxI);
		rlCellJ = (RelativeLayout) findViewById(R.id.rl_ten_boxJ);

		imgCellA = (ImageView) findViewById(R.id.img_ten_boxA);
		imgCellB = (ImageView) findViewById(R.id.img_ten_boxB);
		imgCellC = (ImageView) findViewById(R.id.img_ten_boxC);
		imgCellD = (ImageView) findViewById(R.id.img_ten_boxD);
		imgCellE = (ImageView) findViewById(R.id.img_ten_boxE);
		imgCellF = (ImageView) findViewById(R.id.img_ten_boxF);
		imgCellG = (ImageView) findViewById(R.id.img_ten_boxG);
		imgCellH = (ImageView) findViewById(R.id.img_ten_boxH);
		imgCellI = (ImageView) findViewById(R.id.img_ten_boxI);
		imgCellJ = (ImageView) findViewById(R.id.img_ten_boxJ);

		btnConfirm = (Button) findViewById(R.id.btn_boxten_confirm);
		
//		rlCellA.setOnClickListener(this);
//		rlCellB.setOnClickListener(this);
//		rlCellC.setOnClickListener(this);
//		rlCellD.setOnClickListener(this);
//		rlCellE.setOnClickListener(this);
//		rlCellF.setOnClickListener(this);
//		rlCellG.setOnClickListener(this);
//		rlCellH.setOnClickListener(this);
//		rlCellI.setOnClickListener(this);
//		rlCellJ.setOnClickListener(this);
//		btnConfirm.setOnClickListener(this);

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setCancelable(false);
		alertDialog.setTitle("Confirm");
		alertDialog.setMessage("Did you take your medicine?");
		alertDialog.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						isConfirm = true;
						isAlarm = false;
//						reportDone = true;
						ReportDetails();
					}
				});

		alertDialog.setNegativeButton(R.string.no_thanks,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						isConfirm = false;
						isAlarm = false;
//						reportDone = true;
						ReportDetails();
					}
				});

		alertMsg = alertDialog.create();

		TextView tv = (TextView) findViewById(R.id.tv_userbox_name);
		TextView tvBoxname = (TextView) findViewById(R.id.tv_userbox_boxname);
		
		Log.i("", userid + "-----------" + boxid);
		User_Model arr = db.getPatients(String.valueOf(userid));
		try{
		tv.setText("User : " + arr.name + " " + arr.surname);
		}catch (Exception e) {
		}
		
		tvBoxname.setText(" Box: " + boxid);

//		cellinfolist = db.getCellInfoForBox(loginid, userid, boxid, -1);

//		if (cellinfolist.size() > 0) {
//			a = -1;
//			b = -1;
//			c = -1;
//			d = -1;
//			e = -1;
//			f = -1;
//			g = -1;
//			h = -1;
//			i = -1;
//			j = -1;
//
//			for (int i = 0; i < cellinfolist.size(); i++) {
//				Log.i("Cellid", "--------------" + cellinfolist.get(i).cellid);
//				switch (cellinfolist.get(i).cellid) {
//				case 0: 
//					tvCellA.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 0);
//						if (tmp.size() > 0) {
//							tvTimeA.setText(tmp.get(0).strTime);
//							tvTimeA.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//					}
//					picid = cellinfolist.get(i).picid;
//					a = i;
//					break;
//				case 1:
//					tvCellB.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 1);
//						if (tmp.size() > 0) {
//							tvTimeB.setText(tmp.get(0).strTime);
//							tvTimeB.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//					}
//					picid = cellinfolist.get(i).picid;
//					b = i;
//					break;
//				case 2:
//					tvCellC.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 2);
//						if (tmp.size() > 0) {
//							tvTimeC.setText(tmp.get(0).strTime);
//							tvTimeC.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					c = i;
//					break;
//				case 3:
//					tvCellD.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 3);
//						if (tmp.size() > 0) {
//							tvTimeD.setText(tmp.get(0).strTime);
//							tvTimeD.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					d = i;
//					break;
//				case 4:
//					tvCellE.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 4);
//						if (tmp.size() > 0) {
//							tvTimeE.setText(tmp.get(0).strTime);
//							tvTimeE.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					e = i;
//					break;
//				case 5:
//					tvCellF.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 5);
//						if (tmp.size() > 0) {
//							tvTimeF.setText(tmp.get(0).strTime);
//							tvTimeF.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					f = i;
//					break;
//				case 6:
//					tvCellG.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 6);
//						if (tmp.size() > 0) {
//							tvTimeG.setText(tmp.get(0).strTime);
//							tvTimeG.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					g = i;
//					break;
//				case 7:
//					tvCellH.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 7);
//						if (tmp.size() > 0) {
//							tvTimeH.setText(tmp.get(0).strTime);
//							tvTimeH.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					h = i;
//					break;
//				case 8:
//					tvCellI.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 8);
//						if (tmp.size() > 0) {
//							tvTimeI.setText(tmp.get(0).strTime);
//							tvTimeI.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					this.i = i;
//					break;
//				case 9:
//					tvCellJ.setText(""
//							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
//					try {
//						ArrayList<Notification_Model> tmp = db
//								.getCellNotification(loginid, userid, boxid, 9);
//						if (tmp.size() > 0) {
//							tvTimeJ.setText(tmp.get(0).strTime);
//							tvTimeJ.setVisibility(View.VISIBLE);
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					picid = cellinfolist.get(i).picid;
//					j = i;
//					break;
//
//				}
//			}
//		}

//		if (cell_pos != -1 && stop==false) {
////			Toast.makeText(getApplicationContext(), getString(R.string.tapToast), Toast.LENGTH_LONG).show();
//			isAlarm = true;
//			ArrayList<CellInfo_Model> arrCell = db.getCellInfoForBox(loginid,
//					userid, boxid, cell_pos);
//            mediaPlayer.create(getApplicationContext(), Constant.Sound[alarmSound]) ;
//            mediaPlayer.setVolume(100, 100) ;
//
//            
//			try {
//				CellInfo_Model cell = arrCell.get(0);
//				blink = cell.blBlink == 1 ? true : false;
//				miniImg = cell.blMini == 1 ? true : false;
//				vibrat = cell.blVibrant == 1 ? true : false;
//				alert = cell.blAlert == 1 ? true : false;
//				int color = cell.intBg;
//				final int limit = cell.intBuzz;
//
//				switch (color) {
//				case 1:
//					setColor(Color.RED);
//					break;
//				case 2:
//					setColor(Color.YELLOW);
//					break;
//				case 3:
//					setColor(Color.GREEN);
//					break;
//				case 4:
//					setColor(Color.rgb(255, 200, 0));
//					break;
//				case 5:
//					setColor(Color.rgb(198, 195, 181));
//					break;
//				case 6:
//					setColor(Color.WHITE);
//					break;
//				}
//
//				// show alarm time
//				tvtmpTime.setVisibility(View.VISIBLE);
//				sdf = new SimpleDateFormat("HH:mm");
//				String currentDateandTime = sdf.format(new Date(System
//						.currentTimeMillis()));
//				System.out.println("current time" + currentDateandTime);
//				tvtmpTime.setText(currentDateandTime);
//
//				Log.i("TAG===",
//						"loginid, userid, boxid, cell_pos,blink,minImg,vibrat,alert,color,limit ===== \n"
//								+ loginid
//								+ " "
//								+ userid
//								+ " "
//								+ boxid
//								+ " "
//								+ cell_pos
//								+ " "
//								+ blink
//								+ " "
//								+ miniImg
//								+ " "
//								+ vibrat
//								+ " "
//								+ alert
//								+ " "
//								+ color
//								+ " "
//								+ limit);
//
//				sound = true;
//
//				if (WaytoStop==1)
//					Toast.makeText(getApplicationContext(),
//							getString(R.string.tapToast), Toast.LENGTH_LONG)
//							.show();
//				else
//					Toast.makeText(getApplicationContext(),
//							getString(R.string.tapCellToast), Toast.LENGTH_LONG)
//							.show();
//				
//				
//				// check whether blink,miniImg,alert,vibrate are true or
//				// false...
//
//				if (WaytoStop == 1 || WaytoStop == 0) {
//					btnConfirm.setVisibility(View.VISIBLE);
//					rlCellA.setClickable(false);
//					rlCellB.setClickable(false);
//					rlCellC.setClickable(false);
//					rlCellD.setClickable(false);
//					rlCellE.setClickable(false);
//					rlCellF.setClickable(false);
//					rlCellG.setClickable(false);
//					rlCellH.setClickable(false);
//					rlCellI.setClickable(false);
//					rlCellJ.setClickable(false);
//				}
//
//				// blinking cell
//				if (blink == true) {
//					tmpRl.setAnimation(anim);
//					System.out.println("Working~~~~~~1");
//				}
//				// to show to not mini Image.
//				if (miniImg == true) {
//					System.out.println("Working~~~~~~2");
//					tmpIV.setVisibility(View.VISIBLE);
//					picmodel = db.getPicture(picid);
//					Bitmap bitmap = GlobalMethods.decodeFile(picmodel.path);
//					System.out.println("wint three box cell~~~~~~~~~~~~"
//							+ picmodel.path);
//					if (bitmap == null) {
//						tmpIV.setVisibility(View.GONE);
//						// tmpIV.setBackgroundResource(R.drawable.add_photo);
//					} else {
//						tmpIV.setImageBitmap(bitmap);
//					}
//				}
//				// To vibrate or not.
//				if (vibrat == true) {
//					System.out.println("Working~~~~~~3");
//					CommonMethod.vibration(this);
//				}
//
//				if (sound == true && alert == true) {
//					System.out.println("Both~~~~~~~");
////					CommonMethod.SoundPlayer(this, Constant.Sound[alarmSound]);
//					startMediaplayer();
//
//					mediaPlayer
//							.setOnCompletionListener(new OnCompletionListener() {
//
//								@Override
//								public void onCompletion(MediaPlayer mp) {
//									// /will use count and loop as per number of
//									// repetition chosen.
//
//									Intent checkIntent = new Intent();
//									checkIntent
//											.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//									startActivityForResult(checkIntent,
//											MY_DATA_CHECK_CODE);
//
//									handler.postDelayed(new Runnable() {
//
//										public void run() {
//											if (counter >= limit) {
////												CommonMethod.player.stop();
//												stopMediaPlayer();
//											} else {
////												CommonMethod.player.start();
//												startMediaplayer();}
//											counter++;
//										}
//									}, 3000);
//								}
//							});
//
//				}
//				if (sound == true && alert == false) {
//					System.out.println("sound true alert false~~~~~~~");
////					CommonMethod.SoundPlayer(this, Constant.Sound[alarmSound]);
//					startMediaplayer();
//					mediaPlayer
//							.setOnCompletionListener(new OnCompletionListener() {
//
//								@Override
//								public void onCompletion(MediaPlayer mp) {
//									// /will use count and loop as per number of
//									// repetition chosen.
//									handler.postDelayed(new Runnable() {
//
//										public void run() {
//											if (counter >= limit) {
////												CommonMethod.player.stop();
//												stopMediaPlayer();
//											} else {
//												startMediaplayer();
//											}
//											counter++;
//										}
//									}, 3000);
//								}
//							});
//				}
//				if (sound == false && alert == true) {
//					System.out.println("sound false alert true~~~~~~~");
//					Intent checkIntent = new Intent();
//					checkIntent
//							.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//					startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
//
//				} else {
//					System.out.println("~~~~~~~~~~~~do nothing~~~~~~~");
//				}
//			} catch (Exception e) {
//
//			}
//		}

		titleHeadLayout = (RelativeLayout) findViewById(R.id.rlHeadTitlelayout);
		MainBgLayout = (LinearLayout) findViewById(R.id.MainAlarmLayout);

		System.out.println("strcheck~~~~" + Constant._StrCheck);

		try {
			SplashScreen.Cmethod.CheckAddShowScreen(Constant._StrCheck,
					titleHeadLayout, MainBgLayout);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Button btnHome = (Button) findViewById(R.id.button_home);
		btnHome.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(BoxTenActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

	

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				mTts = new TextToSpeech(this, this);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public void onInit(int pos) {
		if (alert == false)
			return;

		System.out.println("medname~~~~~~~~~~~~" + MedName + "" + "~~~~~~~~"
				+ Desc);
		AudioManager am = (AudioManager) getSystemService(this.AUDIO_SERVICE);
		int amStreamMusicMaxVol = am.getStreamMaxVolume(am.STREAM_MUSIC);
		am.setStreamVolume(am.STREAM_MUSIC, amStreamMusicMaxVol, 15);
		mTts.speak(MedName + ".   " + Desc, TextToSpeech.QUEUE_FLUSH, // Drop
				// all
				// pending
				// entries
				// in
				// the
				// playback queue.
				null);

	}

	@Override
	public void onDestroy() {
		// Don�t forget to shutdown!
		try {
			if (mTts != null) {
				mTts.stop();
				mTts.shutdown();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		CommonMethod.releaseSoundPlayer() ;
		stopMediaPlayer();
		super.onDestroy();
	}

	
	private  void stopMediaPlayer(){
		if (mediaPlayer!=null)
			try {  
				mediaPlayer.stop() ;
				Log.d("Media player ", "is stop ") ;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
	}
	
	private void startMediaplayer(){
		mediaPlayer.setVolume(100f, 100f);
		if(!mediaPlayer.isPlaying())
		startMediaplayer();
		Log.d("Media player ", "is on ") ;
}
	
	public void setColor(int color) {
		switch (cell_pos) {
		case 0:
			rlCellA.setBackgroundColor(color);
			tmpRl = rlCellA;
			tmpIV = imgCellA;
			tvtmpTime = tvTimeA;
			break;
		case 1:
			rlCellB.setBackgroundColor(color);
			tmpRl = rlCellB;
			tmpIV = imgCellB;
			tvtmpTime = tvTimeB;
			break;
		case 2:
			rlCellC.setBackgroundColor(color);
			tmpRl = rlCellC;
			tmpIV = imgCellC;
			tvtmpTime = tvTimeC;
			break;
		case 3:
			rlCellD.setBackgroundColor(color);
			tmpRl = rlCellD;
			tmpIV = imgCellD;
			tvtmpTime = tvTimeD;
			break;
		case 4:
			rlCellE.setBackgroundColor(color);
			tmpRl = rlCellE;
			tmpIV = imgCellE;
			tvtmpTime = tvTimeE;
			break;
		case 5:
			rlCellF.setBackgroundColor(color);
			tmpRl = rlCellF;
			tmpIV = imgCellF;
			tvtmpTime = tvTimeF;
			break;
		case 6:
			rlCellG.setBackgroundColor(color);
			tmpRl = rlCellG;
			tmpIV = imgCellG;
			tvtmpTime = tvTimeG;
			break;

		case 7:
			rlCellH.setBackgroundColor(color);
			tmpRl = rlCellH;
			tmpIV = imgCellH;
			tvtmpTime = tvTimeH;
			break;
		case 8:
			rlCellI.setBackgroundColor(color);
			tmpRl = rlCellI;
			tmpIV = imgCellI;
			tvtmpTime = tvTimeI;
			break;
		case 9:
			rlCellJ.setBackgroundColor(color);
			tmpRl = rlCellJ;
			tmpIV = imgCellJ;
			tvtmpTime = tvTimeJ;
			break;
		}
	}

	public void doShowToast(final View v)
	{
//		if(cell_pos!=-1&!(blink == true || miniImg == true || vibrat == true|| sound == true))
	if (fromWhere==null)
		{
				LayoutInflater inflater = getLayoutInflater();
		        View view = inflater.inflate(R.layout.custom_toast,
		                                       (ViewGroup) findViewById(R.id.llayout));
		        Toast toast = new Toast(getApplicationContext());
		        toast.setGravity(Gravity.FILL, 0, 0);
		        toast.setView(view);
		        toast.show();
		}
		        new Handler().post(new Runnable() {
					@Override
					public void run() {
						doClick(v);		
					}
				});
				
	}
	void doClick(View v)
	{
		if (CommonMethod.vibrator != null)
			CommonMethod.vibrator.cancel();
		if (WaytoStop == 2) {
			try {
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				tmpRl.setBackgroundColor(Color.TRANSPARENT);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		switch (v.getId()) {
		case R.id.rl_ten_boxA:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
				;
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true || fromWhere!=null) 
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				alertMsg.show();
			}

			else {
				cell_pos = 0;
				if (a != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxB:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true ||fromWhere!=null)
				if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				alertMsg.show();
			}

			else {
				cell_pos = 1;
				if (b != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxC:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null) 
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				alertMsg.show();
			}

			else {
				cell_pos = 2;
				if (c != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxD:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod..stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null) 
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 3;
				if (d != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxE:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
				vibrat = false;
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
				sound = false;
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null)
				if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 4;
				if (e != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxF:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);
				blink = false;

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
				vibrat = false;
			}

			if (sound == true) {
//				CommonMethod..stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
				sound = false;
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null)
				if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 5;
				if (f != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxG:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);
			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null)
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 6;
				if (g != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxH:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod..stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null)
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 7;
				if (h != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxI:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);
			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}
			if (sound == true) {
				
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null) 
			if (fromWhere!=null)
			{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				Constant.flag = true;
			}

			else {
				alertMsg.show();
				cell_pos = 8;
				if (i != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.rl_ten_boxJ:
			alert = false;
			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				btnConfirm.setVisibility(View.GONE);

			}
			if (miniImg == true) {

			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
				btnConfirm.setVisibility(View.GONE);
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null) 
		if (fromWhere!=null)
				{
				blink = false;
				miniImg = false;
				vibrat = false;
				sound = false;
				tmpIV.setVisibility(View.GONE);
				tvtmpTime.setVisibility(View.GONE);
				Constant.flag = true;
				alertMsg.show();
			}

			else {
				cell_pos = 9;
				if (j != -1)
					task = "show";
				Call_actvity();
			}
			break;
		case R.id.btn_boxten_confirm:
			alert = false;
			rlCellA.setClickable(true);
			rlCellB.setClickable(true);
			rlCellC.setClickable(true);
			rlCellD.setClickable(true);
			rlCellE.setClickable(true);
			rlCellF.setClickable(true);
			rlCellG.setClickable(true);
			rlCellH.setClickable(true);
			rlCellI.setClickable(true);
			rlCellJ.setClickable(true);

			if (blink == true) {
				anim.cancel();
				tmpRl.clearAnimation();
				tmpRl.setBackgroundColor(Color.TRANSPARENT);
			}
			if (miniImg == true) {
				tmpIV.setVisibility(View.GONE);
			}
			if (vibrat == true) {
				CommonMethod.vibrator.cancel();
			}

			if (sound == true) {
//				CommonMethod.player.stop();
				stopMediaPlayer();
			}
			if (mTts != null)
				if (mTts.isSpeaking()) {
					mTts.stop();
					mTts.shutdown();
				}
//			if (blink == true || miniImg == true || vibrat == true
//					|| sound == true||fromWhere!=null)
			if (fromWhere!=null)
			{
				btnConfirm.setVisibility(View.GONE);
				blink = false;
				miniImg = false;
				vibrat = false;
				Constant.flag = true;
				sound = false;
				alertMsg.show();
			} else {
				// do nothing.
			}

			tvtmpTime.setVisibility(View.GONE);
			break;
		}
	}

	public void ReportDetails() {
		DateFormat df = new android.text.format.DateFormat();
		String date = df.format("dd/MM/yyyy", new Date()).toString();
		String time = df.format("kk:mm:ss", new Date()).toString();
		String dosage_taken = isConfirm == true ? "Y" : "N";
		int qty = 1;
		try {
			qty = cell.intDos_Mgt;
		} catch (Exception e) {
		}
		
		int lid = loginid;
		User_Model user = db.getSinglePatientForId(userid).get(0);
		String username = user.name + " " + user.surname;
		String med = MedName;

		// asfas

		Log.i("Login Id", "" + lid);
		Log.i("User name", username);
		Log.i("Med Name", med);

		Log.i("Dosage Taken", dosage_taken);
		Log.i("Date Time", date + " " + time);

		db.insertReport(lid, username, med, date, time, dosage_taken, qty);
		
//		fromWhere = null ;
		
		SP.edit().putString("BoxTenPid", pendingInterntID).commit();
		 
		finish();
		
//		Intent intent  = new Intent(BoxTenActivity.this, MainActivity.class) ;
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK) ;
//		if (Build.VERSION.SDK_INT >= 11 )//11 for Honeycomb
//	        intent.addFlags(0x8000);
//		startActivity(intent) ;
		
		
	}

	public void Call_actvity() {
		intent = new Intent(BoxTenActivity.this,
				CellManage_AddShowActivity.class);
		intent.putExtra("CellID", cell_pos);
		intent.putExtra("BoxID", boxid);
		intent.putExtra("UserID", userid);
		intent.putExtra("LoginID", loginid);
		intent.putExtra("Task", task);
		startActivity(intent);
//		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			CommonMethod.vibrator.cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}
//		CommonMethod.releaseSoundPlayer();
//		Onpause = true ;
		
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		
		counter=1 ;
		mediaPlayer = MediaPlayer.create(context,
					Constant.Sound[alarmSound]);

		if (vibrat == true) {
			CommonMethod.vibration(this);
		} else {
			try {
				CommonMethod.vibrator.cancel();
			} catch (Exception e) {
			}
		}
		
		task = "Add"; 
		cellinfolist = db.getCellInfoForBox(loginid, userid, boxid, -1);
				a = -1;
				b = -1;
				c = -1;
				d = -1;
				e = -1;
				f = -1;
				g = -1;
				h = -1;
				i = -1;
				j = -1;
				
		tvCellA.setText("A/1");
		tvCellB.setText("B/2");
		tvCellC.setText("C/3");
		tvCellD.setText("D/4");
		tvCellE.setText("E/5");
		tvCellF.setText("F/6");
		tvCellG.setText("G/7");
		tvCellH.setText("H/8");
		tvCellI.setText("I/9");
		tvCellJ.setText("J/10");

		tvTimeA.setText("");
		tvTimeB.setText("");
		tvTimeC.setText("");
		tvTimeD.setText("");
		tvTimeE.setText("");
		tvTimeF.setText("");
		tvTimeG.setText("");
		tvTimeH.setText("");
		tvTimeI.setText("");
		tvTimeJ.setText("");
		
		if (cellinfolist.size() > 0) {
			for (int i = 0; i < cellinfolist.size(); i++) {
				Log.i("Cellid", "--------------" + cellinfolist.get(i).cellid);
				switch (cellinfolist.get(i).cellid) {
				case 0:
					tvCellA.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 0);
						if (tmp.size() > 0) {
							tvTimeA.setText(tmp.get(0).strTime);
							tvTimeA.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					a = i;
					break;
				case 1:
					tvCellB.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 1);
						if (tmp.size() > 0) {
							tvTimeB.setText(tmp.get(0).strTime);
							tvTimeB.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					b = i;
					break;
				case 2:
					tvCellC.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 2);
						if (tmp.size() > 0) {
							tvTimeC.setText(tmp.get(0).strTime);
							tvTimeC.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					c = i;
					break;
				case 3:
					tvCellD.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 3);
						if (tmp.size() > 0) {
							tvTimeD.setText(tmp.get(0).strTime);
							tvTimeD.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					d = i;
					break;
				case 4:
					tvCellE.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 4);
						if (tmp.size() > 0) {
							tvTimeE.setText(tmp.get(0).strTime);
							tvTimeE.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					e = i;
					break;
				case 5:
					tvCellF.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 5);
						if (tmp.size() > 0) {
							tvTimeF.setText(tmp.get(0).strTime);
							tvTimeF.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					f = i;
					break;
				case 6:
					tvCellG.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 6);
						if (tmp.size() > 0) {
							tvTimeG.setText(tmp.get(0).strTime);
							tvTimeG.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					g = i;
					break;
				case 7:
					tvCellH.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 7);
						if (tmp.size() > 0) {
							tvTimeH.setText(tmp.get(0).strTime);
							tvTimeH.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					h = i;
					break;
				case 8:
					tvCellI.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 8);
						if (tmp.size() > 0) {
							tvTimeI.setText(tmp.get(0).strTime);
							tvTimeI.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					this.i = i;
					break;
				case 9:
					tvCellJ.setText(""
							+ db.getMedical(cellinfolist.get(i).medid).get(0).nm);
					try {
						ArrayList<Notification_Model> tmp = db
								.getCellNotification(loginid, userid, boxid, 9);
						if (tmp.size() > 0) {
							tvTimeJ.setText(tmp.get(0).strTime);
							tvTimeJ.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
					}
					picid = cellinfolist.get(i).picid;
					j = i;
					break;

				}
			}
		}
		
		cellinfolist = db.getCellInfoForBox(loginid, userid, boxid, -1);
		
		if (cell_pos != -1 && stop==false) {
//			Toast.makeText(getApplicationContext(), getString(R.string.tapToast), Toast.LENGTH_LONG).show();
			isAlarm = true;
			ArrayList<CellInfo_Model> arrCell = db.getCellInfoForBox(loginid,
					userid, boxid, cell_pos);
            mediaPlayer.create(getApplicationContext(), Constant.Sound[alarmSound]) ;
            mediaPlayer.setVolume(100, 100) ;

            
			try {
				CellInfo_Model cell = arrCell.get(0);
				blink = cell.blBlink == 1 ? true : false;
				miniImg = cell.blMini == 1 ? true : false;
				vibrat = cell.blVibrant == 1 ? true : false;
				alert = cell.blAlert == 1 ? true : false;
				int color = cell.intBg;
				final int limit = cell.intBuzz;

				switch (color) {
				case 1:
					setColor(Color.RED);
					break;
				case 2:
					setColor(Color.YELLOW);
					break;
				case 3:
					setColor(Color.GREEN);
					break;
				case 4:
					setColor(Color.rgb(255, 200, 0));
					break;
				case 5:
					setColor(Color.rgb(198, 195, 181));
					break;
				case 6:
					setColor(Color.WHITE);
					break;
				}

				// show alarm time
				tvtmpTime.setVisibility(View.VISIBLE);
				sdf = new SimpleDateFormat("HH:mm");
				String currentDateandTime = sdf.format(new Date(System
						.currentTimeMillis()));
				System.out.println("current time" + currentDateandTime);
				tvtmpTime.setText(currentDateandTime);

				Log.i("TAG===",
						"loginid, userid, boxid, cell_pos,blink,minImg,vibrat,alert,color,limit ===== \n"
								+ loginid
								+ " "
								+ userid
								+ " "
								+ boxid
								+ " "
								+ cell_pos
								+ " "
								+ blink
								+ " "
								+ miniImg
								+ " "
								+ vibrat
								+ " "
								+ alert
								+ " "
								+ color
								+ " "
								+ limit);

				sound = true;

				if (WaytoStop==1)
					Toast.makeText(getApplicationContext(),
							getString(R.string.tapToast), Toast.LENGTH_LONG)
							.show();
				else
					Toast.makeText(getApplicationContext(),
							getString(R.string.tapCellToast), Toast.LENGTH_LONG)
							.show();
				
				
				// check whether blink,miniImg,alert,vibrate are true or
				// false...

				if (WaytoStop == 1 || WaytoStop == 0) {
					btnConfirm.setVisibility(View.VISIBLE);
					rlCellA.setClickable(false);
					rlCellB.setClickable(false);
					rlCellC.setClickable(false);
					rlCellD.setClickable(false);
					rlCellE.setClickable(false);
					rlCellF.setClickable(false);
					rlCellG.setClickable(false);
					rlCellH.setClickable(false);
					rlCellI.setClickable(false);
					rlCellJ.setClickable(false);
				}

				// blinking cell
				if (blink == true) {
					tmpRl.setAnimation(anim);
					System.out.println("Working~~~~~~1");
				}
				// to show to not mini Image.
				if (miniImg == true) {
					System.out.println("Working~~~~~~2");
					tmpIV.setVisibility(View.VISIBLE);
					picmodel = db.getPicture(picid);
					Bitmap bitmap = GlobalMethods.decodeFile(picmodel.path);
					System.out.println("wint three box cell~~~~~~~~~~~~"
							+ picmodel.path);
					if (bitmap == null) {
						tmpIV.setVisibility(View.GONE);
						// tmpIV.setBackgroundResource(R.drawable.add_photo);
					} else {
						tmpIV.setImageBitmap(bitmap);
					}
				}
				// To vibrate or not.
				if (vibrat == true) {
					System.out.println("Working~~~~~~3");
					CommonMethod.vibration(this);
				}

				if (sound == true && alert == true) {
					System.out.println("Both~~~~~~~");
//					CommonMethod.SoundPlayer(this, Constant.Sound[alarmSound]);
					startMediaplayer();

					mediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {
									// /will use count and loop as per number of
									// repetition chosen.

									Intent checkIntent = new Intent();
									checkIntent
											.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
									startActivityForResult(checkIntent,
											MY_DATA_CHECK_CODE);

									handler.postDelayed(new Runnable() {

										public void run() {
											if (counter >= limit) {
//												CommonMethod.player.stop();
												stopMediaPlayer();
											} else {
//												CommonMethod.player.start();
												startMediaplayer();}
											counter++;
										}
									}, 3000);
								}
							});

				}
				if (sound == true && alert == false) {
					System.out.println("sound true alert false~~~~~~~");
//					CommonMethod.SoundPlayer(this, Constant.Sound[alarmSound]);
					startMediaplayer();
					mediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {
									// /will use count and loop as per number of
									// repetition chosen.
									handler.postDelayed(new Runnable() {

										public void run() {
											if (counter >= limit) {
//												CommonMethod.player.stop();
												stopMediaPlayer();
											} else {
												startMediaplayer();
											}
											counter++;
										}
									}, 3000);
								}
							});
				}
				if (sound == false && alert == true) {
					System.out.println("sound false alert true~~~~~~~");
					Intent checkIntent = new Intent();
					checkIntent
							.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
					startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

				} else {
					System.out.println("~~~~~~~~~~~~do nothing~~~~~~~");
				}
			} catch (Exception e) {

			}
		}
		
	}

	
	private void  launchHomeActivity(){
		
	    Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
}
	private  void unlockScren(){
		Window wind;
		wind = this.getWindow();
		wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
		wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isAlarm ) {
				alertMsg.show();
				Toast.makeText(BoxTenActivity.this,
						getResources().getString(R.string.stop_alarm_msg),
						Toast.LENGTH_LONG).show();
//				
//				System.out.println("frist loop~~~~");
//
//				if (Constant.flag == true) {
//					boolean finish = true;
//					Intent intent = new Intent(BoxTenActivity.this,
//							MainActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//					intent.putExtra("Finish", finish);
//					startActivity(intent);
//					finish();
//					Constant.flag = false;
//					System.out.println("second loop~~~~");
//				} else {
//					alertMsg.show();
//					Toast.makeText(BoxTenActivity.this,
//							getResources().getString(R.string.stop_alarm_msg),
//							Toast.LENGTH_LONG).show();
//				}
			}
			else
			{
				boolean finish = true;
				Intent intent = new Intent(BoxTenActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("Finish", finish);
				startActivity(intent);
				finish();
				Constant.flag = false;
				System.out.println("second loop~~~~");
				
			}
		} 
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public void onClick(View v) {
		
	}

}
