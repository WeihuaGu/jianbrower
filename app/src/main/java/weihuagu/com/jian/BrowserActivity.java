/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;

import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.ui.manager.UIManager;
import weihuagu.com.jian.ui.manager.PhoneUIManager;

public class BrowserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PhoneUrlBar urlbar=null;
    CustomWebView webview =null;

    UIManager phoneuimanager=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initResources();
        this.bindUIManager();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fresh) {
            this.phoneuimanager.freshUrl();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent(BrowserActivity.this,ScanActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_setting) {
            Intent intent=new Intent(BrowserActivity.this,SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            String sharestring=phoneuimanager.getCurrentUrl();
            Intent intent1=new Intent(Intent.ACTION_SEND);
            intent1.putExtra(Intent.EXTRA_TEXT,sharestring);
            intent1.setType("text/plain");
            startActivity(Intent.createChooser(intent1,"share"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(this.phoneuimanager.onKeyBack())
                ;
            else
                finish();
            return true;
        }
        return false;



    }


    public void initResources(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        webview = (CustomWebView) findViewById(R.id.webView); //加载WebView
        urlbar=(PhoneUrlBar) findViewById(R.id.urlbar);
    }

    public void bindUIManager(){
        this.phoneuimanager=new PhoneUIManager(urlbar,webview);
    }

}
