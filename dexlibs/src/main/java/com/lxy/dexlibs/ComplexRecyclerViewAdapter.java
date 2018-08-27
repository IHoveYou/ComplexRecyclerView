package com.lxy.dexlibs;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXY on 2018/8/23.
 */

public abstract class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<ComplexRecyclerViewAdapter.ComplexViewHolder> {
    protected List datas;
    protected Context mContext;
    protected LayoutInflater inflater;
    ArrayList<Integer> btns = new ArrayList<>();
    ArrayList<OnItemBtnClickListener> onItemBtnClickListeners = new ArrayList<>();
    ArrayList<ComplexViewHolder> holderList = new ArrayList();
    private OnItemClickListener onItemClickListener;

    @Override
    public ComplexRecyclerViewAdapter.ComplexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComplexViewHolder complexViewHolder = new ComplexViewHolder(onBindLayout(inflater, parent, viewType));
        complexViewHolder.horizontalScrollView.setMoveClickListener(moveClickListener);
        holderList.add(viewType, complexViewHolder);
        return complexViewHolder;
    }

    @Override
    public void onBindViewHolder(ComplexRecyclerViewAdapter.ComplexViewHolder holder, int position) {
        holder.addBtn(position, datas.get(position));
        onBindViewData(datas.get(position), holder, position);
        holder.setOnClickListener(datas.get(position), position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ComplexRecyclerViewAdapter(Context context, List list) {
        this.mContext = context;
        this.datas = list;
        this.inflater = LayoutInflater.from(context);
    }

    protected abstract View onBindLayout(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract void onBindViewData(Object data, ComplexRecyclerViewAdapter.ComplexViewHolder holder, int position);


    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    public void addBtn(int btn, OnItemBtnClickListener onItemBtnClickListener) {
        this.btns.add(btn);
        this.onItemBtnClickListeners.add(onItemBtnClickListener);
    }


    public class ComplexViewHolder extends RecyclerView.ViewHolder {
        MyHorizontalScrollView horizontalScrollView;
        View content;
        Object bean;
        int position;
        OnItemClickListener onItemClickListener;

        public void setOnClickListener(Object bean, int position, OnItemClickListener onItemClickListener
        ) {
            this.onItemClickListener = onItemClickListener;
            this.bean = bean;
            this.position = position;
            content.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position, ComplexViewHolder.this, bean);
                }
            }
        };

        public ComplexViewHolder(View itemView) {
            super(new MyHorizontalScrollView(itemView.getContext()));
            content = itemView;
            horizontalScrollView = (MyHorizontalScrollView) this.itemView;
            horizontalScrollView.addView(itemView);
            horizontalScrollView.setHorizontalScrollBarEnabled(false);
            itemView.getLayoutParams().width = mContext.getResources().getDisplayMetrics().widthPixels;
        }

        public void addBtn(final int pson, final Object bean) {
            ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
            if (btns.size() != 0) {
                for (int i = 0; i < btns.size(); i++) {
                    final int finalI = i;
                    horizontalScrollView.addBtn(btns.get(i), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemBtnClickListeners.get(finalI).onItemBtnClickListener(pson, ComplexViewHolder.this, bean);
                        }
                    });
                }
            }
        }

        public <T extends View> T getView(int id) {
            return (T) itemView.findViewById(id);
        }

        public void setText(int id, String text) {
            if (text != null) {
                TextView textView = getView(id);
                textView.setText(text);
            }
        }

        public ImageView getImageView(int id) {
            return (ImageView) getView(id);
        }

        public void romeAll() {
            horizontalScrollView.removeAllViews();
        }

    }

    @Override
    public void onViewDetachedFromWindow(ComplexViewHolder holder) {
        holder.horizontalScrollView.setRollOut(false);
        super.onViewDetachedFromWindow(holder);
    }


    @Override
    public void onViewRecycled(ComplexViewHolder holder) {
        holder.horizontalScrollView.setRollOut(false);
        holder.romeAll();
        super.onViewRecycled(holder);
    }

    MyHorizontalScrollView.MoveClickListener moveClickListener = new MyHorizontalScrollView.MoveClickListener() {
        @Override
        public void onMoveClickListener(MyHorizontalScrollView myHorizontalScrollView, int moveX) {

        }

        @Override
        public void onUpClickListener(MyHorizontalScrollView myHorizontalScrollView, boolean isRollOut) {
            if (isRollOut)
                for (ComplexViewHolder holder : holderList) {
                    if (holder.horizontalScrollView.isRollOut() && holder.horizontalScrollView != myHorizontalScrollView) {
                        holder.horizontalScrollView.setRollOut(false);
                    }
                }
        }
    };

    public interface OnItemBtnClickListener {
        void onItemBtnClickListener(int pson, ComplexViewHolder holder, Object bean);
    }

    public Context getmContext() {
        return mContext;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pson, ComplexViewHolder holder, Object bean);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
