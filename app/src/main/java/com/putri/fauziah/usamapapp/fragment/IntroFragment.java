package com.putri.fauziah.usamapapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {

    private static final String argsLayoutResId  = "layoutResId";
    private int layoutResId;

    public static IntroFragment newInstance(int layoutResId ) {

        Bundle args = new Bundle();
        args.putInt(argsLayoutResId, layoutResId);

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( getArguments() != null && getArguments().containsKey( argsLayoutResId ) ) {
            layoutResId = getArguments().getInt( argsLayoutResId );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( layoutResId, container,false );
    }

}
