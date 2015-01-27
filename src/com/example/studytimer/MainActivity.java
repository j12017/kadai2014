package com.example.studytimer;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {
	public int flag = 0;
	public int newFlag = 0;
	public int minute = 0;
	public Timer timer = new Timer();
	
	//コンポーネント
	public TextView TextTime;
	public TextView TextTitle;
	public TextView TextResult;
	public EditText TextUser;
	public EditText TextPass;
	public Button Swhichbtn;
	public Button Clearbtn;
	public Button Postbtn;
	public Spinner spinner;
	
	public AlertDialog.Builder alertDialog;
	private Handler mHandler = new Handler();
	
	public int nowTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//ID取得
		TextTime = (TextView)findViewById(R.id.Timer);
		TextTitle = (TextView)findViewById(R.id.Title);
		TextResult = (TextView)findViewById(R.id.Result);
		TextUser = (EditText)findViewById(R.id.LoginId);
		TextPass = (EditText)findViewById(R.id.LoginPass);
		Swhichbtn = (Button)findViewById(R.id.SwhichButton);
		Clearbtn = (Button)findViewById(R.id.ClearButton);
		Postbtn = (Button)findViewById(R.id.Add);
		
		//別スレッド
		alertDialog = new AlertDialog.Builder(this);

		//時間関連の処理
		
		
		
				
		//勉強の内容を選択するよ
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		adapter.add("産技");
		adapter.add("帝京");
		adapter.add("資格");
		adapter.add("その他");
		spinner = (Spinner)findViewById(R.id.WhatToStudy);
		spinner.setAdapter(adapter);
		
//		timer.schedule(mc, 1000);

		
		//スウィッチボタンを押した時の処理
		Swhichbtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
			
				
				
				
				if(flag == 0){
					timer = new Timer();
					flag = 1;


					timer.schedule(new TimeCount(), 1000, 1000);
					
				}
				else{
					flag = 0;
					timer.cancel();
					
				}
//				alertDialog.setTitle(flag+"");
				
				if(newFlag == 0){
					newFlag = 1;
				}
//			    alertDialog.create();
//			    alertDialog.show();
			}
						
		});
		
		//クリアボタンを押した時の処理
		Clearbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(flag == 1){
					flag = 0;
					timer.cancel();
					nowTime = 0;
					TextTime.setText("0時間0分0秒");
				}
				else{
					nowTime = 0;
					TextTime.setText("0時間0分0秒");
				}
				
			}});
		
		//送信ボタンを押した時の処理
		Postbtn.setOnClickListener(new OnClickListener(){


			
			@Override
			public void onClick(View v) {
				if(flag == 1){
					flag = 0;
					timer.cancel();
					
				}
				if(nowTime != 0){
					
										
					// TODO 自動生成されたメソッド・スタブ
					RequestParams params = new RequestParams();
					//学籍番号
					String login_id = TextUser.getText().toString();
					params.put("login_id", login_id);
					//パスワード
					String password = TextPass.getText().toString();
					params.put("password", password);
					
					//何の勉強をしたか
					String category = (String)spinner.getSelectedItem();
					params.put("category",category);
					
					//勉強時間
					String studytime = nowTime + "";
					params.put("studytime", studytime);
					
					AsyncHttpClient client = new AsyncHttpClient();

					client.post("http://j12017.sangi01.net/post.php", params, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						// TODO 自動生成されたメソッド・スタブ
						TextResult.setText(Arrays.toString(arg2));
						TextResult.setText(arg1+"");
						
					}
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO 自動生成されたメソッド・スタブ
						System.out.println(arg0);
						try {
							String result = new String(arg2,"UTF8");
							TextResult.setText(result);
							if(result.equals("データの送信に成功しました。")){
								nowTime = 0;
								TextTime.setText("0時間0分0秒");
							}

						} catch (UnsupportedEncodingException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}
					});

				}


				
			}
			
			


				
			});
		

	}

	
	public class TimeCount extends TimerTask {
		
		
		
		@Override
		public void run() {
			 mHandler.post( new Runnable() {

				@Override
				public void run() {
					int setS = 0;
					int setM = 0;
					int setH = 0;
					String setT;
					
					
					
					nowTime = nowTime + 1;
					setS = nowTime;
					if(setS / 60 > 0){
						setM = setS / 60;
						setS = setS - 60 * (setS / 60);
					}
					if(setM / 60 > 0){
						setH = setM / 60;
						setM = setM - 60 * (setM / 60);
					}
					
					setT = setH + "時間" + setM + "分" + setS + "秒";				
					
					//minute++;
					//TextTime.setText(minute+"分");
					TextTime.setText(setT);
					//nowTime = 0;
				} 
				 
			 });

			}
		}
		

		
	}




