package pereberge.sumproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import pereberge.sumproject.R;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.ServiceFactory;

public class SplashScreenActivity extends AppCompatActivity {

    Thread splashTread;
    private ReservationService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        service = ServiceFactory.getReservationService(getApplicationContext());
        startAnimations();
    }

    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout layout = (LinearLayout) findViewById(R.id.lin_lay);
        assert layout != null;
        layout.clearAnimation();
        layout.startAnimation(anim);

        Animation anim_alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        ImageView iv2 = (ImageView) findViewById(R.id.title_app);
        assert iv != null;
        assert iv2 != null;
        iv.clearAnimation();
        iv2.clearAnimation();
        iv.startAnimation(anim);
        iv2.startAnimation(anim_alpha);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    while (service.getPartnerNames().size() == 0) {
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, TimetableActivity.class);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
