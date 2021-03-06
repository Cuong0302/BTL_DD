package com.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.ViewFlipper;

import com.example.appdoctruyen.adapter.adapterchuyenmuc;
import com.example.appdoctruyen.adapter.adapterthongtin;
import com.example.appdoctruyen.adapter.adaptertruyen;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.model.TaiKhoan;
import com.example.appdoctruyen.model.Truyen;
import com.example.appdoctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;

    NavigationView navigationView;
    ListView listView,listViewNew,listviewThongtin;
    DrawerLayout drawerLayout;



    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;


    ArrayList<Truyen> TruyenArrayList;
    adaptertruyen adaptertruyen;

    adapterthongtin adapterthongtin;

    adapterchuyenmuc adapterchuyenmuc;

    DatabaseDocTruyen databaseDocTruyen;
    String email;
    String tentaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        databaseDocTruyen = new DatabaseDocTruyen(this);


        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");


        AnhXa();

        ActionBar();
        ActionViewFlipper();


        //listview chuy??n m???c
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 11){

                        if(i == 2) {
                            Intent intent = new Intent(MainActivity.this, ManAdmin.class);
                            intent.putExtra("Id",idd);
                            startActivity(intent);
                    }
                        else {
                            Toast.makeText(MainActivity.this,"B???n kh??ng c?? quy???n ",Toast.LENGTH_SHORT).show();
                            Log.e("????ng b??i : ","B???n kh??ng c?? quy???n ");
                        }
                    }
                else if(position == 0){
                    Intent intent = new Intent(MainActivity.this,ManThongTinApp.class);
                    startActivity(intent);
                }
                else if(position == 1){
                    finish();
                }

                else if(position == 2){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // Tao su kien ket thuc app
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startActivity(startMain);
                    finish();
                }
            }
        });


        //listview Truyen
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ManNoiDungTruyen.class);
                String tent =   TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                int SoLuotXem = TruyenArrayList.get(position).getSoluotxem();
                SoLuotXem += 1;

                Log.d("fjdhfjsf", "onItemClick: fjshfjhsdf");

                databaseDocTruyen.soLuotXem(SoLuotXem,TruyenArrayList.get(position).getID());

                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                intent.putExtra("soluotxem",SoLuotXem );
                intent.putExtra("vitri",position );
                //Log.e("T??n truy???n : ",tent);
                startActivity(intent);


                //Thay ?????i m??u khi click v??o
//                view.setBackgroundColor(Color.parseColor("#FFBB86FC"));
            }
        });

    }


    //T???o thanh action bar v???i toolbar
    private void ActionBar() {
        //H??m h??? tr??? toolBar
        setSupportActionBar(toolbar);
        //set n??t c???a toolbar l?? true
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //T???o icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        //T???o s??? ki???n click cho toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //G???i l???i drawerlayout, do toolbar ???????c g???i ra nh??? drawerlayout
                drawerLayout.openDrawer(GravityCompat.START);   //GravityCompat.START l??m n?? nh???y ra gi???a
            }
        });

    }


    //T???o Flipper qu???ng c??o
    private void ActionViewFlipper() {
        //M???ng ch???a c??c t???m h??nh
        ArrayList<String> mangquangcao = new ArrayList<>();
        //Th??m h??nh
        mangquangcao.add("https://toplist.vn/images/800px/rua-va-tho-230179.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/deo-chuong-cho-meo-230180.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cu-cai-trang-230181.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cau-be-chan-cuu-va-cay-da-co-thu-230184.jpg");

        //G??n link ???nh v??o imageView, r???i g??n g??n image ra app
        for(int i =0; i <mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            //H??m th?? vi???n c???a picasso. l???y ???nh t??? internet v??? cho v??o imageview
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //ph????ng th???c c??n ch???nh t???m h??nh v???a v???i khung qu???ng c??o
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Th??m ???nh t??? imageview v??o ViewFlipper
            viewFlipper.addView(imageView);
        }
        //Thi???t l???p t??? ch???y cho viewFlipper ch???y trong 5 gi??y
        viewFlipper.setFlipInterval(5000);
        //viewFlipper run
        viewFlipper.setAutoStart(true);
        //G???i animation cho in v?? out . Animation gi??p cho n?? bi???n d???i gi???a c??c giao di???n h??nh ???nh
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        //G???i animation v??o ViewFlipp???
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew = findViewById(R.id.listviewNew);
        navigationView = findViewById(R.id.navigationview);
        listView = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        listviewThongtin = findViewById(R.id.listviewThongTin);



        //Thong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));
        adapterthongtin = new adapterthongtin(this,R.layout.nav_thong_tin,taiKhoanArrayList);
        listviewThongtin.setAdapter(adapterthongtin);

        //chuy??n m???c
        chuyenmucArrayList = new ArrayList<>();
//        chuyenmucArrayList.add(new chuyenmuc("????ng b??i", R.drawable.ic_post));
        chuyenmucArrayList.add(new chuyenmuc("Th??ng tin", R.drawable.ic_face));
        chuyenmucArrayList.add(new chuyenmuc("????ng xu???t", R.drawable.ic_login));
//        chuyenmucArrayList.add(new chuyenmuc("Tho??t", R.drawable.ic_login));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyen_muc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);



        //New Truy???n
        TruyenArrayList = new ArrayList<>();
        Cursor cursor1 = databaseDocTruyen.getData1();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int soluotxem = cursor1.getInt(4);
            int id_tk = cursor1.getInt(5);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,soluotxem));

            adaptertruyen = new adaptertruyen(getApplicationContext(),TruyenArrayList);
            listViewNew.setAdapter(adaptertruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();

    }

    //N???p m???t menu t??m ki???m v??o actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    //B???t s??? ki???n khi click v??o search
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
            case R.id.menu1:
                Intent intent = new Intent(this,ManTimKiem.class);
                startActivity(intent);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}