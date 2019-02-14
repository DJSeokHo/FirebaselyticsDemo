package com.swein.firebaselyticsdemo.list.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.framework.toast.ToastUtil;
import com.swein.firebaselyticsdemo.list.adapter.listitemdata.ListItemData;
import com.swein.firebaselyticsdemo.list.adapter.listitemviewholder.ListItemViewHolder;
import com.swein.firebaselyticsdemo.list.adapter.listitemviewholder.interfaces.ListItemViewHolderInterface;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter implements ListItemViewHolderInterface {

    private final static String TAG = "MyListAdapter";

    private List<ListItemData> listItemDataList = new ArrayList<>();

    private static final int TYPE_ITEM_LIST = 0;

    private Context context;


    /*
        View Holder UI 생성
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_list_item, viewGroup, false);


        return new ListItemViewHolder(view, this);
    }

    /*
        View Holder UI 갱신
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

//        if (viewHolder instanceof ListItemViewHolder) {
//
//
//        }

        ListItemViewHolder listItemViewHolder = (ListItemViewHolder) viewHolder;
        listItemViewHolder.updateView(listItemDataList.get(position));

    }

    public void loadMoreList(List<ListItemData> listItemData) {
        this.listItemDataList.addAll(listItemData);

        notifyDataSetChanged();
    }

    public void reloadList(List<ListItemData> listItemData) {
        this.listItemDataList.clear();
        this.listItemDataList.addAll(listItemData);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // change item view holder ui here
        return TYPE_ITEM_LIST;
//        return TYPE_ITEM_GRID;
    }

    @Override
    public int getItemCount() {
        return listItemDataList.size();
    }

    @Override
    public void onRecyclerViewHolderClicked(ListItemData listItemData) {
        ToastUtil.showCustomShortToastNormal(context, listItemData.title + " " + listItemData.message + " " + listItemData.checked);
    }
}
