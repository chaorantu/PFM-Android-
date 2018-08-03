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
	 title.put("序号","序号");
	 title.put("经销商","经销商");
	 title.put("积分","积分");
	 items.add(title);
	 int i=0;
	 while(rec.moveToNext())
			{
		        
				String TypeName=rec.getString(0);
				String Score=rec.getString(1);
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("序号",""+i+"");
	            map.put("经销商", ""+TypeName+"");
	            map.put("积分", ""+Score+"");
	            items.add(map);
		        i++;
			}
	 SimpleAdapter sa=new SimpleAdapter(
               Agency.this, //上下文环境
               items,     //数据源
               R.layout.list,  //内容布局   在这里调用
               new String[]{"序号","经销商","积分"},   //数据源的arrayName
               new int[]{R.id.text1,R.id.text2,R.id.text3}  //i装载数据的控件（这个就是的吧是什么 list里的控件  嗯 哦）
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
            Typename=map.get("经销商");
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
			{Toast.makeText(Agency.this, "经销商不能为空！！", Toast.LENGTH_LONG).show();}
			else{db.execSQL("INSERT INTO AgType VALUES('"+typename.getText()+"','0')");
			Toast.makeText(Agency.this, ""+typename.getText()+"添加成功！", Toast.LENGTH_LONG).show();
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
			{Toast.makeText(Agency.this, "经销商不能为空！！", Toast.LENGTH_LONG).show();}
			else 
			{
				Cursor c=db.rawQuery("select * from AgType where TypeName=?" , new String[]{""+typename.getText()+""});
	        	if(c.getCount()==0)
	        	{Toast.makeText(Agency.this,""+typename.getText()+"不存在该项目！",Toast.LENGTH_SHORT).show();
	        	refresh();}
	        	else
	        	{db.execSQL("DELETE FROM AgType where TypeName='"+typename.getText()+"'");
				Toast.makeText(Agency.this,""+typename.getText()+"删除成功！",Toast.LENGTH_SHORT).show();
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
			Toast.makeText(Agency.this, "经销商不能为空！", Toast.LENGTH_SHORT).show();
		}
		else
		{
		    db.execSQL("UPDATE AgType SET TypeName='"+typename.getText()+"' where TypeName='"+Typename.toString()+"'");
			Toast.makeText(Agency.this, "修改成功！", Toast.LENGTH_SHORT).show();
			refresh();
		}
		}
	 }
}