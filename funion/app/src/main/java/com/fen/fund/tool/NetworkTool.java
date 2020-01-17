package com.fen.fund.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 网络工具类
 */
public class NetworkTool {
    private static final int NET_TYPE_WIFI = 0x01;
    private static final int NET_TYPE_CM_WAP = 0x02;
    private static final int NET_TYPE_CM_NET = 0x03;
    // 二进制32位为全1的整数值
    private static final long ALL_32_ONE = 4294967295L;
    private static final String TAG = NetworkTool.class.getName();

    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = Objects.requireNonNull(connectivity).getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (Objects.requireNonNull(networkInfo).getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            //否则调用旧版本方法
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NET_TYPE_CM_NET;
                } else {
                    netType = NET_TYPE_CM_WAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NET_TYPE_WIFI;
        }
        return netType;
    }

    /**
     * 获取当前ip地址
     */
    public static Long getLocalIp(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return (long) i;
        } catch (Exception ex) {
            Log.e(TAG, "获取IP地址异常" + ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 字符串IP转成数字IP
     */
    public static long ipToLong(String ipv4) {
        if(!isValidIpv4(ipv4)) {
            return -1;
        }

        long temp = 0;
        String cur;
        int pos = ipv4.indexOf(".", 0);
        while (pos != -1) {
            cur = ipv4.substring(0, pos);
            ipv4 = ipv4.substring(pos + 1);
            temp = (temp << 8) | Long.valueOf(cur);
            pos = ipv4.indexOf(".", 0);
        }
        temp = (temp << 8) | Long.valueOf(ipv4);
        return temp;
    }

    /**
     * 数字IP转成字符串IP
     */
    public static String ipToString(long ipv4Long) {
        if(ipv4Long < 0 || ipv4Long > ALL_32_ONE) {
            return null;
        }

        final long MASK = 255;
        long result = ipv4Long & MASK;
        String temp = String.valueOf(result);
        for (int i = 0; i < 3; i++) {
            ipv4Long = ipv4Long >> 8;
            result = ipv4Long & MASK;
            temp = String.valueOf(result) + "." + temp;
        }
        return temp;
    }

    /**
     * 验证IP地址是否合法的ipv4
     */
    private static boolean isValidIpv4(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        StringTokenizer st = new StringTokenizer(ip, ".");
        int i = 0;
        for (; st.hasMoreTokens(); i++) {
            int n;
            try {
                n = Integer.valueOf(st.nextToken());
            } catch (Exception e) {
                return false;
            }
            if (n > 255 || n < 0) {
                return false;
            }
        }
        return i == 4;
    }

}
