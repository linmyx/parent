package com.linmyx.service_msm.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teautil.models.RuntimeOptions;
import javassist.tools.reflect.Sample;

import java.util.List;

public class MsmSend {
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    public static Boolean sendClient(String phone,String code){
        try {
            Client client = createClient("LTAI5tLiHFZzCFePnnCkHBHH", "fzmDw9HI6MnEJIJTojGjTAa9cMaVK6");
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(phone)
                    .setTemplateParam("{code:"+code+"}");
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp)));
            if (resp == null){
                return  false;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
