package ervamed.com.br.ervamed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
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


import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Arrays;

import ervamed.com.br.ervamed.adapter.DataAdapter;
import ervamed.com.br.ervamed.entity.JSONResponse;
import ervamed.com.br.ervamed.model.ModelImagem;
import ervamed.com.br.ervamed.model.ModelPlanta;
import ervamed.com.br.ervamed.service.ervasAPI;
import ervamed.com.br.ervamed.ui.SplashActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        /*
         * Verifica se existe conexão para habilitar o botao de atualização
         */
        if (!verificaConexao()){
            Menu navView = navigationView.getMenu();
            MenuItem navItemView = navView.findItem(R.id.nav_atualizar);
            navItemView.setEnabled(false);
        }


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

    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable() && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
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
                LoadErvas loadErvas = new LoadErvas();
                loadErvas.loadErvas();

                Toast.makeText(this, "Atualizando Ervas", Toast.LENGTH_LONG).show();

                break;
            case R.id.nav_contato:
                Intent intent = new Intent(MainActivity.this, Contato.class);
                this.startActivity(intent);

                Toast.makeText(this, "CONTATO", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
