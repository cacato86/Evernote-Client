package com.cct.evernoteclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cct.evernoteclient.Creator.NoteCreatorFactory;
import com.cct.evernoteclient.Domains.ErrorManager;
import com.cct.evernoteclient.Domains.TaskRepositoryFactory;
import com.cct.evernoteclient.Domains.TaskResultInterface;
import com.cct.evernoteclient.Models.Filter;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.Models.User.User;
import com.cct.evernoteclient.View.Adapters.NoteAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ContentLoadingProgressBar progresBar;
    private RecyclerView recycleview;
    private TextView emptyview;
    private NoteAdapter adapter;
    private ArrayList<Note> arrayNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fillHeaderNavigationView(navigationView);

        recycleview = (RecyclerView) findViewById(R.id.recycler_view);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setHasFixedSize(true);

        adapter = new NoteAdapter(MainActivity.this);
        recycleview.setAdapter(adapter);

        progresBar = (ContentLoadingProgressBar) findViewById(R.id.pb);
        emptyview = (TextView) findViewById(R.id.empty_view);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void fillHeaderNavigationView(NavigationView navigationView) {
        View view = navigationView.getHeaderView(0);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView username = (TextView) view.findViewById(R.id.username);

        new TaskRepositoryFactory().getRepository().getUser(new TaskResultInterface<User>() {
            @Override
            public void onSucces(User result) {
                Utils.saveUserName(MainActivity.this, result.getUsername());
                name.setText(result.getName());
                username.setText(result.getUsername());
            }

            @Override
            public void onError(ErrorManager error) {
                Toast.makeText(MainActivity.this, error.getReason(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getNotes(Filter filter) {
        progresBar.show();
        new TaskRepositoryFactory().getRepository().getNotes(filter, new TaskResultInterface<ArrayList<Note>>() {
            @Override
            public void onSucces(ArrayList<Note> result) {
                if (result.size() > 0){
                    arrayNotes = result;
                    adapter.setNoteArray(result);
                    emptyview.setVisibility(View.GONE);
                }else{
                    emptyview.setVisibility(View.VISIBLE);
                }
                progresBar.hide();
            }

            @Override
            public void onError(ErrorManager error) {
                Toast.makeText(MainActivity.this, error.getReason(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            createNoteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.create_note)
                .setItems(R.array.createNote, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                createNote(NoteCreatorFactory.TypeCreators.Keyboard);
                                break;
                            case 1:
                                createNote(NoteCreatorFactory.TypeCreators.OCR);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void createNote(NoteCreatorFactory.TypeCreators type) {
        new NoteCreatorFactory(MainActivity.this).getNoteCreator(type).createNote(new TaskResultInterface<Note>() {
            @Override
            public void onSucces(Note result) {
                recycleview.scrollToPosition(0);
                arrayNotes.add(0, result);
                adapter.animateTo(arrayNotes);
            }

            @Override
            public void onError(ErrorManager error) {
                Toast.makeText(MainActivity.this, error.getReason(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_order_title) {
            getNotes(createFilterByTitle());
        } else if (id == R.id.nav_order_time) {
            getNotes(createFilterByCreation());
        } else if (id == R.id.logout) {
            new TaskRepositoryFactory().getRepository().logout();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Filter createFilterByTitle() {
        Filter filter = new Filter.FilterBuilder()
                .setParameters(Filter.FilterBuilder.FilterParameters.TITLE)
                .setOrder(Filter.FilterBuilder.FilterOrder.ASCENDING)
                .createFilter();
        return filter;
    }

    private Filter createFilterByCreation() {
        Filter filter = new Filter.FilterBuilder()
                .setParameters(Filter.FilterBuilder.FilterParameters.CREATION)
                .setOrder(Filter.FilterBuilder.FilterOrder.DESCENDING)
                .createFilter();
        return filter;
    }
}
