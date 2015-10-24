package com.seimos.dgestao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seimos.dgestao.domain.Stuff;

/**
 * @author moesio @ gmail.com
 * @date Oct 20, 2014 12:22:57 AM
 */
@Controller
public class RawController {

	@RequestMapping("/test")
	public String test() {
		return "test";
	}

	@RequestMapping("/json")
	@ResponseBody
	public List<Stuff> json() {
		ArrayList<Stuff> list = new ArrayList<Stuff>();
		list.add(new Stuff().setId(1).setName("um"));
		list.add(new Stuff().setId(2).setName("dois"));
		list.add(new Stuff().setId(3).setName("três"));
		list.add(new Stuff().setId(4).setName("quatro"));
		list.add(new Stuff().setId(5).setName("cinco"));
		list.add(new Stuff().setId(6).setName("seis"));
		list.add(new Stuff().setId(7).setName("sete"));
		return list;
	}
}
