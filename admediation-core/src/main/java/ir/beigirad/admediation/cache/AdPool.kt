package ir.beigirad.admediation.cache

import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Ad
import org.jetbrains.annotations.TestOnly

class AdPool {
    private val cache = sortedSetOf<Entry>()

    fun putNewAd(ad: Ad, liveDuration: Long = defaultLiveDuration) {
        cache.add(Entry.of(ad, liveDuration))
        Logger.i("an ad has cached. $ad")
    }

    fun getAnAd(): Ad? {
        val candid = cache.firstOrNull() ?: return null
        if (candid.expireTime < System.currentTimeMillis()) {
            cache.remove(candid)
            return getAnAd()
        }

        return candid.ad
    }

    @TestOnly
    fun size(): Int = cache.size

    companion object {
        val defaultLiveDuration = 60 * 60 * 1000L
    }

    class Entry private constructor(
        val expireTime: Long,
        val ad: Ad,
    ) : Comparable<Entry> {
        // to be able to use sorted* data structures and keep sort of ads by their expiration-time
        override fun compareTo(other: Entry): Int =
            expireTime.compareTo(other.expireTime)

        companion object {
            fun of(
                ad: Ad,
                liveDuration: Long,
            ): Entry = Entry(System.currentTimeMillis() + liveDuration, ad)
        }
    }
}