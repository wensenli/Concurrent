package com.concurrent.huige;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Test {
    public static void main(String[] args) {
        // start=0&limit=10&queryDischargePlace-tpl=allImformation
        String url = "http://shipmft.singlewindow.gd.cn/portal/BaseCodeCheckAction.do?action=getImformation";
        int start = 0;
        int limit = 8;

        List<Object> resultList = new ArrayList<>();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("queryDischargePlace-tpl", "allImformation");
        paramMap.put("limit", limit + "");

        for (int i = 0; i < 95; i++) {
            try {
                paramMap.put("start", start + "");
                String post = HttpClientUtil.post(url, paramMap);
                JSONObject jsonObject = new JSONObject(post);
                JSONArray jsonArray = new JSONArray(jsonObject.get("List").toString());

                for (int j = 0; j < jsonArray.length(); j++) {
                    Object o = jsonArray.get(j);
                    log.debug(" json is : {}", o.toString());
                    resultList.add(o);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            start = start + 8;
        }
        log.debug("resultList size is : {}", resultList.size());
        log.debug("----------------------------------------------------------------------------");
        String content = "";
        String dischargePlace = "";
        String placeDesc = "";
        for (int n = 0; n < resultList.size(); n++) {
            log.debug("第{}条记录 ,内容为： {}", n, resultList.get(n).toString());
            JSONObject jsonObject = (JSONObject)resultList.get(n);
            dischargePlace = (String) jsonObject.get("dischargePlace");
            placeDesc = (String) jsonObject.get("placeDesc");
            content = dischargePlace + "    " + placeDesc;
            inputFile(content);
        }
    }

    public static void inputFile(String content) {
        try {

            OutputStream out = new FileOutputStream(
                    "C:\\Users\\asus\\Desktop\\2.txt", true);
            byte[] buff = (content + "\r\n").getBytes();
            out.write(buff);
            out.flush();


            log.debug("input success, content is : {}", content);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
