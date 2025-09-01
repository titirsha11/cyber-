package com.example.cybershield.controller;

import com.example.cybershield.service.TotpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/totp")
public class TotpController {
    @Autowired
    private TotpService totpService;

    @GetMapping("/generate-secret")
    public ResponseEntity<String> generateSecret() {
        String secret = totpService.generateSecret();
        return ResponseEntity.ok(secret);
    }

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getQrCode(@RequestParam String secret, @RequestParam String user) throws Exception {
        return totpService.generateQrCode(secret, user);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestParam String secret, @RequestParam int code) {
        boolean valid = totpService.verifyCode(secret, code);
        return ResponseEntity.ok(valid);
    }
}
