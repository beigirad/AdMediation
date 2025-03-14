package ir.beigirad.admediation.admob

import com.google.android.gms.ads.rewarded.RewardedAd
import ir.beigirad.admediation.model.Ad

class AdmobAdWrapper(
    override val slug: String,
    val actualAd: RewardedAd,
) : Ad