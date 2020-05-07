package net.smallacademy.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class nodustbins extends AppCompatActivity {
        EditText bins;
    EditText binsSerial;
        Button ok;
        String cat,cat2,cat10;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    DatabaseReference refff;
    long maxxids=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodustbins);
        bins=findViewById(R.id.editText);
        binsSerial=findViewById(R.id.editText2);
        ok=findViewById(R.id.buttonbin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                cat=documentSnapshot.getString("Institue");
                cat10=documentSnapshot.getString("Sector name");
                cat2=documentSnapshot.getString("Category");
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String abc=bins.getText().toString().trim();
                String serials=binsSerial.getText().toString().trim();
                String[] elements=serials.split(",",Integer.parseInt(abc));
                refff= FirebaseDatabase.getInstance().getReference().child(cat).child(cat10).child(cat2);
                members m1=new members();
                m1.setSerials(serials);
                refff.child("1").child("Serials").setValue(m1);
                startActivity(new Intent(getApplicationContext(),show.class));
                finish();
            }



        });
    }

}
