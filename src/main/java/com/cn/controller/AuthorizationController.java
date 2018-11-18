package com.cn.controller;

import com.cn.cache.AuthorizerInfo;
import com.cn.cache.AuthorizerInfoMap;
import com.cn.common.LogHelper;
import com.cn.service.ThirdTogetPubFuncService;
import com.cn.service.impl.weChatThridServiceImpl;
import com.cn.service.weChatThridService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by cuixiaowei on 2017/2/23.
 */
@Controller
@RequestMapping("/authorization")
public class AuthorizationController
{

    @Autowired
    private weChatThridService wcsImpl;
    @Autowired
    private ThirdTogetPubFuncService togetPubFuncService;
    @Autowired
    private weChatThridServiceImpl wctsImpl;
    private static  int a =0;
    @RequestMapping(value="/componentVerifyTicket")
    public void getComponentVerifyTicket(HttpServletResponse response,HttpServletRequest request)
    {
        try {
            togetPubFuncService.handleAuthorize(request,response);
            PrintWriter pw = response.getWriter();
            pw.write("success");
            pw.flush();
        } catch (Exception e) {
            LogHelper.error(e,"授权异常！！！！",false);
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/authorizQrCodeUrl")
    public String Authorization_QrCodeUrl(ModelMap model,HttpServletResponse response, HttpServletRequest request)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.entranceWinXin();
            model.put("qrCode",qrCode);
        } catch (Exception e) {
            LogHelper.error(e,"获取授权二维码异常！！！！",false);
            e.printStackTrace();
        }

        return "wechat_auth";
    }

    @ResponseBody
    @RequestMapping(value="/signPackage",produces="application/json;charset=UTF-8")
    public String signPackage(HttpServletResponse response,HttpServletRequest request)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.getSignPackage(request.getParameter("appid"), request.getHeader("referer"));
        } catch (Exception e) {
            LogHelper.error(e,"h5签名注册异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }

    @RequestMapping(value="/commonReturn")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public void commonReturn(HttpServletResponse response,HttpServletRequest request)
    {
        String qrCode="";
        LogHelper.info("进入微信公众号消息与事件接收---"+request.getRequestURL()+",appid----"+request.getParameter("appid"));
        try {
            togetPubFuncService.commonReturn(request,response);
            //response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                response.getWriter().write("success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //return qrCode;
    }

    /**
     * 获取openid
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getOpenid",produces="application/json;charset=UTF-8")
    public String getOpenid(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.getOpenId(source);
            } catch (Exception e) {
            LogHelper.error(e,"获取openid异常！！！！",false);
            e.printStackTrace();
        }

        return qrCode;
    }
    /**
     * 统一下单
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/weChatOrder",produces="application/json;charset=UTF-8")
    public String weChatOrder(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            qrCode= wctsImpl.weixinForPay(source);
        } catch (Exception e) {
            LogHelper.error(e,"统一下单异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 获取商户公众号信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/publicNumInfo",produces="application/json;charset=UTF-8")
    public String getPublicNumInfo(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            String appid=source.get("appid").toString();
            qrCode= togetPubFuncService.getShopPublicNumberInfo(appid);
        } catch (Exception e) {
            LogHelper.error(e,"获取公众号信息异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 获取用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getUserInfo",produces="application/json;charset=UTF-8")
    public String getUserInfo(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.getUserInfo(source);
        } catch (Exception e) {
            LogHelper.error(e,"获取用户信息异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 获取公众号菜单配置
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenu",produces="application/json;charset=UTF-8")
    public String getMenu(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.getMenu(source);
        } catch (Exception e) {
            LogHelper.error(e,"获取公众号菜单信息异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 创建公众号菜单
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/createMenu",produces="application/json;charset=UTF-8")
    public String createMenu(@RequestBody Map<String, Object> source)
    {
        String qrCode="";
        try {
            qrCode= togetPubFuncService.createMenu(source);
        } catch (Exception e) {
            LogHelper.error(e,"创建公众号菜单信息异常！！！！",false);
            e.printStackTrace();
        }
        return qrCode;
    }
    @ResponseBody
    @RequestMapping(value ="/accessToken",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public String addKeFu(HttpServletResponse response,HttpServletRequest request)
    {
        LogHelper.info("下游获取access_token的APPID为："+request.getParameter("appid"));
        String access_token=togetPubFuncService.getAuthAccesstoken(request.getParameter("appid"));
        AuthorizerInfo authorizerInfo= AuthorizerInfoMap.get(request.getParameter("appid"));
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("token",access_token);
        jsonObject.put("expires_in",authorizerInfo.getAuthorizer_access_tokenExprise());
         return jsonObject.toString();
    }
}
