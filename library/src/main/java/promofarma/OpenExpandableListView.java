package promofarma;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * Created by Fran on 17/09/2014.
 */
public class OpenExpandableListView extends ExpandableListView implements ExpandableListView.OnGroupClickListener {

    public OpenExpandableListView(Context context) {
        super(context);
        setOnGroupClickListener(this);
    }

    public OpenExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnGroupClickListener(this);
    }

    public OpenExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnGroupClickListener(this);
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandGroup(i);
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return true;
    }

    public void collapseAllGroup() {
        ExpandableListAdapter adapter = getExpandableListAdapter();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            collapseGroup(i);
        }
    }

    public void expandAllGroup() {
        ExpandableListAdapter adapter = getExpandableListAdapter();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandGroup(i);
        }
    }
}
