package lzw.csu.lannerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen_Binan on 2016/4/6.
 */
public class Lanner extends FrameLayout implements View.OnClickListener{

    private List<LannerBean> mLannerBeanList;
    private List<View> mViewList;
    private Context mContext;
    private ViewPager mViewPager;
    private boolean isAutoPlay;
    private int mCurrentItem;
    private int mDelayTime;
    private int mScrollerDuration;
    private LinearLayout mDotLinearLayout;
    private List<ImageView> mDotImageViewList;
    private Handler handler = new Handler();
    private OnLannerItemClickListener mLannerItemClickListener;
    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                mCurrentItem = mCurrentItem % (mLannerBeanList.size() + 1) + 1;
                if (mCurrentItem == 1) {
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    handler.post(task);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    handler.postDelayed(task, mDelayTime);
                }
            } else {
                handler.postDelayed(task, mDelayTime);
            }
        }
    };

    public Lanner(Context context) {
        super(context);
    }

    public Lanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mViewList=new ArrayList<View>();
        mDotImageViewList=new ArrayList<ImageView>();
        mLannerBeanList=new ArrayList<LannerBean>();

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.Lanner);
        mScrollerDuration=ta.getInteger(R.styleable.Lanner_scrollerDuration,600);
        mDelayTime=ta.getInteger(R.styleable.Lanner_delayTime,5000);
        ta.recycle();
    }


    public void setLannerBeanList(List<LannerBean> list){
        mLannerBeanList=list;
        if(mViewList!=null)
            mViewList.clear();
        initView();
    }

    private void setViewPageDefaultScrollerDuation(int mDuration) {
        try {
            Field mScroller=ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            CustomSpeedScroller scroller=new CustomSpeedScroller(mContext);
            scroller.setmDuration(mDuration);
            mScroller.set(mViewPager,scroller);
        } catch (Exception e) {
        }
    }

    private void initView(){
        View view= LayoutInflater.from(mContext).inflate(R.layout.view_lanner, this, true);
        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        setViewPageDefaultScrollerDuation(mScrollerDuration);
        mDotLinearLayout= (LinearLayout) view.findViewById(R.id.dotsLinearLayout);
        mDotLinearLayout.removeAllViews();
        int len=mLannerBeanList.size();
        for(int i=0;i<len;i++){
            ImageView dotItemImageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            mDotLinearLayout.addView(dotItemImageView, params);
            mDotImageViewList.add(dotItemImageView);
        }
        for(int i=0;i<=len+1;i++){
            View itemView=LayoutInflater.from(mContext).inflate(
                    R.layout.view_kanner_item, null);
            ImageView titleImageView = (ImageView)itemView.findViewById(R.id.titleImageView);
            TextView titleTextView = (TextView)itemView.findViewById(R.id.titleTextView);
            if (i == 0) {
                Picasso.with(mContext).load(mLannerBeanList.get(len-1).getImageUrl()).into(titleImageView);
                titleTextView.setText(mLannerBeanList.get(len-1).getTitle());
            } else if (i == len + 1) {
                Picasso.with(mContext).load(mLannerBeanList.get(0).getImageUrl()).into(titleImageView);
                titleTextView.setText(mLannerBeanList.get(0).getTitle());
            } else {
                Picasso.with(mContext).load(mLannerBeanList.get(i-1).getImageUrl()).into(titleImageView);
                titleTextView.setText(mLannerBeanList.get(i-1).getTitle());
            }
            itemView.setOnClickListener(this);
            mViewList.add(itemView);
        }
        for (int i = 0; i < mDotImageViewList.size(); i++) {
            if (i==0) {
                mDotImageViewList.get(i).setImageResource(R.drawable.dot_focus);
            } else {
                mDotImageViewList.get(i).setImageResource(R.drawable.dot_blur);
            }
        }
        mViewPager.setAdapter(new LannerPagerAdapter());
        mViewPager.setFocusable(true);
        mViewPager.setCurrentItem(1);
        mCurrentItem = 1;
        mViewPager.addOnPageChangeListener(new LannerOnPageChangeListener());
        startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;
        handler.postDelayed(task, mDelayTime);
    }


    class LannerPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class LannerOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mDotImageViewList.size(); i++) {
                if (i+1== position) {
                    mDotImageViewList.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    mDotImageViewList.get(i).setImageResource(R.drawable.dot_blur);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isAutoPlay = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(mLannerBeanList.size(), false);
                    } else if (mViewPager.getCurrentItem() == mLannerBeanList.size() + 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                    mCurrentItem = mViewPager.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mLannerItemClickListener != null) {
            LannerBean bean=mLannerBeanList.get(mViewPager.getCurrentItem() - 1);
            mLannerItemClickListener.click(v,bean);
        }
    }

    public interface OnLannerItemClickListener {
        void click(View v,LannerBean lb);
    }

    public void setOnLannerItemClickListener(OnLannerItemClickListener mItemClickListener) {
        this.mLannerItemClickListener = mItemClickListener;
    }
}
