package team.tomato.springbootdemo.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import team.tomato.springbootdemo.common.exception.BusinessException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Iverson on 2019/7/4.
 * 通用工具类
 */
public class CommonUtils {

    /**
     * UUID工具类
     */
    public static class UUIDUtil {
        public static final String randomUUID() {
            String uuid = UUID.randomUUID().toString();
            uuid = StringUtils.remove(uuid, '-');
            return uuid;
        }
    }

    /**
     * 订单Id生成方法
     */
    public static String generalOrderNo(){
        final String frontString = "HBXC";
        Date date = new Date();
        Random random = new Random();
        int ends = random.nextInt(99);
        String.format("%02d",ends);//如果不足两位，前面补0
        String orderNo = frontString + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + ends;
        return orderNo;
    }

    /**
     * 日期时间工具类
     */
    public static class DateUtil {

        /**
         * 日期格式
         */
        public enum DATE_PATTERN {

            /**
             * yyyyMMdd
             */
            yyyyMMdd("yyyyMMdd", "^\\d{2,4}\\d{1,2}\\d{1,2}$"),

            /**
             * yyyy/MM
             */
            yyyy_MM("yyyy/MM", "^\\d{2,4}/\\d{1,2}$"),

            /**
             * yyyy-MM
             */
            yyyy_MM2("yyyy-MM", "^\\d{2,4}-\\d{1,2}$"),

            /**
             * yyyy/MM/dd
             */
            yyyy_MM_dd("yyyy/MM/dd", "^\\d{2,4}/\\d{1,2}/\\d{1,2}$"),

            /**
             * yyyy-MM-dd
             */
            yyyy_MM_dd2("yyyy-MM-dd", "^\\d{2,4}-\\d{1,2}-\\d{1,2}$"),

            /**
             * yyyy/MM/dd HH:mm
             */
            yyyy_MM_dd_HH_mm("yyyy/MM/dd HH:mm", "^\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}$"),

            /**
             * yyyy-MM-dd HH:mm
             */
            yyyy_MM_dd_HH_mm2("yyyy-MM-dd HH:mm", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}$"),

            /**
             * yyyy/MM/dd HH:mm:ss
             */
            yyyy_MM_dd_HH_mm_ss("yyyy/MM/dd HH:mm:ss", "^\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}$"),

            /**
             * yyyy-MM-dd HH:mm:ss
             */
            yyyy_MM_dd_HH_mm_ss2("yyyy-MM-dd HH:mm:ss", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}$"),

            /**
             * yyyy/MM/dd HH:mm:ss.S
             */
            yyyy_MM_dd_HH_mm_ss_S("yyyy/MM/dd HH:mm:ss.S", "^\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1}$"),

            /**
             * yyyy-MM-dd HH:mm:ss.S
             */
            yyyy_MM_dd_HH_mm_ss_S2("yyyy-MM-dd HH:mm:ss.S", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1}$"),

            /**
             * yyyy/MM/dd HH:mm:ss.SS
             */
            yyyy_MM_dd_HH_mm_ss_SS("yyyy/MM/dd HH:mm:ss.SS", "^\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{2}$"),

            /**
             * yyyy-MM-dd HH:mm:ss.SS
             */
            yyyy_MM_dd_HH_mm_ss_SS2("yyyy-MM-dd HH:mm:ss.SS", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{2}$"),


            /**
             * yyyy/MM/dd HH:mm:ss.SSS
             */
            yyyy_MM_dd_HH_mm_ss_SSS("yyyy/MM/dd HH:mm:ss.SSS", "^\\d{2,4}/\\d{1,2}/\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{3,}$"),

            /**
             * yyyy-MM-dd HH:mm:ss.SSS
             */
            yyyy_MM_dd_HH_mm_ss_SSS2("yyyy-MM-dd HH:mm:ss.SSS", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{3,}$");


            private String value;

            private String pattern;

            private DATE_PATTERN(String value, String pattern) {
                this.value = value;
                this.pattern = pattern;
            }

            /**
             * 根据样本获取模式
             *
             * @param date
             * @return
             */
            public static DATE_PATTERN getPatternBySample(String date) {

                if (date != null) {

                    date = date.trim();

                    for (DATE_PATTERN value : DATE_PATTERN.values()) {

                        if (date.matches(value.pattern)) {
                            return value;
                        }
                    }
                }

                throw new BusinessException("日期为空或是不支持的样本格式");
            }

            @Override
            public String toString() {
                return value;
            }
        }

        /**
         * 日期比较
         *
         * @param date1
         * @param date2
         * @return 如果 date1 > date2，则返回 1;如果 date1 = date2,则返回 0;如果 date1 < date2，则返回 -1
         */
        public static int compareDate(Date date1, Date date2) {

            if (date1.getTime() > date2.getTime()) {

                return 1;
            } else if (date1.getTime() == date2.getTime()) {

                return 0;
            } else {

                return -1;
            }
        }

        /**
         * 日期比较
         *
         * @param date1
         * @param date2
         * @return 如果 date1 > date2，则返回 1;如果 date1 = date2,则返回 0;如果 date1 < date2，则返回 -1
         */
        public static int compareDate(String date1, DATE_PATTERN date1Pattern, String date2, DATE_PATTERN date2Pattern) {

            Date _date1 = parseDate(date1, date1Pattern);
            Date _date2 = parseDate(date2, date2Pattern);

            return compareDate(_date1, _date2);
        }

        /**
         * 根据指定的模式，将 java.util.Date 转换为 java.sql.Date
         *
         * @param date
         * @param datePattern
         * @return
         */
        public static java.sql.Date toSqlDate(String date, DATE_PATTERN datePattern) {

            return new java.sql.Date(parseDate(date, datePattern).getTime());
        }

        /**
         * 根据指定的模式，将 java.util.Date 转换为 java.sql.Timestamp
         *
         * @param date
         * @param datePattern
         * @return
         */
        public static Timestamp toTimestamp(String date, DATE_PATTERN datePattern) {

            return new Timestamp(parseDate(date, datePattern).getTime());
        }

        /**
         * 解析任意受支持格式的时间
         *
         * @param date
         * @return
         */
        @SuppressWarnings("deprecation")
        public static Date parseDate(String date) {

            if (date == null) {
                return null;
            }

            date = date.trim();
            if (date.indexOf("中国标准时间") != -1 || date.indexOf("CST") != -1) {
                return new Date(Date.parse(date));
            }

            return parseDate(date, getPatternBySample(date));
        }

        /**
         * 根据指定模式解析时间字符串为 date 对象并返回
         *
         * @param date    date字符串
         * @param pattern 模式
         * @return
         */
        public static Date parseDate(String date, DATE_PATTERN pattern) {

            try {

                if (pattern == DATE_PATTERN.yyyy_MM_dd_HH_mm_ss_SSS || pattern == DATE_PATTERN.yyyy_MM_dd_HH_mm_ss_SSS2) {
                    if (date != null) {
                        int index = date.lastIndexOf(".");
                        if (index != -1) {
                            date = date.substring(0, index + 4);
                        }
                    }
                }

                DateFormat df = new SimpleDateFormat(pattern.toString());
                return df.parse(date);

            } catch (Exception e) {
                throw new BusinessException("");
            }
        }

        /**
         * 格式化时间
         * <br/>
         * 按照指定模式格式化时间字符串
         *
         * @param srcDateStr        源日期
         * @param srcDatePattern    源日期模式
         * @param targetDatePattern 目标日期模式
         * @return
         */
        public static String formatDate(String srcDateStr, DATE_PATTERN srcDatePattern, DATE_PATTERN targetDatePattern) {

            try {

                Date srcDate = parseDate(srcDateStr, srcDatePattern);

                DateFormat df2 = new SimpleDateFormat(targetDatePattern.toString());
                String targetDateStr = df2.format(srcDate);

                return targetDateStr;

            } catch (Exception e) {
                throw new BusinessException("");
            }
        }

        /**
         * 格式化时间
         *
         * @param date
         * @param datePattern 模式
         * @return
         */
        public static String formatDate(Date date, DATE_PATTERN datePattern) {

            DateFormat df = new SimpleDateFormat(datePattern.toString());
            return df.format(date);
        }

        /**
         * 根据日期样品获得其模式
         *
         * @param sample
         * @return
         */
        public static DATE_PATTERN getPatternBySample(String sample) {

            return DATE_PATTERN.getPatternBySample(sample);
        }
    }

    public static class SecurityUtil {
        private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

        public SecurityUtil() {
        }

        public static String encodeForBase64(String src) {
            try {
                return Base64.encodeBase64String(src.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException var2) {
                throw new BusinessException("");
            }
        }

        public static String decodeForBase64(String src) {
            try {
                return new String(Base64.decodeBase64(src), "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                throw new BusinessException("");
            }
        }

        public static String MD5_16(String source) {
            return MD5(source, CommonUtils.SecurityUtil.MD5_LENGTH.LENGTH_16, (Charset) null);
        }

        public static String MD5_32(String source) {
            return MD5(source, CommonUtils.SecurityUtil.MD5_LENGTH.LENGTH_32, (Charset) null);
        }

        public static String MD5(String source, CommonUtils.SecurityUtil.MD5_LENGTH length) {
            return MD5(source, length, (Charset) null);
        }

        public static String MD5(String source, CommonUtils.SecurityUtil.MD5_LENGTH length, Charset charset) {
            String result = "";

            try {
                MessageDigest e = MessageDigest.getInstance("MD5");
                e.update(charset == null ? source.getBytes() : source.getBytes(charset));
                byte[] b = e.digest();
                StringBuffer buf = new StringBuffer("");

                for (int offset = 0; offset < b.length; ++offset) {
                    int i = b[offset];
                    if (i < 0) {
                        i += 256;
                    }

                    if (i < 16) {
                        buf.append("0");
                    }

                    buf.append(Integer.toHexString(i));
                }

                result = buf.toString();
                return length == CommonUtils.SecurityUtil.MD5_LENGTH.LENGTH_32 ? result : buf.toString().substring(8, 24);
            } catch (NoSuchAlgorithmException var9) {
                throw new BusinessException("");
            }
        }

        public static String generateKey() {
            try {
                KeyGenerator e = KeyGenerator.getInstance("AES");
                e.init(128);
                SecretKey secretKey = e.generateKey();
                return Base64.encodeBase64String(secretKey.getEncoded());
            } catch (Exception var2) {
                throw new BusinessException("");
            }
        }

        public static String encrypt(String content, String key) {
            try {
                SecretKeySpec e = new SecretKeySpec(Base64.decodeBase64(key), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, e);
                return Base64.encodeBase64String(cipher.doFinal(content.getBytes()));
            } catch (Exception var4) {
                throw new BusinessException("");
            }
        }

        public static String decrypt(String content, String key) {
            try {
                SecretKeySpec e = new SecretKeySpec(Base64.decodeBase64(key), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, e);
                return new String(cipher.doFinal(Base64.decodeBase64(content)));
            } catch (Exception var4) {
                throw new BusinessException("");
            }
        }

        public static enum MD5_LENGTH {
            LENGTH_16(16),
            LENGTH_32(21);

            int length;

            private MD5_LENGTH(int length) {
                this.length = length;
            }
        }

    }
}
