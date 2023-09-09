package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.HistoryOrderSellerRepository;
import com.gzpclass.supdem.domain.HistoryOrderSeller;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@RestController
@RequestMapping("/orderseller")
public class HistoryOrderSellerController {

    @Autowired
    private HistoryOrderSellerRepository repository;

    //加载所有数据
    @CrossOrigin(origins="*")
    @GetMapping("/all")
    public List<HistoryOrderSeller> all(){
        return repository.findAll();
    }

    //添加数据
    @CrossOrigin(origins="*")
    @PostMapping(value = "/add")
    public HistoryOrderSeller create(@RequestParam("sellerId") Integer sellerId,
                                     @RequestParam("product") String product,
                                     @RequestParam("remarkers") String remarkers,
                                     @RequestParam("address") String address,//收货地址
                                     @RequestParam("buyerId") Integer buyerId,
                                     @RequestParam("lng") Double lng,
                                     @RequestParam("lat") Double lat
    ){
        HistoryOrderSeller HistoryOrderSeller=new HistoryOrderSeller();

        HistoryOrderSeller.setSellerId(sellerId);
        HistoryOrderSeller.setBuyerId(buyerId);

        if(product.length()>0){HistoryOrderSeller.setProduct(product);}
        if(remarkers.length()>0){HistoryOrderSeller.setRemarkers(remarkers);}
        if(address.length()>0){ HistoryOrderSeller.setAddress(address);}

        HistoryOrderSeller.setLng(lng);
        HistoryOrderSeller.setLat(lat);

        return repository.save(HistoryOrderSeller);
    }

    //删除数据
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/{orderId}")
    public void goodsDelete(@PathVariable("orderId") Integer id){
        repository.deleteById(id);
    }
}
