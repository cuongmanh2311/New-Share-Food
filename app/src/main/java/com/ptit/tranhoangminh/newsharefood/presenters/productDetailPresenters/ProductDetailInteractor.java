package com.ptit.tranhoangminh.newsharefood.presenters.productDetailPresenters;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.database.DatabaseHelper;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

import java.io.File;
import java.io.IOException;

public class ProductDetailInteractor {
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private LoadProductDetailListener listener;
    private DatabaseHelper db;

    public ProductDetailInteractor(LoadProductDetailListener listener, Context context) {
        this.listener = listener;
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
        this.myRef = FirebaseDatabase.getInstance().getReference();
        db = new DatabaseHelper(context);
    }

    public void createProductDetail(final String id, final String image_id) {
        myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot item : iterable) {
                    final ProductDetail pdetail = item.getValue(ProductDetail.class);
                    if (pdetail.getId().equals(id)) {
                        try {
                            final File localFile = File.createTempFile(image_id, ".png");
                            mStorageRef.child("Products").child(image_id).getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            final Bitmap[] bitmap = new Bitmap[1];
                                            bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            listener.onLoadProductDetailSuccess(pdetail, bitmap[0]);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    listener.onLoadProductDetailFailure("Failed to read image. " + exception.getMessage());
                                }
                            });
                            return;
                        } catch (IOException e) {
                            listener.onLoadProductDetailFailure("Failed to create temp file. " + e.getMessage());
                        }
                    }
                }
                listener.onLoadProductDetailFailure("Failed to find product detail.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLoadProductDetailFailure("Failed to read product detail. " + databaseError.toException());
            }
        });
    }

    public void addProductSqlite(Product product, ProductDetail pDetail, Bitmap bitmap) {
        try {
            db.addProduct(product, bitmap);
            db.addProductDetail(pDetail);
        } catch (SQLiteException ex) {
            listener.onLikeFailure("Failed to add product. " + ex.getMessage());
        }
    }

    public void removeProductSqlite(String id) {
        db.deleteProduct(id);
        db.deleteProductDetail(id);
    }

    public void addLike(final String id) {
        myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot item : iterable) {
                    final ProductDetail pdetail = item.getValue(ProductDetail.class);
                    if (pdetail.getId().equals(id)) {
                        pdetail.addLike(1);
                        item.getRef().setValue(pdetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                listener.onLikeSuccess(pdetail.getLike());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onLikeFailure("Failed to set product detail.");
                            }
                        });
                        return;
                    }
                }
                listener.onLikeFailure("Failed to find product detail.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLikeFailure("Failed to read product detail. " + databaseError.toException());
            }
        });
    }

    public void removeLike(final String id) {
        myRef.child("ProductDetail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot item : iterable) {
                    final ProductDetail pdetail = item.getValue(ProductDetail.class);
                    if (pdetail.getId().equals(id)) {
                        pdetail.removeLike(1);
                        item.getRef().setValue(pdetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                listener.onLikeSuccess(pdetail.getLike());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onLikeFailure("Failed to set product detail.");
                            }
                        });
                        return;
                    }
                }
                listener.onLikeFailure("Failed to find product detail.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLikeFailure("Failed to read product detail. " + databaseError.toException());
            }
        });
    }

    public boolean isExistItemSQlite(String id) {
        if (db.getProduct(id)!=null) {
            return true;
        }
        return false;
    }
}
