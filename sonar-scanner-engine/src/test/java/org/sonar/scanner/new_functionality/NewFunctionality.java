package org.sonar.scanner.mediumtest.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import org.sonar.scanner.report.StringManipulation;

class StringManipulationMediumIT {
    public static final String stringA = "Hello ";
    public static final String stringB = "there";

    private StringManipulation sm;

    @BeforeEach
    void setUp() {
	sm = new StringManipulation(stringA, stringB);
    }

    @Test
    void test_getter() {
	var a = sm.getStringA();
	var b = sm.getStringB();
	assertThat(a).isEqualTo(stringA);
	assertThat(b).isEqualTo(stringB);
    }

    @Test
    void test_setter() {
	var newA = "newA";
	var newB = "newB";
	sm.setStringA(newA);
	sm.setStringB(newB);
	assertThat(sm.getStringA()).isEqualTo(newA);
	assertThat(sm.getStringB()).isEqualTo(newB);
    }

    @Test
    void test_concatenate() {
	var concat = sm.concatenate();
	assertThat(concat).isEqualTo(stringA + stringB);
    }

    @Test
    void test_is_equal_false() {
	assertThat(sm.isEqual()).isFalse();
    }

    @Test
    void test_is_equal_true() {
	var equal_sm = new StringManipulation(stringA, stringA);
	assertThat(equal_sm.isEqual()).isTrue();
    }

    @Test
    void test_contains_false() {
	assertThat(sm.contains()).isFalse();
    }

    @Test
    void test_contains_true() {
	var contains_sm = new StringManipulation(stringA, "Hello");
	assertThat(contains_sm.contains()).isTrue();
    }
}
