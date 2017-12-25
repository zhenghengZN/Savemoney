package wiget.dragView.listener;


import android.support.v7.widget.RecyclerView;

public interface OnItemDragListener extends OnItemMoveListener {
    void onStarDrag(RecyclerView.ViewHolder viewHolder);

}
