package net.devwiki.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.annotation.Target;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private NewsClient newsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsClient = NewsClient.getInstance();
        Button getBtn = findViewById(R.id.getNewBtn);
        getBtn.setOnClickListener(v -> getNews());
    }

    public void getNews() {
        newsClient.getNews("top")
                .subscribe(newsResponse -> {
                    if (newsResponse != null) {
                        Log.d(TAG, "" + newsResponse.getResult().getData().size());
                    }
                }, throwable -> {
                    Log.w(TAG, throwable.getMessage());
                });
    }
}
