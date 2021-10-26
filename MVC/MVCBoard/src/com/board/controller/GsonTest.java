package com.board.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTest {

	public static void main(String[] args) {
		// 1. Java -> Json 형식의 문자열
		Customer c = new Customer("100", "tommy", "la");
		
		//Gson gs = new Gson();
		//Gson gs = new GsonBuilder().create();
		Gson gs = new GsonBuilder().setPrettyPrinting().create();
		String re = gs.toJson(c);
		System.out.println(re);
		
		// 2. Json 문자열 -> Java 객체
		String s = "{'num':'50','name':'jane','address':'pa'}";
		Gson gs2 = new Gson();
		Customer c2 = gs2.fromJson(s, Customer.class);
		System.out.println(c2);
	}

	static class Customer {
		String num, name, address;

		public Customer(String num, String name, String address) {
			super();
			this.num = num;
			this.name = name;
			this.address = address;
		}

		@Override
		public String toString() {
			return "Customer [num=" + num + ", name=" + name + ", address=" + address + "]";
		}
	
	}
}
