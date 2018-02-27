package com.vdopia.sdk21;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vdopia.ads.lw.LVDOAdSize;

import java.util.ArrayList;
import java.util.List;

/**
 * This is important activity Which show how to load Inview ad inside list view
 *
 * @author Cybage Team
 */


public class InviewListActivity extends RequestPermissionActivity {

    private static final String TAG = "InviewListActivity";

    private String apiKey;
    private InviewVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_events);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //String category = intent.getStringExtra(AppConstant.KEY_CATEGORY);

        /*apiKey = intent.getStringExtra(AppConstant.EXTRA_API_KEY);
        if (apiKey == null || apiKey.trim().length() == 0) {
            apiKey = ApiKey.getApiKeyCommon(this);
        }*/

        //apiKey="QZDID8";
        apiKey="QZDID8";
        //apiKey="71a1e6a548a325a8bc741c4ed66c3352";
        //apiKey="c859270852010a9141b6e9ea0125fb6e";
        //apiKey="jAKt1V";
        //apiKey="qZPI8O";

        setAdAdpater();
        super.requestAppPermissions(mPermissions, mRuntimePermissionId, REQUEST_PERMISSIONS);
    }

    private void setAdAdpater() {
        LVDOAdSize adSize = LVDOAdSize.INVIEW_LEADERBOARD;

        final List<Channel> channelArrayList = new ArrayList<>();
        int adPosition = -1;

        mAdapter = new InviewVideoAdapter(this, channelArrayList, apiKey, adSize);
        mAdapter.setAdPosition(adPosition);

        ListView newsView = (ListView) findViewById(R.id.events_list);
        newsView.setAdapter(mAdapter);

        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(InviewListActivity.this, WebViewActivity.class);
                intent.putExtra("uri", channelArrayList.get(position).getLink());
                startActivity(intent);*/
            }
        });

        final HandleXML obj = new HandleXML(this);
        obj.setDataListener(new HandleXML.DataListener() {
            @Override
            public void onDataSuccess(final boolean isSuccess) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess) {
                            channelArrayList.addAll(obj.getChannelArrayList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Check Internet Connection");
                        }
                    }
                });
            }
        });
        obj.fetchXML("http://feeds.feedburner.com/welcometonightvale");
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        int adPosition = 3;
        if (mAdapter != null) {
            mAdapter.setAdPosition(adPosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AdListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.item_setting:
                displayReplyActivity();
                return true;
            default:
                Log.e(TAG, "Default Item Selected...");
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayReplyActivity() {
        Intent intentReplyActivity = new Intent(InviewListActivity.this, SettingActivity.class);
        startActivity(intentReplyActivity);
    }*/

    @Override
    protected void onDestroy() {
        if (mAdapter != null) {
            mAdapter.destroyView();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
