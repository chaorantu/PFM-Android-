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
		 title.put("���","���");
		 title.put("����","����");
		 title.put("��Դ", "��Դ");
		 title.put("����", "����");
		 title.put("����","����");
		 title.put("�ܶ�","�ܶ�");
		 title.put("����", "����");
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
		            map.put("���",""+i+"");
		            map.put("����",""+TypeName+"");
		            map.put("��Դ", ""+AgName+"");
		            map.put("����", ""+NUm+"");
		            map.put("����",""+Price+"");
		            map.put("�ܶ�",""+Money+"");
		            map.put("����", ""+Date+"");
		            items.add(map);
			        i++;
				}
		 listv=(ListView)findViewById(R.id.listView1);
		 SimpleAdapter sa=new SimpleAdapter(
	               Finance.this, //�����Ļ���
	               items,     //����Դ
	               R.layout.list3,  //���ݲ���   ���������
	               new String[]{"���","����","��Դ","����","����","�ܶ�","����"},   //����Դ��arrayName
	               new int[]{R.id.Text1,R.id.Text2,R.id.Text3,R.id.Text4,R.id.Text5,R.id.Text6,R.id.Text7}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
	       );
		 listv.setAdapter(sa);
		 listv.setOnItemClickListener(new ItemClickListener());
		 Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
	        Date mydate=new Date(); //��ȡ��ǰ����Date����
	        mycalendar.setTime(mydate);////ΪCalendar��������ʱ��Ϊ��ǰ����
	        
	        year=mycalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
	        month=mycalendar.get(Calendar.MONTH);//��ȡCalendar�����е���
	        day=mycalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
	        startdate.setText(""+year+"-"+(month+1)+"-"+day); //��ʾ��ǰ��������
	        enddate.setText(""+year+"-"+(month+1)+"-"+day); //��ʾ��ǰ��������
	        select1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//showDialog(10);
					 DatePickerDialog dpd=new DatePickerDialog(Finance.this,Datelistener,year,month,day);
		             dpd.setTitle("ѡ������");
		             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", dpd);
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
			 title0.put("����","����");
			 title0.put("�ܽ��","�ܽ��");
			 title0.put("��ʼʱ��", "��ʼʱ��");
			 title0.put("����ʱ��", "����ʱ��");
			 items0.add(title0);
			 float money1=0;
			 float money2=0;
			 Cursor rec0=db.rawQuery("select Type,Money from Finance where Date<=? and Date>=? ",new String[]{""+enddate.getText().toString()+"",""+startdate.getText().toString()+""});
			 while(rec0.moveToNext())
			 {
				  if(rec0.getString(0).equals("����"))
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
			 map1.put("����","֧��");
			 map1.put("�ܽ��",""+money2+"");
			 map1.put("��ʼʱ��", ""+startdate.getText()+"");
			 map1.put("����ʱ��", ""+enddate.getText()+"");
			 items0.add(map1);
			 Map<String,Object> map2=new HashMap<String, Object>();
			 map2.put("����","����");
			 map2.put("�ܽ��",""+money1+"");
			 map2.put("��ʼʱ��", ""+startdate.getText()+"");
			 map2.put("����ʱ��", ""+enddate.getText()+"");
			 items0.add(map2);
			 Map<String,Object> map3=new HashMap<String, Object>();
			 map3.put("����","�ܽ��");
			 map3.put("�ܽ��",""+(money1-money2)+"");
			 map3.put("��ʼʱ��", ""+startdate.getText()+"");
			 map3.put("����ʱ��", ""+enddate.getText()+"");
			 items0.add(map3);
			 SimpleAdapter sa=new SimpleAdapter(
		               Finance.this, //�����Ļ���
		               items0,     //����Դ
		               R.layout.list2,  //���ݲ���   ���������
		               new String[]{"����","�ܽ��","��ʼʱ��","����ʱ��"},   //����Դ��arrayName
		               new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
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
             dpd.setTitle("ѡ������");
             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", dpd);
			 dpd.show();
		}
		
	}
	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params��view�����¼����������
         * params��myyear����ǰѡ�����
         * params��monthOfYear����ǰѡ�����
         * params��dayOfMonth����ǰѡ�����
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            
            
            //�޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //��������
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
            int num=Integer.parseInt(map.get("���").toString())+2;
            SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
   		 Cursor rec=db.rawQuery("select Detail from Finance where rowid=?",new String[]{""+num+""});
   		 while(rec.moveToNext())
			{
   			 detail.setText(rec.getString(0));
			}
		}
	   
	}
}
