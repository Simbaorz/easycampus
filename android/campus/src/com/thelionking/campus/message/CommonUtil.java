package com.thelionking.campus.message;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.thelionking.campus.message.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * Created by the lion king on 2014/9/21.
 */
public final class CommonUtil {
    public static final String OS = "android";

    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    //一天的毫秒数
    public static final long DAY = 24 * 60 * 60 * 1000;

    public static final String TAG = "CommentUtil";

    public static final String BASE_URL = "http://jenson.sinaapp.com";

    public static final String FROM_TOP_ACTION = "getJokesFromTop";

    public static final String FROM_BOTTOM_ACTION = "getJokesFromBottom";

    /**
     * 根据毫秒数获得日期
     * 格式暂时为 yyyy-MM-dd HH-mm-ss
     * @param millils
     * @return
     */
    public static String getDate(long millils) {
        String date = null;
        SimpleDateFormat f1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat f2 = new SimpleDateFormat("MM-dd HH:mm");
        if(millils >= getMillils(0) && millils < getMillils(0) + CommonUtil.DAY) {
            date= "今日 " + f1.format(millils);
        }else if(millils >= getMillils(0) - CommonUtil.DAY && millils < getMillils(0)) {
            date = "昨日 " + f1.format(millils);
        }else{
            date = f2.format(millils);
        }
        return date;
    }

    /**
     * 获得当天某个小时的起始毫秒数
     * @param hour
     * @return
     */
    public static long getMillils(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得某天某个小时的起始毫秒数
     * @param hour
     * @return
     */
    public static long getMillils(int day, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得当天日期
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(new Date());
        return sdf.toString();
    }

    /**
     * 粘结成url
     * @param action
     * @param start
     * @param type
     * @param mode
     * @param date
     * @return
     */
    public static String spliceUrl(final String action, final int start, final int type, final int mode, final long date) {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        sb.append("/" + action +"?start=" + start + "&type=" + type + "&mode=" + mode + "&date=" + date + "&os=" + OS);
        return sb.toString();
    }

    //MD5转换算法
    public static String md5sum(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            String md5str = bytes2hexString(md.digest());
            return md5str;
        } catch (Exception e) {
            return "";
        }
    }

    //将字节转换成String
    public static String bytes2hexString(byte[] bytes) {
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] result = new char[bytes.length * 2];

        for (int i = 0, k = 0; i < bytes.length; ++i) {
            byte b = bytes[i];
            result[k++] = hexDigits[(b >>> 4) & 0xf];
            result[k++] = hexDigits[b & 0xf];
        }
        return new String(result);
    }

    /**
     * 用来截断author最大长度为7
     * @param author
     * @return
     */
    public static String cutAuthorName(String author) {
        if(author.length() > 7) {
            return author.substring(0, 7) + "...";
        }else{
            return author;
        }
    }

    /**
     * 用来安全地截断ASCII宽度的String，
     * 比如“你好”截取长度是3时，将只返回“你”
     * /
    public static String CutString(String str, int max_length) {
        char[] chars = new char[str.length()];
        StringBuffer sb = new StringBuffer();

        str.getChars(0, str.length(), chars, 0);

        int length = 0;
        int i = 0;
        for (; i < chars.length && length < max_length; ++i) {
            int n = chars[i];
            byte b = (byte) ((n >>> 8) & 0xff);
            if (b == 0) {
                length += 1;
            } else {
                length += 2;
            }
            if (length <= max_length) {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取本机ID
     *
     * @param ctx
     * @return
     */
    public static String getDeviceID(Context ctx) {
    /*
     * 需要添加权限才可以使用： <uses-permission
     * android:name="android.permission.READ_PHONE_STATE" />
     */

        // 下面两个ID只在有通话功能的设备上才可以获取到
        String imei = new String();
        String imsi = new String();
        TelephonyManager telephonyManage = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManage != null) {
            imei = telephonyManage.getDeviceId(); // IMEI for GSM and the MEID or ESN
            // for CDMA
            imsi = telephonyManage.getSubscriberId(); //
        }

        // 下面ID来自Android系统序列号
        String androidId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);

        // 拼一块再做个MD5
        StringBuffer finalID = new StringBuffer();
        finalID.append(imei).append(imsi).append(androidId);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(finalID.toString().getBytes("ISO-8859-1"));
            String md5str = bytes2hexString(md.digest());
            return md5str;
        } catch (Exception e) {
            Log.e("设备信息", "获取设备ID失败：" + e);
        }

        return "";
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String l_VerName = "";

        try {
            PackageManager l_PackMgr = context.getPackageManager();
            PackageInfo l_PackInfo = l_PackMgr.getPackageInfo(context.getPackageName(), 0);
            l_VerName = l_PackInfo.versionName;

            if (l_VerName == null || l_VerName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            l_VerName = "";
            Log.d("VersionInfo", "Exception", e);
        }

        return l_VerName;
    }

    /**
     * 获取网络连接状态
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private static final long GBytes = (1L << 24);
    private static final long MBytes = (1L << 16);
    private static final long KBytes = (1L << 8);

    public static String wellreadSize(long sizeInByte) {
        String unit;
        double fresult = 0.0;
        if (sizeInByte >= GBytes) {
            fresult = sizeInByte * 1.0 / GBytes;
            unit = "GB";
        } else if (sizeInByte >= MBytes) {
            fresult = sizeInByte * 1.0 / MBytes;
            unit = "MB";
        } else if (sizeInByte >= KBytes) {
            fresult = sizeInByte * 1.0 / KBytes;
            unit = "KB";
        } else {
            fresult = sizeInByte;
            unit = "B";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(fresult) + " " + unit;
    }

    public static String visitUrl(String url, byte[] post, boolean isformed) {
        try {
            URL svrUrl = new URL(url);
            Log.v(TAG, "request for:" + url + " [" + (post == null ? "GET" : "POST") + "]");
            HttpURLConnection con = (HttpURLConnection) svrUrl.openConnection();
            con.setRequestProperty("Accept-Encoding", "gzip,deflate");
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(5 * 1000);
            if (post == null) {
                con.setRequestMethod("GET");
                con.connect(); // connect to server
            } else {
                if (isformed) {
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                } else {
                    con.setRequestProperty("Content-Type", "application/binary");
                }
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                OutputStream os = con.getOutputStream();
                os.write(post);
                os.flush();
                os.close();
            }

            // read the server response
            InputStream in = con.getInputStream();
            byte[] readBytes = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            int readSize = 0;
            while ((readSize = in.read(readBytes)) >= 0) {
                out.write(readBytes, 0, readSize);
            }
            con.disconnect();

            // check the data compressed
            String acceptEnc = con.getContentEncoding();
            if (acceptEnc != null && acceptEnc.equals("gzip")) {
                // uncompress
                GZIPInputStream gzipStream = new GZIPInputStream(new ByteArrayInputStream(out.toByteArray()));
                ByteArrayOutputStream out2 = new ByteArrayOutputStream(1024);
                byte[] content = new byte[1024];
                int readSz = 0;
                while ((readSz = gzipStream.read(content)) >= 0) {
                    out2.write(content, 0, readSz);
                }
                out.close();
                out = out2;
            }

            // restore string
            String s = new String(out.toByteArray(), "utf8");
            out.close();
            return s;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "get server data failed:" + e.getMessage());
        }

        return null;
    }

    /**
     * Joke的比较器，按date排序，date大的在上
     */
    public static class CustomComparator implements Comparator<Joke>{
        @Override
        public int compare(Joke joke1, Joke joke2) {
            if(joke1.getDate()  > joke2.getDate()) {
                return -1;
            }
            else if(joke1.getDate() < joke2.getDate()) {
                return 1;
            }else {
                return 0;
            }
        }
    }

}
