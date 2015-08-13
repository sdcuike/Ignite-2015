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

/**
 * @author doctor
 *
 * @time 2015年8月13日 上午11:31:13
 *
 * @see https://apacheignite.readme.io/docs/cache-queries
 *
 */
public class CacheQueriesScanQueryExample {

	public static void main(String[] args) throws InterruptedException {

		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			CacheConfiguration<String, LocalDateTime> cacheCfg = new CacheConfiguration<>("CacheQueriesScanQueryExample");
			cacheCfg.setCacheMode(CacheMode.PARTITIONED).setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 3)));

			try (IgniteCache<String, LocalDateTime> igniteCache = ignite.getOrCreateCache(cacheCfg)) {

				LocalDateTime now = LocalDateTime.now();

				igniteCache.put("doctor", now);
				igniteCache.put("doctor who", now.plusHours(10L));
				igniteCache.put("doctor who", now.minusYears(1L));

				System.out.println("--------ScanQuery----------");
				try (QueryCursor<Entry<Object, Object>> query = igniteCache.query(new ScanQuery<>((k, v) -> v.equals(now)))) {
					for (Entry<Object, Object> entry : query) {
						System.out.println(entry.getKey() + ":" + entry.getValue());
					}
				}
			}
		}
	}

}
