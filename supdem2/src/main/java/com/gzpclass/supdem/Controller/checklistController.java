package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.checklistRepository;
import com.gzpclass.supdem.domain.checklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checklist")
public class checklistController {

    @Autowired
    private checklistRepository repository;

    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<checklist> list(){return repository.findAll();}

    //更新商品，退货status=3，收货status=2，发货status=1
    //更新商品
    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update/{checklistId}")
    public checklist checklistUpdate(@PathVariable("checklistId") Integer checklistId,
                                 @RequestParam("status") String status){

        Optional<checklist> optional =repository.findById(checklistId);
        if(optional.isPresent()){
            checklist checklist=optional.get();
            if(status.length()>0) {
                checklist.setStatus(status);
            }

            return repository.save(checklist);
        }
        return null;
    }

    //添加商品,购物车中的商品，在available中不再available
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/add")
    public checklist create(@RequestParam("productId") Integer productId,
                            @RequestParam("purchaseDate") Date purchaseDate,
                            @RequestParam("status") String status
    ){
        checklist checklist=new checklist();

        checklist.setProductId(productId);
        checklist.setPurchaseDate(purchaseDate);
        checklist.setStatus(status);

        return repository.save(checklist);
    }

}
