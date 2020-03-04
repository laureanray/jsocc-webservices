package com.fozf.jsoccwebservices.controllers.api;
import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

   final static String BASE_URL = "api/v1/admin";
   private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Admin> findByUsername(@PathVariable String username){
        Admin admin = adminService.findByUsername(username);
        if(admin != null){
            return ResponseEntity.ok(admin);
        }

        return ResponseEntity.notFound().build();
    }

}
