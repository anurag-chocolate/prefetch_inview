package com.vdopia.sdk21;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOAdSize;
import com.vdopia.ads.lw.LVDOBannerAd;
import com.vdopia.ads.lw.LVDOBannerAdListener;
import com.vdopia.ads.lw.LVDOConstants;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyabge Team
 */
public class InviewVideoAdapter extends BaseAdapter {

    private static final String TAG = "InviewVideoAdapter";

    private static final int TYPE_INVIEW = 1;
    private static final int TYPE_NEWSVIEW = 0;

    private int mAdPosition;
    private boolean mAdLoaded;
    private final String mApiKey;


    private LVDOBannerAd mAdView;
    private final LVDOAdSize mAdSize;
    private final List<Channel> mNewsList;

    private final Activity mActivity;
    private final LayoutInflater mInflater;

    private Animation mSlideUp = null;
    private Animation mSlideDown = null;

    private Map<Integer, View> mViews = new HashMap<>();

    public InviewVideoAdapter(Activity activity, List<Channel> channels, String mApiKey, LVDOAdSize mAdSize) {
        this.mActivity = activity;
        this.mApiKey = mApiKey;
        this.mAdSize = mAdSize;
        this.mNewsList = channels;
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAdPosition=3;
    }

    public void setAdPosition(int adPosition) {
        //mAdPosition = adPosition;
        if (mAdPosition == 0) {
            //mSlideDown = AnimationUtils.loadAnimation(mActivity, R.anim.slide_down);
            //mSlideUp = AnimationUtils.loadAnimation(mActivity, R.anim.slide_up);
        }
    }

    @Override
    public int getCount() {
        return mNewsList != null ? mNewsList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mAdPosition) {
            return TYPE_INVIEW;
        }
        return TYPE_NEWSVIEW;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_INVIEW + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.news_events_list_item, null);
            viewHolder.llSecond = (LinearLayout) convertView.findViewById(R.id.llSecond);
            viewHolder.llFirst = (LinearLayout) convertView.findViewById(R.id.llFirst);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.llFirst.setVisibility(View.VISIBLE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.d("SDK21", "Type : " + type);
        switch (type) {
            case TYPE_NEWSVIEW:
                viewHolder.llFirst.setVisibility(View.VISIBLE);
                viewHolder.llSecond.setVisibility(View.GONE);

                if (mNewsList != null && !mNewsList.get(position).getTitle().isEmpty()) {
                    viewHolder.tvTitle.setText(mNewsList.get(position).getTitle());
                    viewHolder.tvDescription.setText(mNewsList.get(position).getDescription());
                }
                break;
            case TYPE_INVIEW:
                viewHolder.llFirst.setVisibility(View.GONE);
                viewHolder.llSecond.setVisibility(View.VISIBLE);
                Log.d("SDK21", "mAdLoaded : " + mAdLoaded);
                if (!mAdLoaded) {
                    loadInviewAd(viewHolder.llSecond, position);
                } else {
                    View view = mViews.get(position);
                    if (view != null) {
                        ViewGroup parentViewGroup = (ViewGroup) view.getParent();
                        if (parentViewGroup != null) {
                            parentViewGroup.removeAllViews();
                        }
                        viewHolder.llSecond.addView(view);
                    }
                }
                break;
            default:
                Log.e(TAG, "Default View Type");
        }

        return convertView;
    }

    private void loadInviewAd(final LinearLayout inViewAd, final int i) {
        LVDOAdRequest adRequest = new LVDOAdRequest(mActivity);

        ArrayList<LVDOConstants.PARTNERS> mPartnerNames = new ArrayList<>();
        LVDOConstants.PARTNERS partner = LVDOConstants.PARTNERS.ALL;
        mPartnerNames.add(partner);
        adRequest.setPartnerNames(mPartnerNames);

        //LocationData locationData = new LocationData(mActivity);
        //adRequest.setLocation(locationData.getDeviceLocation());

        adRequest.setDmaCode("807");
        adRequest.setEthnicity("Asian");
        adRequest.setPostalCode("110096");
        adRequest.setCurrPostal("201301");
        adRequest.setAge("27");
      //  adRequest.setMaritalStatus(LVDOAdRequest.LVDOMartialStatus.Single.toString());
        //adRequest.setAppVersion(Utils.getAppVersion(mActivity));
        adRequest.setGender(LVDOAdRequest.LVDOGender.MALE);
        //adRequest.setBirthday(Utils.getDate());
        adRequest.setRequester("Vdopia");
        //adRequest.setAppBundle("chocolateApp");
        adRequest.setAppDomain("vdopia.com");
       // adRequest.setAppName("VdopiaSampleApp");
        adRequest.setAppStoreUrl("play.google.com");
        adRequest.setCategory("prerollad");
        adRequest.setPublisherDomain("vdopia.com");

        mAdView = new LVDOBannerAd(mActivity, mAdSize, mApiKey, new LVDOBannerAdListener() {
            @Override
            public void onBannerAdLoaded(View banner) {
                Log.d(TAG, "Inview Inline Ad Loaded");
                if (banner != null) {
                    ViewGroup parentViewGroup = (ViewGroup) banner.getParent();
                    if (parentViewGroup != null) {
                        parentViewGroup.removeAllViews();
                    }
                    mViews.put(i, banner);
                    mAdLoaded = true;
                    inViewAd.addView(banner);
                    LVDOAdRequest adRequest1 = new LVDOAdRequest(mActivity);
                    LVDOBannerAd.prefetch(mActivity, LVDOAdSize.INVIEW_LEADERBOARD, "QZDID8", adRequest1);

                    if (mSlideDown != null) {
                        inViewAd.startAnimation(mSlideDown);
                    }
                }

            }

            @Override
            public void onBannerAdFailed(View banner, LVDOConstants.LVDOErrorCode errorCode) {
                Log.d(TAG, "Inview Inline Ad Failed " + errorCode.toString());
                mAdLoaded = false;
            }

            @Override
            public void onBannerAdClicked(View banner) {
                Log.d(TAG, "Inview Inline Ad Clicked");
            }

            @Override
            public void onBannerAdClosed(View banner) {
                Log.d(TAG, "Inview Inline Ad Closed");
                if (mSlideUp != null) {
                    inViewAd.startAnimation(mSlideUp);

                    mSlideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //do something on animation started
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mViews.remove(mAdPosition);
                            inViewAd.removeAllViews();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            //do something on animation repeat
                        }
                    });
                } else {
                    removeViews();
                }
            }

            private void removeViews() {
                mViews.remove(mAdPosition);
                inViewAd.setVisibility(View.GONE);
                inViewAd.removeAllViews();
            }
        });

        mAdView.loadAd(adRequest);
    }

    public void destroyView() {
        if (mAdView != null) {
            mAdView.destroyView();
        }
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        LinearLayout llFirst;
        LinearLayout llSecond;
    }
}
