package com.example.bomb;

import cn.bmob.push.PushConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Myrecevier extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
			String msg = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

}
