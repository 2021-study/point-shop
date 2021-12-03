package product.demo.shop.domain.pointpolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementRequest;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;

public interface GradePointPolicyManagementService {
    Page<GradePolicyDto> findGradePolicies(GradePointPolicyManagementRequest request, Pageable pageable);
    GradePointPolicyManagementDto findGradePolicyById(Long gradePolicyId);
    GradePointPolicyManagementDto insertGradePointPolicy(GradePointPolicyManagementRequest gradePointPolicyManagementRequest);
    GradePointPolicyManagementDto modifyGradePointPolicy(Long gradePolicyId, GradePointPolicyManagementRequest gradePointPolicyManagementRequest);
    void removeGradePointPolicy(Long gradePolicyId);
}
