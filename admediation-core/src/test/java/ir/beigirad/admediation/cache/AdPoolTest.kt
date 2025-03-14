package ir.beigirad.admediation.cache

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockkObject
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Ad
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get

class AdPoolTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(cacheModule)
    }

    @Before
    fun setup() {
        mockkObject(Logger)
        every { Logger.i(any()) } returns Unit
        every { Logger.d(any()) } returns Unit
    }

    @Test
    fun `soon expiring ad must be return when is there multiple ad`() {
        val pool = get<AdPool>()

        val second = TestAd()
        pool.putNewAd(second, 3000)
        val first = TestAd()
        pool.putNewAd(first, 1000)

        val actual = pool.popAd()
        actual shouldBe first
        actual shouldNotBe second
    }

    @Test
    fun `expired ads must be filtered when trying to fetch an ad`() {
        val pool = get<AdPool>()

        val validAd = TestAd()
        pool.putNewAd(validAd, 200)
        val expiredAd = TestAd()
        pool.putNewAd(expiredAd, -200)

        val actual = pool.popAd()
        actual shouldBe validAd
        actual shouldNotBe expiredAd
        pool.size() shouldBe 0
    }

    private class TestAd(override val slug: String = "test") : Ad
}