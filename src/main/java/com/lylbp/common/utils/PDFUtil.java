package com.lylbp.common.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.IOException;

/**
 * @Author weiwenbin
 * @Date 2020/5/26 下午3:20
 */
public class PDFUtil {
    /**
     * 获取pdf大小,先远程下载->读取大小->删除文件
     *
     * @param pdfPath
     * @param pdfSavePath
     * @return
     */
    public static Integer getPDFSize(String pdfPath, String pdfSavePath){
        FileUtil.downloadFile(pdfPath,pdfSavePath);
        PDDocument pdDocument = null;
        int size = 0;
        try {
            pdDocument = PDDocument.load(new File(pdfSavePath));
            size = pdDocument.getNumberOfPages();
            pdDocument.close();
            cn.hutool.core.io.FileUtil.del(pdfSavePath);
        } catch (IOException e) {
            return size;
        }

        return size;
    }
}
