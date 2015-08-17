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
package com.doctor.ignite.getting_started;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache.Entry;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;

import com.doctor.ignite.example.Person;

/**
 * @author doctor
 *
 * @time 2015年8月17日 上午9:55:06
 *
 * @see http://apacheignite.gridgain.org/v1.0/docs/jdbc-driver
 *
 */
public class IgniteJDBCDriverPractice {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			CacheConfiguration<String, Person> cacheCfg = new CacheConfiguration<>("IgniteJDBCDriverPractice");
			cacheCfg.setCacheMode(CacheMode.PARTITIONED).setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 3)));

			try (IgniteCache<String, Person> igniteCache = ignite.getOrCreateCache(cacheCfg)) {

				Person person = new Person(UUID.randomUUID(), "doctor", BigDecimal.valueOf(129999.800), "man", "djfk");

				Person person2 = new Person(UUID.randomUUID(), "doctor who", BigDecimal.valueOf(129999.800), "man", "djfk");

				igniteCache.put(person.getId().toString(), person);
				igniteCache.put(person2.getId().toString(), person2);

				System.out.println("--------ScanQuery----------");
				try (QueryCursor<Entry<Object, Object>> query = igniteCache.query(new ScanQuery<>((k, v) -> true))) {
					for (Entry<Object, Object> entry : query) {
						System.out.println(entry.getKey() + ":" + entry.getValue());
					}
				}
			}
		}

		Class.forName("org.apache.ignite.IgniteJdbcDriver");
		Connection connection = DriverManager.getConnection("jdbc:ignite://127.0.0.1:11211/");
		ResultSet resultSet = connection.createStatement().executeQuery("select name from Person");

		while (resultSet.next()) {
			String string = resultSet.getString(0);
			System.out.println(string);
		}
	}

}
