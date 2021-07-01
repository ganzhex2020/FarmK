package com.jony.farm.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.combodia.basemodule.utils.LogUtils;
import com.jony.farm.R;
import com.jony.farm.model.entity.AnimalEntity;
import com.jony.farm.util.DateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Author:ganzhe
 * 时间:2021/5/26 14:38
 * 描述:This is FilAdapter
 */
public class FilAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<AnimalEntity> mList;

    public FilAdapter(Context mContext, List<AnimalEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }



    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fil, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        TextView tv_fil = holder.getView(R.id.tv_fil);
        TextView tv_filtib = holder.getView(R.id.tv_filtib);

        if (mList.size()==0){
            tv_fil.setText("");
            tv_filtib.setText("");
            return;
        }

        AnimalEntity animalEntity = mList.get(position%mList.size());

        try {
            long time = DateUtil.str2date(animalEntity.getBuyDate()).getTime();
          //  tv_fil.setText("FIL:"+time);
            long hour = ((System.currentTimeMillis() - DateUtil.str2date(animalEntity.getBuyDate()).getTime())/1000/60/60);
            BigDecimal a = new BigDecimal(hour);
            BigDecimal b = new BigDecimal(10000);
            BigDecimal c=a.divide(b);
            tv_filtib.setText(c+" FIL/Tib");
            LogUtils.error("time:"+time+" hour:"+c);

            Flowable.intervalRange(0,time,0,1, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            tv_fil.setText("FIL:"+(time-aLong));
                        }
                    })
                    .subscribe();


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }



    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
