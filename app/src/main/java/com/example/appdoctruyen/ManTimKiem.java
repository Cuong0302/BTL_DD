package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
//import android.widget.SearchView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import android.app.SearchManager;
//import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManTimKiem extends AppCompatActivity {

    ListView listView;
    //Toolbar toolbar;
    //SearchView searchView;
    EditText edt;

    ArrayList<Truyen> TruyenArrayList, SortByViewArrayList;
    //
    ArrayList<Truyen> arrayList;



    adaptertruyen adaptertruyen;
    DatabaseDocTruyen databaseDocTruyen;

    Button btnSort;

//    ArrayAdapter<Truyen> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_tim_kiem);

        listView = findViewById(R.id.listviewtimkiem);
        //toolbar = findViewById(R.id.toolbartimkiem);
        edt = findViewById(R.id.timkiem);
        btnSort = findViewById(R.id.btnSort);

        //ActionBar();
        initList();

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }

        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortByViewArrayList = new ArrayList<>();
                Cursor cursor1 = databaseDocTruyen.getSortByView();
                while (cursor1.moveToNext()){

                    int id = cursor1.getInt(0);
                    String tentruyen = cursor1.getString(1);
                    String noidung = cursor1.getString(2);
                    String anh = cursor1.getString(3);
                    int SoLuotXem = cursor1.getInt(4);
                    int id_tk = cursor1.getInt(5);

                    //Th??m d??? li???u v??o m???ng
                    SortByViewArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,SoLuotXem));

                    adaptertruyen = new adaptertruyen(getApplicationContext(),SortByViewArrayList);
                    //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,TruyenArrayList);
                    listView.setAdapter(adaptertruyen);
                }
                cursor1.moveToFirst();
                cursor1.close();
            }
        });




        //su kien click listiew
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManTimKiem.this,ManNoiDungTruyen.class);
                String tent =   arrayList.get(position).getTenTruyen();
                String noidungt = arrayList.get(position).getNoiDung();
                int soluotxem = arrayList.get(position).getSoluotxem();

                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                intent.putExtra("soluotxem",soluotxem);
                //Log.e("T??n truy???n : ",tent);
                startActivity(intent);
            }
        });

    }



    //search
    private void filter(String text){

        //x??a sau m???i l???n g???i t???i filter
        arrayList.clear();

        ArrayList<Truyen> filteredList = new ArrayList<>();

        for(Truyen item : TruyenArrayList){
            if (item.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);

                //Th??m d??? li???u ????? hi???n th??? ra item n???i dung
               arrayList.add(item);
            }
        }
        adaptertruyen.filterList(filteredList);
    }

    //H??m  g??n d??? li???u t??? CSDL v??o listview
    public void initList(){
        TruyenArrayList = new ArrayList<>();
        //
        arrayList = new ArrayList<>();
        databaseDocTruyen = new DatabaseDocTruyen(this);

        Cursor cursor1 = databaseDocTruyen.getData2();
        while (cursor1.moveToNext()){

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int SoLuotXem = cursor1.getInt(4);
            int id_tk = cursor1.getInt(5);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,SoLuotXem));

            //Th??m d??? li???u v??o m???ng
            arrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,SoLuotXem));

            adaptertruyen = new adaptertruyen(getApplicationContext(),TruyenArrayList);
            //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,TruyenArrayList);
            listView.setAdapter(adaptertruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();
    }

//    //T???o thanh action bar v???i toolbar
//    private void ActionBar() {
//        //H??m h??? tr??? toolBar
//        setSupportActionBar(toolbar);
//        //set n??t c???a toolbar l?? true
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
}