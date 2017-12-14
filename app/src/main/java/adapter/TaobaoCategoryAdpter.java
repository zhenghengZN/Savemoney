package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import app.CommonData;
import com.zhekouxingqiu.main.fragment.TaobaoContentFragment;


/**
 * Created by zhengheng on 17/9/12.
 */
public class TaobaoCategoryAdpter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private TaobaoContentFragment taobaoFragment;
    private ArrayList<HashMap<String, Object>> Params = new ArrayList<>();
    private HashMap<Integer, TaobaoContentFragment> fragmentlist = new HashMap<>();

    public TaobaoCategoryAdpter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public TaobaoCategoryAdpter(FragmentManager fm, ArrayList<HashMap<String, Object>> Params) {
        super(fm);
        this.Params = Params;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentlist.get(position) != null) {
            taobaoFragment = fragmentlist.get(position);
        } else {
            taobaoFragment = new TaobaoContentFragment();
            Bundle bundle = new Bundle();
            HashMap<String, Object> categoryMap = Params.get(position);
            bundle.putSerializable(CommonData.PARAMMAP, categoryMap);
            taobaoFragment.setArguments(bundle);
            fragmentlist.put(position, taobaoFragment);
        }
        return taobaoFragment;
    }

    @Override
    public int getCount() {
        return Params.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Params.get(position).get(CommonData.CATEGORY).toString();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentlist.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }

}
