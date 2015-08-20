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

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
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

	@Resource(name = "igniteSpringBean")
	private Ignite ignite;

	@Resource(name = "CacheConfigurationForDays")
	private CacheConfiguration<UUID, Person> cacheConfiguration;
}
