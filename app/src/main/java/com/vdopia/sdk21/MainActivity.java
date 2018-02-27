package com.vdopia.sdk21;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOAdSize;
import com.vdopia.ads.lw.LVDOBannerAd;
import com.vdopia.ads.lw.LVDOBannerAdListener;
import com.vdopia.ads.lw.LVDOConstants;


public class MainActivity extends AppCompatActivity {
    LVDOAdRequest adRequest = new LVDOAdRequest(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //LVDOBannerAd.prefetch(this, LVDOAdSize.INVIEW_LEADERBOARD, "QZDID8", adRequest);
        prefetch();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //LinearLayout sc=(LinearLayout) findViewById(R.id.sc);
        //sc.setVisibility(View.INVISIBLE);

        Button button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent toy=new Intent(MainActivity.this,InviewListActivity.class);
                startActivity(toy);
                prefetch();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void prefetch(){

        LVDOBannerAd.prefetch(this, LVDOAdSize.INVIEW_LEADERBOARD, "QZDID8", adRequest);


    }
    public void clickmybutton(View v){

        Toast.makeText(getBaseContext(), "Sourav", Toast.LENGTH_SHORT).show();
        //LinearLayout sc=(LinearLayout) findViewById(R.id.sc);
        //sc.setVisibility(View.VISIBLE);
        //Intent toy=new Intent(MainActivity.this,InviewListActivity.class);
        //startActivity(toy);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
