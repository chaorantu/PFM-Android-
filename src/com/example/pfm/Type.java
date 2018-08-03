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

public class Type extends Activity {
	
	Button add;
	Button update;
	Button delete;
	EditText typename;
	EditText price;
	RadioGroup type;
	ListView listv;
	Object Typename;
	Object Price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_type);
//		 r1=(RadioButton)this.findViewById(R.id.radioButton1);
//		 r2=(RadioButton)this.findViewById(R.id.radioButton2);
		 add=(Button)this.findViewById(R.id.BtAdd);
		 update=(Button)this.findViewById(R.id.BtUpdate);
		 delete=(Button)this.findViewById(R.id.BTDelete);
		typename=(EditText)this.findViewById(R.id.editText1);
		price=(EditText)this.findViewById(R.id.editText2);
//		type=(RadioGroup)this.findViewById(R.id.radioGroup);
		add.setOnClickListener(new Add());
		update.setOnClickListener(new Update());
		delete.setOnClickListener(new Delete());
refresh();
	}
		
    private void refresh()
    {		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
	 Cursor rec=db.rawQuery("select * from PfType", null);
	 List<Map<String,Object>>items=new ArrayList<Map<String,Object>>();
	 Map<String, Object> title=new HashMap<String, Object>();
	 title.put("���","���");
	 title.put("����","����");
	 title.put("�۸�", "�۸�");
	 items.add(title);
	 int i=0;
	 while(rec.moveToNext())
			{
		        
				String TypeName=rec.getString(0);
				String Type=rec.getString(1);
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("���",""+i+"");
	            map.put("����", ""+TypeName+"");
	            map.put("�۸�", ""+Type+"");
	            items.add(map);
		        i++;
			}
	 SimpleAdapter sa=new SimpleAdapter(
               Type.this, //�����Ļ���
               items,     //����Դ
               R.layout.list,  //���ݲ���   ���������
               new String[]{"���","����","�۸�"},   //����Դ��arrayName
               new int[]{R.id.text1,R.id.text2,R.id.text3}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
       );      
	 listv=(ListView)findViewById(R.id.listView1);
	 listv.setAdapter(sa);  
	 listv.setOnItemClickListener(new ItemClickListener());
	 typename.setText("");
	 price.setText("");
	 /*r1.setChecked(false);
	 r2.setChecked(false);*/
	 }
	private class ItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			HashMap<String,Object> map=(HashMap<String,Object>)listv.getItemAtPosition(position);   
            Typename=map.get("����");
            Price=map.get("�۸�");
            typename.setText(""+Typename.toString()+"");
            price.setText(""+Price.toString()+"");
		}
	   
	}
	private class Add implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
			if(typename.getText().toString().equals("")&&price.getText().toString().equals(""))
			{Toast.makeText(Type.this, "���ͺͼ۸���Ϊ�գ���", Toast.LENGTH_LONG).show();}
			else if(typename.getText().toString().equals(""))
			{Toast.makeText(Type.this, "���Ͳ���Ϊ�գ���", Toast.LENGTH_LONG).show();}
			else if(price.getText().toString().equals(""))
			{
			Toast.makeText(Type.this, "�۸���Ϊ�գ���", Toast.LENGTH_LONG).show();
			}
			else{db.execSQL("INSERT INTO PfType VALUES('"+typename.getText()+"','"+price.getText()+"')");
			Toast.makeText(Type.this, ""+typename.getText()+"��ӳɹ���", Toast.LENGTH_LONG).show();
			refresh();
			}
			/*else if(r1.isChecked())
			{
			db.execSQL("INSERT INTO PfType VALUES('"+typename.getText()+"','����')");
			Toast.makeText(Type.this, ""+typename.getText()+"��ӳɹ���", Toast.LENGTH_LONG).show();
			refresh();
			}
			else if(r2.isChecked())
			{db.execSQL("INSERT INTO PfType VALUES('"+typename.getText()+"','֧��')");
			Toast.makeText(Type.this, ""+typename.getText()+"��ӳɹ���", Toast.LENGTH_LONG).show();
			refresh();}
			else
			{Toast.makeText(Type.this, "��ѡ�����ͣ�", Toast.LENGTH_LONG).show();}*/
		}
	}
	 private class Delete implements Button.OnClickListener
	 {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
			if(typename.getText().toString().equals(""))
			{Toast.makeText(Type.this, "����������Ϊ�գ���", Toast.LENGTH_LONG).show();}
			else 
			{
				Cursor c=db.rawQuery("select * from PfType where TypeName=?" , new String[]{""+typename.getText()+""});
	        	if(c.getCount()==0)
	        	{Toast.makeText(Type.this,""+typename.getText()+"�����ڸ���Ŀ��",Toast.LENGTH_SHORT).show();
	        	refresh();}
	        	else
	        	{db.execSQL("DELETE FROM PfType where TypeName='"+typename.getText()+"'");
				Toast.makeText(Type.this,""+typename.getText()+"ɾ���ɹ���",Toast.LENGTH_SHORT).show();
				refresh();}
			}
		}}
	 private class Update implements Button.OnClickListener
	 {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE, null);
		if(typename.getText().equals("")&&price.getText().equals(""))
		{
			Toast.makeText(Type.this, "���ͺͼ۸���Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}
		else if(typename.getText().equals(""))
		{
			Toast.makeText(Type.this, "���Ͳ���Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}
		else if(price.getText().equals(""))
		{
			Toast.makeText(Type.this, "�۸���Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}
		else
		{
		    db.execSQL("UPDATE PfType SET TypeName='"+typename.getText()+"',TypePrice='"+price.getText()+"' where TypeName='"+Typename.toString()+"'");
			Toast.makeText(Type.this, "�޸ĳɹ���", Toast.LENGTH_SHORT).show();
			refresh();
		}
		}
	 }
}