package com.pavithra.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("math")
public class MathController {

    @GetMapping(path = "/add")
    public int doAddition(@RequestParam("a") int a, @RequestParam("b") int b) {
        int c = a + b;
        System.out.println(c);
        return c;
    }

    @GetMapping(path = "/sub")
    public int doSubtraction(@RequestParam("a") int a, @RequestParam("b") int b) {
        return a - b;
    }
    @GetMapping(path="/mul")
    public int doMultiply(@RequestParam("a") int a,@RequestParam("b")int b) {
        return a*b;
    }

    @GetMapping(path = "/div")
    public int doDivision(@RequestParam("a")int a,@RequestParam("b")int b){
        int c =a/b;
        System.out.println(c);
        return c;
    }

}
