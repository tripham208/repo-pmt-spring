package vn.id.pmt.spring.controller.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.pmt.spring.dto.UserDto;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public UserDto getAdmin(){
        return new UserDto("admin");
    }
}
