package com.paintology.lite.total.beginner.photoeditor.BrunoSchiavi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.paintology.lite.total.beginner.R;
import com.paintology.lite.total.beginner.photoeditor.DovCharney.AppPreferences;
import com.paintology.lite.total.beginner.photoeditor.DovCharney.PatrickCox;
import com.paintology.lite.total.beginner.photoeditor.KayCohen.FileModel;
import com.paintology.lite.total.beginner.photoeditor.PhotoEditorFiltersFragment;

import java.util.ArrayList;

public class JoeSaba extends RecyclerView.Adapter<JoeSaba.MyViewHolder> {

    public ArrayList<FileModel> dataSet;
    private Context mContext;

    AppPreferences appPrefs;

    public JoeSaba(ArrayList<FileModel> data, Context mContext) {
        this.dataSet = data;
        this.mContext = mContext;
        appPrefs = new AppPreferences(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewIcon;

        FrameLayout LL_Progress;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imgPIPFramePreview);
        }


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.imageViewIcon.setTag("" + listPosition);
        holder.imageViewIcon.setColorFilter(R.color.black);
        holder.imageViewIcon.setImageBitmap(PatrickCox.getBitmapFromAsset(dataSet.get(listPosition).getDirName(), mContext));

        holder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int pos = Integer.parseInt(v.getTag().toString());

                    Bitmap bitmap = PatrickCox.getBitmapFromAsset(dataSet.get(pos).getDirName(), mContext);
                    PhotoEditorFiltersFragment.AddSticker(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_editor_abc_card_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}


