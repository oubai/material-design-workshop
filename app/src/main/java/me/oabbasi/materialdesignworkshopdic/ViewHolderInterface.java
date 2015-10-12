package me.oabbasi.materialdesignworkshopdic;


import java.lang.ref.WeakReference;

/**
 * Implement this interface in any view holder for a recycler view adapter of type RecyclerViewAdapter
 * and the type T is the type of data to be used to fill and update the view holder
 */
public interface ViewHolderInterface<T> {
    /**
     * Use this method to initialize the view holder
     * @param adapterWeakReference a WeakReference of the RecyclerViewAdapter
     */
    void setup(WeakReference<RecyclerViewAdapter> adapterWeakReference);

    /**
     * Use this method to update the data in the view holder
     * @param data The date to use for updating
     * @param position The updated position of the view holder
     */
    void update(T data, int position);
}
