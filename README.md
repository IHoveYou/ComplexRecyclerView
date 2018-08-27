# ComplexRecyclerView
自定义控件
左滑删除的一个关于RecycleView的适配器，只要继承这个适配器即可实现左滑删除功能，无耦合。
添加左滑按钮，可多个
adapter.addBtn(R.layout.btn_layout, new ComplexRecyclerViewAdapter.OnItemBtnClickListener() {
            @Override
            public void onItemBtnClickListener(int pson, ComplexRecyclerViewAdapter.ComplexViewHolder holder, Object bean) {
                Toast.makeText(MainActivity.this, bean.toString(), Toast.LENGTH_LONG).show();
            }
        });
        条目点击事件
         adapter.setOnItemClickListener(new ComplexRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pson, ComplexRecyclerViewAdapter.ComplexViewHolder holder, Object bean) {
                Toast.makeText(MainActivity.this, bean.toString()+"hahah", Toast.LENGTH_LONG).show();
            }
        });
详细使用方法地址 https://blog.csdn.net/qq_35644925/article/details/82109597
