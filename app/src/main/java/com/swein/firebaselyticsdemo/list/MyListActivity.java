package com.swein.firebaselyticsdemo.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.swein.firebaselyticsdemo.R;
import com.swein.firebaselyticsdemo.framework.firebaseanalytics.EventTracker;
import com.swein.firebaselyticsdemo.framework.thread.ThreadUtil;
import com.swein.firebaselyticsdemo.list.adapter.MyListAdapter;
import com.swein.firebaselyticsdemo.list.adapter.listitemdata.ListItemData;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends Activity {

    private final static String TAG = "MyListActivity";

    private FrameLayout frameLayoutNavigationBarContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;

    private MyListAdapter myListAdapter;

    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        findView();

        initRecyclerView();
    }

    private void findView() {
        frameLayoutNavigationBarContainer = findViewById(R.id.frameLayoutNavigationBarContainer);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initRecyclerView() {


        myListAdapter = new MyListAdapter();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "refresh");

                progressBar.setVisibility(View.VISIBLE);

                ThreadUtil.startThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        ThreadUtil.startUIThread(0, new Runnable() {
                            @Override
                            public void run() {
                                // reload here
                                myListAdapter.reloadList(createDefaultTempData());
                                progressBar.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });

            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(myListAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                final int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {

                    EventTracker.getInstance().sendEvent(MyListActivity.this, "MyListActivity", "list", "load more");

                    progressBar.setVisibility(View.VISIBLE);

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ThreadUtil.startUIThread(0, new Runnable() {
                                @Override
                                public void run() {
                                    // load more here
                                    progressBar.setVisibility(View.GONE);
                                    myListAdapter.loadMoreList(createMoreTempData(totalItemCount, 5));
                                }
                            });
                        }
                    });


                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });


        myListAdapter.loadMoreList(createDefaultTempData());

    }

    private List<ListItemData> createDefaultTempData() {

        List<ListItemData> listItemDataList = new ArrayList<>();

        for(int i = 0; i < 5; i++) {

            ListItemData listItemData = new ListItemData();
            listItemData.title = "title " + i;
            listItemData.message = "message " + i;

            listItemDataList.add(listItemData);
        }

        return listItemDataList;
    }

    private List<ListItemData> createMoreTempData(int offset, int limit) {

        List<ListItemData> listItemDataList = new ArrayList<>();

        for(int i = offset; i < offset + limit; i++) {

            ListItemData listItemData = new ListItemData();
            listItemData.title = "title " + i;
            listItemData.message = "message " + i;

            listItemDataList.add(listItemData);
        }

        return listItemDataList;
    }
}
