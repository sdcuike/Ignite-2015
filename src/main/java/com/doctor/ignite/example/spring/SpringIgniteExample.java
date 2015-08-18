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

import org.apache.ignite.Ignite;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author doctor
 *
 * @time 2015年8月18日 下午5:12:54
 *
 * @see
 *
 */
public class SpringIgniteExample {
	// AnnotationConfigApplicationContext
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringIgniteConfig.class);
		Ignite ignite = applicationContext.getBean(Ignite.class);
		System.out.println(ignite);
	}

}
