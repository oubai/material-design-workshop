package me.oabbasi.materialdesignworkshopdic;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Extend this class in order to implement a recycler view adapter with predefined functionalities
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements RecyclerViewAdapterInterface<T> {
    /**
     * The dataset to be used in the recycler view
     */
    protected ArrayList<T> mDataSet;
    /**
     * A WeakReference to the parent AppCompatActivity
     */
    protected WeakReference<AppCompatActivity> activityWeakReference;
    /**
     * The last item removed index to be used in the undo removal action
     */
    private int removedItemIndex;
    /**
     * The last item removed value to be used in the undo removal action
     */
    private T removedItem;
    /**
     * A WeakReference to the recycler view this adapter belongs to
     */
    protected WeakReference<RecyclerView> recyclerViewWeakReference;
    /**
     * The number of items before the dateset in the recycler view
     */
    protected int itemsBeforeCounter = 0;
    /**
     * The number of items after the dateset in the recycler view
     */
    protected int itemsAfterCounter = 0;


    /**
     * Construct the adapter
     *
     * @param recyclerViewWeakReference A WeakReference to the RecyclerView this adapter being attached
     *                                  to
     */
    public RecyclerViewAdapter(WeakReference<RecyclerView> recyclerViewWeakReference) {
        this.recyclerViewWeakReference = recyclerViewWeakReference;
    }

    /**
     * Use this method to retrieve the size of your dataSet and any extra items before or after the dataset
     *
     * @return The size of your dataSet and any extra views in the recycler view
     */
    @Override
    public int getItemCount() {
        return mDataSet.size() + itemsAfterCounter + itemsBeforeCounter;
    }

    /**
     * Use this method to add an item to the RecyclerView dataSet in a specific location
     * and update the View and generate a notifyDataSetChanged call
     *
     * @param itemToBeAdded The item to be added
     * @param position      The position to add the new item to, if null it will append as the last item
     */
    @Override
    public void addItem(T itemToBeAdded, Integer position) {
        if (position == null) {
            mDataSet.add(itemToBeAdded);
            notifyItemInserted(getItemCount() - itemsAfterCounter - 1);
        } else {
            mDataSet.add(position, itemToBeAdded);
            notifyItemInserted(position + itemsBeforeCounter);
        }
        itemAdded(itemToBeAdded);
    }

    /**
     * Use this method to append an item to the RecyclerView dataSet and update the View and generate
     * a notifyDataSetChanged call
     *
     * @param itemToBeAdded The item to be added
     */
    @Override
    public void addItem(T itemToBeAdded) {
        addItem(itemToBeAdded, null);

    }

    /**
     * Use this method to remove an item to the RecyclerView dataSet and update the View and generate
     * a notifyDataSetChanged and itemRemoved call as well as keep a copy in case of an Undo action
     *
     * @param position The position of the to be removed
     */
    @Override
    public void removeItem(int position) {
        removedItemIndex = position - itemsBeforeCounter;
        removedItem = mDataSet.get(position - itemsBeforeCounter);
        mDataSet.remove(position - itemsBeforeCounter);
        notifyItemRemoved(position);
        notifyItemRangeChanged(itemsBeforeCounter, mDataSet.size() + 1);
        itemRemoved(position, removedItem);
    }

    /**
     * Use this method to undo the removal of the last item removed, mainly to be used from the
     * SnackBar action
     */
    @Override
    public void undoRemove() {
        addItem(removedItem, removedItemIndex);
        notifyItemRangeChanged(itemsBeforeCounter, mDataSet.size() + 1);
    }

    public AppCompatActivity getActivityReference() {
        return this.activityWeakReference.get();
    }

    /**
     * Use this method to get the values in the dataSet of the RecyclerView adapter
     *
     * @return The dataSet of the Adapter
     */
    @Override
    public ArrayList<T> getValues() {
        return this.mDataSet;
    }

    /**
     * Use this method to set the values for the dataSet to be used in the Adapter which would cause
     * a notifyDataSetChanged call
     *
     * @param values The new values for the dataSet
     */
    @Override
    public void setValues(ArrayList<T> values) {
        this.mDataSet = values;
        notifyDataSetChanged();
    }

    /**
     * Use this method to react to a item being added to the dataset
     * @param addedItem The item added
     */
    @Override
    public void itemAdded(T addedItem) {
    }

    /**
     * Use this method to react to an item being removed from the dataset
     * @param position The position the item was removed from
     * @param removedItem The item removed
     */
    @Override
    public void itemRemoved(int position, T removedItem) {
    }
}
