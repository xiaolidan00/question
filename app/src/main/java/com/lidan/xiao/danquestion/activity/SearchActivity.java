package com.lidan.xiao.danquestion.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lidan.xiao.danquestion.R;
import com.lidan.xiao.danquestion.hepler.ToolHelper;
import com.lidan.xiao.danquestion.view.MyListView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText sv;
    private MyListView lv;
    private TextView tv;
    private ImageView submit;
    private boolean isLv=false;
    private int num=0,limit=10;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //ActionBar工具栏设置
        Toolbar toolbar =  findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sv=findViewById(R.id.sv);
        submit=findViewById(R.id.img_search);
        tv=findViewById(R.id.tv_info);
        lv=findViewById(R.id.lv_search);
        submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String text=sv.getText().toString();
        if(!text.isEmpty()){
            searchResult(text);
        }else {
            Toast.makeText(this,"请输入查询内容",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
        finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void searchResult(final String text) {
          cursor = ToolHelper.loadDB(this,"select _id,que from que where que like '%"+text
                +"%' or choiceA like '%"+text+"%' or choiceB like'%"+text
                +"%' or choiceC like'%"+text+"%' or choiceD like'%"+text+"%' limit "+limit);

        num=cursor.getCount();
        if(num>0) {
            if(!isLv) {//如果lv未创建
                adapter = new SimpleCursorAdapter(this, R.layout.listitem1, cursor,
                        new String[]{"que"}, new int[]{R.id.tv_item2},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cursor.moveToPosition(position);
                        int select = cursor.getInt(cursor.getColumnIndex("_id"));
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        intent.putExtra("qid", select);
                        startActivity(intent);
                    }
                });
                isLv=true;
            }else {//如果lv已经创建，数据改变则重新加载lv
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
            }
            resultTv(text);

        }else {
            limit=10;
            if(isLv){
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
                isLv=false;
            }
                tv.setVisibility(View.VISIBLE);
                tv.setText("无查询结果");
        }
    }

    private void resultTv(final String text) {
        if(num<limit) {//如果查询结果数小于限制数
            tv.setVisibility(View.GONE);
            limit=10;
        }else if(num>=limit){//如果查询结果数多于限制数
            tv.setText("更多查询数据");
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limit=limit+10;
                    searchResult(text);
                    //Toast.makeText(SearchActivity.this,"limit="+String.valueOf(limit),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
