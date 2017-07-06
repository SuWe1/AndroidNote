package com.ysw.user_defined_view.swipeDelete_dragChange_recyclerview;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.ysw.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Swy on 2017/7/6.
 */

public class MyViewActivity extends AppCompatActivity {
    private static final String TAG = "MyViewActivity";
    
    @Bind(R.id.swipeDeleteDragChangeRecyclerview)
    RecyclerView swipeDeleteDragChangeRecyclerview;

    MyRecyclerViewAdapter adapter;
    private List<String> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipedelete_dragchange_recyclerview);
        ButterKnife.bind(this);
        list=new ArrayList<>();
        for (int i=0;i<50;i++){
            list.add("RecyclerView Item　"+i);
        }
        adapter=new MyRecyclerViewAdapter(this,list);
        addSwipeDeleteAndDragChange(swipeDeleteDragChangeRecyclerview);
        swipeDeleteDragChangeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        swipeDeleteDragChangeRecyclerview.setItemAnimator(new DefaultItemAnimator());
        swipeDeleteDragChangeRecyclerview.setHasFixedSize(true);
        swipeDeleteDragChangeRecyclerview.setAdapter(adapter);

    }

    /**
     * dragDirs - 表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
       swipeDirs - 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
     */
    ItemTouchHelper.Callback callback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
        /**
         *
         * @param recyclerView　绑定的RecyclerView
         * @param viewHolder 需要拖动的ViewHolder
         * @param target　　目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from=viewHolder.getAdapterPosition();
            int to=target.getAdapterPosition();
            /**
             * 这里拖动的想法是往下拖动则把中间的所有item上移
             * 往下则把所有的item下移
             * 如果想实现拖动前后位置item的交换　　即只需要交换两个位置的item
             */
            if (from<to){
                //中间上移
                for (int i=from;i < to;i++){
                    Collections.swap(list,i,i+1);
                }
            }else {
                for (int i=from;i>to;i--){
                    Collections.swap(list,i,i-1);
                }
            }
            adapter.notifyItemMoved(from,to);
            //return true表示执行移动
            return true;
        }

        /**
         *
         * @param viewHolder 滑动的RecyclerView
         * @param direction 滑动的方向
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            list.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
         Log.v(TAG,"onSelectedChanged");
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
            Log.v(TAG, "clearView");
        }
    };

    private  void  addSwipeDeleteAndDragChange(RecyclerView recyclerView){
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }
}
