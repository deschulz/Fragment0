package net.deschulz.fragment0;

/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(MainActivity.TAG,"Headlines Fragment:onCreate()");

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        // This could also be accomplished by putting arrays into the layout -- des
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // nothing special to do here -- this just shows the lifecycle events
        Log.i(MainActivity.TAG,"Headlines Fragment:onCreateView()");
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(MainActivity.TAG,"Headlines Fragment:onStart()");
        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null &&
                ((MainActivity)getActivity()).dualPane() == true) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onArticleSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }

    public void onResume() {
        super.onResume();
        Log.i(MainActivity.TAG, "Headlines Fragment LIFECYCLE EVENT: onResume()");
    }

    @Override
    public void onDetach() {
        Log.i(MainActivity.TAG, "Headlines Fragment LIFECYCLE EVENT: onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i(MainActivity.TAG, "Headlines Fragment LIFECYCLE EVENT: onPause(): ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(MainActivity.TAG, "Headlines Fragment LIFECYCLE EVENT: onStop(): ");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(MainActivity.TAG, "Headlines Fragment LIFECYCLE EVENT: onDestroy()");
        super.onDestroy();
    }
}