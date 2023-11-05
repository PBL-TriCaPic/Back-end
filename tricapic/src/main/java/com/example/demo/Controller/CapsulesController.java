package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.data_tables.Capsules;
import com.example.demo.service.CapsulesService;

@RestController
@RequestMapping("/capsules")
public class CapsulesController {

    private final CapsulesService capsulesService;

    @Autowired
    public CapsulesController(CapsulesService capsulesService) {
        this.capsulesService = capsulesService;
    }

    @PostMapping("/create")
    public boolean createCapsule(@RequestBody Capsules capsule) {
        return capsulesService.createCapsule(capsule);
    }

    @GetMapping("/{id}")
    public boolean getCapsule(@PathVariable Long id) {
        return capsulesService.getCapsule(id);
    }

    @PostMapping("/saveImage")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file) {
        String fileName = capsulesService.saveImage(file);
        if (fileName != null) {
            return new ResponseEntity<>("Image saved successfully with file name: " + fileName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to save image", HttpStatus.BAD_REQUEST);
        }
    }
}
