package product.demo.shop.domain.pointpolicy.repository;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import product.demo.shop.domain.grade.entity.enums.GradeName;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.entity.enums.GradePolicyObject;

import java.util.List;

public interface CustomGradePolicyRepository {
    PageImpl<GradePolicyDto> findGradePolicies(GradePointPolicyManagementDto dto, Pageable pageable);
    List<GradePolicyDto> findGradePoliciesByGradeName(GradeName gradeName, GradePolicyObject objectType);
}
