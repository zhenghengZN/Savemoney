package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.HashMap;

import app.CommonData;
import com.zhekouxingqiu.main.fragment.MineFragment;
import so.bubu.lib.helper.Helper;
import com.zhekouxingqiu.main.activity.MainActivity;
import com.zhekouxingqiu.main.fragment.SettingIndependentFragment;
import com.zhekouxingqiu.main.fragment.TaobaoFragment;

/**
 * 类型切换
 * <p/>
 * Created by Administrator on 2016/3/23.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_NUM = 3;

    private TaobaoFragment taobaoFragment;
//    private MyFragment myFragment;
    private MineFragment mineFragment;
    private SettingIndependentFragment settingindependentfragment;
    //    private NewMyFragment userFragment;
    private HashMap<String, Object> parammap;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter(FragmentManager fm, HashMap<String, Object> parammap) {
        super(fm);
        this.parammap = parammap;
    }

//    public MessageFragment getInformationFragment() {
//        return mMessageFragment;
//    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {

            case MainActivity.ABOUT:

                if (Helper.isNull(settingindependentfragment)) {
                    settingindependentfragment = new SettingIndependentFragment();
                }
                fragment = settingindependentfragment;

                break;

            case MainActivity.TAOBAO:

                if (Helper.isNull(taobaoFragment)) {
                    Bundle bd = new Bundle();
                    bd.putSerializable(CommonData.PARAMMAP, parammap);
                    taobaoFragment = new TaobaoFragment();
                    taobaoFragment.setArguments(bd);
                }
                fragment = taobaoFragment;

                break;

            case MainActivity.USER:

                if(Helper.isNull(mineFragment)) {
//                    myFragment = new MyFragment();
                    mineFragment =  new MineFragment();
                }
                fragment = mineFragment;

                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

}
