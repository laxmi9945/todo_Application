package com.app.todo.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.todo.R;
import com.app.todo.adapter.RecyclerAdapter;
import com.app.todo.baseclass.BaseActivity;
import com.app.todo.database.DataBaseUtility;
import com.app.todo.fragment.AboutFragment;
import com.app.todo.fragment.MyFragment;
import com.app.todo.fragment.NotesFragment;
import com.app.todo.model.NotesModel;
import com.app.todo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class TodoNotesActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, CallBackItemClick {
    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;
    boolean isListView = false;
    RecyclerAdapter recyclerAdapter;
    SharedPreferences sharedPreferences;
    public List<NotesModel> mdata = new ArrayList<>();
    CardView cardView;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    private Menu menu;
    DataBaseUtility dataBaseUtility;
    AppCompatTextView titleTextView;
    AppCompatTextView dateTextview;
    AppCompatTextView contentTextview;
    protected OnBackPressedListener onBackPressedListener;
    Cursor cursor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);
        initView();
        initSwipeView();
        setSupportActionBar(toolbar);
        dataBaseUtility=new DataBaseUtility(this);
        List<NotesModel> notesModels=dataBaseUtility.getDatafromDB();
        //cursor.moveToFirst();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this,notesModels,this);
        recyclerView.setAdapter(recyclerAdapter);
        sharedPreferences = getSharedPreferences(Constants.keys, Context.MODE_PRIVATE);
        floatingActionButton.setVisibility(View.VISIBLE);

    }

    public interface OnBackPressedListener {
        void doBack();

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }

    private void initSwipeView() {
/*
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                // recyclerAdapter.remove(position);
                                recyclerAdapter.notifyItemRemoved(position);
                                recyclerAdapter.notifyItemRangeChanged(position, recyclerAdapter.getItemCount());
                            }
                        });

        //touchListener.setDismissDelay=3000;
        recyclerView.setOnTouchListener(touchListener);
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Snackbar snackbar = Snackbar
                                .make(getCurrentFocus(), "Message is deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Snackbar snackbar1 = Snackbar.make(getCurrentFocus(), "Message is restored!", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                });

                        snackbar.show();
                    }
                }));*/

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {

                    recyclerAdapter.deleteItem(position);
                   /* ((ViewGroup) view.getParent()).removeView(view);
                    recyclerView.setAdapter(recyclerAdapter);*/
                }
                else
                {

                    recyclerAdapter.archiveItem(position);
                    //((ViewGroup) view.getParent()).removeView(view);
                    recyclerView.setAdapter(recyclerAdapter);
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /*@Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Close app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TodoNotesActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
*/
    @Override
    public void initView() {

        View view = getLayoutInflater().inflate(R.layout.activity_todonotes, null, false);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_button);
        recyclerView = (RecyclerView) findViewById(R.id.myrecyclerView);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        titleTextView = (AppCompatTextView) view.findViewById(R.id.title_TextView);
        dateTextview = (AppCompatTextView) view.findViewById(R.id.date_TextView);
        contentTextview = (AppCompatTextView) view.findViewById(R.id.content_TextView);
        cardView = (CardView) view.findViewById(R.id.myCardView);
        setClicklistener();
    }

    @Override
    public void setClicklistener() {
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_notes_action, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.changeview) {
            toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        MenuItem item = menu.findItem(R.id.changeview);
        if (!isListView) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            item.setIcon(R.drawable.listbutton);
            isListView = true;
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            item.setIcon(R.drawable.gridbutton);
            isListView = false;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String titleData = "";
        String contentData = "";
        String recentTimeData = "";

        if (resultCode == RESULT_OK) {

            Bundle bundle = data.getBundleExtra("bundle");
            if (bundle != null) {
                Log.i("g", "onActivityResult: ");
                titleData = bundle.getString("Title_data");
                contentData = bundle.getString("Content_data");
                recentTimeData = bundle.getString("date_data");
            }
            NotesModel note = new NotesModel();
            note.setTitle(titleData);
            note.setContent(contentData);
            note.setDate(recentTimeData);
            Log.i("abc", "onActivityResult: ");
            recyclerAdapter.addNotes(note);
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclerAdapter);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_button:

                Intent intent = new Intent(this, NotesAddActivity.class);
                startActivityForResult(intent, 2);


                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.notes) {
            fragment = new NotesFragment();
            toolbar.setTitle("Notes");
        } else if (itemId == R.id.reminder) {
            Toast.makeText(this, getString(R.string.logic), LENGTH_SHORT).show();
        } else if (itemId == R.id.about) {
            fragment = new AboutFragment();
            toolbar.setTitle("About");
        } else if (itemId == R.id.nav_share) {
            Toast.makeText(this, getString(R.string.logic), LENGTH_SHORT).show();

        } else if (itemId == R.id.logout) {
            SharedPreferences.Editor shEditor = sharedPreferences.edit();
            if (sharedPreferences.contains("login")) {

                Log.i("check", "onOptionsItemSelected: ");
                shEditor.putString("login", "false");
                shEditor.commit();
                Intent intent = new Intent(TodoNotesActivity.this, LoginActivity.class);
                Toast.makeText(TodoNotesActivity.this, getString(R.string.logout), LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
            Toast.makeText(TodoNotesActivity.this, getString(R.string.logout_success), LENGTH_SHORT).show();
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.myframe_layout, fragment);
            transaction.commit();
            drawer.closeDrawers();
            return true;
        }
        return false;
    }
    public void editedNote(NotesModel notesModel){
        recyclerAdapter.addNotes(notesModel);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemClick(NotesModel model) {
        MyFragment fragment = new MyFragment(this);
        Bundle args = new Bundle();

        args.putString("title",model.getTitle());
        args.putString("content",model.getContent());

        fragment.setArguments(args);
        getFragmentManager().beginTransaction().add(R.id.myframe_layout, fragment).commit();
    }

}
