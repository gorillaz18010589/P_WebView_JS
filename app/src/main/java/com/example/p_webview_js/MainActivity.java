package com.example.p_webview_js;
//瀏覽器有ip定位沒有gps定位,但手機有
//跟行動裝置沒有關係的話用瀏覽器就好
//**目的連接webView,1.讓頁面導入我們的webView 2.按下返回鍵ok 3.讓js顯示出來
//你的用戶不用在那邊搜尋
//可以照相,gps定位,資料可以傳遞,這是網頁不同,可以掃條碼,這是app的好處,沒有這些功能用web-view就好
//1.配置webview夜面,initWebView();
//2.網路權限開啟<uses-permission android:name="android.permission.INTERNET"/>
//3.使用明瑪傳送true   android:usesCleartextTraffic="true"
//4.js才是玩web-view的重點

//**以下目的是讓我們安卓也可以撰寫js方法
//1.app => new => Folder => Assets Forder 資源不大包進去還ok,可把關卡的資料放這
//2.在assets新增File寫網頁html
//3.也可以套bootstrap要intentr權限
//4.也可以寫js,但有一些類似alert等止在瀏覽器有用,行動裝置沒有
//5.如果要加入圖片assets=>Directory=>imgs
//6.也可以連接到第二html還有跟行動裝置元件互動

//goggle map要這功能要信用卡,goggle地圖
//有兩種方式,目前這種事javascript
//1.搜尋google map javascript方式
//2.複製他的hellow方式,寫一頁新的html,其中有一個key參數就是等你信用卡寫好後,改植才能傭有的功能
//map:https://developers.google.com/maps/documentation/javascript/tutorial
//gps:要打開這兩個設定
//<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
//brad.html當你輸入數值,要用java來抓

//補充
/*WebViewClient 讓View設定一個客戶端角色如果只用webView.loadView就只是一個畫面而已
*回到上一頁因為上一頁原生就是返回所以要重寫onBack
* 要玩JS的話要先取得ＷebSetting取得可以使用js才能支援Js
*
* */
/*6.也可以用寫網頁的JS來寫APP的ui,這邊示範加上boostrap的東西,跟將圖片放在資源黨縮放顯示
*file/folder/assets ->在設計一些遊戲關卡可放這網頁也放這,可以跟專案一起包出去,另外一種太大事放在sdCard上,所以線上遊戲都是先從app下載,所以小遊戲放在assets就好
* assets:寫html可以放這邊,css/html/js的東西要連Jqery跟Js也可以,但JSp等要伺服器的不能在這邊用
*這招是可以讀取本幾我寫的網頁webView.loadUrl("file:///android_asset/brad.html");//file內部檔案:三根斜線通訊協定/安卓程式_asset存放區不加s因為只有單一頁面/html頁面
* file://是本幾通訊協定/第三根的開始android_asset檔案沒有s因為這是一個資源
* 也可以接boostrap記得把它js,cs要引入在html檔案網路權限要開
* 行動裝置是無法玩到alert得因為那是js才有andorid stuido沒有
* 以下兩句控制你寫的js方法在行動裝置上顯示的大小
*   Settings.setUseWideViewPort(true);//設定使用者的圖片跟螢幕裝置一樣寬
*  Settings.setLoadWithOverviewMode(true);//設定當你讀黨時圖片寬就跟全景一樣寬
*  */

/*7.可以開啟我們自己html寫的圖片
*assets -> diectory 新增一個圖片區
* 將圖片放在imgs -> 在html src取得圖片
* 在html檔案加入我們放在安著上的圖片<img src="imgs/bg2.png">
<!-- 引入圖片同時連接到第二頁html -->
<a href="testjs.html">
    <img src="imgs/bg2.png"></a>
* */

/*8.寫js德方法,重點是按下安著的按鈕可以玩js,且可以把資料灌到webView,這件事如果通的話抓到gps就灌到googlemap去
*testjs這格式點網頁寫的js當然是可以
* 呼叫本頁面自己寫的js方法,方法名要一模一樣webView.loadUrl("javascript:test1()");
* 在editText輸入值帶到js的參數去產生樂透 => webView.loadUrl("javascript:test1("+num.getText().toString()+")");
* 在網頁的js document裡面網頁東西都是字串
*
* */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);//初始化抓到webview
        num = findViewById(R.id.num);
        initWebView();
    }

    private void initWebView() {
//        webView.loadUrl("https://www.iiiedu.org.tw"); //連接外部url要接的web("網址");
        webView.loadUrl("file:///android_asset/hank.html");//file內部檔案:三根斜線通訊協定/安卓程式_asset存放區不加s因為只有單一頁面/html頁面
        WebViewClient webViewClient = new WebViewClient();//網頁視野客戶端處理物件
        webView.setWebViewClient(webViewClient);//設定網頁客戶端使用(webViewClient ),這樣使用我們自己的view到下一頁
        // 3.設定讓js可以使用
        WebSettings Settings = webView.getSettings();//webView.取得設定物件(回傳WebSettings)
        Settings.setJavaScriptEnabled(true); //設定js是否開啟(bollean是/否)
        //4.一開始圖片很大張,讓圖片可以縮放,打開三個設定才能進行縮放
        Settings.setSupportZoom(true);//設定圖片支援縮放(是/否)
        Settings.setBuiltInZoomControls(true);//設定內部的縮放控制開關(是/否)
        Settings.setDisplayZoomControls(true);//設定顯示縮放控制開關(是/否)

        //5.讓圖片大小直接跟你的螢幕寬依樣,這會控制你自己寫的js檔案縮放大小
        Settings.setUseWideViewPort(true);//設定使用者的圖片跟螢幕裝置一樣寬
        Settings.setLoadWithOverviewMode(true);//設定當你讀黨時圖片寬就跟全景一樣寬

    }



    public void goForward(View view) {
        webView.goForward();
    }

    //重整
    public void reload(View view) {
        webView.reload();
    }

    //這顆按鈕是手機原生的按下去可以玩我寫的js方法出樂透
    public void lottery(View view) {
        webView.loadUrl("javascript:test1("+num.getText().toString()+")");
    }


    //按下返回見時,keyCode等於4,但真的會死掉的點在onBackPressed(),所以不要super的話按下keyDonw會抓到4但不會關掉
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("hank","onKeyDown" +"code:" + keyCode);
        if(keyCode == 4 && webView.canGoBack()){ //如果按下返回而且webView還有可以回去的頁面
            webView.goBack(); //回上一頁
            return true; //return ture 才不會跑道super.onKeyDown(keyCode, event);又下去了
        }
        return super.onKeyDown(keyCode, event); // =>這行是在叫onBackPressed()的
    }

    //按下返回鍵時
    @Override
    public void onBackPressed() {
        Log.v("hank","onBackPressed");
//        super.onBackPressed();
    }
}
