package dev.duhwan.pay.persistence.repository

import dev.duhwan.pay.persistence.entity.AdsEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AdsRepository : CoroutineCrudRepository<AdsEntity, Long> {
    suspend fun findByAdTitle(adTitle: String): AdsEntity?

    @Query("SELECT * FROM ads WHERE ad_start_datetime <= NOW() AND ad_end_datetime >= NOW() AND ad_current_participation < ad_max_participation")
    suspend fun findAllEnableAds(): List<AdsEntity>

    @Modifying
    @Query(
        """
            UPDATE ads SET ad_current_participation = ad_current_participation + 1 
            WHERE ad_id = :adId AND ad_current_participation < ad_max_participation
        """
    )
    suspend fun updateByAdCurrentParticipationAdd(adId: Long): Long

    @Query(
        """
            UPDATE ads SET ad_current_participation = ad_current_participation - 1 
            WHERE ad_id = :adId AND ad_current_participation > 0
        """
    )
    suspend fun updateByAdCurrentParticipationMinus(adId: Long): Int
}