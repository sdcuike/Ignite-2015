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

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * @author doctor
 *
 * @time 2015年8月12日 下午4:53:13
 *
 * @see https://github.com/apache/incubator-ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/datagrid/CacheApiExample.java
 *
 */
public class CacheApiExample {

	public static void main(String[] args) throws Exception {
		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			CacheConfiguration<String, LocalDateTime> cacheCfg = new CacheConfiguration<>("CacheApiExample");
			cacheCfg.setCacheMode(CacheMode.PARTITIONED).setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 3)));

			try (IgniteCache<String, LocalDateTime> igniteCache = ignite.getOrCreateCache(cacheCfg)) {
				LocalDateTime time = igniteCache.get("doctor");
				assert time == null;
				LocalDateTime now = LocalDateTime.now();

				igniteCache.put("doctor", now);
				time = igniteCache.get("doctor");
				assert time == now;
				System.out.println(time);

				igniteCache.invoke("doctor", (entry, arg) -> {
					LocalDateTime value = entry.getValue();
					entry.setValue(value.plusDays(10L));
					return null;

				});

				TimeUnit.SECONDS.sleep(5);

				time = igniteCache.get("doctor");

				System.out.println(time);
			}

		}
	}

}
