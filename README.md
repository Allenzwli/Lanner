# Lanner
自定义Android广告轮播条(无限循环轮播,可自定义dots样式位置,延迟时间,滚动时间,item点击事件)

## 效果图
![](https://github.com/lizhaowei213/Lanner/blob/master/LannerDemo/demo.gif)
## 使用方法(详见Demo工程)

```Java
public class MainActivity extends AppCompatActivity {

    private List<LannerBean> lannerBeans;
    private Lanner mLanner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLanner= (Lanner) findViewById(R.id.lanner);
        
        lannerBeans=new ArrayList<LannerBean>();
        
        LannerBean bean=new LannerBean();
        bean.setImageUrl("http://......");
        bean.setTitle("测试0");
        lannerBeans.add(bean);
        ......

        mLanner.setLannerBeanList(lannerBeans);
        mLanner.setOnLannerItemClickListener(new Lanner.OnLannerItemClickListener() {
            @Override
            public void click(View v, LannerBean lb) {
                Toast.makeText(MainActivity.this,lb.getTitle(),Toast.LENGTH_SHORT).show();
                //可通过扩充LannerBean类成员,实现自定义点击事件。
            }
        });
    }
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="lzw.csu.lannerdemo.MainActivity">

    <lzw.csu.lannerdemo.Lanner
        android:id="@+id/lanner"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:dotsSelectedIcon="@drawable/dot_focus"<!-- 选中时的dots图标 -->
        app:dotsUnSelectedIcon="@drawable/dot_blur"<!-- 未选中时dots图标 -->
        app:delayTime="3000"<!-- 单张图片显示时间 -->
        app:scrollerDuration="800"/><!-- scroller滚动时间 -->
</RelativeLayout>

```
