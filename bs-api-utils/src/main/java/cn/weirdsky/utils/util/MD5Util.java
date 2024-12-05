package cn.weirdsky.utils.util;

public interface MD5Util {

    /**
     * 加密指定内容
     * @param dataStr
     * @return
     */
    String encrypt(String dataStr);

    /**
     * 生成MD5
     * @param str
     * @return
     */
    String getMD5(String str);

}
