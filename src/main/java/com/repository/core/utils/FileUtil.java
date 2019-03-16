package com.repository.core.utils;

import com.repository.core.config.ProjectConfig;
import com.repository.core.enums.FileEnum;
import com.repository.core.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件相关工具类
 * @author schuyler
 */
@Slf4j
public class FileUtil {

    public static String fileUpLoad(MultipartFile multipartFile, ProjectConfig projectConfig) {

        File fileDir = new File(projectConfig.getPicturePath());

        try {
            if (!fileDir.exists()) {
                FileUtils.forceMkdir(fileDir);
            }
        } catch (IOException e) {
            throw new FileException(FileEnum.FILE_DIR_MAKE_ERROR);
        }

        //组合文件名字
        String fileName = getUUIDFileName(multipartFile.getOriginalFilename());
        File file = new File(fileDir, fileName);

        //进行文件存放
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), file);
        } catch (IOException e) {
            log.error("【上传文件】{}", FileEnum.FILE_COPY_ERROR.getMessage());
            throw new FileException(FileEnum.FILE_COPY_ERROR);
        }

        return projectConfig.getPictureUrl().concat(fileName);
    }


    /**
     * 删除图片
     * @param fileName
     */
    public static void fileDelete(String fileName, ProjectConfig projectConfig) {
        File filePath = new File(projectConfig.getPicturePath());

        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

        File file = new File(filePath, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取存放文件名
     * @return
     */
    public static String getUUIDFileName(String fileName) {
        String currentDateStr = DateUtil.getCurrentDateStr();
        return UUID.randomUUID().toString().concat("-").concat(currentDateStr).concat("-").concat(fileName);
    }
}
