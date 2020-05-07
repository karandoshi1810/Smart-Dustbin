package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class twentyfive extends AppCompatActivity {
    DatabaseReference ref,ref2,ref3;
    EditText t1,t2;
    Button back;
    public static int n1,n2,n3;
    int twentyFive;
    String e3;
    setNumbers obj=new setNumbers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twentyfive);
        t1=findViewById(R.id.editText8);
        t2=findViewById(R.id.editText9);
        back=findViewById(R.id.button3);
        ref= FirebaseDatabase.getInstance().getReference().child("Dustbin").child("1");
        //ref2= FirebaseDatabase.getInstance().getReference().child("Dustbin").child("2").child("userId");
        //ref3= FirebaseDatabase.getInstance().getReference().child("Dustbin").child("3").child("level");

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

        /*ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String e2= String.valueOf(dataSnapshot.getValue());
                    t1.setText(e2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                e3= String.valueOf(dataSnapshot.getValue());
                n3=Integer.parseInt(e3);
                obj.setN3(n3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Toast.makeText(twentyfive.this,e3,Toast.LENGTH_SHORT).show();


    }


    /*public void sort(){
        int t1=obj.getN1();
        int t2=obj.getN2();
        int t3=obj.getN3();
        int temp=0,result;
        temp = t1 < t2 ? t1:t2;
        result = t3 < temp ? t3:temp;
        Toast.makeText(twentyfive.this,String.valueOf(result),Toast.LENGTH_SHORT).show();
    }*/
}

