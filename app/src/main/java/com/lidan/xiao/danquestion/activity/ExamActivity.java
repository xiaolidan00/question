package com.lidan.xiao.danquestion.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lidan.xiao.danquestion.R;
import com.lidan.xiao.danquestion.hepler.ToolHelper;
import com.lidan.xiao.danquestion.view.MyListView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ExamActivity extends AppCompatActivity {
    private MyListView lv;
    private TextView tv;
    private Cursor cursor;
    private boolean isLv = false;
    private int num = 0, limit = 10;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        //ActionBar工具栏设置
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("测试记录");
        lv = findViewById(R.id.lv_record);
        tv = findViewById(R.id.tv_info2);
        searchResult();
    }

    private void searchResult() {
        cursor = ToolHelper.loadDB(this, "select * from exam order by examDate desc limit " + limit);
        num = cursor.getCount();
        if (num > 0) {
            if (!isLv) {//如果lv未创建
                adapter = new SimpleCursorAdapter(this, R.layout.listitem_exm, cursor,
                        new String[]{"title", "score", "examTime", "examDate"},
                        new int[]{R.id.tv_item_title, R.id.tv_item_score, R.id.tv_item_time, R.id.tv_item_date},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cursor.moveToPosition(position);
                        int select = cursor.getInt(cursor.getColumnIndex("_id"));
                        Intent intent = new Intent(ExamActivity.this, DetailActivity.class);
                        intent.putExtra("qid", select);
                        startActivity(intent);
                    }
                });
                isLv = true;
            } else {//如果lv已经创建，数据改变则重新加载lv
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
            }
            resultTv();

        } else {
            limit = 10;
            if (isLv) {
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
                isLv = false;
            }
            tv.setVisibility(View.VISIBLE);
            tv.setText("无记录结果");
        }
    }

    private void resultTv() {
        if (num < limit) {//如果查询结果数小于限制数
            tv.setVisibility(View.GONE);
            limit = 10;
        } else if (num >= limit) {//如果查询结果数多于限制数
            tv.setText("更多查询数据");
            tv.setVisibility(View.VISIBLE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limit = limit + 10;
                    searchResult();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
