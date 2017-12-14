package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.ResourceHelper;

/**
 * Created by wangwn on 2016/8/22.
 */
public class MessageAdapter extends FragmentPagerAdapter {

//    private DynamicFragment dynamicFragment;
//    private InformationFragment mInformationFragment;

    private String[] tabTitles = new String[] { ResourceHelper.getString(R.string.text_city_information)/*, ResourceHelper.getString(R.string.text_main_attention)*/ };

    public MessageAdapter(FragmentManager fm) {
        super(fm);
    }

//    public DynamicFragment getDynamicFragment() {
//        return dynamicFragment;
//    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {

//            // 消息
//            case 0:
//                if (Helper.isNull(mInformationFragment)) {
//                    mInformationFragment = new InformationFragment();
//                }
//                fragment = mInformationFragment;
//                break;

//            // 动态
//            case 1:
//                if (Helper.isNull(dynamicFragment)) {
//                    dynamicFragment = new DynamicFragment();
//                }
//                fragment = dynamicFragment;
//                break;

        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
