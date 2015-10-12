package me.oabbasi.materialdesignworkshopdic;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Extend this class to use as a view holder for the RecyclerViewAdapter to have some general and
 * extra functionalities
 */
public abstract class ViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    /**
     * A WeakReference to the RecyclerViewAdapter this ViewHolder is being used in
     */
    protected WeakReference<RecyclerViewAdapter> adapter;

    /**
     * The default required constructor for the ViewHolder
     * @param itemView The inflated view to be used by the ViewHolder
     */
    protected ViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Use this method to handle to click events for the various views inside the ViewHolder
     * @param v The view being clicked/tapped
     */
    @Override
    public void onClick(View v) {

    }

    public void setup(WeakReference<RecyclerViewAdapter> adapter) {
        this.adapter = adapter;
    }
}
