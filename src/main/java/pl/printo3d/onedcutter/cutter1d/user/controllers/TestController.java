package pl.printo3d.onedcutter.cutter1d.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.user.repo.UserRepo;

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
