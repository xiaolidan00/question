package com.lidan.xiao.danquestion.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidan.xiao.danquestion.R;
import com.lidan.xiao.danquestion.hepler.ToolHelper;

/**
 * Created by Administrator on 2018/1/7.
 */

public class DetailActivity extends AppCompatActivity {
    private TextView tv_que, tv_choice, tv_answer, tv_source, tv_detail;
    private FloatingActionButton fabcollect;
    private int qid;
    private boolean tag = false;
    private String kind, type, choice, que, answer, source, detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //ActionBar工具栏设置
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_que = findViewById(R.id.tv_que);
        tv_choice = findViewById(R.id.tv_choice);
        tv_answer = findViewById(R.id.tv_answer);
        tv_source = findViewById(R.id.tv_source);
        tv_detail = findViewById(R.id.tv_detail);
        fabcollect = findViewById(R.id.fab_collect);

        Intent intent = getIntent();
        qid = intent.getIntExtra("qid", 0);
        if (qid > 0) {
            loadData();
            initData();
            initCollect();
        }
    }

    private void initCollect() {
        final Cursor cursor = ToolHelper.loadDB(this, "select qid from collection where qid=" + qid);
        if (cursor.getCount() > 0) {
            tag = true;
            fabcollect.setImageResource(R.drawable.star_on);
        } else {
            tag = false;
            fabcollect.setImageResource(R.drawable.star1);
        }
        fabcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag) {
                    fabcollect.setImageResource(R.drawable.star1);
                    Toast.makeText(DetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    ToolHelper.excuteDB(DetailActivity.this, "delete from collection where qid=" + qid);
                } else {
                    fabcollect.setImageResource(R.drawable.star_on);
                    Toast.makeText(DetailActivity.this, "成功收藏", Toast.LENGTH_SHORT).show();
                    ToolHelper.excuteDB(DetailActivity.this, "insert into collection (_id,qid) values (" + (Math.random()*10000) + "," + qid + ")");
                }
            }
        });
    }

    private void loadData() {
        Cursor cursor = ToolHelper.loadDB(this, "select * from que where _id=" + qid);

        cursor.moveToFirst();
        kind = cursor.getString(cursor.getColumnIndex("kind"));
        String choiceA = "A."+cursor.getString(cursor.getColumnIndex("choiceA"));
        String choiceB = "B."+cursor.getString(cursor.getColumnIndex("choiceB"));
        String choiceC = "C."+cursor.getString(cursor.getColumnIndex("choiceC"));
        String choiceD = "D."+cursor.getString(cursor.getColumnIndex("choiceD"));
        StringBuffer sb = new StringBuffer();
        if (choiceA != "null") {
            sb.append(choiceA + "\n");
        }
        if (choiceB != "null") {
            sb.append(choiceB + "\n");
        }
        if (choiceC != "null") {
            sb.append(choiceC + "\n");
        }
        if (choiceD != "null") {
            sb.append(choiceD + "\n");
        }
        choice = sb.toString();
        que = cursor.getString(cursor.getColumnIndex("que"));
        type = cursor.getString(cursor.getColumnIndex("type"));
        answer = cursor.getString(cursor.getColumnIndex("answer"));
        source = cursor.getString(cursor.getColumnIndex("source"));
        detail = cursor.getString(cursor.getColumnIndex("detail"));
    }

    private void initData() {
        setTitle(kind);
        tv_que.setText("(" + type + ")" + que);
        tv_choice.setText(choice);
        tv_answer.setText("【答案】" + answer);
        tv_source.setText("【来源】" + source);
        tv_detail.setText("【解析】" + detail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
