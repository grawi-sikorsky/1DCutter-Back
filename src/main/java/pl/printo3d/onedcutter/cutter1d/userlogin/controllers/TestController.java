package pl.printo3d.onedcutter.cutter1d.userlogin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.repo.UserRepo;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TestController {

    @Autowired
    private UserRepo uRepo;

    @GetMapping("/test")
    public List<UserModel> getUsers() {
        return (List<UserModel>) uRepo.findAll();
    }

    @PostMapping("/test")
    public void addUser(@RequestBody UserModel uModel) {
        uRepo.save(uModel);
    }
}
