package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.sellerRepository;
import com.gzpclass.supdem.domain.seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/seller")
public class sellerController {

    @Autowired
    private sellerRepository repository;

    //加载所有数据
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<seller> all(){
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/button2")
    public List<seller> findByusellerNameAndPass(@RequestParam("user") String user, @RequestParam("password") String password){
        System.out.println(repository.findBysellerNameAndPass(user,password));
        return repository.findBysellerNameAndPass(user,password);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/seller")
    public void insertseller(@RequestParam("sellername") String sellername,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             @RequestParam("qq") String qq,
                             @RequestParam("address") String address,
                             @RequestParam("lon") String lon,
                             @RequestParam("lat") String lat){
        seller seller1 = new seller();
        seller1.setUsername(sellername);
        seller1.setPassword(password);
        seller1.setName(name);
        seller1.setPhone(phone);
        seller1.setQq(qq);
        seller1.setAddress(address);
        seller1.setLng(Double.parseDouble(lon));
        seller1.setLat(Double.parseDouble(lat));
        repository.save(seller1);
    }

    //根据id获取
    //Id查询
    @CrossOrigin(origins = "*")
    @GetMapping("/ID/{sellerId}")
    public Optional<seller> findID(@PathVariable("sellerId") Integer id){

        return repository.findById(id);
    }

}
