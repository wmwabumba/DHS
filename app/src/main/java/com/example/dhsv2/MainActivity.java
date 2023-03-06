package com.example.dhsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    //creating the adapter object from the useradapter class
    private UserAdapter adapter;
    //creating the  recycler view;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the recylerview using ID and assigning it the context
        //and also ensuring that the recycler view has fixed size
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //creating a retrofit instance to read the API and convert the data to readable format using Gson
        //imported the retrofit and Gson implementations in the gradle file
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://exercise-b342.restdb.io/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //passing the json class with get method
        JSONPlaceholder jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<List<User>> call =  jsonPlaceholder.getUser();
        //using the call variable above to call the reponse
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                //checking of the response is not successful to return the corresponding response code
                // otherwise get the data
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }
                //code to execute when the is a successful connection
                //this will assign the output to the adapter
                List<User> userList = response.body();
                adapter = new UserAdapter(MainActivity.this, userList);
                recyclerView.setAdapter(adapter);


            }

            //getting the feedback in of a failure
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //creating the search view
    //assigning the menu.xml layout to the menu
    //setting action as when user is typing in the search view
    //calling the getFilter method from the user adapter to do search as one is typing
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Filter by city");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //ada

                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}