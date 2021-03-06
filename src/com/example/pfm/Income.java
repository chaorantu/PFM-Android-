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

public class Income extends Activity {

    Spinner type;
    Spinner type0;
    TextView date;
    EditText num;
    EditText price;
    EditText detail;    
    Button select;
    Button ok;
    Button cancel;
    int year,day,month;
    String typename;
    String agname;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_income);
	SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
	Cursor c=db.rawQuery("select TypeName from PfType ", null);
	Cursor c0=db.rawQuery("select TypeName from AgType ", null);
	String [] a=new String[c.getCount()];
	String [] b=new String[c0.getCount()];
	int i=0;
	int j=0;
	while(c.moveToNext())
	{
		a[i]=c.getString(0);
		i++;
	}
	while(c0.moveToNext())
	{
		b[j]=c0 .getString(0);
		j++;
	}
	 type = (Spinner) findViewById(R.id.spinner1); 
	 type0 = (Spinner) findViewById(R.id.spinner0); 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item, a); 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        type.setAdapter(adapter);
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, 
        android.R.layout.simple_spinner_item, b); 
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        type0.setAdapter(adapter0);
        date=(TextView)this.findViewById(R.id.textview2);
        num=(EditText)this.findViewById(R.id.edittext1);
        price=(EditText)this.findViewById(R.id.edittext3);
        detail=(EditText)this.findViewById(R.id.edittext2);
        select=(Button)this.findViewById(R.id.button1);
        ok=(Button)this.findViewById(R.id.button2);
        cancel=(Button)this.findViewById(R.id.button3);
        ok.setOnClickListener(new Add0());
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
        
        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        date.setText(""+year+"-"+(month+1)+"-"+day); //显示当前的年月日
        select.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//showDialog(10);
				 DatePickerDialog dpd=new DatePickerDialog(Income.this,Datelistener,year,month,day);
	             dpd.setTitle("选择日期");
	             dpd.setButton(DialogInterface.BUTTON_POSITIVE, "确定", dpd);
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
			Toast.makeText(Income.this, "请填写数量!", Toast.LENGTH_LONG).show();
		}
		else if(detail.getText().toString().equals(""))
		{
			Toast.makeText(Income.this, "请填写详细备注!", Toast.LENGTH_LONG).show();
		}
		else{
	    typename=type.getSelectedItem().toString();
	    agname=type0.getSelectedItem().toString();
		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		Float Money=Integer.parseInt(num.getText().toString())*Float.parseFloat(price.getText().toString());
		db.execSQL("INSERT INTO Finance VALUES('"+typename+"','"+agname+"','"+date.getText()+"','"+Integer.parseInt(num.getText().toString())+"','"+Float.parseFloat(price.getText().toString())+"','"+Money+"','"+detail.getText()+"')");
		Toast.makeText(Income.this, "添加成功!", Toast.LENGTH_LONG).show();
		}
	}}
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
            date.setText(""+year+"-"+(month+1)+"-"+day);
        }
    };

}
