package com.nftime.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nftime.app.Adapter.TopNftRecyclerAdapter;
import com.nftime.app.objects.ArtistObj;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetActiveArtistsTask;
import com.nftime.app.util.asyncTasks.GetAllWorksTask;

import java.util.ArrayList;
import java.util.Locale;

public class AllWorksActivity extends AppCompatActivity {
    private Context context;
    private Spinner spinnerCategory;
    private Spinner spinnerArtist;
    private RecyclerView rvAllWorks;
    TopNftRecyclerAdapter topNftRecyclerAdapter;

    private boolean afterCreatedCategorySpinner = false;
    private boolean afterCreatedArtistSpinner = false;

    ArrayList<NftWorkObj> nftWorkObjs;

    String[] category = {"Category", "Digital", "Drawing", "illust", "Fine Art"};
    String[] artist = {"Artist"};

    private String currentCategory;
    private String currentArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_works);

        context = this;

        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerArtist = findViewById(R.id.spinner_artist);
        rvAllWorks = findViewById(R.id.rv_all_works);

        new GetActiveArtistsTask(new AsyncResponse<ArtistObj[]>() {
            @Override
            public void onAsyncSuccess(ArtistObj[] result) {
                if(result != null){
                    artist = new String[result.length + 1];
                    artist[0] = "Artist";
                    for(int i = 0; i < result.length; i++){
                        artist[i + 1] = result[i].name;
                    }
                }

                // Spinner Setting
                ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, category);
                adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(adapterCategory);

                ArrayAdapter<String> adapterArtist = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, artist);
                adapterArtist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerArtist.setAdapter(adapterArtist);

                currentCategory = category[0];
                currentArtist = artist[0];

                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(afterCreatedCategorySpinner){
                            currentCategory = category[position];
                            topNftRecyclerAdapter.setTopArtList(nftFilter(currentCategory, currentArtist));
                        }
                        else {
                            afterCreatedCategorySpinner = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerArtist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(afterCreatedArtistSpinner){
                            currentArtist = artist[position];
                            topNftRecyclerAdapter.setTopArtList(nftFilter(currentCategory, currentArtist));
                        }
                        else {
                            afterCreatedArtistSpinner = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }).execute();

        // Layout Setting
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvAllWorks.setLayoutManager(gridLayoutManager);

        new GetAllWorksTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                nftWorkObjs = ArrayHelper.toArrayList(result);

                topNftRecyclerAdapter = new TopNftRecyclerAdapter(context);
                rvAllWorks.setAdapter(topNftRecyclerAdapter);
                topNftRecyclerAdapter.setTopArtList(nftWorkObjs);
            }
        }).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<NftWorkObj> nftFilter(String cat, String art){
        String cat_l = cat.toLowerCase(Locale.ROOT);

        ArrayList<NftWorkObj> nftWorkObjsNew = new ArrayList<>();
        if(cat.equals(category[0]) && art.equals(artist[0])){
            return nftWorkObjs;
        }
        else if(cat.equals(category[0])){
            nftWorkObjs.forEach(v -> {
                if(v.artist_name.equals(art))
                    nftWorkObjsNew.add(v);
            });
        }
        else if(art == artist[0]){
            nftWorkObjs.forEach(v -> {
                if(v.category.equals(cat_l))
                    nftWorkObjsNew.add(v);
            });
        }
        else {
            nftWorkObjs.forEach(v -> {
                if(v.category.equals(cat_l) && v.artist_name.equals(art))
                    nftWorkObjsNew.add(v);
            });
        }

        return nftWorkObjsNew;
    }
}