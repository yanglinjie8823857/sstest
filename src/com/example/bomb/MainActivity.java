package com.example.bomb;


import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText editText1;
	EditText editText2;
	Button button;
	Button button1;
	Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        	Bmob.initialize(this, "12171615cb0ded9c07a1d397da4c6192");
        	BmobInstallation.getCurrentInstallation(MainActivity.this).save();
        	BmobPush.startWork(this, "12171615cb0ded9c07a1d397da4c6192");
        	editText1 = (EditText) findViewById(R.id.editText1);
        	editText2 = (EditText) findViewById(R.id.editText2);
        	button =(Button) findViewById(R.id.button1);
        	button1 =(Button) findViewById(R.id.button2);
        	button2 =(Button) findViewById(R.id.button3);
        	button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String name = editText1.getText().toString();
					String password = editText2.getText().toString();
					if(name.equals("")||password.equals("")){
						Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
						return;
					}
					Bean bean = new Bean();
					bean.setName(name);
					bean.setPassword(password);
					bean.save(MainActivity.this, new SaveListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
        	button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BmobQuery<Bean> query = new BmobQuery<Bean>();
					query.addWhereEqualTo("name", "ylj");
					query.findObjects(MainActivity.this, new FindListener<Bean>() {
						
						@Override
						public void onSuccess(List<Bean> beans) {
							// TODO Auto-generated method stub
							AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
							builder.setTitle("result");
							String str = "";
							for(Bean bean:beans){
								str += bean.getName()+":"+bean.getPassword()+"\n";
							}
							builder.setMessage(str);
							builder.create().show();
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
        	button2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BmobPushManager bmobPushManager = new BmobPushManager(MainActivity.this);
					bmobPushManager.pushMessageAll("test");
				}
			});
    }


   
    
}
