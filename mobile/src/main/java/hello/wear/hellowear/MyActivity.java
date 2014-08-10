package hello.wear.hellowear;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends Activity {

    private EditText mEditText;
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
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
        return true;
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
