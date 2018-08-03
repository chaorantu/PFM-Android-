package com.example.pfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Agency extends Activity {
	
	Button add;
	Button update;
	Button delete;
	EditText typename;
	ListView listv;
	Object Typename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agency);
		 add=(Button)this.findViewById(R.id.BtAdd);
		 update=(Button)this.findViewById(R.id.BtUpdate);
		 delete=(Button)this.findViewById(R.id.BTDelete);
		typename=(EditText)this.findViewById(R.id.editText1);
		add.setOnClickListener(new Add());
		update.setOnClickListener(new Update());
		delete.setOnClickListener(new Delete());
refresh();
	}
		
    private void refresh()
    {		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
	 Cursor rec=db.rawQuery("select * from AgType", null);
	 List<Map<String,Object>>items=new ArrayList<Map<String,Object>>();
	 Map<String, Object> title=new HashMap<String, Object>();
	 title.put("���","���");
	 title.put("������","������");
	 title.put("����","����");
	 items.add(title);
	 int i=0;
	 while(rec.moveToNext())
			{
		        
				String TypeName=rec.getString(0);
				String Score=rec.getString(1);
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("���",""+i+"");
	            map.put("������", ""+TypeName+"");
	            map.put("����", ""+Score+"");
	            items.add(map);
		        i++;
			}
	 SimpleAdapter sa=new SimpleAdapter(
               Agency.this, //�����Ļ���
               items,     //����Դ
               R.layout.list,  //���ݲ���   ���������
               new String[]{"���","������","����"},   //����Դ��arrayName
               new int[]{R.id.text1,R.id.text2,R.id.text3}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
       );      
	 listv=(ListView)findViewById(R.id.listView1);
	 listv.setAdapter(sa);  
	 listv.setOnItemClickListener(new ItemClickListener());
	 typename.setText("");
	 }
	private class ItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			HashMap<String,Object> map=(HashMap<String,Object>)listv.getItemAtPosition(position);   
            Typename=map.get("������");
            typename.setText(""+Typename.toString()+"");
		}
	   
	}
	private class Add implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		    if(typename.getText().toString().equals(""))
			{Toast.makeText(Agency.this, "�����̲���Ϊ�գ���", Toast.LENGTH_LONG).show();}
			else{db.execSQL("INSERT INTO AgType VALUES('"+typename.getText()+"','0')");
			Toast.makeText(Agency.this, ""+typename.getText()+"��ӳɹ���", Toast.LENGTH_LONG).show();
			refresh();
			}
		}
	}
	 private class Delete implements Button.OnClickListener
	 {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
			if(typename.getText().toString().equals(""))
			{Toast.makeText(Agency.this, "�����̲���Ϊ�գ���", Toast.LENGTH_LONG).show();}
			else 
			{
				Cursor c=db.rawQuery("select * from AgType where TypeName=?" , new String[]{""+typename.getText()+""});
	        	if(c.getCount()==0)
	        	{Toast.makeText(Agency.this,""+typename.getText()+"�����ڸ���Ŀ��",Toast.LENGTH_SHORT).show();
	        	refresh();}
	        	else
	        	{db.execSQL("DELETE FROM AgType where TypeName='"+typename.getText()+"'");
				Toast.makeText(Agency.this,""+typename.getText()+"ɾ���ɹ���",Toast.LENGTH_SHORT).show();
				refresh();}
			}
		}}
	 private class Update implements Button.OnClickListener
	 {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE, null);
		if(typename.getText().equals(""))
		{
			Toast.makeText(Agency.this, "�����̲���Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}
		else
		{
		    db.execSQL("UPDATE AgType SET TypeName='"+typename.getText()+"' where TypeName='"+Typename.toString()+"'");
			Toast.makeText(Agency.this, "�޸ĳɹ���", Toast.LENGTH_SHORT).show();
			refresh();
		}
		}
	 }
}