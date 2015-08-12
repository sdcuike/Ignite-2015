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
package com.doctor.javax.cache;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

/**
 * javax.cache.Cache 例子
 * 
 * @author doctor
 *
 * @time 2015年8月12日 上午10:08:46
 *
 * @see https://ignite.incubator.apache.org/jcache/1.0.0/javadoc/javax/cache/package-summary.html
 *      http://gregluck.com/blog/archives/2011/10/javax-cache-the-new-java-caching-standard/
 *      https://dzone.com/articles/introduction-jcache-jsr-107
 *      https://dzone.com/articles/apache-ignite-word-count
 *
 */
public class CacheManagerPractice {

	public static void main(String[] args) throws InterruptedException {

		// resolve a cache manager
		CachingProvider cachingProvider = Caching.getCachingProvider();

		System.out.println(cachingProvider);// 得到的是ignite版本。org.apache.ignite.cache.CachingProvider@1c4af82c

		// Creating a Cache
		CacheManager cacheManager = cachingProvider.getCacheManager();
		// 对该Cache的配置，类型、缓存失效策略。我们用了时间窗口模式缓存失效策略
		MutableConfiguration<String, String> configuration = new MutableConfiguration<>();
		configuration.setTypes(String.class, String.class).setStatisticsEnabled(true)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5)));

		final String cacheName = "testCache";

		// 创建
		Cache<String, String> myCache = cacheManager.createCache(cacheName, configuration);

		// myCache = cacheManager.getCache(cacheName, String.class, String.class);
		System.out.println(myCache);// IgniteCacheProxy,Ignite实现

		//
		myCache.put("name", "doctor who");
		System.out.println(myCache.get("name"));

		myCache.put("name", "doctor ");
		System.out.println(myCache.get("name"));

		TimeUnit.SECONDS.sleep(7);

		System.out.println(myCache.get("name"));
	}
}
