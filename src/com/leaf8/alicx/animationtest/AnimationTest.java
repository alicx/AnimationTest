package com.leaf8.alicx.animationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

/**
 * 动画测试
 * 
 * @author alicx
 * @date Feb 14, 2014
 */
public class AnimationTest extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    private ViewGroup layout;
    private TextView button_translate;
    private Button button_rotate;
    private Animation myAnimation_Rotate;
    private ViewGroup con;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        layout = (ViewGroup) findViewById(R.id.layout);// 动画层
        con = (ViewGroup) findViewById(R.id.widget32);
        con.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);

        final Rotate3dAnimation rotation =
            new Rotate3dAnimation(270, 360, con.getHeight() / 2.0f, con.getWidth() / 2.0f, 310.0f,
                true);
        rotation.setDuration(1000);// 速度1秒
        rotation.setInterpolator(new DecelerateInterpolator());// 减速展开
        rotation.setAnimationListener(new MyListener());// 设置监听
        layout.startAnimation(rotation);

        button_translate = (TextView) findViewById(R.id.flip2);
        button_rotate = (Button) findViewById(R.id.button_Rotate);
        button_rotate.setOnClickListener(this);
    }

    public void onClick(View button) {
        switch (button.getId()) {
        case R.id.button_Rotate:
            myAnimation_Rotate = AnimationUtils.loadAnimation(this, R.anim.my_rotate_action);
            myAnimation_Rotate.setFillAfter(true);// 使控件停留在最后状态
            button_rotate.startAnimation(myAnimation_Rotate);
            break;
        default:
            break;
        }
    }

    class MyListener implements Animation.AnimationListener {

        private MyListener() {
        }

        public void onAnimationStart(Animation animation) {
            con.post(new SwapViews(false));
        }

        public void onAnimationEnd(Animation animation) {
            con.post(new SwapViews(true));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private final class SwapViews implements Runnable {
        private boolean isShow;

        public SwapViews(boolean show) {
            isShow = show;
        }

        public void run() {
            if (isShow) {
                Animation translate = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.my_translate_action);
                translate.setFillAfter(true);// 使控件停留在最后状态
                button_rotate.setAnimation(translate);
                button_translate.setAnimation(translate);
                button_translate.setVisibility(View.VISIBLE);
                button_rotate.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            } else {
                button_translate.setVisibility(View.GONE);
                button_rotate.setVisibility(View.GONE);
            }
        }
    }
}
