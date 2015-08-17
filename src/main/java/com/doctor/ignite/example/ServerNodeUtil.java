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

import java.util.concurrent.TimeUnit;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * @author doctor
 *
 * @time 2015年8月17日 下午3:03:34
 *
 * @see
 *
 */
public class ServerNodeUtil {

	public static void main(String[] args) throws InterruptedException {
		try (Ignite ignite = Ignition.start("config/cluster-config-content.xml")) {

			TimeUnit.HOURS.sleep(8);
		}

	}

}
