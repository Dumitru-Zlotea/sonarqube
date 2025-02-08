package org.sonar.scanner.mediumtest.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import org.sonar.scanner.report.StringManipulation;

class StringManipulationMediumIT {
  public static final String STRING_A = "Hello ";
  public static final String STRING_B = "there";

  private StringManipulation sm;

  @BeforeEach
  void setUp() {
	  sm = new StringManipulation(STRING_A, STRING_B);
  }

  @Test
  void testGetter() {
	  var a = sm.getStringA();
	  var b = sm.getStringB();
	  assertThat(a).isEqualTo(STRING_A);
	  assertThat(b).isEqualTo(STRING_B);
  }

  @Test
  void testSetter() {
	  var newA = "newA";
	  var newB = "newB";
	  sm.setStringA(newA);
	  sm.setStringB(newB);
	  assertThat(sm.getStringA()).isEqualTo(newA);
	  assertThat(sm.getStringB()).isEqualTo(newB);
  }

  @Test
  void testConcatenate() {
	  var concat = sm.concatenate();
	  assertThat(concat).isEqualTo(STRING_A + STRING_B);
  }

  @Test
  void testIsEqualFalse() {
	  assertThat(sm.isEqual()).isFalse();
  }

  @Test
  void testIsEqualTrue() {
	  var equalSm = new StringManipulation(STRING_A, STRING_A);
	  assertThat(equalSm.isEqual()).isTrue();
  }

  @Test
  void testContainsFalse() {
	  assertThat(sm.contains()).isFalse();
  }

  @Test
  void testContainsTrue() {
	  var containsSm = new StringManipulation(STRING_A, "Hello");
	  assertThat(containsSm.contains()).isTrue();
  }
}
