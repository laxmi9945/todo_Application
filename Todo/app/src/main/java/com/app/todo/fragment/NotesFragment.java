package com.app.todo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.todo.R;
import com.app.todo.ui.TodoNotesActivity;

/**
 * Created by bridgeit on 6/4/17.
 */

public class NotesFragment extends Fragment implements TodoNotesActivity.OnBackPressedListener{
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.actvity_recycler, container, false);
        ((TodoNotesActivity) getActivity()).setOnBackPressedListener(this);
        //toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void doBack() {
        Intent intent=new Intent(getActivity(),TodoNotesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
