package com.sharesoft.sharebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBookActivity extends AppCompatActivity {

    Button btnBack, btnAdd;
    ImageView imgPhoto;
    EditText etBookname, etAuthor, etPublisher, etISBN, etPrice, etContact;
    Spinner spnrGenre;
    DatabaseReference reff;
    Book book;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        etBookname = (EditText) findViewById(R.id.etBookname);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etPublisher = (EditText) findViewById(R.id.etPublisher);
        etISBN = (EditText) findViewById(R.id.etISBN);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etContact = (EditText) findViewById(R.id.etContact);
        spnrGenre = (Spinner) findViewById(R.id.spnrGenre);
        book = new Book();
        reff = FirebaseDatabase.getInstance().getReference().child("Book");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                book.setBookname(etBookname.getText().toString().trim());
                book.setAuthor(etAuthor.getText().toString().trim());
                book.setPublisher(etPublisher.getText().toString().trim());
                book.setISBN(etISBN.getText().toString().trim());
                book.setGenre((String.valueOf(spnrGenre.getSelectedItem())).trim());
                book.setPrice(etPrice.getText().toString().trim());
                book.setContact(etContact.getText().toString().trim());

                //reff.push().setValue(book); No incrementation.
                //reff.child("book1").setValue(book); Each record replaces one another.
                reff.child(String.valueOf(maxid+1)).setValue(book);
                Toast.makeText(AddBookActivity.this,"Data inserted successfully",Toast.LENGTH_LONG).show();
                etBookname.setText("");
                etAuthor.setText("");
                etPublisher.setText("");
                etISBN.setText("");
                spnrGenre.setSelected(false);
                etPrice.setText("");
                etContact.setText("");


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddBookActivity.this, Bookshelf.class));
            }
        });
    }

}
