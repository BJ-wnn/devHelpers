package com.github.nan.dataprocess;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@Data
public class ProgramOptions {


    private String configFolderPath;


    private int maxNum;

    public void init() {
        // 如果 configFolderPath 没有配置，设置默认值为 JAR 包同级目录下的 config 文件夹
        if (ObjectUtils.isEmpty(configFolderPath.trim())) {
            configFolderPath = getDefaultConfigFolderPath();
        }
    }

    private String getDefaultConfigFolderPath() {

        // 获取 JAR 包所在目录
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String jarDir = Paths.get(jarPath).getParent().toString();

        // 构建默认的 config 文件夹路径
        Path defaultConfigFolderPath = Paths.get(jarDir, "config");
        return defaultConfigFolderPath.toString();
    }


}
