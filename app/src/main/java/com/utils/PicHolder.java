package com.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.aware.R;
/**
 *
 *
 * picture_Adapter's ViewHolder
 */
public class PicHolder {
    public ImageView picture;

    PicHolder(@NonNull View itemView) {
        //super(itemView);

        picture = itemView.findViewById(R.id.image);
    }
}
