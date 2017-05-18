package com.stef.android.marvel.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irene on 29-06-16.
 */
public abstract class LineScaleIndicator extends BaseIndicatorController {

    public static final float SCALE=0.9f;

    int color1 =  Color.parseColor("#005FCD");
    int color2 =  Color.parseColor("#FA50A5");

    GradientDrawable gradient;
    int[] colors = {color1,color2,color1,color2,color1,color2};

    float[] scaleYFloats=new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX=getWidth()/11;
        float translateY=getHeight()/2;
        //gradient = new GradientDrawable(GradientDrawable.Orientation.BL_TR,colors);

        for (int i = 0; i < 5; i++) {


            // start at 0,0 and go to 0,max to use a vertical
            // gradient the full height of the screen.
            paint.setShader(new LinearGradient(0, -70, 0, getHeight(), color2, color1, Shader.TileMode.CLAMP));

            //paint.setColor(colors[i]);
            //gradient.mutate();
            // gradient.setShape(GradientDrawable.RADIAL_GRADIENT);
            // This just makes it disappear:
            // setGradientType (GradientDrawable.RADIAL_GRADIENT);

            //gradient.draw(canvas);
            canvas.save();
            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
            canvas.scale(SCALE, scaleYFloats[i]);
            RectF rectF=new RectF(-translateX/2,-getHeight()/2.5f,translateX/2,getHeight()/2.5f);
            canvas.drawRoundRect(rectF, 4, 4, paint);
            canvas.restore();
        }
    }

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators=new ArrayList<>();
        long[] delays=new long[]{200,300,400,500,600};
        for (int i = 0; i < 5; i++) {
            final int index=i;
            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1, 1f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-10);
            scaleAnim.setStartDelay(delays[i]);

            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }



}

