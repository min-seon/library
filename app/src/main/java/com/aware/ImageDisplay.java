package com.aware;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Fade;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.utils.PicHolder;
import com.utils.itemClickListener;
import com.utils.pictureFacer;

import java.util.ArrayList;

/**
 * This Activity get a path to a folder that contains images from the MainActivity Intent and displays
 * all the images in the folder inside a RecyclerView
 */

public class ImageDisplay extends AppCompatActivity implements itemClickListener {

    TextView folderName;
    String foldePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folderName = findViewById(R.id.foldername);
        folderName.setText(getIntent().getStringExtra("folderName"));
        foldePath = getIntent().getStringExtra("folderPath");
    }


    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */
    public ArrayList<pictureFacer> getAllImagesByFolder(String path){
        //FdActivity에서 얼굴갯수 받아오기
        Bundle ext=getIntent().getExtras();
        int faceLen = ext.getInt("len");
        //System.out.println("------------get face count: " + faceLen);
        int j =0; //for문 활용

        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = ImageDisplay.this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                int[] rank = {1,1,2,2,2,3,3,3,3,3}; //임의로 사진에 대한 rank설정 (얼굴갯수=rank)
                pictureFacer pic = new pictureFacer(); //기존에 존재
                pic.setLen(rank[j]); //j번째 사진의 설정한 rank불러오기
                j+=1;
                //System.out.println("11111: "+faceLen);
                //System.out.println("22222: "+pic.getLen());
                // System.out.println("33333: "+j);
//
                if (faceLen == pic.getLen()) {
                    pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                    pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                    pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));
                }

                images.add(pic);

            }while(cursor.moveToNext());
            cursor.close();
//            System.out.println("########"+images.get(0).getLen());
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > -1;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
    /**
     *
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics An ArrayList of all the items in the Adapter
     */
    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {

    }
}




