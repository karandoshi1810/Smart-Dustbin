package net.smallacademy.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

public class home extends AppCompatActivity {
    public static final String TAG = "TAG";
    DatabaseReference reference,refrence1,ref2;
    RecyclerView recyclerView;
    ArrayList<Profile> list;
    MyAdapter adapter;

    String catt,catt2,catt10;
    String[] elements;
    FirebaseAuth fAuthh;
    FirebaseFirestore fStoree;
    String userId;
    DatabaseReference reffff,node1,node2,node3;
    /*ListView myListView;
    ArrayList<String> myArrayList=new ArrayList<>();
    DatabaseReference mRef;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuthh = FirebaseAuth.getInstance();
        fStoree = FirebaseFirestore.getInstance();


        userId = fAuthh.getCurrentUser().getUid();
        DocumentReference documentReference = fStoree.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                catt=documentSnapshot.getString("Institue");
                catt10=documentSnapshot.getString("Sector name");
                catt2=documentSnapshot.getString("Category");
                reffff=FirebaseDatabase.getInstance().getReference().child(catt).child(catt10).child(catt2).child("1").child("Serials");
                reffff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String e1= String.valueOf(dataSnapshot.child("serials").getValue());
                        //node1=FirebaseDatabase.getInstance().getReference().child("Dustbin").child("1");
                        //node2=FirebaseDatabase.getInstance().getReference().child("Dustbin").child("2");
                        //node3=FirebaseDatabase.getInstance().getReference().child("Dustbin").child("3");
                       //String e2=e1;
                        //ArrayList<String>mylist = new ArrayList<String>();

                        elements=e1.split(",");
                        //mylist= (ArrayList<String>) Arrays.asList(elements);
                       // Integer arrayLength = (Integer) mylist.size();
                        //Toast.makeText(home.this,arrayLength,Toast.LENGTH_SHORT).show();
                        /*int i=0;
                        while(elements[i]!=null)
                        {
                            i++;
                        }*/
                        //int length=elements.length();
                       // Toast.makeText(home.this,i,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
            }
        });


        //String str;
        /*reffff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e1= String.valueOf(dataSnapshot.child("serials").getValue());
                String e2=e1;

                String[] elements=e2.split(",");
                Toast.makeText(home.this,e2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });*/
       // Toast.makeText(home.this,"")
        recyclerView=(RecyclerView)findViewById(R.id.recycle1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<Profile>();

        reference=FirebaseDatabase.getInstance().getReference().child("Dustbin");
        refrence1=FirebaseDatabase.getInstance().getReference().child("Indystry");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                int i=1;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())

                {
                    Profile p=dataSnapshot1.getValue(Profile.class);
                    String str= String.valueOf(dataSnapshot.child(String.valueOf(i)).child("level").getValue());

                    if(str.equals("Full"))
                    {
                        Toast.makeText(home.this,"FFFFFFFFUUUUUUULLLLLLLLL",Toast.LENGTH_SHORT).show();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("+919574564660", null, "Dustbin "+i+ " is Full Now...!! ", null, null);

                        Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();
                    }
                    i++;
                    Log.d(TAG, "onFailure: " +str);
                    list.add(p);
                }

                adapter=new MyAdapter(home.this,list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(home.this,"Opps ",Toast.LENGTH_SHORT).show();
            }
        });




        /*final ArrayAdapter<String> myArrayAdaptar=new ArrayAdapter<String>(home.this,android.R.layout.simple_list_item_1,myArrayList);

        myListView=(ListView)findViewById(R.id.listview1);
        myListView.setAdapter(myArrayAdaptar);
        mRef= FirebaseDatabase.getInstance().getReference();
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value= dataSnapshot.getValue(String.class);
                myArrayList.add(value);
                myArrayAdaptar.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdaptar.notifyDataSetChanged();
            }

            @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



    }
}
