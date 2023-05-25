package com.foodykey.food.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.foodykey.food.ControllerApplication;
import com.foodykey.food.R;
import com.foodykey.food.activity.MainActivity;
import com.foodykey.food.adapter.OrderAdapter;
import com.foodykey.food.databinding.FragmentOrderBinding;
import com.foodykey.food.model.Order;
import com.foodykey.food.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment {

    private FragmentOrderBinding mFragmentOrderBinding;
    private List<Order> mListOrder;
    private OrderAdapter mOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        initView();
        getListOrders();
        return mFragmentOrderBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.order));
        }
    }

    private void initView() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentOrderBinding.rcvOrder.setLayoutManager(linearLayoutManager);

        mListOrder = new ArrayList<>();
        mOrderAdapter = new OrderAdapter(mListOrder);
        mFragmentOrderBinding.rcvOrder.setAdapter(mOrderAdapter);
    }

    public void getListOrders() {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getBookingDatabaseReference().child(Utils.getDeviceId(getActivity()))
                .addChildEventListener(new ChildEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null || mOrderAdapter == null) {
                            return;
                        }
                        mListOrder.add(0, order);
                        mOrderAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null
                                || mListOrder.isEmpty() || mOrderAdapter == null) {
                            return;
                        }
                        for (int i = 0; i < mListOrder.size(); i++) {
                            if (order.getId() == mListOrder.get(i).getId()) {
                                mListOrder.set(i, order);
                                break;
                            }
                        }
                        mOrderAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null
                                || mListOrder.isEmpty() || mOrderAdapter == null) {
                            return;
                        }
                        for (Order orderObject : mListOrder) {
                            if (order.getId() == orderObject.getId()) {
                                mListOrder.remove(orderObject);
                                break;
                            }
                        }
                        mOrderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
