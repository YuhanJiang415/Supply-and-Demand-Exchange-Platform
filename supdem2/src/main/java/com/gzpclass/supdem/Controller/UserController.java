package com.gzpclass.supdem.Controller;

import com.gzpclass.supdem.Repository.userRepository;
import com.gzpclass.supdem.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userRepository repository;

    //加载所有数据
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public List<user> all(){
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/button1")
    public List<user> findByuserNameAndPass(@RequestParam("user") String user, @RequestParam("password") String password){
        System.out.println(repository.findByuserNameAndPass(user,password));
        return repository.findByuserNameAndPass(user,password);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user")
    public void insertuser(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("name") String name,
                           @RequestParam("phone") String phone,
                           @RequestParam("qq") String qq){
        user user1 = new user();
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setName(name);
        user1.setPhone(phone);
        user1.setQq(qq);
        repository.save(user1);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update/{userId}")
    public user userUpdate(@PathVariable("userId") Integer id,
                           @RequestParam("address") String address
    ){

        Optional<user> optional =repository.findById(id);
        if(optional.isPresent()){
            user user=optional.get();
            System.out.println(user.getAddress1());
            if (user.getAddress1()==null)
            {
                user.setAddress1(address);
            }
            else{
                if(user.getAddress2()==null)
                {
                    user.setAddress2(address);
                }
                else {
                    user.setAddress3(address);
                }
            }
            return repository.save(user);
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ID/{userId}")
    public user findUserID(@PathVariable("userId") Integer id){
        return repository.findById(id).get();
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/delete/{userId}")
    public user userDelete(@PathVariable("userId") Integer id,
                           @RequestParam("address") String address
    ){

        Optional<user> optional =repository.findById(id);
        if(optional.isPresent()){
            user user=optional.get();
            if (user.getAddress1()!=null)
            {
                if(user.getAddress1().contentEquals(address))
                {
                    user.setAddress1(null);
                }

            }
            else{
                if(user.getAddress2()!=null)
                {
                    if(user.getAddress2().contentEquals(address))
                    {
                        user.setAddress2(null);
                    }
                }
                else {
                    if(user.getAddress3()!=null)
                    {
                        if(user.getAddress3().contentEquals(address))
                        {
                            user.setAddress3(null);
                        }
                    }
                }
            }
            return repository.save(user);
        }
        return null;
    }

}
