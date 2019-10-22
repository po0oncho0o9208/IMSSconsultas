package com.aojhev.imssconsultas;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Calendar;

public class SliderAdapterCalendario extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    Activity activity;
    public int[] listaimagenes = {R.mipmap.febrero2019,R.mipmap.febrero2019, R.mipmap.marzo2019, R.mipmap.abril2019, R.mipmap.mayo2019, R.mipmap.junio2019, R.mipmap.julio2019,
            R.mipmap.agosto2019, R.mipmap.septiembre2019, R.mipmap.octubre2019, R.mipmap.noviembre2019, R.mipmap.diciembre2019};
    SharedPreferences sharedPref;


    Calendar c = Calendar.getInstance();
    int mes = c.get(Calendar.MONTH);

    public int[] lista = new int[12 - mes];


    @Override
    public int getCount() {
        return lista.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        sharedPref = context.getSharedPreferences("record", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.viewpagercalendario, container, false);
        ImageView imagenv = view.findViewById(R.id.imageviewcal);
        for (int i = 0; i < (12 - mes); i++) {
            lista[i] = listaimagenes[i + mes];
        }
        imagenv.setBackground(context.getResources().getDrawable(lista[position]));
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    public SliderAdapterCalendario(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

}
