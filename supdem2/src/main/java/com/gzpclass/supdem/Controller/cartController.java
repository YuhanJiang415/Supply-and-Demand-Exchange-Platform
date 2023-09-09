package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.cartRepository;
import com.gzpclass.supdem.domain.cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class cartController {

    @Autowired
    private cartRepository repository;

    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<cart> list(){
        return repository.findAll();
    }

    //删除商品
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/{cartId}")
    public void cartDelete(@PathVariable("cartId") Integer id){
        repository.deleteById(id);
    }

    //添加商品
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public cart create(@RequestParam("productId") Integer productId,
                          @RequestParam("addDate") Date addDate
    ){
        cart cart=new cart();

        cart.setProductId(productId);
        cart.setAddDate(addDate);

        return repository.save(cart);
    }

}
