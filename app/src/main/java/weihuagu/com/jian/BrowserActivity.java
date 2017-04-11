/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.Manifest;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import weihuagu.com.jian.model.RuntimeSetting;
import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.ui.manager.UIManager;
import weihuagu.com.jian.ui.manager.PhoneUIManager;
import weihuagu.com.jian.model.BrowserContainer;
import weihuagu.com.jian.model.WebviewNameListAdapter;
import weihuagu.com.jian.ui.manager.ITabManager;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;




public class BrowserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ITabManager{
    private static final String ACTION_OPENURL = "com.weihuagu.jian.action.OPENURL";
    private static final String EXTRA_URL = "com.weihuagu.jian.extra.url";

    RelativeLayout webviewlayout=null;
    PhoneUrlBar urlbar=null;
    UIManager phoneuimanager=null;
    ProgressBar progressbar = null;
    DrawerLayout mrootdrawerlayout=null;
    RelativeLayout tabmanagerlayout=null;
    private RecyclerView webviewnamelist =null;
    boolean filledupwebviewlayout=false;
    WebviewNameListAdapter webviewnamelistadapter=null;
    ImageButton addtab=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initResources();
        this.createRuntimeSetting();
        this.bindUIManager();
        this.handleIntent();
        if(!checkPermissionWRITE_EXTERNAL_STORAGE()){
            this.getPermissionWRITE_EXTERNAL_STORAGE();
        }




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

        if (id==R.id.action_loadinhttps){
            String url=phoneuimanager.getCurrentUrl();
            if(url!=null)
            this.phoneuimanager.loadUrlInHttps(url);
        }

        if(id==R.id.action_newtab){
           this.openNewTab();

        }
        if (id==R.id.action_tabmanage){
            Log.i("webviewnum",""+BrowserContainer.WebViewNum());
            Log.i("webnamelist",BrowserContainer.getWebViewListname().toString());
            openTabManager();
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
            if(tabmanagerlayout.getVisibility()==View.VISIBLE){
                hideTabManager();

            }else{
                if(this.phoneuimanager.onKeyBack()){
                    return true;
                }
                else{
                    //moveTaskToBack(true);
                    finish();
                    return true;
                }

            }



        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            //你的操作
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            //你的操作
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


        webviewlayout=(RelativeLayout)findViewById(R.id.webViewlayout);
        if(filledupwebviewlayout==false) {
            Log.i("web filled flag",""+filledupwebviewlayout);
            addWebviewToLayout(createNewWebview("front"));
        }
        mrootdrawerlayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        tabmanagerlayout=(RelativeLayout)findViewById(R.id.tabs);

        this.webviewnamelist =(RecyclerView) findViewById(R.id.webviewlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.webviewnamelist.setLayoutManager(layoutManager);
        urlbar=(PhoneUrlBar) findViewById(R.id.urlbar);
        progressbar=(ProgressBar)findViewById(R.id.progressbar);
        addtab=(ImageButton)findViewById(R.id.addtab);
        addtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BrowserActivity.this,"onClick单击",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private ItemTouchHelper getItemTouchHelper(){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView rv, RecyclerView.ViewHolder vh) {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                //根据adapterPosition移除item

            }
            @Override
            public void clearView(RecyclerView rv, RecyclerView.ViewHolder viewHolder) {

            }
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

            }
            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {

            }
        });
        return helper;


    }
    public void openTabManager(){
        List<String> resultlist=BrowserContainer.getWebViewListname();
        List<String> datalist= new ArrayList<String>();
        if(resultlist!=null) {
            for (int i = 0; i < resultlist.size(); i++) {
                if (resultlist.get(i) != null) {
                    datalist.add(resultlist.get(i));
                }

            }

            this.webviewnamelistadapter=new WebviewNameListAdapter(getBaseContext());
            this.webviewnamelistadapter.addWebviewNameList(datalist);
            this.webviewnamelistadapter.setTabManager(this);
            this.webviewnamelist.setAdapter(webviewnamelistadapter);
            this.getItemTouchHelper().attachToRecyclerView(webviewnamelist);
        }
        this.tabmanagerlayout.setVisibility(View.VISIBLE);

    }
    public void bindUIManager(){
        this.phoneuimanager = new PhoneUIManager(urlbar, (CustomWebView) BrowserContainer.getCurrent(),progressbar, getApplicationContext());

    }

    public void createRuntimeSetting(){
        RuntimeSetting.setInstance(getApplicationContext());
    }

    public void handleIntent(){
        Intent intent=getIntent();
        if(intent.getAction()==ACTION_OPENURL){
            Log.i("broseractivity","received:"+ACTION_OPENURL);
            String url=intent.getStringExtra(EXTRA_URL);
            this.phoneuimanager.loadUrl(url);
        }
        if(intent.getAction()=="android.intent.action.VIEW"){
            //String [] categories= new String [2];
            //intent.getCategories().toArray(categories);
            String url=intent.getData().toString();
            if(url!=null){
                this.phoneuimanager.loadUrl(intent.getData().toString());
                this.phoneuimanager.hideurl();

            }

        }

    }


    //webview
    private  CustomWebView createNewWebview(String positon){
        Log.i("createnew","webvie");
        CustomWebView tmpwebveiw=new CustomWebView(getApplicationContext());
        if(positon.equals("front")){
            BrowserContainer.addCurrent(tmpwebveiw);

        }else {
            BrowserContainer.add(tmpwebveiw);
        }
        return tmpwebveiw;
    }
    private void addWebviewToLayout(CustomWebView webview){

        this.webviewlayout.removeAllViews();
        this.webviewlayout.addView(webview);
        this.filledupwebviewlayout=true;
        Log.i("web alter fill boolean",""+filledupwebviewlayout);
    }


    //permissions

    public void getPermissionWRITE_EXTERNAL_STORAGE(){
        AndPermission.with(this)
                .requestCode(101)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();
    }
    public void getPermissionCAMERA(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)
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
    public boolean checkPermissionWRITE_EXTERNAL_STORAGE(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
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

    @Override
    public void openNewTab() {
        addWebviewToLayout(createNewWebview("front"));
        this.phoneuimanager.setCurrentWebview((CustomWebView)BrowserContainer.getCurrent());

    }

    @Override
    public void alterToTab(int tabindex) {
        BrowserContainer.setCurrentindex(tabindex);
        phoneuimanager.setCurrentWebview((CustomWebView)BrowserContainer.getCurrent());
    }

    @Override
    public void closeTab(int tabindex) {
        Log.i("tab:","close tab"+tabindex);
        if(tabindex!=BrowserContainer.getCurrentindex()){

            BrowserContainer.remove(tabindex);
        }
    }

    @Override
    public void showTabManager() {
        this.tabmanagerlayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTabManager() {
        this.tabmanagerlayout.setVisibility(View.GONE);

    }
}
