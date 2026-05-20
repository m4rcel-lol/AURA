package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.UsageStatsRepository
import dev.m5rcel.aura.domain.model.AppUsageStat
import javax.inject.Inject

class GetRecentAppsUseCase @Inject constructor(
    private val repository: UsageStatsRepository
) {
    operator fun invoke(): List<AppUsageStat> = repository.getRecentAppsUsage()
}
