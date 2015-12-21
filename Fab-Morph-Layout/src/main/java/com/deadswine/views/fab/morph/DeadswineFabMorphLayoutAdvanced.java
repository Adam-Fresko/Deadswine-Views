package com.deadswine.views.fab.morph;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


/**
 * Created by Adam Fręśko - Deadswine Studio on 06.12.2015.
 * Deadswine.com
 * <br>
 * This is and exemaple of compound view
 */
public class DeadswineFabMorphLayoutAdvanced extends FrameLayout implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    boolean isDebug = true;

    public void log(String log) {
        Log.d(TAG, log);
    }


    boolean isExpanded;
    boolean isInProgress;

    PointF origin;
    PointF target;

    private Path pathForward;
    private Path pathBackward;
    PathMeasure pathMeasureForward;
    PathMeasure pathMeasureBackward;

    ObjectAnimator arcAnimForward;
    ObjectAnimator arcAnimRewerse;

    ValueAnimator animatorForward;
    ValueAnimator animatorBackward;

    Interpolator interpolator;

    int animDuration = 500;

    Handler mHandler;

    int resourceId; // variable storing layout passed in xml

    DeadswineFabMorphInterface mInterface;

    public void setInterface(DeadswineFabMorphInterface mInterface) {
        this.mInterface = mInterface;
    }

    public interface DeadswineFabMorphInterface {

        public void onDeadswineMorphInitialized();


    }


    /**
     * Constructor allowing for reading custom xml attributes
     *
     * @param context
     * @param attrs
     */
    public DeadswineFabMorphLayoutAdvanced(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeadswineViews, 0, 0);
        try {
            resourceId = ta.getResourceId(R.styleable.DeadswineViews_layoutResource, 0);
        } finally {
            ta.recycle(); // remember to recycle
        }


        inflate();
    }


    ImageButton fab;
    View vTarget;

    private void inflate() {
        //  View view = LayoutInflater.from(getContext()).inflate(R.layout.fab_morph_layout, this, true);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, this, true);

        fab = (ImageButton) view.findViewById(R.id.fab); // your fab needs to have this id
        vTarget = view.findViewById(R.id.morphTarget);  // your target view needs to have this id
        vTarget.setVisibility(INVISIBLE);
        vTarget.post(new Runnable() { // this runnable will be run after views has been set allowing us to properly calculate animations
            @Override
            public void run() {
                init(); // method initializing paths for animations
            }
        });

    }
    TransitionDrawable transition;
    Transition  transitionBackground;

    private void init() {
        interpolator = new FastOutSlowInInterpolator();
        mHandler = new Handler();

        isExpanded = false; // control boolean variables
        isInProgress = false;

        RelativeLayout tmp = (RelativeLayout) vTarget;

        LayoutParams lp = (LayoutParams) tmp.getLayoutParams();

        int i = lp.leftMargin;
        int i2 = lp.bottomMargin;
        tmp = null;
        lp = null;

        // store center of target
        target = new PointF(vTarget.getX() + i + ((((vTarget.getWidth()-(i*2)) / 2)) - (fab.getWidth() / 2)),
                (vTarget.getY() -i2+ (vTarget.getHeight() / 2)) - (fab.getHeight() / 2));


        origin = new PointF(fab.getX(), fab.getY()); // store center of fab

        float x1 = fab.getX();
        float y1 = fab.getY();

        pathForward = new Path();
        pathForward.moveTo(x1, y1);
        float x2 = (x1 + target.x) / 2;
        float y2 = y1;
        pathForward.quadTo(x2, y2, target.x, target.y);

        pathMeasureForward = new PathMeasure(pathForward, false);

        pathBackward = new Path();
        pathBackward.moveTo(x1, y1);
        x2 = (x1 + target.x) / 2;
        y2 = (y1 + (target.y - y1));
        pathBackward.quadTo(x2, y2, target.x, target.y);

        pathMeasureBackward = new PathMeasure(pathBackward, false);

 //   fab.setBackgroundResource(R.drawable.fab_transition_drawable);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            fab.setBackground(, getContext().getTheme()));
//        } else {
//            fab.setBackground(getResources().getDrawable(R.drawable.fab_transition_drawable));
//        }


      transition = (TransitionDrawable) getResources().getDrawable(R.drawable.fab_transition_drawable);

              fab.setBackground(transition);


        fab.setOnClickListener(this);

        if (mInterface != null) {
            mInterface.onDeadswineMorphInitialized();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        log("onCreate - START");

    }


    public void toggle() {

        if (isInProgress) {
            log("Returning - transition in progress");
            return;
        }


        if (isExpanded) {
            collapse();

        } else {
            expand();
        }

    }


    private void expand() {
        log("EXPANDING FAB");

        isInProgress = true;

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {

            forwardLolipop();
        } else {
            forwardOld();
        }


    }

    private void expandTarget() {
        vTarget.setVisibility(VISIBLE);

        int cx = (int) vTarget.getX() + vTarget.getWidth() / 2;
        int cy = (int) vTarget.getHeight() / 2;


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int bigRadius = Math.max(vTarget.getWidth(), vTarget.getHeight());


        Animator anim = ViewAnimationUtils.createCircularReveal(vTarget, cx, cy, 0, bigRadius);
        // anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(animDuration);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                vTarget.clearAnimation();

                isInProgress = false;
            }
        });

        anim.start();

    }

    private void collapse() {
        log("COLLAPSING FAB");

        isInProgress = true;

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            colapseTargetLolipop();
        } else {
            collapseTargetOld();
        }

    }

    private void collapseTargetOld() {


        vTarget.animate().scaleX(0).scaleY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.clearAnimation();
                fab.setScaleX(0);
                fab.setScaleY(0);
                fab.animate().scaleX(1).scaleY(1).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                vTarget.setVisibility(INVISIBLE);
                vTarget.clearAnimation();
                backOld();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    private void colapseTargetLolipop() {

        vTarget.setVisibility(VISIBLE);

        int cx = (int) vTarget.getX() + vTarget.getWidth() / 2;
        int cy = (int) vTarget.getHeight() / 2;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int bigRadius = Math.max(vTarget.getWidth(), vTarget.getHeight());


        Animator anim = ViewAnimationUtils.createCircularReveal(vTarget, cx, cy, bigRadius, 0);
        anim.setInterpolator(interpolator);
        anim.setDuration(animDuration);
        anim.addListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                vTarget.setVisibility(INVISIBLE);
                vTarget.clearAnimation();
                // arcAnimForward.reverse();

                backLolipop();
            }

        });

        anim.start();


    }

    private void forwardLolipop() {

        if (arcAnimForward == null) {

            arcAnimForward = ObjectAnimator.ofFloat(fab, View.X, View.Y, pathForward);
            arcAnimForward.setInterpolator(interpolator);
            arcAnimForward.setDuration(animDuration);
            arcAnimForward.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    transition.startTransition(animDuration);

                    fab.animate().scaleY(0.7f).scaleX(0.7f).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.setImageDrawable(null);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }

                @Override
                public void onAnimationEnd(Animator animation) {


                    if (isExpanded) {
                        isInProgress = false;

                        isExpanded = false;
                        vTarget.setVisibility(INVISIBLE);
                    } else {
                        isExpanded = true;

                        expandTarget();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }

        arcAnimForward.start();

    }

    //coordinates will be here
    float aCoordinates[] = {0f, 0f};

    private void forwardOld() {
        log("Width: " + getWidth() + "   height: " + getHeight());

        animatorForward = ValueAnimator.ofFloat(0, 1);
        animatorForward.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();

                pathMeasureForward.getPosTan(pathMeasureForward.getLength() * value, aCoordinates, null);
                fab.animate().x(aCoordinates[0]).y(aCoordinates[1]).start();

                //  log("value: " + value + " x: " + aCoordinates[0] + " y: " + aCoordinates[1]);

            }

        });

        animatorForward.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isInProgress = false;

                log("animation == animatorForward");
                if (isExpanded) {
                    isExpanded = false;
                    vTarget.setVisibility(INVISIBLE);
                } else {


                    mHandler.postDelayed(animationRunnable, 300);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorForward.start();
    }

    private void backLolipop() {

        if (arcAnimRewerse == null) {





            arcAnimRewerse = ObjectAnimator.ofFloat(fab, View.X, View.Y, pathForward);
            arcAnimRewerse.setInterpolator(interpolator);
            arcAnimRewerse.setDuration(animDuration);
            arcAnimRewerse.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                fab.animate().scaleX(1f).scaleY(1f).start();
                    transition.reverseTransition(animDuration);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isExpanded) {
                        isInProgress = false;

                        isExpanded = false;
                        vTarget.setVisibility(INVISIBLE);
                        fab.clearAnimation();
                        fab.setImageResource(R.drawable.ic_search_24dp);

                    } else {
                        isExpanded = true;

                        expandTarget();

                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }

        arcAnimRewerse.reverse();

    }


    private void backOld() {
        log("Width: " + getWidth() + "   height: " + getHeight());

        animatorBackward = ValueAnimator.ofFloat(1, 0);
        animatorBackward.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();

                pathMeasureBackward.getPosTan(pathMeasureForward.getLength() * value, aCoordinates, null);
                fab.animate().x(aCoordinates[0]).y(aCoordinates[1]).start();

                //  log("value: " + value + " x: " + aCoordinates[0] + " y: " + aCoordinates[1]);

            }

        });

        animatorBackward.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isInProgress = false;

                log("animation == animatorForward");
                if (isExpanded) {
                    isExpanded = false;
                    vTarget.setVisibility(INVISIBLE);
                } else {
                    isExpanded = true;
                    expandTarget();

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorBackward.start();

    }

    @Override
    public void onClick(View v) {
        log("FAB ON CLICK");

        toggle();
    }

    Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {

            fab.animate().scaleX(0).scaleY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();

            vTarget.setVisibility(VISIBLE);
            vTarget.setScaleX(0);
            vTarget.setScaleY(0);


            vTarget.animate().scaleX(1).scaleY(1).setStartDelay(200).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isExpanded = true;
                    vTarget.clearAnimation();
                    fab.setScaleX(1);
                    fab.setScaleY(1);
                    fab.clearAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    };


    public ImageButton getFab() {

        return fab;
    }

    public View getTargetView() {

        return vTarget;
    }


    //   LinearLayout myTargetLayout = (LinearLayout) getTargetView();

}



