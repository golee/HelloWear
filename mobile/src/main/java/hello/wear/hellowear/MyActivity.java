package hello.wear.hellowear;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;


public class MyActivity extends Activity {

    private EditText mEditText;
    private TextView mTextView;
    private Button mButton;

    private static GoogleApiClient mGoogleApiClient;
    private static String TAG = new String("Jebum");

    private static String PATH;
    private static String KEY_TITLE="keytitle";
    private static String KEY_IMAGE="keyimage";

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

        mEditText=(EditText)findViewById(R.id.editText1);
        mTextView=(TextView)findViewById(R.id.textView1);
        mButton=(Button)findViewById(R.id.button1);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    mTextView.setText(mEditText.getText());
                    return true;
                }
                else
                {
                    mTextView.setText("actionId : "+actionId+" EditorInfo : "+EditorInfo.IME_ACTION_UNSPECIFIED);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        if ( mGoogleApiClient.isConnected() )
        {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    public void onClick(View v)
    {
        if (sendMessage(mTextView.getText()))
        {
            mTextView.setText("\""+mTextView.getText()+" \" transfer success!");
        }
        else
        {

        }
    }
    public boolean sendMessage(CharSequence s)
    {
        if (mGoogleApiClient.isConnected()) {
            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(PATH);

            // Add data to the request
            putDataMapRequest.getDataMap().putString(KEY_TITLE,
                    String.format("%s %d", s, count++));

            Bitmap icon = BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_launcher);
            Asset asset = createAssetFromBitmap(icon);
            putDataMapRequest.getDataMap().putAsset(KEY_IMAGE, asset);

            PutDataRequest request = putDataMapRequest.asPutDataRequest();

            Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            Log.d(TAG, "putDataItem status: "
                                    + dataItemResult.getStatus().toString());
                        }
                    });
        }
        return true;
    }


    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
