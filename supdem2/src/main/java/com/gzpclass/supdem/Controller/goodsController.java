package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.goodsRepository;
import com.gzpclass.supdem.domain.goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/goods")
public class goodsController {

    @Autowired
    private goodsRepository repository;

    //加载所有数据
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<goods> list(){
        //List<product> Product=repository.findByAvailable("1");
        //return Product;
        return repository.findAll();
    }

    //查询新加入的数据
    @CrossOrigin(origins = "*")
    @GetMapping("/latest")
    public goods latest(){
        return repository.findFirstByOrderByGoodsIdDesc();
    }

    //***************************************************新加的
    //查询可售的数据
    @CrossOrigin(origins = "*")
    @GetMapping("/statusOK")
    public List<goods> listAvailable(){
        List<goods> Goods = repository.findByStatus("1");
        //return Product;
        return Goods;
    }

    //************************************************新
    //查询某商家的菜单
    @CrossOrigin(origins = "*")
    @GetMapping("/SellerGoods/{sellerId}")
    public List<goods> SellerGoods(@PathVariable("sellerId") Integer id){
        List<goods> sGoods = repository.findBySellerId(id);
        //return Product;
        return sGoods;
    }

    //Id查询
    @CrossOrigin(origins = "*")
    @GetMapping("/ID/{goods_id}")
    public Optional<goods> findID(@PathVariable("goods_id") Integer id){
        return repository.findById(id);
    }

//    //某商家所有商品查询
//    @CrossOrigin(origins = "*")
//    @GetMapping("/SID/{seller_id}")
//    public List<goods> findsellerID(@RequestParam("seller_id") Integer seller_id){
//        return repository.findBySellerId(seller_id);
//    }

    //添加商品
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public goods create(
            @RequestParam("seller_id") Integer seller_id,
            @RequestParam("goods_name") String goods_name,
            @RequestParam("goods_price") String goods_price
    ){
        goods goods=new goods();

        if(goods_name.length() > 0) {
            goods.setName(goods_name);
        }
        if(goods_price.length() > 0){
            goods.setPrice(Float.parseFloat(goods_price));
        }

        goods.setSellerId(seller_id);
//        setGoodsId

        goods.setStatus("1");

        return repository.save(goods);
    }

    //更新商品
    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update/{goodsId}")
    public goods goodsUpdate(
            @PathVariable("goodsId") Integer id,
            @RequestParam("goods_name") String goods_name,
            @RequestParam("goods_price") String goods_price,
            @RequestParam("status") String status
    ){
        Optional<goods> optional =repository.findById(id);
        if(optional.isPresent()){
            goods goods=optional.get();

            if(goods_name.length() > 0) {
                goods.setName(goods_name);
            }
            if(goods_price.length() > 0){
                goods.setPrice(Float.parseFloat(goods_price));
            }
            if(status.length() > 0){
                goods.setStatus(status);
            }
            return repository.save(goods);
        }
        return null;
    }

    //删除商品
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/{goods_id}")
    public void goodsDelete(@PathVariable("goods_id") Integer id){
        repository.deleteById(id);
    }

//    // 调用百度地图API根据地址，获取坐标
//    public static double[] getCoordinate(String address) {
//        String AK = "b5QreG4xEDxNrqIdPRFLQ6EzdwmjL31p"; // 百度地图密钥
//        if (address != null && !"".equals(address)) {
//            address = address.replaceAll("\\s*", "").replace("#", "栋");
//            String url = " http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + AK;
//            String json = loadJSON(url);
//            if (json != null && !"".equals(json)) {
//                JSONObject obj = JSONObject.fromObject(json);
//                if ("0".equals(obj.getString("status"))) {
//                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
//                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
//                    //DecimalFormat df = new DecimalFormat("#.######");
//                    double coor[]=new double[2];
//                    coor[0]=lng;
//                    coor[1]=lat;
//                    return coor;
//                    //[df.format(lng),df.format(lat)];
//                }
//            }
//        }
//        return null;
//    }
//    public static String loadJSON(String url) {
//        StringBuilder json = new StringBuilder();
//        try {
//            URL oracle = new URL(url);
//            URLConnection yc = oracle.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
//            String inputLine = null;
//            while ((inputLine = in.readLine()) != null) {
//                json.append(inputLine);
//            }
//            in.close();
//        } catch (MalformedURLException e) {} catch (IOException e) {}
//        return json.toString();
//    }
}
