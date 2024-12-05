package cn.weirdsky.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public interface ZipUtil {

    /**
     * 解压zip压缩文件到指定目录
     *
     * @param zipPath zip压缩文件绝对路径
     * @param descDir 指定的解压目录
     */
    boolean unzipFile(String zipPath, String descDir) throws IOException;

}
