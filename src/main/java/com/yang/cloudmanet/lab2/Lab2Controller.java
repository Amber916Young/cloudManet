package com.yang.cloudmanet.lab2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.cloudmanet.lab2.MatrixMultiplication;
import com.yang.cloudmanet.service.Lab2Service;
import com.yang.cloudmanet.utils.AJAXReturn;
import com.yang.cloudmanet.utils.Page;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/lab2")
public class Lab2Controller {
    @Autowired
    Lab2Service lab2Service;

    public String listToString(List list, char separator) {
        return org.apache.commons.lang.StringUtils.join(list.toArray(), separator);
    }
    @SneakyThrows
    @ResponseBody
    @GetMapping("/matrix/test/rounds/{rounds}")
    public Object matrixTest(@PathVariable("rounds") int rounds) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        for(int i = 2; i < rounds+2; i++) {
            int[][] matrix = MatrixMultiplication.generateMatrix(i);
            int[][] matrix2 = MatrixMultiplication.generateMatrix(i);
            int[][] res = MatrixMultiplication.Multiplication(matrix,matrix2,i);
//            System.out.println("result---->"+res);
        }
       
        HashMap<String, Object> map = new HashMap<>();
        long endTime = System.currentTimeMillis();    //获取结束时间
//        System.out.println("execution time：" + (endTime - startTime) + "ms");    //输出程序运行时间
        long execution_time = (endTime - startTime);
        map.put("execution_time" ,execution_time);
        map.put("rounds" ,rounds);
        map.put("start_time" ,startTime);
        map.put("finish_time" ,endTime);
        lab2Service.insertResult(map);
        return  AJAXReturn.success("done! execution time "+(endTime - startTime) + "ms");
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/queryAll")
    public Object MANETQueryAll(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        page.setRows(limit);
        page.setKeyWord(keyword);
        List<HashMap> lists = lab2Service.pageQueryData(page);
        int totals=lab2Service.pageQueryCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }
    @Value("${Delcode.key}")
    private String Delcode;
    @ResponseBody
    @PostMapping(value = "/deletes")
    public Object MANETDeletes(@RequestBody String jsonData)  throws UnsupportedEncodingException {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        JSONObject json = JSONObject.parseObject(jsonData);
        JSONArray ids = json.getJSONArray("ids");
        String code = json.getString("code");
        if (code.equals(Delcode)) {
            for (int i = 0; i < ids.size(); i++) {
                String id  =  ids.get(i).toString();
                lab2Service.deletesByid(id);
            }
            return AJAXReturn.success("successfully");
        } else return AJAXReturn.error("Code is wrong，delete fail!");
    }


}
