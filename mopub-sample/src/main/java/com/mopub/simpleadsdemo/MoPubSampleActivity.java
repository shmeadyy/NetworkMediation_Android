package com.mopub.simpleadsdemo;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;
import android.util.Log;

import com.mopub.common.MoPub;


public class MoPubSampleActivity extends FragmentActivity implements MediationHomeFragment.OnFragmentInteractionListener {

    // Sample app web views are debuggable.
    static {
        setWebDebugging();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setWebDebugging() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mediation_home);

        if (savedInstanceState != null) {
            return;
        }

        // Set location awareness and precision globally for your app:
        MoPub.setLocationAwareness(MoPub.LocationAwareness.TRUNCATED);
        MoPub.setLocationPrecision(4);

        if (findViewById(R.id.fragment_mediation_home) != null) {
            final MediationHomeFragment collectionFragment = new MediationHomeFragment();
            collectionFragment.setArguments(getIntent().getExtras());
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_mediation_home, collectionFragment)
                    .commit();
            Log.i("MOPUB", "found fragment_mediation_home XML");
        } else {
            Log.i("MOPUB", "fragment_container is null");
        }

        // Intercepts all logs including Level.FINEST so we can show a toast
        // that is not normally user-facing. This is only used for native ads.
        LoggingUtils.enableCanaryLogging(this);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
