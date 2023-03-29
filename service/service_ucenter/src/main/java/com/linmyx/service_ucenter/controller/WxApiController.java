package com.linmyx.service_ucenter.controller;

import com.google.gson.Gson;
import com.linmyx.commonUtils.JwtUtils;

import com.linmyx.service_ucenter.entity.UcenterMember;
import com.linmyx.service_ucenter.service.UcenterMemberService;
import com.linmyx.service_ucenter.utils.ConstantWxUtils;
import com.linmyx.service_ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //获取扫码信息
    @GetMapping("/callback")
    public String callback(String code, String state){
        //1.先获取code的值
        //2.拿着code请求微信的固定地址，得到两个值：access_token和openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);
        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo获取到access_token和openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            //将扫码人信息添加到数据库当中
            UcenterMember ucenterMember = memberService.getOpenid(openid);

            if (ucenterMember == null) {
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl= String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                ucenterMember=new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                memberService.save(ucenterMember);
            }
            //使用jwt生成token
            String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            throw new RuntimeException("微信登录失败!!");
        }
    }
    //生成微信扫描二维码
    @GetMapping("/login")
    public String getWxCode(){
        //请求微信地址
//        String WxUrl="https://open.weixin.qq.com/connect/qrconnect?appid="+
//                ConstantWxUtils.WX_OPEN_APP_ID;
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl=ConstantWxUtils.WX_OPEN_REDIRECT_URL;

        try {
            redirectUrl= URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "guigu"
        );
        return "redirect:"+url;
    }

}
