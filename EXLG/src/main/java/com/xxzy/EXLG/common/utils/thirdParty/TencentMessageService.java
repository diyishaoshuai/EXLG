package com.xxzy.EXLG.common.utils.thirdParty;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author gjq0117
 * @email  gjq0117@163.com
 * @date 2022/5/17 下午 09:09
 * @describe
 */
@Component
public class TencentMessageService {

    @Value("${tencentcloud.SecretId}")
    private String SecretId;

    @Value("${tencentcloud.SecretKey}")
    private String SecretKey;

    public void sendMessage(String phoneNumber,String signCode){
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        Credential cred = new Credential(SecretId, SecretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        // 设置手机号
        String[] phoneNumbers = {phoneNumber};
        req.setPhoneNumberSet(phoneNumbers);
        //  设置我的腾讯云短信应用的SDKAppID
        req.setSmsSdkAppId("1400679800");
        //  设置短信签名内容  本站用的是我备案时的网站名
        req.setSignName("GJQ实践个人网");
        //  设置短信模板ID
        req.setTemplateId("1405756");

        //  设置签收码
        String[] signCodes = {signCode};
        req.setTemplateParamSet(signCodes);

        // 发送短信
        try {
            client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
