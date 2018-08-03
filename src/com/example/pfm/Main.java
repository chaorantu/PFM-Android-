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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
		 Cursor rec=db.rawQuery("select * from User", null);
		 List<Map<String,Object>>items=new ArrayList<Map<String,Object>>();
		 Map<String, Object> title=new HashMap<String, Object>();
		 title.put("���","���");
		 title.put("�û�","����");
		 title.put("����", "����");
		 items.add(title);
		 int i=0;
		 while(rec.moveToNext())
				{
			        
					String username=rec.getString(0);
					String password=rec.getString(1);
		            Map<String, Object> map=new HashMap<String, Object>();
		            map.put("���",""+i+"");
		            map.put("�û�", ""+username+"");
		            map.put("����", ""+password+"");
		            items.add(map);
			        i++;
				}
		 SimpleAdapter sa=new SimpleAdapter(
	                Main.this, //�����Ļ���
	                items,     //����Դ
	                R.layout.list,  //���ݲ���   ���������
	                new String[]{"���","�û�","����"},   //����Դ��arrayName
	                new int[]{R.id.text1,R.id.text2,R.id.text3}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
	        );      
		 ListView listv=(ListView)findViewById(R.id.listView1);
		 listv.setAdapter(sa);    //
				//String password=rec.getString(1);
				//Toast.makeText(Main.this, ""+password+"", Toast.LENGTH_LONG).show();
	}
}
