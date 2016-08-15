package com.example.yibu;

import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private static String murl="http://www.imooc.com/api/teacher?type=4&num=30";
	ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list);
        new Myasynctask().execute(murl);
    }
    public List<Mybean> getjsondata(String url){
    	List<Mybean> mybeans = new ArrayList<Mybean>();
    	
    	
    	JSONObject jsonObject;
    	Mybean mybean;
		try {
			String json = readstream(new URL(url).openStream());
			
			JSONObject mJsonObject = new JSONObject(json);
			JSONArray mJsonArray = mJsonObject.getJSONArray("data");
			for(int i=0;i<mJsonArray.length();i++){
				jsonObject = mJsonArray.getJSONObject(i);
				mybean = new Mybean();
				mybean.setContent(jsonObject.getString("description"));
				mybean.setTittle(jsonObject.getString("name"));
				mybean.setImageurl(jsonObject.getString("picSmall"));
				mybeans.add(mybean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return mybeans;
    }

   public String readstream(InputStream is){
	   
	   
	   String result = "";
	   
			try {
				
				InputStreamReader iis= new InputStreamReader(is);
				BufferedReader bis = new BufferedReader(iis);
				String line;
				while((line=bis.readLine())!=null){
					result += line;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return result;
   }
    class Myasynctask extends AsyncTask<String, Void, List<Mybean>>{

		@Override
		protected List<Mybean> doInBackground(String... params) {
			
			return getjsondata(params[0]);
		}
    	@Override
    	protected void onPostExecute(List<Mybean> list) {
    		NewAdapter adapter =new NewAdapter(MainActivity.this,list,mListView);
    		
    		mListView.setAdapter(adapter);
    	}
    }
}
