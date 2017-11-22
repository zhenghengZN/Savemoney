package so.bubu.Coupon.AliTrade.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import java.io.Serializable;
import java.util.HashMap;

import app.CommonData;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.Coupon.AliTrade.activity.Taobao.SearchActivity;
import so.bubu.Coupon.AliTrade.fragment.TaobaoContentFragment;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;
import utils.InformationHelper;

public class SearchResultActivity extends TitleAppCompatActivity implements View.OnClickListener {
    private static final String TAG = SearchResultActivity.class.getSimpleName();

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        intent = getIntent();
        setContentView(R.layout.activity_search_result);
        parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
        registerReceiver(new SearchResultReceiver(), new IntentFilter("NEW_RESULT"));
    }

    private TextView title;
    private FragmentManager fm;
    private TaobaoContentFragment taobaoContentFragment;
    private Intent intent;

    @Override
    protected void initView() {
        super.initView();
        fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        title = (TextView) findViewById(R.id.tv_title);
        String pageTitle = (String) parammap.get("pageTitle");
        String keyword = (String) parammap.get(CommonData.KEYWORD);
        if (pageTitle != null) {
            title.setText(pageTitle);
        } else {
            title.setText(keyword);
        }

        changeViewBg(R.id.tv_search_imageview, IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH));
        findViewById(R.id.iv_back_poi_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK));
        findViewById(R.id.iv_back_poi).setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);

        taobaoContentFragment = new TaobaoContentFragment();
        fragmentold = taobaoContentFragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.PARAMMAP, parammap);
        taobaoContentFragment.setArguments(bundle);
        ft.replace(R.id.fragment, taobaoContentFragment);


        // addToBackStack添加到回退栈,addToBackStack与ft.add(R.id.fragment, new
        // MyFragment())效果相当
        // ft.addToBackStack("test");

        ft.commit();
    }


    private HashMap<String, Object> parammap = new HashMap<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.intent = intent;
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        finish();
    }

    private Fragment fragmentold;

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume ");
//        if()
//        FragmentTransaction transaction = fm.beginTransaction();
//        if (fragmentold != null) {
//            transaction.remove(fragmentold);
//        }
//        transaction.commit();
//        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_poi:
                finish();
                break;
            case R.id.tv_search:

//                FragmentTransaction transaction = fm.beginTransaction();
//                if (fragmentold != null) {
//                    transaction.remove(fragmentold);
//                }
//                transaction.commit();

                NavigationHelper.alphaActivityAddsearchTypeData(this, SearchActivity.class, CommonData.TAOBAO, null, false);
                break;
        }
    }


    class SearchResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("NEW_RESULT")) {
//                SearchResultActivity.this.intent = getIntent();
                FragmentTransaction transaction = fm.beginTransaction();
                if (fragmentold != null) {
                    transaction.remove(fragmentold);
                }
                transaction.commit();
                parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
                initView();
            }
        }
    }
}
