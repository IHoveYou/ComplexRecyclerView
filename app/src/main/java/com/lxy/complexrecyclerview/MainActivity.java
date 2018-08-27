package com.lxy.complexrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lxy.dexlibs.ComplexRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("第" + i + "条");
        }
        Demo adapter=new Demo(this,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.addBtn(R.layout.btn_layout, new ComplexRecyclerViewAdapter.OnItemBtnClickListener() {
            @Override
            public void onItemBtnClickListener(int pson, ComplexRecyclerViewAdapter.ComplexViewHolder holder, Object bean) {
                Toast.makeText(MainActivity.this, bean.toString(), Toast.LENGTH_LONG).show();
            }
        });
        adapter.addBtn(R.layout.btn_layout, new ComplexRecyclerViewAdapter.OnItemBtnClickListener() {
            @Override
            public void onItemBtnClickListener(int pson, ComplexRecyclerViewAdapter.ComplexViewHolder holder, Object bean) {
                Toast.makeText(MainActivity.this, bean.toString(), Toast.LENGTH_LONG).show();
            }
        });


        adapter.setOnItemClickListener(new ComplexRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pson, ComplexRecyclerViewAdapter.ComplexViewHolder holder, Object bean) {
                Toast.makeText(MainActivity.this, bean.toString()+"hahah", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
