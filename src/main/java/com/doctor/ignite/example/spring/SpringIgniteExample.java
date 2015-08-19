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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.doctor.ignite.example.Person;

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
	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringIgniteConfig.class);
		Ignite ignite = applicationContext.getBean(Ignite.class);

		CacheConfiguration<UUID, Person> cacheConfiguration = applicationContext.getBean("CacheConfigurationForDays", CacheConfiguration.class);

		IgniteCache<UUID, Person> igniteCache2 = ignite.cache("cacheForDays");

		if (igniteCache2 == null) {
			CacheConfiguration<UUID, Person> cacheConfiguration2 = new CacheConfiguration<>(cacheConfiguration);
			cacheConfiguration2.setName("cacheForDays");
			igniteCache2 = ignite.createCache(cacheConfiguration2);

			System.out.println("--------createCache:" + "cacheForDays");
		}

		Person person = new Person(UUID.randomUUID(), "doctor", BigDecimal.valueOf(88888888.888), "man", "...");
		System.out.println(igniteCache2.get(person.getId()));
		igniteCache2.put(person.getId(), person);
		System.out.println(igniteCache2.get(person.getId()));

		TimeUnit.SECONDS.sleep(6);
		System.out.println(igniteCache2.get(person.getId()));

		System.out.println("所有：");
		Person person1 = new Person(UUID.randomUUID(), "doctor 118", BigDecimal.valueOf(88888888.888), "man", "...");
		igniteCache2.put(person1.getId(), person1);

		Person person2 = new Person(UUID.randomUUID(), "doctor who 88 ", BigDecimal.valueOf(188888888.888), "man", "...");
		igniteCache2.put(person2.getId(), person2);

		try (QueryCursor<List<?>> queryCursor = igniteCache2.query(new SqlFieldsQuery("select name from Person"))) {
			for (List<?> entry : queryCursor) {
				System.out.println(entry);
			}
		}

		applicationContext.close();
	}
}
