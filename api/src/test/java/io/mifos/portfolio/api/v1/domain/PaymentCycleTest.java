/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.portfolio.api.v1.domain;

import io.mifos.core.test.domain.ValidationTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Myrle Krantz
 */
@RunWith(Parameterized.class)
public class PaymentCycleTest {
  @Parameterized.Parameters
  public static Collection testCases() {
    final Collection<ValidationTestCase> ret = new ArrayList<>();

    ret.add(new ValidationTestCase<PaymentCycle>("valid"));
    ret.add(new ValidationTestCase<PaymentCycle>("nullAlignmentDay")
            .adjustment(x -> x.setAlignmentDay(null))
            .valid(true));
    ret.add(new ValidationTestCase<PaymentCycle>("invalidAlignmentWeek")
            .adjustment(x -> x.setAlignmentWeek(3))
            .valid(false));
    ret.add(new ValidationTestCase<PaymentCycle>("nullTemporalUnit")
            .adjustment(x -> x.setTemporalUnit(null))
            .valid(false));
    ret.add(new ValidationTestCase<PaymentCycle>("lastDayOfMonth")
            .adjustment(x -> x.setAlignmentWeek(-1))
            .valid(true));
    return ret;
  }
  private final ValidationTestCase<PaymentCycle> testCase;

  public PaymentCycleTest(final ValidationTestCase<PaymentCycle> testCase)
  {
    this.testCase = testCase;
  }

  @Test()
  public void test(){
    final PaymentCycle testSubject = createPaymentCycle();
    testCase.applyAdjustment(testSubject);
    Assert.assertTrue(testCase.toString(), testCase.check(testSubject));
  }

  private PaymentCycle createPaymentCycle() {
    final PaymentCycle ret = new PaymentCycle();

    ret.setPeriod(12);
    ret.setTemporalUnit(ChronoUnit.MONTHS);
    ret.setAlignmentDay(1);
    ret.setAlignmentWeek(null);
    ret.setAlignmentMonth(null);

    return ret;
  }
}
