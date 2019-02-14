package com.swein.firebaselyticsdemo.list.adapter.listitemviewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.list.adapter.listitemdata.ListItemData;
import com.swein.firebaselyticsdemo.list.adapter.listitemviewholder.interfaces.ListItemViewHolderInterface;

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "SHRecyclerViewHolder";


    private TextView textViewTitle;
    private TextView textViewMessage;
    private ImageButton imageButtonCheck;

    private View itemView;

    private ListItemData listItemData;

    private ListItemViewHolderInterface listItemViewHolderInterface;

    public ListItemViewHolder(View itemView, ListItemViewHolderInterface listItemViewHolderInterface) {

        super(itemView);

        this.itemView = itemView;
        this.listItemViewHolderInterface = listItemViewHolderInterface;
        findView();
    }

    private void findView(){

        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewMessage = itemView.findViewById(R.id.textViewMessage);

        imageButtonCheck = itemView.findViewById(R.id.imageButtonCheck);

        imageButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check 여부 확인 햅니다.
                if(listItemData.checked) {
                    listItemData.checked = false;
                }
                else {
                    listItemData.checked = true;
                }

                // update UI
                updateCheck();

                listItemViewHolderInterface.onRecyclerViewHolderClicked(listItemData);
            }
        });

    }

    private void updateCheck() {
        if(listItemData.checked) {
            imageButtonCheck.setImageResource(R.drawable.checked);
        }
        else {
            imageButtonCheck.setImageResource(R.drawable.un_checked);
        }
    }

    public void updateView(ListItemData listItemData){
        this.listItemData = listItemData;
        textViewTitle.setText(listItemData.title);
        textViewMessage.setText(listItemData.message);

        updateCheck();
    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();
    }
}
