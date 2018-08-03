package com.example.pfm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Finance extends Activity {

	ListView listv;
	ListView listv0;
	Button select1;
	Button select2;
	Button start;
	TextView detail;
	int year,day,month;
	TextView startdate;
	TextView enddate;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finance);
		detail=(TextView)this.findViewById(R.id.textView2);
		select1=(Button)this.findViewById(R.id.button1);
		select2=(Button)this.findViewById(R.id.button2);
		start=(Button)this.findViewById(R.id.button3);
		startdate=(TextView)this.findViewById(R.id.text1);
		enddate=(TextView)this.findViewById(R.id.text2);
		start.setOnClickListener(new Start());
		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		 Cursor rec=db.rawQuery("select * from Finance", null);
		 
		 List<Map<String,Object>>items=new ArrayList<Map<String,Object>>();
		 Map<String, Object> title=new HashMap<String, Object>();
		 title.put("序号","序号");
		 title.put("车型","车型");
		 title.put("来源", "来源");
		 title.put("数量", "数量");
		 title.put("单价","单价");
		 title.put("总额","总额");
		 title.put("日期", "日期");
		 items.add(title);
		
		  int i=0;
		 while(rec.moveToNext())
				{
			        
					String TypeName=rec.getString(0);
					String AgName=rec.getString(1);
					String Date=rec.getString(2);
					String NUm=rec.getString(3);
					String Price=rec.getString(4);
					String Money=rec.getString(5);
		            Map<String, Object> map=new HashMap<String, Object>();
		            map.put("序号",""+i+"");
		            map.put("车型",""+TypeName+"");
		            map.put("来源", ""+AgName+"");
		            map.put("数量", ""+NUm+"");
		            map.put("单价",""+Price+"");
		            map.put("总额",""+Money+"");
		            map.put("日期", ""+Date+"");
		            items.add(map);
			        i++;
				}
		 listv=(ListView)findViewById(R.id.listView1);
		 SimpleAdapter sa=new SimpleAdapter(
	               Finance.this, //上下文环境
	               items,     //数据源
	               R.layout.list3,  //内容布局   在这里调用
	               new String[]{"序号","车型","来源","数量","单价","总额","日期"},   //数据源的arrayName
	               new int[]{R.id.Text1,R.id.Text2,R.id.Text3,R.id.Text4,R.id.Text5,R.id.Text6,R.id.Text7}  //i装载数据的控件（这个就是的吧是什么 list里的控件  嗯 哦）
	       );
		 listv.setAdapter(sa);
		 listv.setOnItemClickListener(new ItemClickListener());
		 Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
	        Date mydate=new Date(); //获取当前日期Date对象
	        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
	        
	        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
	        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
	        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
	        startdate.setText(""+year+"-"+(month+1)+"-"+day); //显示当前的年月日
	        enddate.setText(""+year+"-"+(month+1)+"-"+day); //显示当前的年月日
	        select1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//showDialog(10);
					 DatePickerDialog dpd=new DatePickerDialog(Finance.this,Datelistener,year,month,day);
		             dpd.setTitle("选择日期");
		             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "确定", dpd);
					 dpd.show();
				}
	        });
	        select2.setOnClickListener(new show());
	        select1.setOnClickListener(new show());
		 
	}
	
	private class Start implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
			 List<Map<String, Object>>items0=new ArrayList<Map<String,Object>>();
			 Map<String,Object> title0=new HashMap<String, Object>();
			 title0.put("类型","类型");
			 title0.put("总金额","总金额");
			 title0.put("开始时间", "开始时间");
			 title0.put("结束时间", "结束时间");
			 items0.add(title0);
			 float money1=0;
			 float money2=0;
			 Cursor rec0=db.rawQuery("select Type,Money from Finance where Date<=? and Date>=? ",new String[]{""+enddate.getText().toString()+"",""+startdate.getText().toString()+""});
			 while(rec0.moveToNext())
			 {
				  if(rec0.getString(0).equals("厂家"))
				  {
					  money2=money2+Float.parseFloat(rec0.getString(1));
				  }
				  else
				  {
					  money1=money1+Float.parseFloat(rec0.getString(1));
				  }
			 }
			 Toast.makeText(Finance.this, ""+money1+"", Toast.LENGTH_LONG).show();
			 Map<String,Object> map1=new HashMap<String, Object>();
			 map1.put("类型","支出");
			 map1.put("总金额",""+money2+"");
			 map1.put("开始时间", ""+startdate.getText()+"");
			 map1.put("结束时间", ""+enddate.getText()+"");
			 items0.add(map1);
			 Map<String,Object> map2=new HashMap<String, Object>();
			 map2.put("类型","收入");
			 map2.put("总金额",""+money1+"");
			 map2.put("开始时间", ""+startdate.getText()+"");
			 map2.put("结束时间", ""+enddate.getText()+"");
			 items0.add(map2);
			 Map<String,Object> map3=new HashMap<String, Object>();
			 map3.put("类型","总金额");
			 map3.put("总金额",""+(money1-money2)+"");
			 map3.put("开始时间", ""+startdate.getText()+"");
			 map3.put("结束时间", ""+enddate.getText()+"");
			 items0.add(map3);
			 SimpleAdapter sa=new SimpleAdapter(
		               Finance.this, //上下文环境
		               items0,     //数据源
		               R.layout.list2,  //内容布局   在这里调用
		               new String[]{"类型","总金额","开始时间","结束时间"},   //数据源的arrayName
		               new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4}  //i装载数据的控件（这个就是的吧是什么 list里的控件  嗯 哦）
		       );      
			 listv0=(ListView)findViewById(R.id.listView2);
			 listv0.setAdapter(sa);
		}}
	private class show implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			id=v.getId();
			 DatePickerDialog dpd=new DatePickerDialog(Finance.this,Datelistener,year,month,day);
             dpd.setTitle("选择日期");
             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "确定", dpd);
			 dpd.show();
		}
		
	}
	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            
            
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            if(R.id.button1==id)
            {startdate.setText(""+year+"-"+(month+1)+"-"+day);}
            else if(R.id.button2==id)
            {enddate.setText(""+year+"-"+(month+1)+"-"+day);}
        }
    };
	private class ItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			HashMap<String,Object> map=(HashMap<String,Object>)listv.getItemAtPosition(position);   
            int num=Integer.parseInt(map.get("序号").toString())+2;
            SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
   		 Cursor rec=db.rawQuery("select Detail from Finance where rowid=?",new String[]{""+num+""});
   		 while(rec.moveToNext())
			{
   			 detail.setText(rec.getString(0));
			}
		}
	   
	}
}
