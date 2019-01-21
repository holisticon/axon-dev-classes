package io.holunda.academy.axon.dailymeal.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class DomainTest {

  @Test
  fun `amount tests`() {

    val amount1 = Amount(BigDecimal(10.0))
    val amount2 = Amount(BigDecimal(2.0))
    val amount3 = Amount(BigDecimal(2.0))
    val amount4 = Amount(BigDecimal(2.0), Currency("USD"))


    assertThat(amount1.isCapped(amount1)).isTrue()
    assertThat(amount1.isCapped(amount2)).isFalse()
    assertThat(amount2.isCapped(amount1)).isTrue()
    assertThat(amount2.isCapped(amount3)).isTrue()
    assertThat(amount2.isCapped(amount4)).isFalse()
  }
}
