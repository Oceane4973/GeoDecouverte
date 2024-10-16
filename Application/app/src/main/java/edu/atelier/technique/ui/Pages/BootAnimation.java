package edu.atelier.technique.ui.Pages;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;


/**
 * Cette activité représente la bootAnimation
 */
public class BootAnimation extends AppCompatActivity {

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_animation);

        this.findViewById(R.id.app_name).setAlpha(0f);
        this.findViewById(R.id.app_name).setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this.findViewById(R.id.ic_gps), "translationX", -300f);
            ObjectAnimator animatorName = ObjectAnimator.ofFloat(this.findViewById(R.id.app_name), "translationX", 100f);
            animator.setDuration(3000);
            animatorName.setDuration(3000);
            animator.start();
            animatorName.start();
            this.findViewById(R.id.app_name).animate()
                    .alpha(1f)
                    .setDuration(2500)
                    .setListener(null);
        }

        new Handler().postDelayed(()-> {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                finish();
        }, 3000);
    }
}