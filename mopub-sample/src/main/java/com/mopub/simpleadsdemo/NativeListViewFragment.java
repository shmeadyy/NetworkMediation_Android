package com.mopub.simpleadsdemo;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.FragmentTransaction;

import com.mopub.nativeads.MoPubAdAdapter;
import com.mopub.nativeads.MoPubNativeAdRenderer;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;

import java.util.EnumSet;

import static com.mopub.nativeads.RequestParameters.NativeAdAsset;
import com.mopub.common.logging.MoPubLog;


public class NativeListViewFragment extends Fragment {
    private MoPubAdAdapter mAdAdapter;
    private MoPubSampleAdUnit mAdConfiguration;
    private RequestParameters mRequestParameters;

//    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mAdConfiguration = MoPubSampleAdUnit.fromBundle(getArguments());
        final View view = inflater.inflate(R.layout.native_list_view_fragment, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.native_list_view);
        final DetailFragmentViewHolder views = DetailFragmentViewHolder.fromView(view);
        views.mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If your app already has location access, include it here.
                final Location location = null;
                final String keywords = views.mKeywordsField.getText().toString();

                // Setting desired assets on your request helps native ad networks and bidders
                // provide higher-quality ads.
                final EnumSet<NativeAdAsset> desiredAssets = EnumSet.of(
                        NativeAdAsset.TITLE,
                        NativeAdAsset.TEXT,
                        NativeAdAsset.ICON_IMAGE,
                        NativeAdAsset.MAIN_IMAGE,
                        NativeAdAsset.CALL_TO_ACTION_TEXT);

                mRequestParameters = new RequestParameters.Builder()
                        .location(location)
                        .keywords(keywords)
                        .desiredAssets(desiredAssets)
                        .build();

                mAdAdapter.loadAds(mAdConfiguration.getAdUnitId(), mRequestParameters);
            }
        });
        final String adUnitId = mAdConfiguration.getAdUnitId();
        views.mDescriptionView.setText(mAdConfiguration.getDescription());
        views.mAdUnitIdView.setText(adUnitId);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1);
        for (int i = 0; i < 100; ++i) {
            adapter.add("Item " + i);

        }

        AdapterView.OnItemClickListener listItemClicked = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;

                final FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();

                try {
                    newFragment = NativeGalleryFragment.class.newInstance();
                    final MoPubSampleAdUnit nativeGalleryAdUnit =
                            new MoPubSampleAdUnit.Builder(adUnitId, MoPubSampleAdUnit.AdType.LIST_VIEW)
                                    .description("")
                                    .isUserDefined(true)
                                    .build();
                    newFragment.setArguments(nativeGalleryAdUnit.toBundle());
                } catch (java.lang.InstantiationException e) {
                    MoPubLog.e("Error creating fragment for class " + newFragment, e);
                    return;
                } catch (IllegalAccessException e) {
                    MoPubLog.e("Error creating fragment for class " + newFragment, e);
                    return;
                }
                fragmentTransaction
                        .replace(R.id.fragment_container, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        };

        listView.setOnItemClickListener(listItemClicked);

        // Create an ad adapter that gets its positioning information from the MoPub Ad Server.
        // This adapter will be used in place of the original adapter for the ListView.
        mAdAdapter = new MoPubAdAdapter(getActivity(), adapter);

        // Set up an renderer that knows how to put ad data in an ad view.
        final MoPubNativeAdRenderer adRenderer = new MoPubNativeAdRenderer(
                new ViewBinder.Builder(R.layout.native_ad_list_item)
                        .titleId(R.id.native_title)
                        .textId(R.id.native_text)
                        .mainImageId(R.id.native_main_image)
                        .iconImageId(R.id.native_icon_image)
                        .callToActionId(R.id.native_cta)
                        .daaIconImageId(R.id.native_daa_icon_image)
                        .build());

        // Register the renderer with the MoPubAdAdapter and then set the adapter on the ListView.
        mAdAdapter.registerAdRenderer(adRenderer);
        listView.setAdapter(mAdAdapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        // You must call this or the ad adapter may cause a memory leak.
        mAdAdapter.destroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        // MoPub recommends loading new ads when the user returns to your activity.
        mAdAdapter.loadAds(mAdConfiguration.getAdUnitId(), mRequestParameters);
        super.onResume();
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }
}
