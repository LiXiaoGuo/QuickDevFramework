package com.linxiao.framework.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

/**
 * TODO:BindingAdapter未完事项
 * Created by Extends on 2017/3/22.
 */

public class BindingAdapterUtil {
    @BindingAdapter({"imgUrl"})
    public static void loadImage(ImageView view, String url) {

    }

    @BindingAdapter({"imgSrc"})
    public static void loadImage(ImageView view, int imgId) {
        view.setImageResource(imgId);
    }

    @BindingAdapter({"spinner_updata"})
    public static void updata(Spinner spinner, List<String> list){
//        ArrayAdapter adapter = new ArrayAdapter<>(BaseApplication.getContext(), android.R.layout.simple_spinner_item, list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
    }

//    @BindingAdapter({"setRating"})
//    public static void setRating(SimpleRatingBar view, String rating) {
//        float f = 0f;
//        try{
//            f = Float.valueOf(rating);
//        }catch (Exception e){
//            f = 0f;
//        }
//        view.setRating(f);
//    }
}
