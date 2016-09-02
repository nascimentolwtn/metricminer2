package br.com.metricminer2.notifiers;

public interface NotifierWhenDone {

	void notifyAfterMining(String message) throws NotifyMechanismException;

}
