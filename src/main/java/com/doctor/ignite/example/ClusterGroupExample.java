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

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;

/**
 * @author doctor
 *
 * @time 2015年8月12日 下午4:20:08
 *
 * @see https://github.com/apache/incubator-ignite/blob/master/examples/src/main/java8/org/apache/ignite/examples/java8/cluster/ClusterGroupExample.java
 *
 */
public class ClusterGroupExample {

	public static void main(String[] args) throws InterruptedException {
		try (Ignite ignite = Ignition.start("config/default-config.xml")) {

			IgniteCluster igniteCluster = ignite.cluster();
			System.out.println("节点数目：" + igniteCluster.nodes().size());

			// Say hello to all nodes in the cluster, including local node.
			sayHello(ignite, igniteCluster);

			// Say hello to all remote nodes.
			sayHello(ignite, igniteCluster.forRemotes());

			sayHello(ignite, igniteCluster.forRandom());
			sayHello(ignite, igniteCluster.forRemotes().forRandom());
			sayHello(ignite, igniteCluster.forPredicate(n -> n.metrics().getCurrentCpuLoad() < 0.5));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void sayHello(final Ignite ignite, final ClusterGroup clusterGroup) {
		ignite.compute(clusterGroup).broadcast(() -> System.out.println(">>> hello node:" + clusterGroup.ignite().cluster().localNode().id()));
	}

}
