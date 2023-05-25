package com.foodykey.food.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.foodykey.food.R;
import com.foodykey.food.activity.MainActivity;
import com.foodykey.food.adapter.ContactAdapter;
import com.foodykey.food.constant.GlobalFuntion;
import com.foodykey.food.databinding.FragmentContactBinding;
import com.foodykey.food.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentContactBinding mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        mContactAdapter = new ContactAdapter(getActivity(), getListContact(), () -> GlobalFuntion.callPhoneNumber(getActivity()));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvData.setNestedScrollingEnabled(false);
        mFragmentContactBinding.rcvData.setFocusable(false);
        mFragmentContactBinding.rcvData.setLayoutManager(layoutManager);
        mFragmentContactBinding.rcvData.setAdapter(mContactAdapter);

        return mFragmentContactBinding.getRoot();
    }

    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));

        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));

        return contactArrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContactAdapter.release();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.contact));
        }
    }
}
