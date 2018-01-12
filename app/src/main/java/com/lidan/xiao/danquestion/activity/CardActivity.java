package com.lidan.xiao.danquestion.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidan.xiao.danquestion.R;
import com.lidan.xiao.danquestion.hepler.MyTag;

public class CardActivity extends AppCompatActivity {
private GridView gv;
private int select,num,from;
private TextView selectView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("选择题号");
        Intent intent=getIntent();
        num=intent.getIntExtra("num",0);
        from=intent.getIntExtra("from",1);
        createCard();
    }

    private void createCard() {
        gv=findViewById(R.id.gv_card);
        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return num;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view= LayoutInflater.from(CardActivity.this).inflate(R.layout.carditem,null);
                TextView tv=view.findViewById(R.id.tv_carditem);
                tv.setText(String.valueOf(position+1));
                if(from==1){
                    if(!QuestionActivity.anList.get(position).equals("")){
                        tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else {
                        tv.setTextColor(getResources().getColor(R.color.gray));
                    }
                }else if(from==2){
                    if(!PracticeActivity.anList.get(position).equals("")){
                        tv.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else {
                        tv.setTextColor(getResources().getColor(R.color.gray));

                    }
                }
                return view;
            }
        };
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectView!=null) {
                    selectView.setTextColor(getResources().getColor(R.color.gray));
                }
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorAccent));

                selectView= (TextView) view;
                select=position;
                selectCard();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectCard() {
            Intent intent1 = new Intent(this, CardActivity.class);
            intent1.putExtra("card", select);
            //返回数据到前一个Activity
            setResult(MyTag.CARD, intent1);
            finish();
    }
}
