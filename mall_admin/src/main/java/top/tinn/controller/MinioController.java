package top.tinn.controller;

import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.tinn.common.api.CommonResult;
import top.tinn.dto.MinioUploadDto;

import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;


@Api(tags = "MinioController", description = "MinIO对象存储管理")
@RequestMapping("/minio")
@RestController
public class MinioController {
    private static final Logger LOGGER= LoggerFactory.getLogger(MinioController.class);
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        try {
            MinioClient minioClient=new MinioClient(ENDPOINT,ACCESS_KEY,SECRET_KEY);
            boolean isExist=minioClient.bucketExists(BUCKET_NAME);
            if (isExist) LOGGER.info("minio bucket exists.");
            else{
                minioClient.makeBucket(BUCKET_NAME);
                minioClient.setBucketPolicy(BUCKET_NAME,"*.*",PolicyType.READ_ONLY);
            }
            String fileName=file.getOriginalFilename();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            String objectName=sdf.format(new Date())+"/"+fileName;
            minioClient.putObject(BUCKET_NAME,objectName,file.getInputStream(),file.getContentType());
            LOGGER.info("file upload success.");
            MinioUploadDto minioUploadDto=new MinioUploadDto();
            minioUploadDto.setName(fileName);
            minioUploadDto.setUrl(ENDPOINT+"/"+BUCKET_NAME+"/"+objectName);
            return CommonResult.success(minioUploadDto);
        }catch (Exception e){
            LOGGER.info("upload failed:{}",e.getMessage());
        }
        return CommonResult.failed();
    }

    @ApiOperation("文件删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject(BUCKET_NAME, objectName);
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }

}
