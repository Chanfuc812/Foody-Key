package com.foodykey.food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodykey.food.databinding.ItemFoodPopularBinding;
import com.foodykey.food.listener.IOnClickFoodItemListener;
import com.foodykey.food.model.Food;
import com.foodykey.food.utils.GlideUtils;

import java.util.List;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder> {

    private final List<Food> mListFoods;
    public final IOnClickFoodItemListener iOnClickFoodItemListener;

    public FoodPopularAdapter(List<Food> mListFoods, IOnClickFoodItemListener iOnClickFoodItemListener) {
        this.mListFoods = mListFoods;
        this.iOnClickFoodItemListener = iOnClickFoodItemListener;
    }

    @NonNull
    @Override
    public FoodPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodPopularBinding itemFoodPopularBinding = ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodPopularViewHolder(itemFoodPopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPopularViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        GlideUtils.loadUrlBanner(food.getBanner(), holder.mItemFoodPopularBinding.imageFood);
        if (food.getSale() <= 0) {
            holder.mItemFoodPopularBinding.tvSaleOff.setVisibility(View.GONE);
        } else {
            holder.mItemFoodPopularBinding.tvSaleOff.setVisibility(View.VISIBLE);
            String strSale = "Giảm " + food.getSale() + "%";
            holder.mItemFoodPopularBinding.tvSaleOff.setText(strSale);
        }
        holder.mItemFoodPopularBinding.layoutItem.setOnClickListener(v -> iOnClickFoodItemListener.onClickItemFood(food));
    }

    @Override
    public int getItemCount() {
        if (mListFoods != null) {
            return mListFoods.size();
        }
        return 0;
    }

    public static class FoodPopularViewHolder extends RecyclerView.ViewHolder {

        private final ItemFoodPopularBinding mItemFoodPopularBinding;

        public FoodPopularViewHolder(@NonNull ItemFoodPopularBinding itemFoodPopularBinding) {
            super(itemFoodPopularBinding.getRoot());
            this.mItemFoodPopularBinding = itemFoodPopularBinding;
        }
    }
}
