package pereberge.sumproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pereberge.sumproject.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initListeners();
    }

    private void initListeners() {
        TextView first = (TextView) findViewById(R.id.text_about_3);
        TextView second = (TextView) findViewById(R.id.text_about_4);
        TextView third = (TextView) findViewById(R.id.text_about_5);

        assert first != null;
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.pere_link)));
                startActivity(browserIntent);
            }
        });

        assert second != null;
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.albert_link)));
                startActivity(browserIntent);
            }
        });

        assert third != null;
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.elena_link)));
                startActivity(browserIntent);
            }
        });
    }
}
