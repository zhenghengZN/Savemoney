package so.bubu.lib.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import so.bubu.lib.helper.Helper;


/**
 * function: 适配器基类
 * @param <T>
 * 
 * @ author:linhuan
 */
public abstract class BasicsAdapter<T> extends BaseAdapter {

	public Context act;

	public List<T> dataList;									// 数据
	public boolean hasTopView;									// 是否有头数据

	public BasicsAdapter(Context act) {
		this.act = act;
		this.hasTopView = setIsTopView();
	}

	public BasicsAdapter(Context act, List<T> dataList) {
		this.act = act;
		this.dataList = dataList;
		this.hasTopView = setIsTopView();
	}

	@Override
	public int getCount() {
		return Helper.isNull(dataList) ? 1 : (hasTopView ? getDataCount(dataList.size()) : dataList.size());
	}

	@Override
	public Object getItem(int position) {
		return Helper.isNull(dataList) ? 0 : dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 有头
		if (Helper.isNull(dataList) || (Helper.isEmpty(dataList) && hasTopView)) {
			View view = getEmptyView(0);
			if (Helper.isNotNull(view)) {
				return view;
			}
		}

		getData(position);
		convertView = viewIsNull(convertView) ? setViewHolder(convertView) : getViewHolder(convertView);
		setItemData(position);

		return convertView;
	}

	/**
	 * 获取数据
	 *
	 * @param position
	 */
	protected void getData(int position) {}

	/**
	 * function: 若为0则返回1，其他不变
	 *
	 * @param size
	 * @return
	 *
	 * @ author:linhuan 2014-9-5 上午9:53:24
	 */
	public int getDataCount(int size) {
		return 0 == size ? 0 : size;
	}

	/**
	 * function: 返回没有数据界面
	 *
	 * @param size
	 * @return
	 *
	 * @ author:linhuan 2014-9-5 上午9:55:11
	 */
	protected View getEmptyView(int size) {
		return 0 == size ? new View(act) : null;
	}

	/**
	 * function: 设置是否有头布局
	 *
	 * @return
	 *
	 * @ author:linhuan 2015-1-13 下午1:53:00
	 */
	protected boolean setIsTopView() {
		return false;
	}

	/**
	 * function: view是否为空
	 *
	 * @param convertView
	 * @return
	 *
	 * @ author:linhuan 2015-1-13 下午1:42:06
	 */
	protected boolean viewIsNull(View convertView) {
		return Helper.isNull(convertView);
	}

	/**
	 * function: 无view设置item
	 *
	 * @param convertView
	 * @return
	 *
	 * @ author:linhuan 2015-1-13 下午1:46:21
	 */
	protected abstract View setViewHolder(View convertView);

	/**
	 * function: 有view获取item
	 *
	 * @param convertView
	 * @return
	 *
	 * @ author:linhuan 2015-1-13 下午1:47:19
	 */
	protected abstract View getViewHolder(View convertView);

	/**
	 * function: 设置item数据
	 *
	 * @ author:linhuan 2015-1-13 下午1:48:03
	 */
	protected void setItemData(int position) {

	}

}
