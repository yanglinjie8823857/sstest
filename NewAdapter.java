package com.example.yibu;

import java.util.List;
import java.util.zip.Inflater;

import org.apache.http.client.methods.AbortableHttpRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewAdapter extends BaseAdapter implements OnScrollListener {
	List<Mybean> mlist ;
	LayoutInflater mInflater;
	ImageLoader imageLoader;
	public static String[] URLS;
	int start,end;
	private boolean mFirstIn;
	public NewAdapter(Context context,List<Mybean> mdata,ListView listView){
		mInflater = LayoutInflater.from(context);
		mlist = mdata;
		imageLoader = new ImageLoader(listView);
		
		  URLS=new String[mdata.size()];
	        for (int i = 0; i < mdata.size(); i++) {
	            URLS[i] = mdata.get(i).getImageurl();
	        }
	        mFirstIn=true;
	        //注册滑动事件
	        listView.setOnScrollListener(this);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertview == null){
			viewHolder = new ViewHolder();
			convertview=mInflater.inflate(R.layout.item, null);
			viewHolder.imageView = (ImageView) convertview.findViewById(R.id.image);
			viewHolder.tittle =  (TextView) convertview.findViewById(R.id.tittle);
			viewHolder.content =  (TextView) convertview.findViewById(R.id.content);
			convertview.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertview.getTag();
		}
		viewHolder.imageView.setImageResource(R.drawable.ic_launcher);
		String url = mlist.get(position).getImageurl();
		viewHolder.imageView.setTag(url);
		imageLoader.showimagebyasynctask(viewHolder.imageView,url);
		viewHolder.tittle.setText(mlist.get(position).getTittle());
		viewHolder.content.setText(mlist.get(position).getContent());
		return convertview;
	}
	private class ViewHolder{
		ImageView imageView;
		TextView tittle;
		TextView content;
	}
	@Override
	public void onScroll(AbsListView view, int first, int count, int arg3) {
		// TODO Auto-generated method stub
		start = first;
		end = first + count;
		 if (mFirstIn&&count>0){
	            imageLoader.loadImages(start,end);
	            mFirstIn=false;
	}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollstate) {
		// TODO Auto-generated method stub
		  if (scrollstate == SCROLL_STATE_IDLE) {
	            //加载可见项
			  imageLoader.loadImages(start, end);
	        } else {
	            //停止任务
	        	imageLoader.cancelAllTask();
	}
  }
	
}
