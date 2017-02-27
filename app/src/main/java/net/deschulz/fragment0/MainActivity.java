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

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends FragmentActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {
    public static final String TAG = "DesDebug";
    // we need this so we know later in the ArticleFragment what the name of our
    // text control is.   I suppose we could generate the control on the fly and then
    // we wouldn't need to know.
    private boolean mTwoPanes = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"MainActivity: On Create()");

        setContentView(R.layout.news_articles);

        //The fragment container is defined in the "generic" news_article.xml layout.
        //This is the one that is used unless we are in landscape mode or in large mode

        if (findViewById(R.id.fragment_container) != null) {
            mTwoPanes = false;

            Log.i(TAG,"MainActivity: found \"fragment_container\" => single pane");

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.

            if (savedInstanceState != null) {
                Log.i(TAG,"MainActivity: doing nothing");
                return;
            }


            Log.i(TAG,"MainActivity: Creating new Headlines Fragment");

            // Create an instance of Headlines Fragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            // The fragment_container is defined in news_articles.xml generic (not land
            // or large).
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
        else {
            // we must be in a two-pane environment (land) or (large)
            Log.i(TAG,"MainActivity: no \"fragment_container\" => dual pane");
            mTwoPanes = true;
        }
    }

    /*
        This will not be active unless the following is added to the manifest
        <activity android:name=".MyActivity"
          android:configChanges="orientation|screenSize"
          android:label="@string/app_name">
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"MainActivity: onConfigurationChange()");
    }

    public boolean dualPane() {
        return mTwoPanes;
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout

        Log.i(TAG,"MainActivity: OnArticleSelected() " + position);

        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (mTwoPanes && articleFrag != null) {
            // If article frag is available, we're in two-pane layout, otherwise we are
            // in a single pane layout

            Log.i(TAG,"ArticleSelected: Two Pane-layout");
            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            Log.i(TAG,"ArticleSelected: Single Pane layout");
            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
