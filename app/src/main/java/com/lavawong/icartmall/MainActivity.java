package com.lavawong.icartmall;

import android.annotation.TargetApi;
import android.content.SyncStatusObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private EditText inputMsg;

    private ArrayList<String> sendHistory;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendHistory = new ArrayList<>();
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        inputMsg = findViewById(R.id.edit_message);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.edit_message);
                Editable text = et.getText();
                sendHistory.add(text.toString());
                System.out.println(text);
                StringBuilder sendStr = new StringBuilder();
                for (final String msg : sendHistory) {
                    sendStr.append(msg).append("<br/>");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    mTextMessage.setText(Html.fromHtml(sendStr.toString(), 1,
                            new Html.ImageGetter() {
                        @Override
                        public Drawable getDrawable(String source) {
                            Drawable draw = null;
                            try {
                                Field field = R.drawable.class.getField(source);
                                int resourceId = Integer.parseInt(field.get(null).toString());
                                draw = getResources().getDrawable(resourceId);
                                draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return draw;
                        }
                    }, null));
                else {
                    mTextMessage.setText(Html.fromHtml(sendStr.toString()));
                }

                inputMsg.setText("");

            }
        });
    }

    public void sendMessage() {

    }

}
