package com.mopub.simpleadsdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.util.Log;
import android.support.v4.app.FragmentTransaction;

import com.mopub.common.logging.MoPubLog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediationHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediationHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediationHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RadioGroup adFormatRadioGroup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediationHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediationHomeFragment newInstance(String param1, String param2) {
        MediationHomeFragment fragment = new MediationHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mediationView = inflater.inflate(R.layout.fragment_mediation_home, container, false);
        adFormatRadioGroup = (RadioGroup) mediationView.findViewById(R.id.adFormatRadioGroup);
        adFormatRadioGroup.clearCheck();
        Log.i("MOPUB", "Inside OnCreateView()");
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adUnitId = ((TextView) mediationView.findViewById(R.id.adUnitId)).getText().toString();
                final RadioGroup adFormatRadioGroup = (RadioGroup) mediationView.findViewById(R.id.adFormatRadioGroup);
                final RadioButton checkedRadioButton = (RadioButton) mediationView.findViewById(adFormatRadioGroup.getCheckedRadioButtonId());
                Fragment newFragment = null;

                final String radioButtonText = checkedRadioButton.getText().toString();

                final FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();

                switch(radioButtonText) {
                    case "Banner":
                        try {
                            newFragment = BannerDetailFragment.class.newInstance();
                            if (adUnitId.length() != 32){
                                adUnitId = "b195f8dd8ded45fe847ad89ed1d016da";
                            }

                            final MoPubSampleAdUnit bannerAdUnit =
                                    new MoPubSampleAdUnit.Builder(adUnitId, MoPubSampleAdUnit.AdType.BANNER)
                                            .description("")
                                            .isUserDefined(true)
                                            .build();
                            newFragment.setArguments(bannerAdUnit.toBundle());
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
                        break;
                    case "Interstitial":
                        try {
                            newFragment = InterstitialDetailFragment.class.newInstance();
                            if(adUnitId.length() != 32){
                                   adUnitId = "24534e1901884e398f1253216226017e";
                            }
                            final MoPubSampleAdUnit interstitialAdUnit =
                                    new MoPubSampleAdUnit.Builder(adUnitId, MoPubSampleAdUnit.AdType.INTERSTITIAL)
                                            .description("")
                                            .isUserDefined(true)
                                            .build();
                            newFragment.setArguments(interstitialAdUnit.toBundle());
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
                        break;
                    case "Native":
                        try {
                            newFragment = NativeListViewFragment.class.newInstance();
                            if(adUnitId.length() != 32){
                                adUnitId = "11a17b188668469fb0412708c3d16813";
                            }
                            final MoPubSampleAdUnit nativeAdUnit =
                                    new MoPubSampleAdUnit.Builder(adUnitId, MoPubSampleAdUnit.AdType.LIST_VIEW)
                                            .description("")
                                            .isUserDefined(true)
                                            .build();
                            newFragment.setArguments(nativeAdUnit.toBundle());
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
                        break;
                    case "Rewarded Video":
                        try {
                            newFragment = RewardedVideoDetailFragment.class.newInstance();
                            final MoPubSampleAdUnit rewardedAdUnit =
                                    new MoPubSampleAdUnit.Builder(adUnitId, MoPubSampleAdUnit.AdType.LIST_VIEW)
                                            .description("")
                                            .isUserDefined(true)
                                            .build();
                            newFragment.setArguments(rewardedAdUnit.toBundle());
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
                        break;
                }
            }
        };
        mediationView.findViewById(R.id.go_button).setOnClickListener(clickListener);
        return mediationView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
