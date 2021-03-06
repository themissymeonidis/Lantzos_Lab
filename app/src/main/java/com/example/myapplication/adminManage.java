package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.myapplication.R.id.lv1;

public class adminManage extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout linearLayout;
    List<String> arrayList;
    ListView lst;
    String[] local = {""};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage);

        TextView userlist = findViewById(R.id.AdminUserList);
        Button fire = findViewById(R.id.AdminFire);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userlist.setText("");
        userlist.setMovementMethod(new ScrollingMovementMethod());

        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                        String names = documentChange.getDocument().getData().get("Name").toString();
                        String gender = documentChange.getDocument().getData().get("Gender").toString();
                        String address = documentChange.getDocument().getData().get("Address").toString();
                        userlist.append("\n \n" + names + "/" + gender + "/" + address);

                    }
                }
            }
        });


//
        fire.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ServiceCast")
            @Override
            public void onClick(View view) {


                layoutInflater = (LayoutInflater) getApplication().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.admin_popup, null);

                popupWindow = new PopupWindow(container, 850, 1400, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                lst = container.findViewById(lv1);
                arrayList = new ArrayList<>();
                Collections.addAll(arrayList,local);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(adminManage.this, android.R.layout.simple_list_item_1, arrayList);
                arrayList.remove(arrayList.get(0));
                db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                            if (documentChange.getDocument().getData().get("isAdmin").toString().equals("0")) {

                                String names = documentChange.getDocument().getData().get("Name").toString();
                                arrayList.add(names);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
                lst.setAdapter(adapter);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String itemValue = (String) lst.getItemAtPosition(position);
                        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                if (e !=null)
                                {

                                }

                                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                                {
                                    if (documentChange.getDocument().getData().get("Name").toString().equals(itemValue)) {

                                        String emailtofire = documentChange.getDocument().getData().get("UserEmail").toString();
                                        db.collection("Users").document(emailtofire)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(adminManage.this, "Deleted User " + emailtofire, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(adminManage.this, "An error occured while deleting " + itemValue , Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    arrayList.remove(itemValue);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });

                    }
                });

                popupWindow.showAsDropDown(fire);
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ((PopupWindow) popupWindow).dismiss();
                        return false;
                    }
                });




            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                setContentView(R.layout.aboutus);
                return true;
            case R.id.item2:
                setContentView(R.layout.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}




