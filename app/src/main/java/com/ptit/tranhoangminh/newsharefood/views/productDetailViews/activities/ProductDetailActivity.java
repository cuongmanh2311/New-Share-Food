package com.ptit.tranhoangminh.newsharefood.views.productDetailViews.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.presenters.productDetailPresenters.ProductDetailPresenter;

/**
 * Created by Dell on 3/11/2018.
 */

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView {
    Toolbar toolbar;
    Product objectKey;
    TabHost tabHost;
    TextView tvCommentNum;
    TextView tvLike;
    TextView tvTenmon;
    ImageView imgHinhmon;
    TextView tvMaterials;
    TextView tvRecipe;
    TextView tvVideo;
    TextView tvComment;
    ProgressBar pgbProductDetail;
    CheckBox cbLike;
    ProductDetailPresenter productDetailPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail_layout);
        objectKey = (Product) getIntent().getSerializableExtra("objectKey");
        setControl();
        setEvents();
        setTabHost();
        initPresenter();
        productDetailPresenter.loadProductDetail(objectKey.getId(), objectKey.getImage());

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CHI TIẾT MÓN ĂN ");
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void setControl() {
        toolbar = findViewById(R.id.toolbarChiTiet);
        tabHost = findViewById(R.id.tabhost);
        tvCommentNum = findViewById(R.id.textViewCommentCount);
        tvLike = findViewById(R.id.textViewLike);
        tvTenmon = findViewById(R.id.textViewTenmondetail);
        imgHinhmon = findViewById(R.id.imageViewdetail);
        tvMaterials = findViewById(R.id.textViewMaterials);
        tvRecipe = findViewById(R.id.textViewRecipe);
        tvVideo = findViewById(R.id.textViewVideo);
        tvComment = findViewById(R.id.textViewComment);
        pgbProductDetail = findViewById(R.id.progressBarProductDetail);
        cbLike = findViewById(R.id.checkboxLike);
    }

    void setEvents() {
        cbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void setTabHost() {
        tabHost.setup();
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("NGUYÊN LIỆU");
        tabSpec1.setIndicator("NGUYÊN LIỆU");

        tabSpec1.setContent(R.id.tab1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("CÁCH NẤU");
        tabSpec2.setIndicator("CÁCH NẤU");
        tabSpec2.setContent(R.id.tab2);

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("BÌNH LUẬN");
        tabSpec4.setIndicator("BÌNH LUẬN");
        tabSpec4.setContent(R.id.tab3);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("VIDEO");
        tabSpec3.setIndicator("VIDEO");
        tabSpec3.setContent(R.id.tab4);

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);
    }

    private void initPresenter() {
        productDetailPresenter = new ProductDetailPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        pgbProductDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbProductDetail.setVisibility(View.GONE);
    }
    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayProductDetail(ProductDetail productDetail, Bitmap bitmap) {
        tvTenmon.setText(objectKey.getName());
        tvCommentNum.setText(productDetail.getCommentcount() + " bình luận");
        tvLike.setText(productDetail.getLike() + " yêu thích");
        tvMaterials.setText(productDetail.getMaterials());
        tvRecipe.setText(productDetail.getRecipe());
        tvVideo.setText(productDetail.getVideo());
        tvComment.setText(productDetail.getComment());
        imgHinhmon.setImageBitmap(bitmap);
    }
}