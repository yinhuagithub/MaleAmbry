package com.graduation.yinhua.maleambry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yinhua.maleambry.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinhua on 2016/8/31.
 */
public abstract class BaseRecyclerAdapter<D,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    protected final List<D> mDatas = new ArrayList<D>();
    private OnItemClickListener<D> mItemClickListener;
    private OnItemPositionListener mItemPositionListener;

    @Override
    public void onBindViewHolder(V holder, int position) {
        bindDataToItemView(holder, position);
        bindListener(holder, position);
        setupItemViewClickListener(holder, position);
    }


    /**
     * 绑定数据到item视图
     * @param holder
     * @param position
     */
    protected void bindDataToItemView(V holder, int position) {
    }

    /**
     * 绑定监听事件
     * @param holder
     * @param position
     */
    protected void bindListener(V holder, int position) {
    }

    /**
     * 设置item的点击事件
     * @param holder
     * @param position
     */
    protected void setupItemViewClickListener(V holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null) {
                    mItemClickListener.onClick(mDatas.get(position));
                }
                if(mItemPositionListener != null) {
                    mItemPositionListener.onPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 :mDatas.size();
    }

    /**
     * 获取item详细信息
     * @param position
     * @return
     */
    protected D getItem(int position) {
        return mDatas.get(position);
    }

    public List<D> getItems() {
        return mDatas;
    }

    /**
     * 添加item数据，去掉重复item信息
     * @param items
     */
    public void addItems(List<D> items, boolean isRemove) {
        if(isRemove) {
            mDatas.removeAll(items);
        } else {
            mDatas.clear();
        }
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 在第0个位置上添加item数据集合
     * @param items
     */
    public void refreshItems(List<D> items) {
        mDatas.removeAll(items);
        mDatas.addAll(0, items);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 填充item视图
     * @param viewGroup
     * @param layoutId
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    public OnItemClickListener<D> getOnItemClickListener() {
        return mItemClickListener;
    }
    public void setOnItemClickListener(OnItemClickListener<D> listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemPositionListener {
        void onPosition(int position);
    }

    public OnItemPositionListener getOnItemPositionListener() {
        return mItemPositionListener;
    }
    public void setOnItemPositionListener(OnItemPositionListener listener) {
        this.mItemPositionListener = listener;
    }
}
