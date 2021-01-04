package com.lylbp.manager.minio.demo.controller;

import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.entity.ResResult;
import com.lylbp.manager.minio.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * <p>
 * 文件存储相关
 * </p>
 *
 * @author weiwenbin
 * @date 2021/1/4 上午10:09
 */
@RestController
@RequestMapping("/demo/minio")
@Api(tags = "文件存储相关")
public class MinioController {
    @Resource
    private MinioService minioService;

    @Resource
    private HttpServletResponse response;

    @PostMapping(value = "/uploadFile")
    @ApiOperation("上传")
    public ResResult<String> uploadFile(@NotNull @ApiParam(name = "file", value = "file", required = true)
                                                MultipartFile file)
            throws IOException {
        String contentType = file.getContentType();
        return ResResultUtil.success(minioService.uploadFile(file, contentType, "test"));
    }

    /**
     * 以流访问资源
     *
     * @param filePath 文件存储目录
     */
    @GetMapping(value = "/getResourceByIo")
    @ApiOperation(value = "以流访问资源")
    public void getResourceByIo(@RequestParam String filePath) {
        minioService.outImgByStream("test", filePath, response);
    }

    /**
     * 以url访问资源
     *
     * @param filePath 文件存储目录
     */
    @GetMapping(value = "/getResourceByUrl")
    @ApiOperation(value = "以url访问资源")
    public ResResult<String> getResourceByUrl(@RequestParam String filePath) {
        return ResResultUtil.success(minioService.presignedGetObject("test", filePath, 60 * 60 * 1));
    }
}
