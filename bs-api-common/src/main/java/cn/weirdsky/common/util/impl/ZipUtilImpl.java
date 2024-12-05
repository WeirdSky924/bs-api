package cn.weirdsky.common.util.impl;

import cn.weirdsky.common.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class ZipUtilImpl implements ZipUtil {

    public boolean unzipFile(String zipPath, String descDir) throws IOException {
        try {
            File zipFile = new File(zipPath);
            if (!zipFile.exists()) {
                throw new IOException("要解压的压缩文件不存在");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            InputStream input = new FileInputStream(zipPath);
            unzipWithStream(input, descDir);
        } catch (Exception e) {
            throw new IOException(e);
        }
        return true;
    }

    /**
     * 解压
     *
     * @param inputStream
     * @param descDir
     */
    public boolean unzipWithStream(InputStream inputStream, String descDir) {
        if (!descDir.endsWith(File.separator)) {
            descDir = descDir + File.separator;
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream, Charset.forName("GBK"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String zipEntryNameStr = zipEntry.getName();
                String zipEntryName = zipEntryNameStr;
                if (zipEntryNameStr.contains("/")) {
                    String str1 = zipEntryNameStr.substring(0, zipEntryNameStr.indexOf("/"));
                    zipEntryName = zipEntryNameStr.substring(str1.length() + 1);
                }
                String outPath = (descDir + zipEntryName).replace("\\\\", "/");
                File outFile = new File(descDir);
                if (!outFile.exists()) {
                    outFile.mkdirs();
                }
                writeFile(outPath, zipInputStream);
                zipInputStream.closeEntry();
            }
            log.info("======解压成功=======");
        } catch (IOException e) {
            log.error("压缩包处理异常，异常信息{}" + e);
            return false;
        }
        return true;
    }

    //将流写到文件中
    public boolean writeFile(String filePath, ZipInputStream zipInputStream) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] bytes = new byte[4096];
            int len;
            while ((len = zipInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException ex) {
            log.error("解压文件时，写出到文件出错");
            return false;
        }
        return true;
    }

}
