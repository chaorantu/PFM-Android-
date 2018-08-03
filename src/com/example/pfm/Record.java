package com.example.pfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Record extends Activity {
	ListView listv;
	Button delete;
	EditText typename;
	EditText num;
	EditText agtype;
	EditText date;
	Object Typename;
	Object Num;
	Object Agtype;
	Object Date;
	
    Map<Integer,Boolean> mChecked=new HashMap<Integer, Boolean>();
//    ViewHolder2 holder2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		 typename=(EditText)this.findViewById(R.id.editText1);
		 num=(EditText)this.findViewById(R.id.editText2);
		 agtype=(EditText)this.findViewById(R.id.editText3);
		 date=(EditText)this.findViewById(R.id.editText4);
		 delete=(Button)findViewById(R.id.button1);
		 delete.setOnClickListener(new Delete());
		 
		 refresh();
	}
	private void refresh(){
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
	               Record.this, //�����Ļ���
	               items,     //����Դ
	               R.layout.list3,  //���ݲ���   ���������
	               new String[]{"���","����","��Դ","����","����","�ܶ�","����"},   //����Դ��arrayName
	               new int[]{R.id.Text1,R.id.Text2,R.id.Text3,R.id.Text4,R.id.Text5,R.id.Text6,R.id.Text7}  //iװ�����ݵĿؼ���������ǵİ���ʲô list��Ŀؼ�  �� Ŷ��
	       );
		 listv.setAdapter(sa);
		 listv.setOnItemClickListener(new ItemClickListener());
	}
	
	private class Delete implements Button.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);	
	        	db.execSQL("DELETE FROM Finance where TypeName='"+typename.getText()+"'and Type='"+agtype.getText()+"' and Num='"+num.getText()+"' and Date='"+date.getText()+"'");
				Toast.makeText(Record.this,"ɾ���ɹ���",Toast.LENGTH_SHORT).show();
				refresh();}
	}

//	 private List<Map<String, Object>> getDate(){ 
//         List<Map<String,Object>>items=new ArrayList<Map<String,Object>>();
//         /**Ϊ��̬�����������*/     
//         SQLiteDatabase db=openOrCreateDatabase("PFM3.db", Context.MODE_PRIVATE,null);
//		 Cursor rec=db.rawQuery("select * from Finance", null);
//		 int i=0;
//		 while(rec.moveToNext())
//				{
//			        
//					String TypeName=rec.getString(0);
//					String Type=rec.getString(1);
//					String Date=rec.getString(2);
//					String Money=rec.getString(3);
//		            Map<String, Object> map=new HashMap<String, Object>();
//		            map.put("���","");
//		            map.put("������", ""+TypeName+"");
//		            map.put("����", ""+Type+"");
//		            map.put("���", ""+Money+"");
//		            map.put("����",""+Date+"");
//		            items.add(map);
//		            i++;
//				}
//         return items; 
//    } 

	
//	public   class MyAdapter extends BaseAdapter {
//
//		 
//		 //HashMap<Integer, View> map=new HashMap<Integer, View>();
//		 private LayoutInflater mInflater;
//		public MyAdapter(Context context) { 
//
//           this.mInflater=LayoutInflater.from(context);
//           
//           for(int i=0;i<getDate().size();i++)
//           {
//        	   mChecked.put(i,false);
//           }
//  } 
//
//		
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return getDate().size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return getDate().get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			//ViewHolder1 holder1=new ViewHolder1();
//			
//			holder2=new ViewHolder2();
//	
//			if(convertView==null)
//			{
//				/*if(position==0)
//				{
//				convertView=mInflater.inflate( R.layout.list1, null);
//				holder1.text1=(TextView)convertView.findViewById(R.id.text1);
//				holder1.text2=(TextView)convertView.findViewById(R.id.text2);
//				holder1.text3=(TextView)convertView.findViewById(R.id.text3);
//				holder1.text4=(TextView)convertView.findViewById(R.id.text4);
//				holder1.text5=(TextView)convertView.findViewById(R.id.text5);
//				convertView.setTag(holder1);
//			}
//			else if(position>0)
//			{*/
//				// Log.e("Record","position1 = "+position);
//				convertView=mInflater.inflate( R.layout.list3, null);
//				holder2.check=(CheckBox)convertView.findViewById(R.id.checkBox1);
//				holder2.Txttypename=(TextView)convertView.findViewById(R.id.Text1);
//				holder2.Txttype=(TextView)convertView.findViewById(R.id.Text2);
//				holder2.Txtmoney=(TextView)convertView.findViewById(R.id.Text3);
//				holder2.Txtdate=(TextView)convertView.findViewById(R.id.Text4);
//				// final int p=position;
//			     /*map.put(position,convertView);
//			        holder2.check.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							CheckBox cb=(CheckBox)v;
//							mChecked.set(p, cb.isChecked());
//						}
//					});
//					*/
//			        convertView.setTag(holder2);	
//				
//			//}
//			}
//			else
//			{
//				/*if(position==0)
//				{
//					holder1=(ViewHolder1)convertView.getTag();
//				}
//				else if(position>0)
//				{*/
//					 //Log.e("MainActivity","position = "+position);
//					//convertView=map.get(position);
//					holder2=(ViewHolder2)convertView.getTag();
//				//}
//				
//			}
//			/*if(position==0)
//			{
//			holder1.text1.setText("ѡ��");
//			holder1.text2.setText("����");
//			holder1.text3.setText("�����֧��");
//			holder1.text4.setText("���");
//			holder1.text5.setText("����");
//			return convertView;
//			}
//			else
//			{*/
//				holder2.Txttypename.setText(getDate().get(position).get("������").toString());
//				holder2.Txttype.setText(getDate().get(position).get("����").toString());
//				holder2.Txtmoney.setText(getDate().get(position).get("���").toString());
//				holder2.Txtdate.setText(getDate().get(position).get("����").toString());       
//				//holder2.check.setChecked(mChecked.get(position));	  
//				holder2.check.setChecked(mChecked.get(position));
//				return convertView;
//			//}
//	        
//		}
//		
//	}
//	private class ViewHolder1{
//		public TextView text1;
//		public TextView text2;
//		public TextView text3;
//		public TextView text4;
//		public TextView text5;
//	}
//	public final class ViewHolder2{
//		public CheckBox check;
//		public TextView Txttypename;
//		public TextView Txttype;
//		public TextView Txtmoney;
//		public TextView Txtdate;
//	}
	private class ItemClickListener implements OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String,Object> map=(HashMap<String,Object>)listv.getItemAtPosition(position);   
	            Typename=map.get("����");
	            Agtype=map.get("��Դ");
	            Num=map.get("����");
	            Date=map.get("����");
	            typename.setText(""+Typename.toString()+"");
	            agtype.setText(""+Agtype.toString()+"");
	            num.setText(""+Num.toString()+"");
	            date.setText(""+Date.toString()+"");
			}
	}
}
