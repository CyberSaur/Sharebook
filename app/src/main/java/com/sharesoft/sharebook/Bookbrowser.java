package com.sharesoft.sharebook;

        //import android.support.v7.app.AppCompatActivity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        //import android.support.v7.widget.DefaultItemAnimator;
        //import android.support.v7.widget.DividerItemDecoration;
        //import android.support.v7.widget.LinearLayoutManager;
        //import android.support.v7.widget.RecyclerView;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.multidex.MultiDex;
        import androidx.recyclerview.widget.DividerItemDecoration;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.MobileAds;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

        import static androidx.core.content.ContextCompat.startActivity;

public class Bookbrowser extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    //ArrayList<String> profilePicList;
    SearchAdapter searchAdapter;
    Button btnBack;
    ImageButton btnSearch;
    AdView avBtwbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbrowser);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");//This is just a sample id if you want to publish on playstore use your real app id.

        avBtwbb = (AdView) findViewById(R.id.avBtwbb);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();//You have to change these codes when publishing to the app store, refer: https://www.youtube.com/watch?v=w7muIkMYE_A
        avBtwbb.loadAd(adRequest);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookbrowser.this, MainActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookbrowser.this, Bookshelf.class));
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
         * Create a array list for each node you want to use
         * */
        fullNameList = new ArrayList<>();
        userNameList = new ArrayList<>();
        //profilePicList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    fullNameList.clear();
                    userNameList.clear();
                    //profilePicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Book").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                fullNameList.clear();
                userNameList.clear();
                //profilePicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_name = snapshot.child("bookname").getValue(String.class);
                    String user_name = snapshot.child("price").getValue(String.class);
                    //String profile_pic = snapshot.child("profile_pic").getValue(String.class);

                    if (full_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        //profilePicList.add(profile_pic);
                        counter++;
                    } else if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        //profilePicList.add(profile_pic);
                        counter++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(Bookbrowser.this, fullNameList, userNameList/*, profilePicList*/);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
