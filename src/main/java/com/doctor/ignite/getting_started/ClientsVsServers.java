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

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;

/**
 * Clients vs. Servers
 * 
 * @author doctor
 *
 * @time 2015年8月18日 上午11:15:51
 *
 * @see http://apacheignite.gridgain.org/v1.0/docs/clients-vs-servers
 *
 *      本地启动三个服务器节点
 *      此程序配置为客户端，不参与保存缓存数据。
 *      ignite默人配置：config/default-config.xml
 */
public class ClientsVsServers {

	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start()) {
			ClusterGroup servers = ignite.cluster().forServers();
			ClusterGroup clients = ignite.cluster().forClients();

			System.out.println("server nodes:" + servers.nodes());
			System.out.println("client nodes:" + clients.nodes());

			// server nodes:[TcpDiscoveryNode [id=b70f352f-fdd4-4513-bba9-15fa553cea92, addrs=[0:0:0:0:0:0:0:1%lo, 127.0.0.1, 192.168.66.128], sockAddrs=[linux-opensuse/192.168.66.128:47500, /0:0:0:0:0:0:0:1%lo:47500, /127.0.0.1:47500, /192.168.66.128:47500], discPort=47500, order=1, intOrder=1,
			// loc=false, ver=1.3.0#20150710-sha1:2ade6d00, isClient=false], TcpDiscoveryNode [id=4653d6d5-9db5-45ca-95b6-75c19f624d24, addrs=[0:0:0:0:0:0:0:1%lo, 127.0.0.1, 192.168.66.128], sockAddrs=[linux-opensuse/192.168.66.128:47501, /0:0:0:0:0:0:0:1%lo:47501, /127.0.0.1:47501,
			// /192.168.66.128:47501], discPort=47501, order=2, intOrder=2, loc=false, ver=1.3.0#20150710-sha1:2ade6d00, isClient=false], TcpDiscoveryNode [id=6802bdc4-2d69-4315-8c87-3ec6e31d1964, addrs=[0:0:0:0:0:0:0:1%lo, 127.0.0.1, 192.168.66.128], sockAddrs=[linux-opensuse/192.168.66.128:47502,
			// /0:0:0:0:0:0:0:1%lo:47502, /127.0.0.1:47502, /192.168.66.128:47502], discPort=47502, order=3, intOrder=3, loc=false, ver=1.3.0#20150710-sha1:2ade6d00, isClient=false]]

			// client nodes:[TcpDiscoveryNode [id=80ad270f-fb37-49ea-9f07-ea3a2f12dd27, addrs=[0:0:0:0:0:0:0:1%lo, 127.0.0.1, 192.168.66.128], sockAddrs=[linux-opensuse/192.168.66.128:0, /0:0:0:0:0:0:0:1%lo:0, /127.0.0.1:0, /192.168.66.128:0], discPort=0, order=8, intOrder=0, loc=true,
			// ver=1.3.0#19700101-sha1:00000000, isClient=true]]
			//
		}
	}

}
