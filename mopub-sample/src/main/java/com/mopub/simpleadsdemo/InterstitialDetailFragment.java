package com.mopub.simpleadsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import static com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import static com.mopub.simpleadsdemo.Utils.hideSoftKeyboard;
import static com.mopub.simpleadsdemo.Utils.logToast;

public class InterstitialDetailFragment extends Fragment implements InterstitialAdListener {
    private MoPubInterstitial mMoPubInterstitial;
    private Button mShowButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final MoPubSampleAdUnit adConfiguration =
                MoPubSampleAdUnit.fromBundle(getArguments());
        final View view = inflater.inflate(R.layout.interstitial_detail_fragment, container, false);
        final DetailFragmentViewHolder views = DetailFragmentViewHolder.fromView(view);
        hideSoftKeyboard(views.mKeywordsField);

        final String adUnitId = adConfiguration.getAdUnitId();
        views.mDescriptionView.setText(adConfiguration.getDescription());
        views.mAdUnitIdView.setText(adUnitId);
        views.mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMoPubInterstitial == null) {
                    mMoPubInterstitial = new MoPubInterstitial(getActivity(), adUnitId);
                    mMoPubInterstitial.setInterstitialAdListener(InterstitialDetailFragment.this);
                }
                final String keywords = views.mKeywordsField.getText().toString();
                mMoPubInterstitial.setKeywords(keywords);
                mMoPubInterstitial.load();
                mShowButton.setEnabled(false);
            }
        });
        mShowButton = (Button) view.findViewById(R.id.interstitial_show_button);
        mShowButton.setEnabled(false);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoPubInterstitial.show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mMoPubInterstitial != null) {
            mMoPubInterstitial.destroy();
            mMoPubInterstitial = null;
        }
    }

    // InterstitialAdListener implementation
    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        mShowButton.setEnabled(true);
        TextView txtView =  (TextView) getView().findViewById(R.id.interstitialLoaded);
        txtView.setTextColor(Color.GREEN);
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        final String errorMessage = (errorCode != null) ? errorCode.toString() : "";
        logToast(getActivity(), "Error Message: " + errorMessage);
        TextView txtView =  (TextView) getView().findViewById(R.id.interstitialFailed);
        txtView.setTextColor(Color.RED);
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        mShowButton.setEnabled(false);
        TextView txtView =  (TextView) getView().findViewById(R.id.interstitialShown);
        txtView.setTextColor(Color.GREEN);
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {
        TextView txtView =  (TextView) getView().findViewById(R.id.interstitialClicked);
        txtView.setTextColor(Color.GREEN);
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        TextView txtView =  (TextView) getView().findViewById(R.id.interstitialDismissed);
        txtView.setTextColor(Color.GREEN);
    }
}
