package ervamed.com.br.ervamed;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import ervamed.com.br.ervamed.adapter.DataAdapter;
import ervamed.com.br.ervamed.model.ModelPlanta;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<ModelPlanta> list;
    private RecyclerView recyclerView;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Cria um objeto Toolbar e define o mesmo com o XML toolbar criado
         */
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        /*
         * Cria um objeto DrawerKayout (aba lateral da esquerda com algumas opções
         * e define o mesmo com o XML drawer_layout criado, nesse caso não é a aba em si,
         * mas sim o conteudo da tela principal, o design da aba lateral é o NavagationView
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        /*
         * o Objeto toggle é criado recebendo como parametro a tctivity do contexto atual,
         * e recebe o drawer e toobar criado acima
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*
         * Cria um Objeto NavigationView e define o mesmo com o XML nav_view criado,
         * este sim é o layout da aba lateral da esquerda, é nele que é definido as opções do menu.
         */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showPlantas();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                Toast.makeText(this, item.getItemId(), Toast.LENGTH_SHORT).show();
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    public void showPlantas(){
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        list = (ArrayList<ModelPlanta>) new SQLite().select().from(ModelPlanta.class).queryList();
        adapter = new DataAdapter(this, list);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_referencias:
                AlertDialog.Builder alertDialogR = new AlertDialog.Builder(MainActivity.this);
                View mViewR = getLayoutInflater().inflate(R.layout.referencias_bibliograficas, null);
                alertDialogR.setView(mViewR);
                AlertDialog dialogR = alertDialogR.create();
                dialogR.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogR.show();
                break;

            case R.id.nav_about:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_about, null);
                alertDialog.setView(mView);
                AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

            case R.id.nav_atualizar:
                Toast.makeText(this, "ATUALIZAR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_contato:
                Toast.makeText(this, "CONTATO", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
