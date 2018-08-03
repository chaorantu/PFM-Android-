package com.example.pfm;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Expense extends Activity {

	    Spinner type;
	    TextView date;
	    EditText num;
	    EditText price;
	    EditText detail;    
	    Button select;
	    Button ok;
	    Button cancel;
	    int year,day,month;
	    String typename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense);
		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		Cursor c=db.rawQuery("select TypeName from PfType ", null);
		String [] a=new String[c.getCount()];
		int i=0;
		while(c.moveToNext())
		{
			a[i]=c.getString(0);
			i++;
		}
		 type = (Spinner) findViewById(R.id.spinner1); 
            
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	        android.R.layout.simple_spinner_item, a); 
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	        type.setAdapter(adapter);
	        type.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                        int position, long arg3) {
                	SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
                	position++;
              		Cursor rec=db.rawQuery("select TypePrice from PfType where rowid=?",new String[]{""+position+""});
              		 while(rec.moveToNext())
         			{
            			 price.setText(rec.getString(0));
         			}
                	
                }

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
            });
	        date=(TextView)this.findViewById(R.id.textview2);
	        num=(EditText)this.findViewById(R.id.edittext1);
	        price=(EditText)this.findViewById(R.id.edittext3);
	        detail=(EditText)this.findViewById(R.id.edittext2);
	        select=(Button)this.findViewById(R.id.button1);
	        ok=(Button)this.findViewById(R.id.button2);
	        cancel=(Button)this.findViewById(R.id.button3);
	        ok.setOnClickListener(new Add0());
	        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
	        Date mydate=new Date(); //��ȡ��ǰ����Date����
	        mycalendar.setTime(mydate);////ΪCalendar��������ʱ��Ϊ��ǰ����
	        
	        year=mycalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
	        month=mycalendar.get(Calendar.MONTH);//��ȡCalendar�����е���
	        day=mycalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
	        date.setText(""+year+"-"+(month+1)+"-"+day); //��ʾ��ǰ��������
	        select.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//showDialog(10);
					 DatePickerDialog dpd=new DatePickerDialog(Expense.this,Datelistener,year,month,day);
		             dpd.setTitle("ѡ������");
		             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", dpd);
					 dpd.show();
				}
	        });
	}
	private class Add0 implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(num.getText().toString().equals(""))
			{
				Toast.makeText(Expense.this, "����д����!", Toast.LENGTH_LONG).show();
			}
			else if(detail.getText().toString().equals(""))
			{
				Toast.makeText(Expense.this, "����д��ϸ��ע!", Toast.LENGTH_LONG).show();
			}
			else{
		    typename=type.getSelectedItem().toString();
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
			Float Money=Integer.parseInt(num.getText().toString())*Float.parseFloat(price.getText().toString());
			db.execSQL("INSERT INTO Finance VALUES('"+typename+"','����','"+date.getText()+"','"+Integer.parseInt(num.getText().toString())+"','"+Float.parseFloat(price.getText().toString())+"','"+Money+"','"+detail.getText()+"')");
			Toast.makeText(Expense.this, "��ӳɹ�!", Toast.LENGTH_LONG).show();
			}
		}}
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
	            date.setText(""+year+"-"+(month+1)+"-"+day);
	        }
	    };
	
}
