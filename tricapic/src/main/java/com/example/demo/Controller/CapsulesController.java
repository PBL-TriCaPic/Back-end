package com.example.demo.Controller;

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
import com.example.demo.service.CapsulesService;sule);
    }

    @GetMapping("/{id}")
    public boolean getCapsule(@PathVariable Long id) {
        return capsulesService.getCapsule(id);
    }

    @PostMapping("/saveImage")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file) {
        String fileName = capsulesService.saveImage(file);
        if (fileName != null) {
            System.out.println("Image saved successfully with file name: " + fileName);
            return new ResponseEntity<>("Image saved successfully with file name: " + fileName, HttpStatus.OK);
        } else {
            System.out.println("Failed to save image");
            return new ResponseEntity<>("Failed to save image", HttpStatus.BAD_REQUEST);
        }
    }

}
