package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.merchantOrderRepository;
import com.gzpclass.supdem.domain.merchantOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class MerchantOrderController {

    @Autowired
    private merchantOrderRepository repository;

    //加载所有数据
    @CrossOrigin(origins="*")
    @GetMapping("/all")
    public List<merchantOrder> all(){
        return repository.findAll();
    }

    //查询用户的订单
    @CrossOrigin(origins = "*")
    @GetMapping("/buyerId/{BuyerId}")
    public List<merchantOrder> findType (@PathVariable("BuyerId") Integer id){
        return repository.findByBuyerId(id);
    }

    //************************************************改加了日期和价格
    //添加买卖商品的单子
    @CrossOrigin(origins="*")
    @PostMapping(value = "/add")
    public merchantOrder create(@RequestParam("sellerId") Integer sellerId,
                                @RequestParam("product") String product,
                                @RequestParam("remarkers") String remarkers,
                                @RequestParam("address") String address,//收货地址
                                @RequestParam("buyerId") Integer buyerId,
                                @RequestParam("orderDate") Date orderDate,
                                @RequestParam("orderPrice")double orderPrice
    ){
        merchantOrder merchantOrder=new merchantOrder();

        merchantOrder.setSellerId(sellerId);
        merchantOrder.setBuyerId(buyerId);

        if(product.length()>0){merchantOrder.setProduct(product);}
        if(remarkers.length()>0){merchantOrder.setRemarkers(remarkers);}

        merchantOrder.setAddress(address);
        double[] coordinate = getCoordinate(address);
        if(coordinate!=null){
            merchantOrder.setLng(coordinate[0]);
            merchantOrder.setLat(coordinate[1]);
        }else {
            address="武汉大学";
            coordinate = getCoordinate(address);
            merchantOrder.setLng(coordinate[0]);
            merchantOrder.setLat(coordinate[1]);
        }

        merchantOrder.setOrderDate(orderDate);
        merchantOrder.setPrice(orderPrice);

        return repository.save(merchantOrder);
    }


    // 调用百度地图API根据地址，获取坐标
    public static double[] getCoordinate(String address) {
        String AK = "b5QreG4xEDxNrqIdPRFLQ6EzdwmjL31p"; // 百度地图密钥

        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = "http://api.map.baidu.com/place/v2/suggestion?query="+ address + "&region=全国&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject Allobj = JSONObject.fromObject(json);
                if ("0".equals(Allobj.getString("status"))) {

                    JSONArray Tresult = Allobj.getJSONArray("result");
                    JSONObject result = Tresult.getJSONObject(0);


                    JSONObject location = JSONObject.fromObject(result.getString("location"));
                    double lng = location.getDouble("lng"); // 经度
                    double lat = location.getDouble("lat"); // 纬度


                    //DecimalFormat df = new DecimalFormat("#.######");
                    double coor[]=new double[2];
                    coor[0]=lng;
                    coor[1]=lat;
                    return coor;
                    //[df.format(lng),df.format(lat)];
                }
            }
        }
        return null;
    }
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

}
