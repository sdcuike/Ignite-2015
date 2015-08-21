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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Component;

import com.doctor.ignite.example.Person;

/**
 * @author doctor
 *
 * @time 2015年8月20日 上午11:38:27
 *
 * @see
 *
 */

@Component
public class IgniteDao {
	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("YYYYMMdd");
	@Resource(name = "igniteSpringBean")
	private Ignite ignite;

	@Resource(name = "CacheConfigurationForDays")
	private CacheConfiguration<UUID, Person> cacheConfiguration;

	public void put(Person person) {
		IgniteCache<UUID, Person> igniteCache = getCache(person);
		igniteCache.put(person.getId(), person);
	}

	public Person get(UUID id, String timesplice) {
		IgniteCache<UUID, Person> cache = ignite.cache(timesplice);
		if (cache == null) {
			return null;
		}
		return (Person) cache.get(id);
	}

	public List<Person> get(String name, String timesplice) {
		IgniteCache<UUID, Person> cache = ignite.cache(timesplice);
		if (cache == null) {
			return Arrays.asList();
		}

		SqlQuery<Object, Object> sqlQuery = new SqlQuery<>(Person.class, " name = ?").setArgs(name);

		List<Person> list = new ArrayList<>();
		try (QueryCursor<Entry<Object, Object>> queryCursor = cache.query(sqlQuery)) {
			for (Entry<Object, Object> entry : queryCursor) {
				list.add((Person) entry.getValue());
			}
		}

		return list;
	}

	public String getTimesplice(LocalDateTime time) {
		return time.format(timeFormatter);
	}

	public IgniteCache<UUID, Person> getCache(Person person) {
		String timesplice = getTimesplice(person.getBirth());
		return getCache(timesplice);
	}

	public IgniteCache<UUID, Person> getCache(String timesplice) {
		IgniteCache<UUID, Person> cache = ignite.cache(timesplice);
		if (cache == null) {
			CacheConfiguration<UUID, Person> cfg = new CacheConfiguration<>(cacheConfiguration);
			cfg.setName(timesplice);
			cache = ignite.createCache(cfg);
		}
		return cache;
	}
}
