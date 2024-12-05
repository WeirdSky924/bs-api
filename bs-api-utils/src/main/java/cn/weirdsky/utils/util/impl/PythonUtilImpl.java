package cn.weirdsky.utils.util.impl;

import cn.weirdsky.utils.util.PythonUtil;
import cn.weirdsky.utils.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class PythonUtilImpl implements PythonUtil {

    @Value("${python.pythonPath}")
    private String pythonPath;

    @Value("${python.python}")
    private String python;

    @Autowired
    private StringUtil stringUtil;

    @Override
    public String callPythonScript(String pyFileName, String param) {
        String command = "cmd.exe /c " + python + " " + pythonPath + pyFileName + " ";
        if (stringUtil.IsNotEmpty(param)){
            command += ("-f " + param);//这里利用了python的命令行机制可以传入参数
        }
        log.info(command);
        try {
//            Process process = new ProcessBuilder("cmd.exe", "/k start", python, pythonPath + pyFileName).start();
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader errorReader = null;
            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while((s=errorReader.readLine()) != null) {
                log.error(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
