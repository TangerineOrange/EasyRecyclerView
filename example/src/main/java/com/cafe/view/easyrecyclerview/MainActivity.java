package com.cafe.view.easyrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cafe.view.easyrecyclerview.fragment.BaseFragment;
import com.cafe.view.easyrecyclerview.fragment.NavigationFragment;
import com.cafe.view.easyrecyclerview.fragment.SimpleListFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private BaseFragment lastFragment;

    private Interpolator mInterpolator;
    private Animator mCircularReveal;
    private ObjectAnimator mColorChange;
    private FloatingActionButton btnFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        btnFab = (FloatingActionButton) findViewById(R.id.fab_quiz);
        btnFab.setImageResource(android.R.drawable.ic_dialog_email);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(v, new SimpleListFragment());
            }
        });


        if (savedInstanceState == null)
            initFragment();

        mInterpolator = new FastOutSlowInInterpolator();

    }

    private void initFragment() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, new NavigationFragment()).commit();
    }

    public void addFragment(BaseFragment fragment) {
        if (fragment != null) {

            manager.beginTransaction()
                    .replace(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();

        }


    }

    public void addFragment(View clickedView, BaseFragment fragment) {
        if (fragment != null) {

            manager.beginTransaction()
                    .replace(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();

            if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
                FrameLayout container = (FrameLayout) findViewById(R.id.fragment);
                revealFragmentContainerLollipop(clickedView, container);
            }

        }

    }

    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    public void showFAB() {
        Log.i("navigation", " showFAB");

        btnFab.setVisibility(View.VISIBLE);

        btnFab.clearAnimation();
        ViewCompat.animate(btnFab)
                .setDuration(500)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setListener(null)
                .setInterpolator(mInterpolator)
                .withLayer().start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealFragmentContainerLollipop(final View clickedView,
                                                 final FrameLayout fragmentContainer) {
        prepareCircularReveal(clickedView, fragmentContainer);

        ViewCompat.animate(clickedView)
                .setDuration(500)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setInterpolator(mInterpolator)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        Log.i("onAnimationEnd", " GONE");

                        fragmentContainer.setVisibility(View.VISIBLE);
                        clickedView.setVisibility(View.GONE);
                    }
                })
                .withLayer().start();

        fragmentContainer.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mCircularReveal).with(mColorChange);
        animatorSet.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void prepareCircularReveal(View startView, FrameLayout targetView) {
        int centerX = (startView.getLeft() + startView.getRight()) / 2;
        Log.i("anim123", "centerX  " + centerX);
        // Subtract the start view's height to adjust for relative coordinates on screen.
        int centerY = (startView.getTop() + startView.getBottom()) / 2 ;
        Log.i("anim123", "centerY  " + centerY);
        float endRadius = (float) Math.hypot(centerX, centerY);
        Log.i("anim123", "endRadius  " + endRadius);
        mCircularReveal = ViewAnimationUtils.createCircularReveal(
                targetView, centerX, centerY, startView.getWidth(), endRadius);
        mCircularReveal.setInterpolator(new FastOutLinearInInterpolator());

        mCircularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCircularReveal.removeListener(this);
            }
        });
        // Adding a color animation from the FAB's color to transparent creates a dissolve like
        // effect to the circular reveal.
        int accentColor = ContextCompat.getColor(this, R.color.colorAccent);
        mColorChange = ObjectAnimator.ofInt(targetView,
                ViewUtils.FOREGROUND_COLOR, accentColor, Color.TRANSPARENT);
        mColorChange.setEvaluator(new ArgbEvaluator());
        mColorChange.setInterpolator(mInterpolator);
    }



}
