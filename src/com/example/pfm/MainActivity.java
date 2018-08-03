package com.example.pfm;


import android.R.id;
import android.R.string;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputBinding;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText UserName;
	EditText PassWord;
	Button ok;
	Button cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing);
		UserName=(EditText)this.findViewById(R.id.editText1);
		PassWord=(EditText)this.findViewById(R.id.editText2);
		ok=(Button)this.findViewById(R.id.button1);
		cancel=(Button)this.findViewById(R.id.button3);
		cancel.setOnClickListener(new Exit());
		ok.setOnClickListener(new Login());
		SharedPreferences setting = getSharedPreferences("", 0);  
		   Boolean user_first = setting.getBoolean("FIRST",true);  
		   if(user_first){//第一次   
		        setting.edit().putBoolean("FIRST", false).commit();    
		        SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		        db.execSQL("DROP TABLE IF EXISTS User");
		        db.execSQL("DROP TABLE IF EXISTS PfType");
		        db.execSQL("DROP TABLE IF EXISTS Finance");
		        db.execSQL("DROP TABLE IF EXISTS AgType");
	        	db.execSQL("CREATE TABLE User(UserName varchar(20) not null,Password varchar(30) not null)");
	        	db.execSQL("CREATE TABLE PfType(TypeName varchar(20) not null,TypePrice real not null)");
	        	db.execSQL("CREATE TABLE AgType(TypeName varchar(20) not null,Score real not null)");
	        	db.execSQL("CREATE TABLE Finance(TypeName varchar(20) not null,Type varchar(20) not null,Date varchar(20) not null,Num int(10) not null,Price real not null,Money real not null,Detail varchar(50) not null)");
	        	db.execSQL("INSERT INTO User VALUES('tcr','tcr1994')");
		        Toast.makeText(MainActivity.this, "数据库创建成功!", Toast.LENGTH_LONG).show();
		    }
		   else{  
		   }  
	}
	private class Exit implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		
	}
	private void Judge()
	{
		if(UserName.getText().toString().equals("")&&PassWord.getText().toString().equals(""))
		{
			Toast.makeText(MainActivity.this, "用户和密码不能为空!", Toast.LENGTH_LONG).show();
		}
		else if(UserName.getText().toString().equals(""))
		{
			Toast.makeText(MainActivity.this, "用户不能为空!", Toast.LENGTH_LONG).show();
		}
		else
		{Toast.makeText(MainActivity.this, "密码不能为空!", Toast.LENGTH_LONG).show();}
	}
	private class Login implements Button.OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(UserName.getText().toString().equals("")||PassWord.getText().toString().equals(""))
			{
			     Judge();
			}
			else
			{
		        	SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		        	String name=UserName.getText().toString();
		        	String password=PassWord.getText().toString();
		        	Cursor c=db.rawQuery("select * from User where UserName=? and Password=?" , new String[]{""+name+"",""+password+""});
		        	if(c.getCount()==0)
		        	{Toast.makeText(MainActivity.this, "用户密码错误!", Toast.LENGTH_LONG).show();}
		        	else
		        	{Toast.makeText(MainActivity.this, "用户"+name+"登陆成功！", Toast.LENGTH_LONG).show();
					//while(c.moveToNext())
					//{
						//String password=c.getString(c.getColumnIndex("Password"));
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,Management.class);
					startActivity(intent);
		        	}
			}
		}
		
	}
}
