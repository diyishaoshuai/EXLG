package com.xxzy.EXLG.common.utils.thirdParty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.EmployeeDao;
import com.xxzy.EXLG.entity.EmployeeEntity;
import com.xxzy.EXLG.interceptor.LoginUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class AliyunOssService {

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessKeySecret;

    @PostMapping("/oss/policy")
    public R policy(MultipartFile file){
        EmployeeEntity employeeInfo = LoginUserInterceptor.employeeInfo.get();
        try{
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKeySecret);
            // 上传文件流
            InputStream inputStream= file.getInputStream();
            String fileName=file.getOriginalFilename();

            //按照当前日期，创建文件夹，上传到创建文件夹里面
            //  2022/03/15/xx.jpg
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            fileName = format+"/"+fileName;
            // 调用方法实现上传
            ossClient.putObject(bucket,fileName,inputStream);

            // 关闭ossclient
            ossClient.shutdown();
            // 上传之后文件路径
            String url="https://"+bucket+"."+endpoint+"/"+fileName;
            // 将url保存到数据库中
            employeeInfo.setAvatar(url);
            employeeDao.updateById(employeeInfo);
            return R.ok().put("userInfo",employeeInfo);
        }catch (IOException e){
            e.printStackTrace();
            ossClient.shutdown();
            return null;
        }
    }
}
