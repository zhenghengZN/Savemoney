package wiget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.LogUtil;

import so.bubu.lib.helper.ResourceHelper;

/**
 * Created by zhengheng on 17/11/13.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private boolean hasHead;

    public ItemOffsetDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = ResourceHelper.Dp2Px(spacing);
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column
        int itemViewType = parent.getAdapter().getItemViewType(position);
        if (itemViewType == 0) {
            hasHead = true;
            return;
        }
        LogUtil.log.e("getItemOffsets column", " " + column);
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
//            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)                                           0                   1
//            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)  10-(0 +1)*10/2   10-(1+1)*10/2

            if (position >= spanCount && hasHead == false) {
                outRect.top = spacing / 2; // item top
            } else {
                if (position >= spanCount - 1 && hasHead == true) {
                    outRect.top = spacing / 2;
                }
            }
            if (hasHead == false) {
                outRect.right = spacing - (column + 1) * spacing / spanCount;
            } else {
                if (column == 0) {
                    column = 1;
                } else if (column == 1) {
                    column = 0;
                }
                outRect.right = spacing - (column + 1) * spacing / spanCount;
            }

//            if (position == 1 || position == 2) {
//
//                outRect.top = ResourceHelper.Dp2Px(2);
//            }
        }
    }
}