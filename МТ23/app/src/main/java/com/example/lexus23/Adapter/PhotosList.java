package com.example.lexus23.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.lexus23.Api.Api;
import com.example.lexus23.DB.AppDatabase;
import com.example.lexus23.DB.PhotoDao;
import com.example.lexus23.MainActivity;
import com.example.lexus23.Model.Photo;
import com.example.lexus23.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosList extends ListFragment {
    List<Photo> photos;
    MyArrayAdapter adapter;
    AppDatabase db;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null){
            setRetainInstance(true);
            db = MainActivity.getInstance().getDatabase();
            PhotoDao photoDao = db.photoDao();
            Api.getInstance()
                    .getJSON()
                    .getPhotos()
                    .enqueue(new Callback<List<Photo>>() {
                        @Override
                        public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                            photos = response.body();
                            adapter = new MyArrayAdapter(getActivity(), R.layout.item_list, photos);
                            setListAdapter(adapter);

                            for(Photo p : photos){
                                if(photoDao.getCurrent(p.getId()).size() == 0)
                                    photoDao.insertAll(p);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Photo>> call, Throwable t) {
                            photos = photoDao.getAll();
                            if(photos.size() > 0) {
                                adapter = new MyArrayAdapter(getActivity(), R.layout.item_list, photos);
                                setListAdapter(adapter);
                            } else {
                                Toast.makeText(getActivity(), "No internet connection. No saved cache.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Gson gson  = new Gson();
            Type listType = new TypeToken<List<Photo>>(){}.getType();
            photos = gson.fromJson(savedInstanceState.getString("Photos"), listType);
            adapter = new MyArrayAdapter(getActivity(), R.layout.item_list, photos);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json = gson.toJson(photos);
        outState.putString("Photos", json);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Gson gson = new Gson();
        String json = gson.toJson(photos.get(position));
        Bundle b = new Bundle();
        b.putString("Photo", json);

        PhotoDetails pd = new PhotoDetails();
        pd.setArguments(b);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listFragment, pd, "asd")
                .addToBackStack(null)
                .commit();
    }

    private static class MyArrayAdapter extends ArrayAdapter<Photo>{
        private Context context;
        private int ResourceId;
        List<Photo> photos;
        public MyArrayAdapter(@NonNull Context _context, int resource, @NonNull List<Photo> objects) {
            super(_context, resource, objects);
            context = _context;
            ResourceId = resource;
            photos = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(ResourceId, parent, false);

            TextView title = row.findViewById(R.id.textView);
            ImageView image = row.findViewById(R.id.imageView);


            title.setText(photos.get(position).getTitle());
            Picasso.get().load(photos.get(position).getUrl()).into(image);

            return row;
        }
    }
}
