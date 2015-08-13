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
package com.doctor.ignite.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * @author doctor
 *
 * @time 2015年8月13日 下午2:13:05
 *
 * @see https://apacheignite.readme.io/docs/cache-queries
 * 
 *      https://issues.apache.org/jira/browse/IGNITE-302 bug修复版本
 *
 */
public class CacheQueriesSQLQueriesExample {

	public static void main(String[] args) {

		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			CacheConfiguration<UUID, Person> cacheCfg = new CacheConfiguration<>("CacheQueriesSQLQueriesExample");
			cacheCfg.setCacheMode(CacheMode.PARTITIONED).setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 3)));
			cacheCfg.setIndexedTypes(UUID.class, Person.class);

			try (IgniteCache<UUID, Person> igniteCache = ignite.getOrCreateCache(cacheCfg)) {
				Person person1 = new Person(UUID.randomUUID(), "doctor", BigDecimal.valueOf(100000.600), "man", "djfks ksdfk ");
				igniteCache.put(person1.getId(), person1);

				Person person2 = new Person(UUID.randomUUID(), "doctor who ", BigDecimal.valueOf(110000.600), "man", "djfks ksdfk ");
				igniteCache.put(person2.getId(), person2);

				Person person3 = new Person(UUID.randomUUID(), "doctor who me", BigDecimal.valueOf(99999.600), "woman ", "djfks ksdfk ");
				igniteCache.put(person3.getId(), person3);

				SqlQuery<UUID, Person> sqlQuery = new SqlQuery<>(Person.class, "  name = ?");
				sqlQuery.setArgs(person1.getName());

				System.out.println("--------SQL Queries ----------");
				// try (QueryCursor<Entry<UUID, Person>> queryCursor = igniteCache.query(sqlQuery)) {
				// for (Entry<UUID, Person> entry : queryCursor) {
				// System.out.println(entry.getKey() + ":" + entry.getValue());
				// }
				// }

				// try (QueryCursor<Entry<Object, Object>> queryCursor = igniteCache.query(new TextQuery<>(Person.class, "doctor"))) {
				// for (Entry<Object, Object> entry : queryCursor) {
				// System.out.println(entry.getKey() + ":" + entry.getValue());
				// }
				// }

				try (QueryCursor<List<?>> queryCursor = igniteCache.query(new SqlFieldsQuery("select name from Person"))) {
					for (List<?> entry : queryCursor) {
						System.out.println(entry);
					}
				}
			}
		}
	}
}
