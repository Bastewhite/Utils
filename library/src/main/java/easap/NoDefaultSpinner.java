package easap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import es.utils.R;

/**
 * A modified Spinner that doesn't automatically select the first entry in the list.
 *
 * Shows the prompt if nothing is selected.
 *
 * Limitations: does not display prompt if the entry list is empty.
 */
public class NoDefaultSpinner<T> extends Spinner {

    private List<T> mList;
    private T mSelection;

    public NoDefaultSpinner(Context context) {
        super(context);
    }

    public NoDefaultSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoDefaultSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(List<T> list) {
        mList = list;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<T> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        setAdapter(adapter);

        if (mSelection != null) {
            setSelection(mSelection);
        }
    }

    public void setSelection(T selection) {
        mSelection = selection;
        if (selection != null && mList != null) {
            setSelection(mList.indexOf(selection));
            mSelection = null;
        }
    }

    @Override
    public void setAdapter(SpinnerAdapter orig ) {
        final SpinnerAdapter adapter = newProxy(orig);

        super.setAdapter(adapter);

        try {
            final Method m = AdapterView.class.getDeclaredMethod(
                               "setNextSelectedPositionInt",int.class);
            m.setAccessible(true);
            m.invoke(this,-1);

            final Method n = AdapterView.class.getDeclaredMethod(
                               "setSelectedPositionInt",int.class);
            n.setAccessible(true);
            n.invoke(this,-1);
        }
        catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        if (mList != null)
            setAdapter(mList);
    }

    protected SpinnerAdapter newProxy(SpinnerAdapter obj) {
        return (SpinnerAdapter) java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                new Class[]{SpinnerAdapter.class},
                new SpinnerAdapterProxy(obj));
    }

    public boolean isValid() {
        if (getSelectedItemPosition() == -1) {
            ((TextView)getChildAt(0)).setError(getContext().getString(R.string.campo_obligatorio));
            return false;
        }
        return true;
    }

    /**
     * Intercepts getView() to display the prompt if position < 0
     */
    protected class SpinnerAdapterProxy implements InvocationHandler {

        protected SpinnerAdapter obj;
        protected Method getView;


        protected SpinnerAdapterProxy(SpinnerAdapter obj) {
            this.obj = obj;
            try {
                this.getView = SpinnerAdapter.class.getMethod(
                                 "getView",int.class,View.class,ViewGroup.class);
            }
            catch( Exception e ) {
                throw new RuntimeException(e);
            }
        }

        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            try {
                return m.equals(getView) &&
                       (Integer)(args[0])<0 ?
                         getView((Integer)args[0],(View)args[1],(ViewGroup)args[2]) :
                         m.invoke(obj, args);
            }
            catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        protected View getView(int position, View convertView, ViewGroup parent)
          throws IllegalAccessException {

            if(position < 0) {
                final TextView v = (TextView) LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
                v.setText(getPrompt());
                v.setTextColor(getContext().getResources().getColor(R.color.text_color_hint));
                return v;
            }

            return obj.getView(position,convertView,parent);
        }
    }
} 