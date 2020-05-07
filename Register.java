
package net.smallacademy.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    static long myId;
    int flag=0;

    DatabaseReference databaseReference,reff;
    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner10;
    private static final String[] paths = {"Select Category", "Industry", "Hospital"};
    private static final String[] paths2 = {"Select Category", "Admin", "Staff"};
    private static final String[] paths3 = {"Select Category", "Gurukrupa", "Rk","Tata","Reliance","Google"};
    private static final String[] paths4 = {"Select Category", "Sterling", "Wockhart","Gokul","Synergy","HCG"};
    private AdapterView<?> adapterView;
   public String category;
    public String category2;
    public String category10;
    long maxid=0;
    int temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8;
    int flags1=0,flags2=0,flags3=0,flags4=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Toast.makeText(Register.this,category,Toast.LENGTH_SHORT).show();

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_spinner_item,paths2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);




        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }






        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone    = mPhone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){



                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            //DocumentReference documentReference1 = fStore.collection("Industry").document("Admin").collection(userID).document();
                            //DocumentReference documentReference2 = fStore.collection("Industry").document("Staff").collection(userID).document();
                            //DocumentReference documentReference3 = fStore.collection("Hospital").document("Admin").collection(userID).document();
                            //DocumentReference documentReference4 = fStore.collection("Hospital").document("Staff").collection(userID).document();
                            /*final Map<String,Object> user = new HashMap<>();
                            user.put("Name",fullName);
                            user.put("Email",email);
                            user.put("Phone",phone);
                            user.put("Institue",category);
                            user.put("Category",category2);
                            user.put("Sector name",category10);*/

                            /*getthreestring obj=new getthreestring();
                            obj.setCat(category);
                            obj.setCat2(category2);
                            obj.setCat10(category10);*/

                            if(category.equals("Industry"))
                            {
                                //reff= FirebaseDatabase.getInstance().getReference().child("Industry");

                                if(category2.equals("Admin"))
                                {
                                    reff=FirebaseDatabase.getInstance().getReference().child("Industry").child(category10).child("Admin");
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()&&flag==0)
                                            {
                                                maxid=(dataSnapshot.getChildrenCount());
                                                int temp= (int) maxid;
                                                myId=temp+1;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                               temp1=temp+1;
                                                Toast.makeText(Register.this,String.valueOf(temp+1),Toast.LENGTH_SHORT).show();
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flags1=0;

                                                flag=1;

                                            }
                                            else if(!dataSnapshot.exists()&&flag==0)
                                            {
                                                int temp= (int) maxid;
                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp2=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags1=1;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    /*documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });*/
                                }
                                else if(category2.equals("Staff"))
                                {
                                    reff=FirebaseDatabase.getInstance().getReference().child("Industry").child(category10).child("Staff");
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()&&flag==0)
                                            {
                                                maxid=(dataSnapshot.getChildrenCount());
                                                int temp= (int) maxid;
                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp3=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags2=0;

                                            }
                                            else if(!dataSnapshot.exists()&&flag==0)
                                            {
                                                int temp= (int) maxid;
                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp4=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags2=1;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    /*documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });*/
                                }


                            }
                            else if(category.equals("Hospital"))
                            {

                                if(category2.equals("Admin"))
                                {
                                    reff= FirebaseDatabase.getInstance().getReference().child("Hospital").child(category10).child("Admin");
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()&&flag==0)
                                            {
                                                maxid=(dataSnapshot.getChildrenCount());
                                                int temp= (int) maxid;
                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp5=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags3=0;

                                            }
                                            else if(!dataSnapshot.exists()&&flag==0)
                                            {
                                                int temp= (int) maxid;
                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp6=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flags3=1;
                                                flag=1;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    /*documentReference3.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });*/
                                }
                                else if(category2.equals("Staff"))
                                {
                                    reff= FirebaseDatabase.getInstance().getReference().child("Hospital").child(category10).child("Staff");
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()&&flag==0)
                                            {
                                                maxid=(dataSnapshot.getChildrenCount());
                                                int temp= (int) maxid;                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp7=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags4=0;

                                            }
                                            else if(!dataSnapshot.exists()&&flag==0)
                                            {
                                                int temp= (int) maxid;                                                myId=maxid;
                                                members m= new members();
                                                m.setName(fullName);
                                                m.setEmail(email);
                                                m.setPhoneNo(phone);
                                                m.setField(category);
                                                m.setFname(category10);
                                                m.setDesignation(category2);
                                                temp8=temp+1;
                                                //myId=String.valueOf(temp+1);
                                                reff.child(String.valueOf(temp+1)).setValue(m);
                                                flag=1;
                                                flags4=1;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    /*documentReference4.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });*/
                                }
                            }



                            if(category.equals("Industry")&&category2.equals("Admin")&&flags1==0)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp1));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Industry")&&category2.equals("Admin")&&flags1==1)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp2));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Industry")&&category2.equals("Staff")&&flags2==0)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp3));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Industry")&&category2.equals("Staff")&&flags2==1)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp4));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Hospital")&&category2.equals("Admin")&&flags3==0)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp5));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Hospital")&&category2.equals("Admin")&&flags3==1)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp6));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Hospital")&&category2.equals("Staff")&&flags4==0)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp7));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }
                            else if(category.equals("Hospital")&&category2.equals("Staff")&&flags4==1)
                            {
                                final Map<String,Object> user = new HashMap<>();
                                user.put("Name",fullName);
                                user.put("Email",email);
                                user.put("Phone",phone);
                                user.put("Institue",category);
                                user.put("Category",category2);
                                user.put("Sector name",category10);
                                user.put("MAXID",String.valueOf(temp5));
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                            }


                                if(category2=="Admin")
                                {
                                    startActivity(new Intent(getApplicationContext(),nodustbins.class));
                                    finish();
                                }
                                else
                                {
                                    startActivity(new Intent(getApplicationContext(),show.class));
                                    finish();
                                }


                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });




        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();

            }
        });

    }

    int flagg1=0;
    int flagg2=0;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        Spinner spinner1 = (Spinner)parent;
        Spinner spinner10 = (Spinner)parent;

        if(spinner.getId() == R.id.spinner)
        {
            switch (position) {
                case 0:
                    // Whatever you want to happen when the first item gets selected
                    break;
                case 1:
                    // Whatever you want to happen when the second item gets selected
                    category=paths[position];
                    flagg1=0;
                    break;
                case 2:
                    // Whatever you want to happen when the thrid item gets selected
                    category=paths[position];
                    flagg2=0;
                    break;

            }
        }
        if(category=="Industry"&&flagg1==0) {
            spinner10 = (Spinner) findViewById(R.id.spinner10);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Register.this,
                    android.R.layout.simple_spinner_item, paths3);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner10.setAdapter(adapter2);
            spinner10.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
            flagg1=1;
            Toast.makeText(Register.this,"INdustry",Toast.LENGTH_SHORT).show();
        }
        if(category=="Hospital"&&flagg2==0)
        {
            spinner10 = (Spinner)findViewById(R.id.spinner10);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Register.this,
                    android.R.layout.simple_spinner_item,paths4);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner10.setAdapter(adapter2);
            spinner10.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
            flagg2=1;
            Toast.makeText(Register.this,"Hospital",Toast.LENGTH_SHORT).show();
        }

        if(spinner1.getId() == R.id.spinner1)
        {
            switch (position) {
                case 0:
                    // Whatever you want to happen when the first item gets selected
                    break;
                case 1:
                    // Whatever you want to happen when the second item gets selected
                    category2=paths2[position];
                    break;
                case 2:
                    // Whatever you want to happen when the thrid item gets selected
                    category2=paths2[position];
                    break;


            }
        }
        if(spinner10.getId() == R.id.spinner10)
        {
            if(category=="Industry")
            {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        category10=paths3[position];
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        category10=paths3[position];
                        break;
                    case 3:
                        // Whatever you want to happen when the second item gets selected
                        category10=paths3[position];
                        break;
                    case 4:
                        // Whatever you want to happen when the thrid item gets selected
                        category10=paths3[position];
                        break;
                    case 5:
                        // Whatever you want to happen when the thrid item gets selected
                        category10=paths3[position];
                        break;


                }

            }
            else if(category=="Hospital")
            {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        category10=paths4[position];
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        category10=paths4[position];
                        break;
                    case 3:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 4:
                        // Whatever you want to happen when the second item gets selected
                        category10=paths4[position];
                        break;
                    case 5:
                        // Whatever you want to happen when the thrid item gets selected
                        category10=paths4[position];
                        break;

                }
            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
