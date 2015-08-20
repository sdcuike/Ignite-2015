/*
 * Copyright (C) 2014-present  The   Ignite-2015  Authors
 *
 * https://github.com/sdcuike
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.doctor.ignite.example.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author doctor
 *
 * @time 2015年8月20日 上午11:41:03
 *
 * @see
 *
 */
public class SpringIgniteExample2 {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(config.class);
		IgniteDao igniteDao = applicationContext.getBean(IgniteDao.class);
		System.out.println("igniteDao:-----------" + igniteDao);
		applicationContext.close();
	}

	@Configuration
	@ComponentScan("com.doctor.ignite.example.spring")
	@ImportResource("classpath:/Ignite-2015-config/spring-ignite.xml")
	public static class config {

	}
}
