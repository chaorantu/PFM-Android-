package com.example.pfm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Management extends Activity implements android.view.GestureDetector.OnGestureListener  {

	 final int RIGHT = 0;  
	 final int LEFT = 1;  
	    RelativeLayout m;
	    private GestureDetector gestureDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_management);
		m=(RelativeLayout)this.findViewById(R.id.main);
		GridView gridview=(GridView)findViewById(R.id.gridview);
		ArrayList<HashMap<String,Object>> icon=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map1=new HashMap<String, Object>();
		map1.put("Image",R.drawable.ic_tt);
		map1.put("Text","收入管理");
		icon.add(map1);
		HashMap<String, Object> map2=new HashMap<String, Object>();
		map2.put("Image", R.drawable.ic_tt);
		map2.put("Text", "支出管理");
		icon.add(map2);
		HashMap<String, Object> map3=new HashMap<String, Object>();
		map3.put("Image", R.drawable.ic_tt2);
		map3.put("Text", "类型管理");
		icon.add(map3);
		HashMap<String, Object> map4=new HashMap<String, Object>();
		map4.put("Image", R.drawable.ic_tt3);
		map4.put("Text", "财务统计");
		icon.add(map4);
		HashMap<String, Object> map5=new HashMap<String, Object>();
		map5.put("Image", R.drawable.ic_tt1);
		map5.put("Text", "财务记录");
		icon.add(map5);
		HashMap<String, Object> map6=new HashMap<String, Object>();
		map6.put("Image", R.drawable.ic_tt1);
		map6.put("Text","经销商");
		icon.add(map6);
		SimpleAdapter Icon=new SimpleAdapter(
				Management.this,
				icon,
				R.layout.menu,
				new String[]{"Image","Text"},
				new int[]{R.id.imageView0,R.id.textView}
				);
		gridview.setAdapter(Icon);
		gridview.setOnItemClickListener(new ItemClickListener());  
		gestureDetector = new GestureDetector(this);  

	}
	public boolean onTouchEvent(MotionEvent event) {  
	        return gestureDetector.onTouchEvent(event);  
	    }  
	  
	    public void doResult(int action) {  
	  
	        switch (action) {  
	        case RIGHT:  
	        	Toast.makeText(Management.this, "右移", Toast.LENGTH_LONG).show();
	        	m.setBackgroundColor(R.drawable.abc_ic_cab_done_holo_dark);
	            break;  
	  
	        case LEFT:  
	        	Toast.makeText(Management.this, "左移", Toast.LENGTH_LONG).show();
	            break;  
	  
	        }  
	    }  

	class  ItemClickListener implements OnItemClickListener  
	  {  
		
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	    
	     if(position==0)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Income.class);
		     startActivity(intent);
	     }
	     if(position==1)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Expense.class);
		     startActivity(intent);
	     }
	     if(position==2)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Type.class);
		     startActivity(intent);
	     }
	     if(position==3)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Finance.class);
		     startActivity(intent);
	     }
	     if(position==4)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Record.class);
		     startActivity(intent);
	     } 
	     if(position==5)
	     {
	    	 Intent intent = new Intent();
	         intent.setClass(Management.this,Agency.class);
		     startActivity(intent);
	     }
		// TODO Auto-generated method stub
		
	}  
	  }
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		 float x = e2.getX() - e1.getX();  
         float y = e2.getY() - e1.getY();  

         if (x > 0) {  
             doResult(RIGHT);  
         } else if (x < 0) {  
             doResult(LEFT);  
         }  
         return true;  
	}
	
}
