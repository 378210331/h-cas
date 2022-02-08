package com.hsy.cas.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.entity.CasService;
import com.hsy.cas.model.Result;
import com.hsy.cas.service.CasCommonService;
import com.hsy.cas.service.ICasServiceService;
import com.hsy.cas.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ServiceValidateController {

    @Autowired
    ICasServiceService casServiceService;

    /**
     * <cas:user> 这个便签是必备的,填充用户名
     */
    String successXml = "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp'>\n" +
            "    <cas:authenticationSuccess>\n" +
            "        <cas:user>{{username}}</cas:user>\n" +
            "        <cas:attributes>\n" +
            "{{attributes}}" +
            "        </cas:attributes>\n" +
            "    </cas:authenticationSuccess>\n" +
            "</cas:serviceResponse>";

    String errorXml = "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp'>\n" +
            "<cas:authenticationFailure code=\"INVALID_TICKET\">未能够识别出目标 &#39;{{ticket}}#39;票根<:authenticationFailure>\n" +
            "<:serviceResponse>";

    String errorService = "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp'>\n" +
            "<cas:authenticationFailure code=\"INVALID_TICKET\">未识别客户端 &#39;{{service}}#39;票根<:authenticationFailure>\n" +
            "<:serviceResponse>";

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CasCommonService casCommonService;

    @GetMapping("/ticket")
    @ResponseBody
    public Result<String> ticket(@RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) String authorization){
        if(StringUtils.isBlank(authorization)){
            return Result.buildError("单点未登录");
        }
        if(redisUtil.hasKey(RedisPrefixConstant.TOKEN_PREFIX + authorization)){
            return Result.buildSuccess("成功",casCommonService.createTicket(authorization));
        }else{
            return Result.buildError("单点未登录");
        }
    }

    /**
     * 通过ticket兑换登录信息
     * 仿写 官方 cas-client的兑换逻辑，路径不能修改
     * @param service
     * @param ticket
     * @return
     */
    @GetMapping("/serviceValidate")
    @ResponseBody
    public String serviceValidate(@RequestParam("service")String service, @RequestParam("ticket")String ticket,@RequestParam(value = "format",required = false) String format){
        String res;
        CasService casService = casServiceService.isMatch(service);
        if(casService == null){
            return errorService.replace("{{service}}",service);
        }
        if(redisUtil.hasKey(RedisPrefixConstant.TICKET_PREFIX + ticket)){
            Map<String,String> userInfo = (Map<String, String>) redisUtil.get(RedisPrefixConstant.TICKET_PREFIX + ticket);
            String TGT = (String) redisUtil.get(RedisPrefixConstant.TGT_ST + ticket);
            redisUtil.del(RedisPrefixConstant.TICKET_PREFIX + ticket);//使ticket失效
            redisUtil.del(RedisPrefixConstant.TGT_ST + ticket); // 使ticket 和 TGT的关联关系失效
            redisUtil.hmSet(RedisPrefixConstant.TGT_ST_SERVICE + TGT,casService.getId(),ticket); //存储该TGT认证产生的客户端和ticket,给登出使用
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> entry : userInfo.entrySet()){
                if(StringUtils.isNotBlank(entry.getValue())){
                    sb.append("<cas:").append(entry.getKey()).append(">").append(entry.getValue())
                            .append("</cas:").append(entry.getKey()).append("> \n");
                }
            }
            res = successXml.replace("{{attributes}}",sb.toString());
            res = res.replace("{{username}}",userInfo.get("username"));
        }else{
            res = errorXml.replace("{{ticket}}",ticket);
        }
        if("json".equalsIgnoreCase(format)){
            try{
                Document doc= DocumentHelper.parseText(res);
                JSONObject json = new JSONObject();
                dom4j2Json(doc.getRootElement(), json);
                res = json.toJSONString();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 通过ticket兑换登录信息
     * 仿写 官方 cas-client的兑换逻辑，路径不能修改
     * @param service
     * @param ticket
     * @return
     */
    @GetMapping("/p3/serviceValidate")
    @ResponseBody
    public String p3ServiceValidate(@RequestParam("service")String service,@RequestParam("ticket")String ticket,@RequestParam(value = "format",required = false) String format){
        return serviceValidate(service,ticket,format);
    }


    public static void dom4j2Json(Element element, JSONObject json){
        //如果是属性
        for(Object o : element.attributes()){
            Attribute attr = (Attribute)o;
            if(! isEmpty(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        if(chdEl.isEmpty() && ! isEmpty(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }
        for(Element e:chdEl){//有子元素
            if(!e.elements().isEmpty()){//子元素也有子元素
                JSONObject chdjson=new JSONObject();
                dom4j2Json(e,chdjson);
                Object o=json.get(e.getName());
                if(o!=null){
                    JSONArray jsona=null;
                    if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject)o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if(o instanceof JSONArray){
                        jsona = (JSONArray)o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                }else{
                    if(!chdjson.isEmpty()){
                        json.put(e.getName(), chdjson);
                    }
                }


            }else{//子元素没有子元素
                for(Object o:element.attributes()){
                    Attribute attr=(Attribute)o;
                    if(!isEmpty(attr.getValue())){
                        json.put("@"+attr.getName(), attr.getValue());
                    }
                }
                if(!e.getText().isEmpty()){
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
}
