package org.sonar.scanner.report;

public class StringManipulation {
  private String stringA;
  private String stringB;

  public StringManipulation(String a, String b) {
  	this.stringA = a;
	  this.stringB = b;
  }

  public void setStringA(String newA) {
	   this.stringA = newA;
  }

  public void setStringB(String newB) {
	   this.stringB = newB;
  }

  public String getStringA() {
	   return this.stringA;
  }

  public String getStringB() {
	   return this.stringB;
  }

  public String concatenate() {
	   return this.stringA + this.stringB;
  }

  public boolean isEqual() {
	   return this.stringA.equals(this.stringB);
  }

  public boolean contains() {
	   return this.stringA.contains(this.stringB);
  }
}
