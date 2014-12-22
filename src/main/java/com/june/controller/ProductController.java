package com.june.controller;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.june.beans.Products;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String index(){
		
		return "index";
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String products(){
		
		return "products";
	}
	
	@RequestMapping(value = "getJSONList" ,method = RequestMethod.GET)
	public void getJSONList(@ModelAttribute Products products,PrintWriter printWriter){
//		System.out.println(products);
		String jsonString = JSON.toJSONString(products, SerializerFeature.PrettyFormat);
		printWriter.write(jsonString); 
        printWriter.flush(); 
        printWriter.close(); 
	}
}
