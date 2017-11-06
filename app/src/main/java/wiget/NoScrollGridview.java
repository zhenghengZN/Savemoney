package wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不可滑动
 * <p/>
 * Created by Administrator on 2016/3/23.
 */
public class NoScrollGridview extends GridView {

    public NoScrollGridview(Context context) {
        super(context);
    }

    public NoScrollGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 通过重新dispatchTouchEvent方法来禁止滑动
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (MotionEvent.ACTION_MOVE == ev.getAction()) {
//            // 禁止Gridview进行滑动
//            return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    /**
     * 重写该方法，达到使Gridview适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
