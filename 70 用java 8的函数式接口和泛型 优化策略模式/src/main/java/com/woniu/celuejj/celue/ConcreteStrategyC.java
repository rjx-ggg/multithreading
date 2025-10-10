package com.woniu.celuejj.celue;


public class ConcreteStrategyC implements Strategy {

 @Override
 public String strategy() {
  return StrategySelector.strategyC.getStrategy();
 }

 @Override
 public void algorithm() {
  System.out.println("process with strategyC...");
 }
}

