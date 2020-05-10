package com.sharesoft.sharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Bookshelf extends AppCompatActivity {

    private Button btnAddbook;
    private Button btnBack;
    private Button btnBB;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        btnAddbook = (Button)findViewById(R.id.btnAddbook);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBB = (Button) findViewById(R.id.btnBB);

        btnAddbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookshelf.this, AddBookActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookshelf.this, MainActivity.class));
            }
        });

        btnBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookshelf.this, Bookbrowser.class));
            }
        });

        // Find the ListView
        mListView = (ListView) findViewById(R.id.list_view);

        /*
         * Create a DatabaseReference to the data; works with standard DatabaseReference methods
         * like limitToLast() and etc.
         */
        DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference()
                .child("Book");

        // Now set the adapter with a given layout
        mListView.setAdapter(new FirebaseListAdapter<Book>(this, Book.class,
                android.R.layout.two_line_list_item, bookReference) {

            // Populate view as needed
            @Override
            protected void populateView(View view, Book book, int position) {
                ((TextView) view.findViewById(android.R.id.text1)).setText("name: "+book.getBookname());
                ((TextView) view.findViewById(android.R.id.text2)).setText("contact: "+book.getContact());
            }
        });
    }
}
