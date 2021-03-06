package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.util.List;

/**
 * Created by Dell on 3/12/2018.
 */

public class savedProductAdapter extends ArrayAdapter<Product> {
    Context context;
    int resource;
    List<Product> objects;
    StorageReference mStorageRef;

    public savedProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private class ViewHolder {
        ImageView img;
        TextView txt;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*ProductAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resource, null);
            holder = new ProductAdapter.ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imgHinhMonAn);
            holder.txt = (TextView) convertView.findViewById(R.id.txtTenMonAn);
            convertView.setTag(holder);
        } else {
            holder = (ProductAdapter.ViewHolder) convertView.getTag();
        }

        Product pd = objects.get(position);
        holder.txt.setText(pd.getName());
        FirebaseReference.setImageFromFireBase(mStorageRef.child("Products").child(pd.getImage()),pd.getImage(),".png",holder.img);*/
        return convertView;
    }
}
