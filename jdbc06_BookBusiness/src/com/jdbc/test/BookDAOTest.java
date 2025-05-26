package com.jdbc.test;

import com.jdbc.dao.impl.BookDAOImpl;
import com.jdbc.exception.BookNotFoundException;
import com.jdbc.exception.DMLException;
import com.jdbc.exception.DuplicateISBNException;
import com.jdbc.exception.InvalidInputException;
import com.jdbc.vo.Book;

import config.ServerInfo;

public class BookDAOTest {

	public static void main(String[] args) {
		BookDAOImpl dao = BookDAOImpl.getInstance();
//		try {
//		    dao.registerBook(new Book("1", "Java 입문", "홍길동", "한빛미디어", 12000));
//		    dao.registerBook(new Book("2", "스프링 부트", "이순신", "한빛미디어", 15000));
//		    dao.registerBook(new Book("3", "파이썬 기초", "강감찬", "한빛미디어", 18000));
//		    dao.registerBook(new Book("4", "자바스크립트 완벽 가이드", "유관순", "한빛미디어", 22000));
//		    dao.registerBook(new Book("5", "알고리즘 문제 해결", "장보고", "한빛미디어", 25000));
//		    dao.registerBook(new Book("6", "데이터베이스 개론", "김유신", "한빛미디어", 30000));
//		    dao.registerBook(new Book("7", "네트워크 기초", "윤봉길", "한빛미디어", 17000));
//		    dao.registerBook(new Book("8", "운영체제론", "안중근", "한빛미디어", 28000));
//		    dao.registerBook(new Book("9", "리눅스 시스템 프로그래밍", "신사임당", "한빛미디어", 26000));
//		    dao.registerBook(new Book("10", "클린 코드", "로버트 마틴", "한빛미디어", 29000));
//		}catch(DuplicateISBNException e) {
//			System.out.println(e.getMessage());
//		}catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			dao.deleteBook("1");
//		}catch(BookNotFoundException e) {
//			System.out.println(e.getMessage());
//		}catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			Book book=dao.findByBook("1", "Java 입문");
//			System.out.println(book);
//		}catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			dao.findByPublisher("한빛미디어").stream().forEach(System.out::println);
//		}catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			if(!dao.findByPrice(20000,30000).isEmpty())
//			dao.findByPrice(20000,30000).stream().forEach(System.out::println);
//		}catch(InvalidInputException e) {
//			System.out.println(e.getMessage());
//		}catch(DMLException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
			dao.discountBook(10, "한빛미디어");
		}catch(DMLException e){
			System.out.println(e.getMessage());
		}
	} //main

	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
