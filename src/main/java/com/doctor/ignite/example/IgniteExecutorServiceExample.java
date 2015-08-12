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
 *       转型IgniteRunnable，分布式环境序列化的呀。如果用java的Runnable，没有序列化，就会出现序列化失败信息：
 * 
 *       [17:31:44,231][SEVERE][ignite-#10%pub-null%][GridJobWorker] Failed to initialize job [jobId=10a30412f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb, ses=GridJobSessionImpl [ses=GridTaskSessionImpl [taskName=com.doctor.ignite.example.IgniteExecutorServiceExample$$Lambda$8/190628410,
 *       dep=SharedDeployment [rmv=false, super=GridDeployment [ts=1439371904159, depMode=SHARED, clsLdr=GridDeploymentClassLoader [id=37246312f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb, singleNode=false,
 *       nodeLdrMap={2958bf9f-39eb-4437-8472-30fcffbc1369=3d930412f41-2958bf9f-39eb-4437-8472-30fcffbc1369}, p2pTimeout=5000, usrVer=0, depMode=SHARED, quiet=false], clsLdrId=37246312f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb, userVer=0, loc=false,
 *       sampleClsName=com.doctor.ignite.example.IgniteExecutorServiceExample$$Lambda$8/190628410, pendingUndeploy=false, undeployed=false, usage=2]], taskClsName=com.doctor.ignite.example.IgniteExecutorServiceExample$$Lambda$8/190628410, sesId=ff930412f41-2958bf9f-39eb-4437-8472-30fcffbc1369,
 *       startTime=1439371903922, endTime=9223372036854775807, taskNodeId=2958bf9f-39eb-4437-8472-30fcffbc1369, clsLdr=GridDeploymentClassLoader [id=37246312f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb, singleNode=false,
 *       nodeLdrMap={2958bf9f-39eb-4437-8472-30fcffbc1369=3d930412f41-2958bf9f-39eb-4437-8472-30fcffbc1369}, p2pTimeout=5000, usrVer=0, depMode=SHARED, quiet=false], closed=false, cpSpi=null, failSpi=null, loadSpi=null, usage=1, fullSup=false, subjId=2958bf9f-39eb-4437-8472-30fcffbc1369,
 *       mapFut=IgniteFuture [orig=GridFutureAdapter [resFlag=0, res=null, startTime=1439371904214, endTime=0, ignoreInterrupts=false, lsnr=null, state=INIT]]], jobId=10a30412f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb]]
 *       class org.apache.ignite.IgniteCheckedException: Failed to find class with given class loader for unmarshalling (make sure same version of all classes are available on all nodes or enable peer-class-loading): GridDeploymentClassLoader [id=37246312f41-8f7bb9d8-d682-4a0c-b1d1-aed38e10f7eb,
 *       singleNode=false, nodeLdrMap={2958bf9f-39eb-4437-8472-30fcffbc1369=3d930412f41-2958bf9f-39eb-4437-8472-30fcffbc1369}, p2pTimeout=5000, usrVer=0, depMode=SHARED, quiet=false]
 *       at org.apache.ignite.marshaller.optimized.OptimizedMarshaller.unmarshal(OptimizedMarshaller.java:242)
 *       at org.apache.ignite.internal.processors.job.GridJobWorker.initialize(GridJobWorker.java:382)
 *       at org.apache.ignite.internal.processors.job.GridJobProcessor.processJobExecuteRequest(GridJobProcessor.java:1046)
 *       at org.apache.ignite.internal.processors.job.GridJobProcessor$JobExecutionListener.onMessage(GridJobProcessor.java:1728)
 *       at org.apache.ignite.internal.managers.communication.GridIoManager.processRegularMessage0(GridIoManager.java:761)
 *       at org.apache.ignite.internal.managers.communication.GridIoManager.access$1500(GridIoManager.java:59)
 *       at org.apache.ignite.internal.managers.communication.GridIoManager$5.run(GridIoManager.java:724)
 *       at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
 *       at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
 *       at java.lang.Thread.run(Thread.java:745)
 *       Caused by: java.lang.ClassNotFoundException: com.doctor.ignite.example.IgniteExecutorServiceExample$$Lambda$8/190628410
 *       at java.lang.Class.forName0(Native Method)
 *       at java.lang.Class.forName(Class.java:348)
 *       at org.apache.ignite.internal.util.IgniteUtils.forName(IgniteUtils.java:7911)
 *       at org.apache.ignite.internal.MarshallerContextAdapter.getClass(MarshallerContextAdapter.java:177)
 *       at org.apache.ignite.marshaller.optimized.OptimizedMarshallerUtils.classDescriptor(OptimizedMarshallerUtils.java:252)
 *       at org.apache.ignite.marshaller.optimized.OptimizedObjectInputStream.readObjectOverride(OptimizedObjectInputStream.java:246)
 *       at java.io.ObjectInputStream.readObject(ObjectInputStream.java:365)
 *       at org.apache.ignite.internal.processors.closure.GridClosureProcessor$C4.readExternal(GridClosureProcessor.java:1815)
 *       at org.apache.ignite.marshaller.optimized.OptimizedObjectInputStream.readExternalizable(OptimizedObjectInputStream.java:451)
 *       at org.apache.ignite.marshaller.optimized.OptimizedClassDescriptor.read(OptimizedClassDescriptor.java:746)
 *       at org.apache.ignite.marshaller.optimized.OptimizedObjectInputStream.readObjectOverride(OptimizedObjectInputStream.java:252)
 *       at java.io.ObjectInputStream.readObject(ObjectInputStream.java:365)
 *       at org.apache.ignite.marshaller.optimized.OptimizedMarshaller.unmarshal(OptimizedMarshaller.java:236)
 *       ... 9 more
 * 
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
