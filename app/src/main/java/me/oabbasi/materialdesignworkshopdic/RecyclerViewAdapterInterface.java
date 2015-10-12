package me.oabbasi.materialdesignworkshopdic;

import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Implement this interface when creating an adapter for a recycler view in order to provide some of
 * the basic functionalities needed in most recycler view adapters. The type T is the type of data being
 * recycled which can be of any type, ex: String, HashMap ...etc
 */
public interface RecyclerViewAdapterInterface<T> {
    /**
     * Use this method to remove an item from the recyclerview
     * @param position The position of the item to be removed
     */
    void removeItem(int position);

    /**
     * Use this method to undo the removal of the last item removed
     */
    void undoRemove();

    /**
     * Use this method to retrieve the reference to the parent AppCompatActivity initialize in the setup method
     * @return The parent AppCompatActivity
     */
    AppCompatActivity getActivityReference();

    /**
     * Use this method to intialize the adapter with references to needed elements.
     * @param activityWeakReference A weak reference to the parent AppCompatActivity
     * @param itemsBeforeCount The number of items in the recycler view before the dataset being iterated
     *                         and recycled
     * @param itemsAfterCount The number of items in the recycler view after the dataset being iterated
     *                         and recycled
     */
    void setup(WeakReference<AppCompatActivity> activityWeakReference, int itemsBeforeCount, int itemsAfterCount);

    /**
     * Use this method to access the dataset of the recycler view
     * @return The dataset of type T
     */
    ArrayList<T> getValues();

    /**
     * Use this method to set the dataset of the recycler view and notify DataSetChanged
     * @param values The dataset of type T
     */
    void setValues(ArrayList<T> values);

    /**
     * Use this method to append an item to the dataset in the recyclerview as the last item
     * @param itemToBeAdded The item to be added
     */
    void addItem(T itemToBeAdded);

    /**
     * Use this method to append an item to the dataset in the recyclerview in the specified position
     * @param itemToBeAdded The item to be added
     * @param position The position to add the item at
     */
    void addItem(T itemToBeAdded, Integer position);

    /**
     * Use this method to react to a item being added to the dataset
     * @param addedItem The item added
     */
    void itemAdded(T addedItem);

    /**
     * Use this method to react to an item being removed from the dataset
     * @param position The position the item was removed from
     * @param removedItem The item removed
     */
    void itemRemoved(int position, T removedItem);
}
