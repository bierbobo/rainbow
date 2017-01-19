package com.bierbobo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)//让测试运行在spring测试环境下
@ContextConfiguration(locations = { "/spring.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
DirtiesContextTestExecutionListener.class,
TransactionalTestExecutionListener.class })
@Transactional
public class BIIPBaseTest {
	
	Logger logger = Logger.getLogger(BIIPBaseTest.class);
	
//	@Before
//	public void setUp(){
//		logger.info("testCase is beginning...");
//	}
//	@After
//	public void down(){
//		logger.info("testCase is ending...");
//	}
	@Test
	public void baseClass(){
		System.out.println("baseClase...");
	}
	
	

}
