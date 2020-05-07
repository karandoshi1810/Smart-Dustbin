package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class seventyfive extends AppCompatActivity {
    DatabaseReference ref,ref2,ref3;
    EditText t1,t2;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventyfive);
        t1=findViewById(R.id.editText8);
        t2=findViewById(R.id.editText9);
        back=findViewById(R.id.button3);

        ref= FirebaseDatabase.getInstance().getReference().child("Dustbin").child("3");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String e1= String.valueOf(dataSnapshot.child("userId").getValue());
                t1.setText(e1);
                String e2= String.valueOf(dataSnapshot.child("level").getValue());
                t2.setText(e2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),show.class));
                finish();
            }
        });
    }
}
