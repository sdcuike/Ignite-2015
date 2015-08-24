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
import java.util.UUID;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.doctor.ignite.example.Person;

/**
 * @author doctor
 *
 * @time 2015年8月19日 下午4:44:24
 *
 * @see
 *
 */
public class CrossCacheQueries {

	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringIgniteConfig.class);
		Ignite ignite = applicationContext.getBean(Ignite.class);

		CacheConfiguration<UUID, Person> cacheConfiguration = applicationContext.getBean("CacheConfigurationForDays", CacheConfiguration.class);

		IgniteCache<UUID, Person> igniteCache1 = getCache("person1", ignite, cacheConfiguration);

		Person person = new Person(UUID.randomUUID(), "doctor -1 ", BigDecimal.valueOf(88888888.888), "man", "...");
		igniteCache1.put(person.getId(), person);

		Person person1 = new Person(UUID.randomUUID(), "doctor 118", BigDecimal.valueOf(88888888.888), "man", "...");
		igniteCache1.put(person1.getId(), person1);

		Person person2 = new Person(UUID.randomUUID(), "doctor who 88 ", BigDecimal.valueOf(188888888.888), "man", "...");
		igniteCache1.put(person2.getId(), person2);

		IgniteCache<UUID, Person> igniteCache2 = getCache("person2", ignite, cacheConfiguration);

		Person person3 = new Person(UUID.randomUUID(), "doctor who ---88 ", BigDecimal.valueOf(1188888888.888), "man", "...");
		igniteCache2.put(person3.getId(), person3);

		// try (QueryCursor<List<?>> queryCursor = igniteCache1.query(new SqlFieldsQuery("select name from Person"))) {
		// for (List<?> entry : queryCursor) {
		// System.out.println("--}}}}}" + entry);
		// }
		// }

		SqlQuery sqlQuery = new SqlQuery<>(Person.class, "from Person , \"person2\".Person  ");
		try (QueryCursor queryCursor = igniteCache1.query(sqlQuery)) {
			for (Object object : queryCursor) {
				System.out.println(object);
			}
		}

		applicationContext.close();
	}

	public static IgniteCache<UUID, Person> getCache(String cacheName, Ignite ignite, CacheConfiguration cacheConfiguration) {
		IgniteCache<UUID, Person> igniteCache = ignite.cache(cacheName);

		if (igniteCache == null) {
			CacheConfiguration<UUID, Person> cacheConfiguration2 = new CacheConfiguration(cacheConfiguration);
			cacheConfiguration2.setName(cacheName);
			// igniteCache = ignite.createCache(cacheConfiguration2);
			return ignite.getOrCreateCache(cacheConfiguration2);
		}

		return igniteCache;
	}
}
