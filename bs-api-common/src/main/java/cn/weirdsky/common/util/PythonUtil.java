package cn.weirdsky.common.util;

public interface PythonUtil {

    /**
     * 发送请求至人脸识别模块
     * @param param
     * @return
     */
    String callPythonScript(String pyFileName, String param);

}
