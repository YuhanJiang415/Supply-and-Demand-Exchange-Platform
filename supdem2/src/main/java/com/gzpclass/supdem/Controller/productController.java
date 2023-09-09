package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.productRepository;
import com.gzpclass.supdem.domain.product;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/product")
public class productController {

    @Autowired
    private productRepository repository;

    //加载所有数据
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<product> all(){
        //List<product> Product=repository.findByAvailable("1");
        //return Product;
        return repository.findAll();
    }

    //查询新加入的数据
    @CrossOrigin(origins = "*")
    @GetMapping("/latest")
    public product latest(){
        return repository.findFirstByOrderByProductIdDesc();
    }

    //查询指定类型的商品
    @CrossOrigin(origins = "*")
    @GetMapping("/type/{productType}")
    public List<product> findType (@PathVariable("productType") String type){
        return repository.findByType(type);
    }


    //Id查询
    @CrossOrigin(origins = "*")
    @GetMapping("/ID/{productId}")
    public Optional<product> findID(@PathVariable("productId") Integer id){
        return repository.findById(id);
    }

    //添加商品
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public product create(@RequestParam("name") String name,
                          @RequestParam("price") String price,
                          @RequestParam("discription") String discription,
                          @RequestParam("address") String address,
                          @RequestParam("type") String type,
                          @RequestParam("sellerId") Integer sellerId
                             ){
        product product=new product();

        if(name.length() > 0) {
            product.setName(name);
        }
        if(price.length() > 0){
            product.setPrice(Float.parseFloat(price));
        }
        if(discription.length() > 0){
            product.setDiscription(discription);
        }

        product.setSellerId(sellerId);

        if(type.length() > 0){
            product.setType(type);
        }else {
            product.setType("other");
        }

        product.setAvailable("1");

        product.setAddress(address);
        double[] coordinate = getCoordinate(address);
        if(coordinate!=null){
            product.setLng(coordinate[0]);
            product.setLat(coordinate[1]);
        }else {
            address="武汉大学";
            product.setAddress(address);
            coordinate = getCoordinate(address);
            product.setLng(coordinate[0]);
            product.setLat(coordinate[1]);
        }

        return repository.save(product);
    }

    //更新商品
    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update/{productId}")
    public product productUpdate(@PathVariable("productId") Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam("price") String price,
                                 @RequestParam("discription") String discription,
                                 @RequestParam("address") String address,
                                 @RequestParam("type") String type,
                                 @RequestParam("buyerId")Integer buyerId,
                                 @RequestParam("available") String available){

        Optional<product> optional =repository.findById(id);
        if(optional.isPresent()){
            product product=optional.get();
            if(name.length()>0) {
                product.setName(name);
            }
            if(price.length() > 0){
                product.setPrice(Float.parseFloat(price));
            }
            if(discription.length() > 0){
                product.setDiscription(discription);
            }
            if(type.length() > 0){
                product.setType(type);
            }
            if(available.length() > 0){
                product.setAvailable(available);
            }

            product.setBuyerId(buyerId);

            if(address.length() > 0){
                product.setAddress(address);
                double[] coordinate = getCoordinate(address);//百度API查找地址
                if(coordinate!=null){
                    product.setLng(coordinate[0]);
                    product.setLat(coordinate[1]);
                }
            }
            return repository.save(product);
        }
        return null;
    }

    //删除商品
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/{productId}")
    public void productDelete(@PathVariable("productId") Integer id){
        repository.deleteById(id);
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


    //缓冲区开始
    @CrossOrigin(origins = "*")
    @GetMapping("/Buffer")
    public List<product> listByBuffer(@RequestParam(value = "buffer",required = false,defaultValue = "1000") Double buffer, @RequestParam(value = "pointlat") Double pointlat,@RequestParam(value = "pointlng") Double pointlng){
        List<product> Res=repository.findByAvailable("1");
        List<product> Result = new ArrayList<>();
        for(product Prod:Res){
            double lat=Prod.getLat();
            double lng=Prod.getLng();
            double R=6371;
            double dlng=2*Math.asin(Math.sin(buffer/(2*R))/Math.cos(Dec2Rad(pointlat)));
            double dlat=buffer/R;
            dlat=Rad2Dec(dlat);
            dlng=Rad2Dec(dlng);
            if(lat>pointlat-dlat&&lat<pointlat+dlat){
                if(lng>(pointlng-dlng)&&lng<(pointlng+dlng)){
                    double dis=distance(pointlat,lat,pointlng,lng);
                    if(dis<(buffer*1000)) {
                        Result.add(Prod);
                    }
                }
            }
        }
        return Result;
    }


    //用于缓冲区计算
    public static double Dec2Rad(double m){
        return m/180*Math.PI;
    }
    public static double Rad2Dec(double m){
        return m*180/Math.PI;
    }
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // 地球半径
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // 单位转换成米
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }
    //缓冲区结束

}
