package com.alion.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.alion.library.FlowLayout;

import java.util.Random;

public class MainActivity
        extends AppCompatActivity
{
    private FlowLayout mFlowLayout;
    private String[] mDatas = new String[]{"QQ",
                                           "视频",
                                           "放开那三国",
                                           "电子书",
                                           "酒店",
                                           "单机",
                                           "小说",
                                           "斗地主",
                                           "优酷",
                                           "网游",
                                           "WIFI万能钥匙",
                                           "播放器",
                                           "捕鱼达人2",
                                           "机票",
                                           "游戏",
                                           "熊出没之熊大快跑",
                                           "美图秀秀",
                                           "浏览器",
                                           "单机游戏",
                                           "我的世界",
                                           "电影电视",
                                           "QQ空间",
                                           "旅游",
                                           "免费游戏",
                                           "2048",
                                           "刀塔传奇",
                                           "壁纸",
                                           "节奏大师",
                                           "锁屏",
                                           "装机必备",
                                           "天天动听",
                                           "备份",
                                           "网盘",
                                           "海淘网",
                                           "大众点评",
                                           "爱奇艺视频",
                                           "腾讯手机管家",
                                           "百度地图",
                                           "猎豹清理大师",
                                           "谷歌地图",
                                           "hao123上网导航",
                                           "京东",
                                           "youni有你",
                                           "万年历-农历黄历",
                                           "支付宝钱包"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout_main);

        mFlowLayout.setPadding(10,10,10,10);
        mFlowLayout.setSpace(10,10);

        Random rdm = new Random();
        for (int i = 0; i < mDatas.length; i++) {
            TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setBackgroundColor(Color.GRAY);
            view.setTextColor(Color.WHITE);
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
                        view.setTextSize(rdm.nextInt(16) + 10);
//            view.setTextSize(14);
            mFlowLayout.addView(view);//触发重绘
        }
    }
}
