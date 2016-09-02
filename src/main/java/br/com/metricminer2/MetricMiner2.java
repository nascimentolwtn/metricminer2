/**
 * Copyright 2014 Maurício Aniche

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.metricminer2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import br.com.metricminer2.notifiers.NotifierWhenDone;

public class MetricMiner2 {

	public static void main(String[] args) {
		System.out.println("You should not run me! :/");
	}
	
	private static Logger log = Logger.getLogger(MetricMiner2.class);
	private NotifierWhenDone notifierWhenDone;

	public void start(Study study) {

		try {
			
			Calendar startDate = Calendar.getInstance();
			
			log.info("# -------------------------------------------------- #");
			log.info("#                   MetricMiner                      #");
			log.info("#                      v2.0                          #");
			log.info("#             www.metricminer.org.br                 #");
			log.info("# -------------------------------------------------- #");
			log.info("Starting engine: " + new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(startDate.getTime()));
			
			try {
				study.execute();
			} catch (Throwable t) {
				log.error("some study error came to me", t);
			}
			
			String finishMessage = buildFinishMessage(startDate);
			log.info(finishMessage);
			if(this.notifierWhenDone != null) {
				this.notifierWhenDone.notifyAfterMining(finishMessage);
			}
			
		} catch(Throwable ex) {
			log.error("Some error ocurred", ex);
		}
	}

	private String buildFinishMessage(Calendar startDate) {
		StringBuilder sb = new StringBuilder();
		Calendar finishDate = Calendar.getInstance();
		sb.append("Finished: " + new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(finishDate.getTime()));
		sb.append("\n");
		long seconds = (finishDate.getTimeInMillis() - startDate.getTimeInMillis())/1000;
		sb.append("It took " + seconds + " seconds (~" + seconds/60 + " minutes).");
		sb.append("\n");
		sb.append("Brought to you by MetricMiner2 (metricminer.org.br)");
		sb.append("\n");
		sb.append("# -------------------------------------------------- #");
		sb.append("\n");
		return sb.toString();
	}

	public MetricMiner2 notifyWhenDone(NotifierWhenDone notifierWhenDone) {
		this.notifierWhenDone = notifierWhenDone;
		return this;
	}


}
