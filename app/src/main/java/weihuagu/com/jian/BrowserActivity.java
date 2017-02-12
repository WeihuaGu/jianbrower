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
import android.Manifest;
import java.util.List;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.util.Log;


import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.ui.manager.UIManager;
import weihuagu.com.jian.ui.manager.PhoneUIManager;


import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;




public class BrowserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String ACTION_OPENURL = "com.weihuagu.jian.action.OPENURL";
    private static final String EXTRA_URL = "com.weihuagu.jian.extra.url";
    PhoneUrlBar urlbar=null;
    CustomWebView webview =null;
    UIManager phoneuimanager=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initResources();
        this.bindUIManager();
        this.handleIntent();


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
            if(this.checkPermissionCAMERA()) {
                // Handle the camera action
                Intent intent=new Intent(BrowserActivity.this,ScanActivity.class);
                startActivity(intent);

            }else{
                this.getPermissionCAMERA();

            }







        } else if (id == R.id.nav_setting) {
                // 有权限，直接do anything.
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

    public void handleIntent(){
        Intent intent=getIntent();
        if(intent.getAction()==ACTION_OPENURL){
            Log.i("broseractivity","received:"+ACTION_OPENURL);
            String url=intent.getStringExtra(EXTRA_URL);
            this.phoneuimanager.loadUrl(url);
        }
        if(intent.getAction()=="android.intent.action.VIEW"){
            String [] categories= new String [2];
            intent.getCategories().toArray(categories);
            if(categories!=null){
                for(int i=0;i<categories.length;i++){
                    if(categories[i]=="android.intent.category.BROWSABLE")
                        this.phoneuimanager.loadUrl(intent.getData().toString());

                }
            }
        }

    }



    //permission


    public void getPermissionCAMERA(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();


    }

    public boolean checkPermissionCAMERA(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permissionCheck==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if(requestCode == 100) {
                // TODO 相应代码。

                Intent intent=new Intent(BrowserActivity.this,ScanActivity.class);
                startActivity(intent);
            } else if(requestCode == 101) {
                // TODO 相应代码。
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。

        }
    };



}
