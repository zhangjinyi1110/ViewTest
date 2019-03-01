package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityWebBinding;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WebActivity extends AppCompatActivity {

    private ActivityWebBinding binding;

    public final static String RELEASE_KEY = "WU6t5$cB26%t0d0JwX1Qee6Da384#H2jvrwZ^v^q2VaYCbHpWea6poO#f4kFu@kkr5*jj^BDpYAc8mb^xOLP26geA%CYGe29^v2IUKETI2TElLqSoZQk08a5vCeLVtAN";
    private final String BASE_URL = "http://jfmall.51ehw.com/index.php/Pointshop/point_index?integral_system=6&action=login&_source=app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        binding.web.loadUrl(getUrl());
    }

    public String getUrl() {
        String time = String.valueOf(System.currentTimeMillis());
        String url = BASE_URL + "&i=56035"
                + "&m=13192294682"
                + "&_sign_time=" + time.substring(0, time.length() - 3);
        String sign = integralMD5(url.split("\\?")[1]);
        url += ("&_sign=" + sign);
        return url;
    }
    //积分加密md5
    public String integralMD5(String param) {
        String[] params = param.split("&");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(params));
        Collections.sort(list);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i));
        }
        buffer.append(RELEASE_KEY);
        return toMD5(buffer.toString());
    }
    public String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //System.out.println("32位: " + buf.toString());// 32位的加密
            //System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
