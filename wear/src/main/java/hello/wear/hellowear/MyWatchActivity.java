package hello.wear.hellowear;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.InsetActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.io.File;

//import android.support.wearable.activity.WatchActivity;



public class MyWatchActivity extends InsetActivity {

    private static String TAG = new String("Jebum");
    private static TextView mTextView;
    private static ImageView mImageView;
    final static private String pPath = new String("/Pictures/eura.jpg");

    private GoogleApiClient mGoogleAppiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle connectionHint) {
                    Log.d(TAG, "onConnected: " + connectionHint);
                    // Now you can use the data layer API
                }
                @Override
                public void onConnectionSuspended(int cause) {
                    Log.d(TAG, "onConnectionSuspended: " + cause);
                }
            })
            .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult result) {
                    Log.d(TAG, "onConnectionFailed: " + result);
                }
            })
            .addApi(Wearable.API)
            .build();



    @Override
    public void onReadyForContent() {
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mImageView = (ImageView) stub.findViewById(R.id.testImg);
                //Log.d("jebum", "TextView: " + mTextView.getText());
                Log.d("jebum", "ESD"+Environment.getExternalStorageDirectory().toString());

            }
        });
    }


    public void imageSwitch(View v) {
        if (mTextView.getText() != "GIRLSDAY") {
            try {
                File f = new File(Environment.getExternalStorageDirectory().toString() + pPath);

                mTextView.setText("GIRLSDAY");
                mImageView.setImageURI(Uri.fromFile(f));
            } catch (Exception e) {
                mTextView.setText("No Image" + Environment.getExternalStorageDirectory().toString() + pPath + e.toString());
            }
        }
        else
        {
            mTextView.setText("Click");
            mImageView.setImageResource(R.drawable.test_img);
        }

    }
}