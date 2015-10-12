package me.oabbasi.materialdesignworkshopdic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private notesRecyclerViewAdapter notesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton newNote = (FloatingActionButton) findViewById(R.id.newNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewNoteDialog();
            }
        });

        ArrayList<String> mockData = new ArrayList<>();

        RecyclerView noteRecyclerView = (RecyclerView) findViewById(R.id.notesRecyclerView);
        notesRecyclerViewAdapter = new notesRecyclerViewAdapter(new WeakReference<>(noteRecyclerView));
        noteRecyclerView.setHasFixedSize(true);
        LinearLayoutManager eventDatesLayoutManager = new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(eventDatesLayoutManager);
        notesRecyclerViewAdapter.setup(new WeakReference<AppCompatActivity>(this), 0, 0);
        notesRecyclerViewAdapter.setValues(mockData);
        noteRecyclerView.setAdapter(notesRecyclerViewAdapter);
    }

    private void openNewNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notesRecyclerViewAdapter.addItem(input.getText().toString().trim());
                //TODO add it to DB then open new view
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    class NotesViewHolder extends ViewHolder<String> {

        private TextView noteTitle;
        private int position = -1;

        /**
         * The default required constructor for the ViewHolder
         *
         * @param itemView The inflated view to be used by the ViewHolder
         */
        protected NotesViewHolder(View itemView) {
            super(itemView);
            this.noteTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            noteTitle.setOnClickListener(this);
            ImageButton deleteNote = (ImageButton) itemView.findViewById(R.id.deleteNote);
            deleteNote.setOnClickListener(this);
        }

        @Override
        public void update(String data, int position) {
            this.noteTitle.setText(data);
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.deleteNote:
                    adapter.get().removeItem(position);
                    break;
                case R.id.noteTitle:
                    //TODO open next view
                    break;
            }
        }
    }

    class notesRecyclerViewAdapter extends RecyclerViewAdapter<String> implements View.OnClickListener {

        /**
         * Construct the adapter
         *
         * @param recyclerViewWeakReference A WeakReference to the RecyclerView this adapter being attached
         *                                  to
         */
        public notesRecyclerViewAdapter(WeakReference<RecyclerView> recyclerViewWeakReference) {
            super(recyclerViewWeakReference);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_recyclerview_item, parent, false);

            NotesViewHolder notesViewHolder = new NotesViewHolder(v);
            ViewHolder vh = notesViewHolder;

            vh.setup(new WeakReference<RecyclerViewAdapter>(this));


            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ((NotesViewHolder) holder).update(mDataSet.get(position), position);
        }

        @Override
        public void setup(WeakReference<AppCompatActivity> activityWeakReference, int itemsBeforeCount, int itemsAfterCount) {
            this.activityWeakReference = activityWeakReference;
        }

        @Override
        public void itemRemoved(int position, String removedItem) {
            Snackbar.make(recyclerViewWeakReference.get(), "Note Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", this).show();
        }

        @Override
        public void onClick(View v) {
            undoRemove();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
