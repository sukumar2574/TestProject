package com.apps.abercrombiefitch.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.abercrombiefitch.R;
import com.apps.abercrombiefitch.activity.AFWebViewActivity;
import com.apps.abercrombiefitch.model.AFSiteData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class AFRecyclerAdapter extends RecyclerView.Adapter<AFRecyclerAdapter.DataObjectHolder> {
    private List<AFSiteData> afSiteDataList = null;
    private Context context;

    public AFRecyclerAdapter(Context context, List<AFSiteData> afSiteDataList) {
        this.afSiteDataList = afSiteDataList;
        this.context = context;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView topDescription;
        TextView title;
        TextView promoMessage;
        TextView bottomDescription;
        Button btnTitleOne;
        Button btnTitleTwo;
        Button btnTitleThree;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            topDescription = (TextView) itemView.findViewById(R.id.topDescription);
            title = (TextView) itemView.findViewById(R.id.title);
            promoMessage = (TextView) itemView.findViewById(R.id.promoMessage);
            bottomDescription = (TextView) itemView.findViewById(R.id.bottomDescription);
            btnTitleOne = (Button) itemView.findViewById(R.id.btnTitleOne);
            btnTitleTwo = (Button) itemView.findViewById(R.id.btnTitleTwo);
            btnTitleThree = (Button) itemView.findViewById(R.id.btnTitleThree);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.af_card_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        if (afSiteDataList.get(position).getTopDescription() != null) {
            holder.topDescription.setVisibility(View.VISIBLE);
            holder.topDescription.setText(afSiteDataList.get(position).getTopDescription());
        } else {
            holder.topDescription.setVisibility(View.GONE);
        }
        if (afSiteDataList.get(position).getTitle() != null) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(afSiteDataList.get(position).getTitle());
        } else {
            holder.title.setVisibility(View.GONE);
        }
        if (afSiteDataList.get(position).getPromoMessage() != null) {
            holder.promoMessage.setVisibility(View.VISIBLE);
            holder.promoMessage.setText(afSiteDataList.get(position).getPromoMessage());
        } else {
            holder.promoMessage.setVisibility(View.GONE);
        }
        if (afSiteDataList.get(position).getBottomDescription() != null) {
            holder.bottomDescription.setVisibility(View.VISIBLE);
            holder.bottomDescription.setText(Html.fromHtml(afSiteDataList.get(position).getBottomDescription()).toString().trim());
        } else {
            holder.bottomDescription.setVisibility(View.GONE);
        }
        if (afSiteDataList.get(position).getContentList() != null) {
            int i = 0;
            for (AFSiteData.Content content : afSiteDataList.get(position).getContentList()) {
                switch (i) {
                    case 0:
                        holder.btnTitleOne.setVisibility(View.VISIBLE);
                        holder.btnTitleOne.setText(content.getTitle());
                        break;
                    case 1:
                        holder.btnTitleTwo.setVisibility(View.VISIBLE);
                        holder.btnTitleTwo.setText(content.getTitle());
                        break;
                    case 2:
                        holder.btnTitleThree.setVisibility(View.VISIBLE);
                        holder.btnTitleThree.setText(content.getTitle());
                        break;
                }
                i++;
            }
        } else {
            holder.btnTitleOne.setVisibility(View.GONE);
            holder.btnTitleTwo.setVisibility(View.GONE);
            holder.btnTitleThree.setVisibility(View.GONE);
        }
        holder.btnTitleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AFSiteData.Content content = afSiteDataList.get(position).getContentList().get(0);
                String targetforBtn1 = content.getTarget();
                Intent btnTitleOne = new Intent(context, AFWebViewActivity.class);
                btnTitleOne.putExtra("URI_FOR_ONE", targetforBtn1);
                btnTitleOne.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(btnTitleOne);
            }
        });

        holder.btnTitleTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AFSiteData.Content content = afSiteDataList.get(position).getContentList().get(1);
                String targetforBtn2 = content.getTarget();
                Intent btnTitleTwo = new Intent(context, AFWebViewActivity.class);
                btnTitleTwo.putExtra("URI_FOR_TWO", targetforBtn2);
                context.startActivity(btnTitleTwo);
            }
        });

        holder.btnTitleThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AFSiteData.Content content = afSiteDataList.get(position).getContentList().get(2);
                String targetforBtn3 = content.getTarget();
                Intent btnTitleThree = new Intent(context, AFWebViewActivity.class);
                btnTitleThree.putExtra("URI_FOR_THREE", targetforBtn3);
                context.startActivity(btnTitleThree);
            }
        });

        ImageLoader.getInstance().displayImage(afSiteDataList.get(position).getBackgroundImage(), holder.imgIcon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (afSiteDataList != null) {
            return afSiteDataList.size();
        } else {
            return 0;
        }
    }
}