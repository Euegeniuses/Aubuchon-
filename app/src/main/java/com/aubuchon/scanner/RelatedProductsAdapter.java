package com.aubuchon.scanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aubuchon.R;
import com.aubuchon.model.RelatedModel;
import com.aubuchon.utility.GlideApp;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RelatedProductsAdapter extends RecyclerView.Adapter<RelatedProductsAdapter.ViewHolder> {

    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ArrayList<RelatedModel.RelatedProduct> mDataSetFilterData;

    RelatedProductsAdapter(Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    void doRefresh(ArrayList<RelatedModel.RelatedProduct> dataSet) {
        mDataSetFilterData = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.related_products_item_list, viewGroup, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RelatedModel.RelatedProduct relatedProduct = mDataSetFilterData.get(position);
        try {
            holder.setDataToView(relatedProduct, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSetFilterData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelatedProductsAdapter mAdapter;
        ImageView iv_product;
        TextView tv_web_desc, tv_retail_prize;

        ViewHolder(View itemView, final RelatedProductsAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;

            iv_product = itemView.findViewById(R.id.iv_product);
            tv_web_desc = itemView.findViewById(R.id.tv_web_desc);
            tv_retail_prize = itemView.findViewById(R.id.tv_retail_prize);

            itemView.setOnClickListener(this);
        }

        void setDataToView(RelatedModel.RelatedProduct relatedProduct, final int position) {

            iv_product.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            GlideApp.with(context)
                    .load(relatedProduct.getImage())
                    .placeholder(R.drawable.camera)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontTransform()
                    .into(iv_product);

            tv_web_desc.setText(relatedProduct.getWebDesc());
            tv_retail_prize.setText("$" + String.valueOf(relatedProduct.getRetailPrice()));


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    mAdapter.onItemHolderClick(ViewHolder.this);
            }
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(RelatedProductsAdapter.ViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
    }

}
