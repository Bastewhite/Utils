package promofarma;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import es.utils.R;


/**
 * Created by Aria on 30/05/2014.
 */
public class SwipeRefreshListView<T extends ListView> extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private LoadMoreItemsListener mLoadMoreItemsListener;
    private int preLast = -1;
    private T mListView;

    public SwipeRefreshListView(Context context, AttributeSet attrs){
        super(context, attrs);

        configureLoadingColors(attrs);

        setOnRefreshListener(this);
    }

    private void configureLoadingColors(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeRefreshListView);
        try {
            if (a.getBoolean(R.styleable.SwipeRefreshListView_swipelv_expandable, false)) {
                ExpandableListView expandableListView = new ExpandableListView(getContext());
                expandableListView.setGroupIndicator(null);
                mListView = (T) expandableListView;
            }
            else {
                mListView = (T) new ListView(getContext());
//                mListView.setSelector(R.drawable.bg_selectable);

                int divider = a.getColor(R.styleable.SwipeRefreshListView_swipelv_listDivider, 0);
                if (divider != 0 && mListView != null)
                {
                    mListView.setDivider(new ColorDrawable(divider));
//                    mListView.setDividerHeight(Utils.convertDpToPixel(2, getContext()));
                }
            }
            mListView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
            mListView.setOnScrollListener(this);
            addView(mListView);

            int color_1 = a.getResourceId(R.styleable.SwipeRefreshListView_swipelv_color_1, android.R.color.holo_blue_bright);
            int color_2 = a.getResourceId(R.styleable.SwipeRefreshListView_swipelv_color_2, android.R.color.holo_green_light);
            int color_3 = a.getResourceId(R.styleable.SwipeRefreshListView_swipelv_color_3, android.R.color.holo_orange_light);
            int color_4 = a.getResourceId(R.styleable.SwipeRefreshListView_swipelv_color_4, android.R.color.holo_red_light);

            setColorSchemeResources(color_1, color_2, color_3, color_4);
        }
        finally {
            a.recycle();
        }
    }

    /**
     * Shows the SwipeRefreshLayout loading with the configured colors combination.
     * @param isRefreshing
     */
    public void setRefreshing(boolean isRefreshing)
    {
        super.setRefreshing(isRefreshing);
    }

    public T getListView()
    {
        return mListView;
    }

    public void setLoadMoreItemsListener(LoadMoreItemsListener loadMoreItemsListener) {
        mLoadMoreItemsListener = loadMoreItemsListener;
    }

    @Override
    public void onRefresh()
    {
        if (mLoadMoreItemsListener != null)
        {
            setPreLast(-1);
            mLoadMoreItemsListener.onLoadMoreItems(0);
        }
    }

    public void setPreLast(int preLast) {
        if (preLast == -1) {
            this.preLast = preLast;
        }
        else {
            this.preLast = preLast + mListView.getHeaderViewsCount();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Sample calculation to determine if the last
        // item is fully visible.
        if (mListView != null && view.getId() == mListView.getId())
        {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount)
            {
                if (preLast != lastItem)
                { //to avoid multiple calls for last item

                    if (mLoadMoreItemsListener != null)
                    {
                        mLoadMoreItemsListener.onLoadMoreItems(lastItem - mListView.getHeaderViewsCount());
                    }

                    preLast = lastItem;
                }
            }
        }
    }

    public interface LoadMoreItemsListener
    {
        public void onLoadMoreItems(int currentItemPos);
    }
}
