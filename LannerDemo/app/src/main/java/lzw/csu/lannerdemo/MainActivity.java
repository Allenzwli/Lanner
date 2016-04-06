package lzw.csu.lannerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        bean.setImageUrl("http://pic.baike.soso.com/p/20131101/20131101173255-1526197099.jpg");
        bean.setTitle("测试0");
        lannerBeans.add(bean);

        bean=new LannerBean();
        bean.setImageUrl("http://i-7.vcimg.com/crop/a80fdc7b58a9d1893c26e88dfbd0134164072%28600x%29/thumb.jpg");
        bean.setTitle("测试1");
        lannerBeans.add(bean);

        bean=new LannerBean();
        bean.setImageUrl("http://imgsrc.baidu.com/forum/w%3D580/sign=7e06c21ad2a20cf44690fed746084b0c/55ee52d9f2d3572c365bd9418813632763d0c3b5.jpg");
        bean.setTitle("测试2");
        lannerBeans.add(bean);

        bean=new LannerBean();
        bean.setImageUrl("http://img3.douban.com/view/note/large/public/p26342691.jpg");
        bean.setTitle("测试3");
        lannerBeans.add(bean);

        bean=new LannerBean();
        bean.setImageUrl("http://y0.ifengimg.com/8548af1bee665a86/2015/0303/rdn_54f5abbc12a0a.jpg");
        bean.setTitle("测试4");
        lannerBeans.add(bean);

        mLanner.setLannerBeanList(lannerBeans);
        mLanner.setOnLannerItemClickListener(new Lanner.OnLannerItemClickListener() {
            @Override
            public void click(View v, LannerBean lb) {
                Toast.makeText(MainActivity.this,lb.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
