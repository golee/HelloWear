package hello.wear.hellowear;

import android.net.Uri;
import android.os.Environment;
import android.support.wearable.activity.InsetActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

//import android.support.wearable.activity.WatchActivity;



public class MyWatchActivity extends InsetActivity {

    private TextView mTextView;
    private ImageView mImageView;
    final String pPath = new String("/Pictures/eura.jpg");

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