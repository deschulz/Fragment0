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

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
    public final static String TAG = MainActivity.TAG;
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        Log.i(TAG,"ArticleFragment: onCreateView(): mCurrentPosition = "
                + mCurrentPosition);
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.article_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"ArticleFragment: onStart()");

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();

        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {

        TextView article;
        FragmentManager fm = getFragmentManager();
        boolean dual = ((MainActivity)getActivity()).dualPane();

        Log.i(TAG,"updateArticleView: dual = " + dual);

        if (dual) {
            article = (TextView) getActivity().findViewById(R.id.article_fragment);
        }
        else {
            article = (TextView) getActivity().findViewById(R.id.article);
        }

        try {
            article.setText(Ipsum.Articles[position]);
            mCurrentPosition = position;
        } catch (NullPointerException e) {
            Log.e(TAG,"NULL Article.  How could this happen");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG, "Article Fragment LIFECYCLE EVENT: onResume()");
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "Article Fragment LIFECYCLE EVENT: onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Article Fragment LIFECYCLE EVENT: onPause(): ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "Article Fragment LIFECYCLE EVENT: onStop(): ");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Article Fragment LIFECYCLE EVENT: onDestroy()");
        super.onDestroy();
    }
}