package com.vdopia.sdk21;

import android.app.Activity;
import android.text.Html;
import android.util.Log;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to download rss feed data and push into Channel bean(pojo)
 *
 * @author Cybage Team
 */
public class HandleXML {

    private static final String TAG = "HandleXML";

    private final Activity mActivity;
    private DataListener dataListener;

    private static final int mReadTimeOut = 10000;     /* Milli Seconds */
    private static final int mConnectTimeout = 15000;  /* Milli Seconds */

    private List<Channel> mChannelList = null;
    private XmlPullParserFactory mXmlPullParserFactory;

    public interface DataListener {
        void onDataSuccess(boolean isSuccess);
    }

    public HandleXML(Activity activity) {
        mActivity = activity;
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public List<Channel> getChannelArrayList() {
        return mChannelList;
    }

    public void fetchXML(final String urlString) {
       // if (Utils.isInternetAvailable(mActivity)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        conn.setReadTimeout(mReadTimeOut);
                        conn.setConnectTimeout(mConnectTimeout);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream stream = conn.getInputStream();

                        mXmlPullParserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser xmlPullParser = mXmlPullParserFactory.newPullParser();
                        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        xmlPullParser.setInput(stream, null);

                        parseXMLAndStoreIt(xmlPullParser);

                        stream.close();
                        if (dataListener != null) {
                            dataListener.onDataSuccess(true);
                        }
                    } catch (IOException | XmlPullParserException e) {
                        if (dataListener != null) {
                            dataListener.onDataSuccess(false);
                        }
                        Log.e(TAG, "Exception : " + e.getMessage());
                    }
                }
            });
            thread.start();

    }

    private void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        Channel channel = null;

        String tagTitle = "title";
        String tagLink = "link";
        String tagDescription = "description";

        mChannelList = new ArrayList<>();

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals(tagTitle)) {
                            channel = new Channel();
                            channel.setTitle(Html.fromHtml(text).toString());
                        } else if (name.equals(tagLink)) {
                            if (channel != null) {
                                channel.setLink(Html.fromHtml(text).toString());
                            }
                        } else if (name.equals(tagDescription)) {
                            if (channel != null) {
                                channel.setDescription(Html.fromHtml(text).toString());
                            }
                            mChannelList.add(channel);
                        }
                        break;
                     default:
                        Log.e("default event selected","event");

                }
                event = myParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        }
    }
}
