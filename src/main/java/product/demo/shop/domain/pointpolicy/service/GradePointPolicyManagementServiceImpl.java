package product.demo.shop.domain.pointpolicy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import product.demo.shop.configuration.CacheConfiguration;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementRequest;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.entity.GradePolicyEntity;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyStatusType;
import product.demo.shop.domain.pointpolicy.exception.GradePointPolicyErrorCode;
import product.demo.shop.domain.pointpolicy.exception.GradePointPolicyException;
import product.demo.shop.domain.pointpolicy.repository.GradePolicyRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GradePointPolicyManagementServiceImpl implements GradePointPolicyManagementService {

    private final GradePolicyRepository gradePolicyRepository;

    @Override
    public Page<GradePolicyDto> findGradePolicies(
            GradePointPolicyManagementRequest request, Pageable pageable) {
        return this.gradePolicyRepository.findGradePolicies(
                GradePointPolicyManagementDto.fromRequest(request), pageable);
    }

    @Override
    public GradePointPolicyManagementDto findGradePolicyById(Long gradePolicyId) {
        return this.gradePolicyRepository.findById(gradePolicyId).orElseThrow().toManagementDto();
    }

    @Override
    @CacheEvict(
            cacheNames = {"GRADE_POLICIES"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'gradeName-'.concat(#gradePointPolicyManagementRequest.getGradeName().name())"
    )
    public GradePointPolicyManagementDto insertGradePointPolicy(
            GradePointPolicyManagementRequest gradePointPolicyManagementRequest) {
        return this.gradePolicyRepository
                .save(GradePolicyEntity.fromManagementRequestForCreate(gradePointPolicyManagementRequest))
                .toManagementDto();
    }

    @Override
    @CacheEvict(
            cacheNames = {"GRADE_POLICIES"},
            cacheManager = CacheConfiguration.LOCAL_CAFFEINE_CACHE_MANAGER,
            key = "'gradeName-'.concat(#gradePointPolicyManagementRequest.getGradeName().name())"
    )
    public GradePointPolicyManagementDto modifyGradePointPolicy(
            Long gradePolicyId,
            GradePointPolicyManagementRequest gradePointPolicyManagementRequest) {

        gradePointPolicyManagementRequest.setGradePolicyIdIfNull(gradePolicyId);

        return this.gradePolicyRepository
                .save(GradePolicyEntity.fromManagementRequestForUpdate(gradePointPolicyManagementRequest))
                .toManagementDto();
    }

    public void removeGradePointPolicy(Long gradePolicyId) {
        var deleteCandidate = this.gradePolicyRepository.findById(gradePolicyId);
        if( deleteCandidate.isPresent()
                && !deleteCandidate.get().getPolicyStatus().equals(GradePolicyStatusType.HOLD)){
            // 정책 상태가 HOLD가 아닌 경우 삭제를 막음.
            throw new GradePointPolicyException(GradePointPolicyErrorCode.DELETE_BEFORE_HOLD);
        }
        this.gradePolicyRepository.deleteById(gradePolicyId);
    }
}
