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
 * @see https://issues.apache.org/jira/browse/IGNITE-1255(寻求帮助一下).
 * 
 *      https://apacheignite.readme.io/docs/cache-queries
 * 
 *      https://issues.apache.org/jira/browse/IGNITE-302 bug修复版本
 * 
 *      http://apacheignite.gridgain.org/v1.0/discuss/cat/data-grid
 * 
 * 
 *      [15:20:28,659][SEVERE][tcp-disco-msg-worker-#2%null][TcpDiscoverySpi] Failed to unmarshal discovery custom message.
 *      class org.apache.ignite.IgniteCheckedException: Failed to find class with given class loader for unmarshalling (make sure same versions of all classes are available on all nodes or enable peer-class-loading): sun.misc.Launcher$AppClassLoader@18b4aac2
 *      at org.apache.ignite.marshaller.jdk.JdkMarshaller.unmarshal(JdkMarshaller.java:104)
 *      at org.apache.ignite.marshaller.AbstractMarshaller.unmarshal(AbstractMarshaller.java:67)
 *      at org.apache.ignite.spi.discovery.tcp.messages.TcpDiscoveryCustomEventMessage.message(TcpDiscoveryCustomEventMessage.java:77)
 *      at org.apache.ignite.spi.discovery.tcp.ServerImpl$RingMessageWorker.notifyDiscoveryListener(ServerImpl.java:3935)
 *      at org.apache.ignite.spi.discovery.tcp.ServerImpl$RingMessageWorker.processCustomMessage(ServerImpl.java:3907)
 *      at org.apache.ignite.spi.discovery.tcp.ServerImpl$RingMessageWorker.processMessage(ServerImpl.java:1895)
 *      at org.apache.ignite.spi.discovery.tcp.ServerImpl$MessageWorkerAdapter.body(ServerImpl.java:4840)
 *      at org.apache.ignite.spi.IgniteSpiThread.run(IgniteSpiThread.java:62)
 *      Caused by: java.lang.ClassNotFoundException: com.doctor.ignite.example.Person
 *      at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
 *      at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
 *      at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
 *      at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
 *      at java.lang.Class.forName0(Native Method)
 *      at java.lang.Class.forName(Class.java:348)
 *      at org.apache.ignite.internal.util.IgniteUtils.forName(IgniteUtils.java:7911)
 *      at org.apache.ignite.marshaller.jdk.JdkMarshallerObjectInputStream.resolveClass(JdkMarshallerObjectInputStream.java:52)
 *      at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1613)
 *      at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1518)
 *      at java.io.ObjectInputStream.readClass(ObjectInputStream.java:1484)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1334)
 *      at java.io.ObjectInputStream.readArray(ObjectInputStream.java:1707)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1345)
 *      at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2000)
 *      at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
 *      at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
 *      at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2000)
 *      at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
 *      at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
 *      at java.io.ObjectInputStream.readObject(ObjectInputStream.java:371)
 *      at java.util.ArrayList.readObject(ArrayList.java:791)
 *      at sun.reflect.GeneratedMethodAccessor9.invoke(Unknown Source)
 *      at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 *      at java.lang.reflect.Method.invoke(Method.java:497)
 *      at java.io.ObjectStreamClass.invokeReadObject(ObjectStreamClass.java:1017)
 *      at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1900)
 *      at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
 *      at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2000)
 *      at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
 *      at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
 *      at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2000)
 *      at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
 *      at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
 *      at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
 *      at java.io.ObjectInputStream.readObject(ObjectInputStream.java:371)
 *      at org.apache.ignite.marshaller.jdk.JdkMarshaller.unmarshal(JdkMarshaller.java:98)
 *      ... 7 more
 * 
 *
 *      解决：请打包工程jar包及依赖第三方包打包到一起（像JStorm一样打包），copy到apache-ignite lib目录下。
 *      see http://apacheignite.gridgain.org/v1.0/docs/zero-deployment：
 *      Such behavior is possible due to peer class loading (P2P class loading), a special distributed ClassLoader in Ignite for inter-node byte-code exchange. With peer-class-loading enabled, you don't have to manually deploy your Java or Scala code on each node in the grid and re-deploy it each
 *      time it changes.
 * 
 *      It is recommended that peer-class-loading is disabled in production. Generally you want to have a controlled production environment without any magic. To deploy your classes explicitly, you can copy them into Ignite libs folder or manually add them to the classpath on every node.
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
