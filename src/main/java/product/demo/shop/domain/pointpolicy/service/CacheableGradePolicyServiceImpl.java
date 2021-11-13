package product.demo.shop.domain.pointpolicy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import product.demo.shop.configuration.CacheConfiguration;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheableGradePolicyServiceImpl {
    private final GradePolicyRepository gradePolicyRepository;

    @Cacheable(
            cacheNames = {"GRADE_POLICIES"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'gradeName-'.concat(#gradeName.name()).concat(#objectType.name())")
    public List<GradePolicyDto> findByGradePolicyIdFromCache(
            GradeName gradeName, GradePolicyObject objectType) {
        return this.gradePolicyRepository.findGradePoliciesByGradeName(gradeName, objectType);
    }
}
