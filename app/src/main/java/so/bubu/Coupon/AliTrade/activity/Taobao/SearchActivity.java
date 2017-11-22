package so.bubu.Coupon.AliTrade.activity.Taobao;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import adapter.SearchHistoryAdapter;
import app.CommonData;
import common.base.TitleAppCompatActivity;
import greendao.bean.History;
import greendao.dao.HistoryDao;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.Coupon.AliTrade.activity.SearchResultActivity;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.DelayTaskHelper;
import so.bubu.lib.helper.DeviceHelper;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.lib.wiget.DelayTask;
import utils.dbUtils.DbManager;
import wiget.NoScrollListView;

/**
 * 搜索界面
 * <p/>
 * Created by Administrator on 2016/4/12.
 */
public class SearchActivity<T> extends TitleAppCompatActivity {

    private static final int SEARCH = 0;
    private static final int SEARCH_HISTORY = 1;
    private static final int DELAY_POP_UP_KEYBOARD = 300;
    private static final int DELAY_POP_DOWN_KEYBOARD = 300;

    private ScrollView svSelect;
    private EditText etSearch;
    private NoScrollListView nsllSelectHistory;

//    private boolean isInputShow;

    private String searchType, lastSearchKey;
    private History history;
    private List<History> histories;

    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initView() {
        super.initView();

        setVisibility(R.id.et_search, View.VISIBLE);

        setSearch();

        svSelect = findView(R.id.sv_select);

        // 键盘延迟弹出
        etSearch = findView(R.id.et_content_search);
        etSearch.setOnKeyListener(onKeyListener);
        etSearch.clearFocus();
        findViewById(R.id.et_search).setOnClickListener(onClickListener);

        nsllSelectHistory = findView(R.id.nsll_select_history);
        svSelect.setVisibility(View.GONE);
        nsllSelectHistory.setOnItemClickListener(onItemClickListener);
        nsllSelectHistory.setOnScrollListener(new CommonOnScrollListener());

        findViewById(R.id.iv_search_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH, BaseApplication.getInstance().getResources().getColor(R.color.color_menu_infor)));
        findViewById(R.id.tv_clear_history).setOnClickListener(onClickListener);
        findViewById(R.id.ll_content).setOnClickListener(onClickListener);
        findViewById(R.id.iv_search_poi).setOnClickListener(onClickListener);
    }

    @Override
    protected void initData() {
        super.initData();

        lastSearchKey = "";

        histories = new ArrayList<>();
        searchHistoryAdapter = new SearchHistoryAdapter(this, histories);
        nsllSelectHistory.setAdapter(searchHistoryAdapter);

        // 搜索框动画
        findViewById(R.id.ll_history).setVisibility(View.GONE);
        if (DeviceHelper.isBrand(DeviceHelper.BRAND_HUAWEI)) {
            // 华为
            findViewById(R.id.ll_history).setVisibility(View.VISIBLE);
        } else {
            showAnim(R.anim.view_search_top_in, R.id.rl_search, true);
        }

        addAsynTask(SEARCH_HISTORY);

        DelayTaskHelper.doDelayTask(DELAY_POP_UP_KEYBOARD, new SearchOnDelayExecuteListener(SEARCH));
    }

    private class InitSearchAnimListener implements Animation.AnimationListener {

        private boolean searchFlag;

        public InitSearchAnimListener(boolean searchFlag) {
            this.searchFlag = searchFlag;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (searchFlag) {
                findViewById(R.id.rl_search).clearAnimation();
                findViewById(R.id.ll_history).setVisibility(View.VISIBLE);
                showAnim(R.anim.view_search_top_in, R.id.ll_history, false);
            } else {
                findViewById(R.id.ll_history).clearAnimation();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private void showAnim(int animId, int viewAnimId, boolean animFlag) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        animation.setAnimationListener(new InitSearchAnimListener(animFlag));
        findViewById(viewAnimId).startAnimation(animation);
    }

    private class CommonOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
//            super.onScrollStateChanged(view, scrollState);
            switch (scrollState) {

                // 滚动状态关闭软键盘
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    // 触摸后滚动关闭软键盘
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                    if (isInputShow) {
                    InputMethodHelper.closeInputMethod(SearchActivity.this);
//                        isInputShow = false;
//                    }
                    break;

            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    @Override
    protected boolean isSwipeback() {
        return false;
    }

    class SearchOnDelayExecuteListener implements DelayTask.OnDelayExecuteListener {

        private int type;

        public SearchOnDelayExecuteListener(int type) {
            this.type = type;
        }

        @Override
        public void onProgressUpdate() {
        }

        @Override
        public void onPreExecute() {
        }

        @Override
        public void onPostExecute() {
            switch (type) {

                case SEARCH:
                    etSearch.setFocusable(true);
                    etSearch.setFocusableInTouchMode(true);
                    etSearch.requestFocus();
                    break;

                case SEARCH_HISTORY:
                    NavigationHelper.finish(SearchActivity.this, RESULT_OK, null);
                    break;

            }
        }

    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                search();
                Bundle bundle = new Bundle();
                HashMap<String, Object> parammap = new HashMap<>();
                parammap.put(CommonData.KEYWORD, etSearch.getText().toString());
                bundle.putSerializable(CommonData.PARAMMAP, parammap);
                NavigationHelper.slideActivity(SearchActivity.this, SearchResultActivity.class, bundle, false);
                finish();
                return true;
            }
            return false;
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSearchKey(histories.get(position).getSearchContent(), false);
            Bundle bundle = new Bundle();
            HashMap<String, Object> parammap = new HashMap<>();
            parammap.put(CommonData.KEYWORD, etSearch.getText().toString());
            bundle.putSerializable(CommonData.PARAMMAP, parammap);
            NavigationHelper.slideActivity(SearchActivity.this, SearchResultActivity.class, bundle, false);
            finish();
        }
    };

    @Override
    protected void search() {
        setSearchKey(etSearch.getText().toString(), true);
    }

    private void goSearch(String search) {
        if (lastSearchKey.equals(search)) {
            finish();
            return;
        }
        lastSearchKey = search;
        history = new History(search, searchType, new Date());
    }

    private void setSearchKey(String search, boolean isShowHistory) {

        if (Helper.isEmpty(search)) {
            ToastHelper.showToast(R.string.text_city_no_search_key);
        } else {
            etSearch.setText(search);
            LogUtil.log.e("search", "search" + search);
            etSearch.setSelection(search.length());

            // 键盘关闭
            InputMethodHelper.closeInputMethod(this);
//            isInputShow = false;

//            searchString = search;
            goSearch(search);

            if (isShowHistory) {
                // 避免数据重复
                boolean isAddHistory = true;
                for (History oldHistory : histories) {
                    if (oldHistory.getSearchContent().equals(search)) {
                        isAddHistory = false;
                        break;
                    }
                }
                if (isAddHistory) {
                    histories.add(0, history);
                    DbManager.getInstance().insertOrReplaceInTx(DbManager.getInstance().getHistoryDao(), history);
                }
            }

        }
    }

    @Override
    protected void startBackground(int type) {
        switch (type) {

            case SEARCH_HISTORY:
                histories.clear();
                histories.addAll(DbManager.getInstance().getSelectData(DbManager.getInstance().getHistoryDao(), HistoryDao.Properties.SearchDate));
                break;

        }
    }

    @Override
    protected void postExecute(int type) {
        switch (type) {

            case SEARCH_HISTORY:
                showSearchHistory();//显示svSelect
                break;

        }
    }

    private void showSearchHistory() {

        if (Helper.isNotEmpty(histories)) {
            svSelect.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                // 清空搜索历史
                case R.id.tv_clear_history:
                    DbManager.getInstance().deleteAll(DbManager.getInstance().getHistoryDao());
                    svSelect.setVisibility(View.GONE);
                    histories.clear();
                    searchHistoryAdapter.notifyDataSetChanged();
                    break;

                // 点击输入框
                case R.id.et_search:
                    showSearchHistory();
                    break;

                case R.id.ll_content:
                    InputMethodHelper.closeInputMethod(SearchActivity.this);
                    DelayTaskHelper.doDelayTask(DELAY_POP_DOWN_KEYBOARD, new SearchOnDelayExecuteListener(SEARCH_HISTORY));
                    break;

                case R.id.iv_search_poi:
                    SearchActivity.this.finish();

            }
        }
    };

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }

}
