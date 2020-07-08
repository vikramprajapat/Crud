package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Exception.*;
import com.example.demo.model.Employee;
import com.example.demo.servies.EmployeeServies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmployeeServies service;

	@RequestMapping("/")//for view web page
	public ModelAndView viewHomePage(Model model) {		
		
		ModelAndView mav = new ModelAndView();
		List<Employee> list = service.getAllEmployees();
		model.addAttribute("employees", list);
		logger.info("{this is a info message}",list);
		mav.setViewName("list-employees");
		return mav;

	}
	@GetMapping("/edit/{id}")
	public ModelAndView edit(Model model,@PathVariable("id") Long id) throws RecordNotFoundException {		
		ModelAndView mav = new ModelAndView();
		Employee entity = service.getEmployeeById(id);	
		model.addAttribute("employee", entity);
		mav.setViewName("add-edit-employee");
		return mav;

	}

	@GetMapping("/getList")//list for All employees
	public ResponseEntity<List<Employee>> getAllEmployeesList() {
		List<Employee> list = service.getAllEmployees();		
		logger.info("{this is a info list }",list);
		return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")//find user details by id
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Employee entity = service.getEmployeeById(id);		
		return new ResponseEntity<Employee>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/save")//new user save and update
	public ResponseEntity<Employee> saveEmployee(Employee employee) throws RecordNotFoundException {
		Employee updated = service.createOrUpdateEmployee(employee);
		return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
	} 

	@DeleteMapping("delete/{id}")//delete record by id
	public HttpStatus deleteEmployee(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		return HttpStatus.FORBIDDEN;
	}

}
