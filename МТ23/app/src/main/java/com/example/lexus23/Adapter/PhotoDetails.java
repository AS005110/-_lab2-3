package com.example.lexus23.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lexus23.Model.Photo;
import com.example.lexus23.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PhotoDetails extends DialogFragment {
    Photo photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        photo = gson.fromJson(bundle.getString("Photo"), Photo.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.photo_details, container, false);

        TextView title = v.findViewById(R.id.textView);
        ImageView image = v.findViewById(R.id.imageView);

        title.setText(photo.getTitle());
        Picasso.get().load(photo.getUrl()).into(image);
        return v;
    }
}
