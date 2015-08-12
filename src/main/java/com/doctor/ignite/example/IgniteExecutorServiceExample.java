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

import java.util.concurrent.ExecutorService;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteRunnable;

/**
 * @author doctor
 *
 * @time 2015年8月12日 下午5:29:09
 *
 * @see
 *
 */
public class IgniteExecutorServiceExample {

	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			ExecutorService service = ignite.executorService();

			for (String word : "helo doctor, welcome to ignite world".split(" ")) {
				// 转型IgniteRunnable，分布式环境序列化的呀
				service.submit((IgniteRunnable) () -> System.out.println(word + " print on this node"));
			}

			service.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
