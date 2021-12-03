package product.demo.shop.domain.pointpolicy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementDto;
import product.demo.shop.domain.pointpolicy.dto.GradePointPolicyManagementRequest;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyDto;
import product.demo.shop.domain.pointpolicy.service.GradePointPolicyManagementService;

import javax.validation.Valid;


import static product.demo.shop.domain.pointpolicy.controller.PointPolicyManagementController.GRADE_POLICY_MANAGEMENT_API_PATH;

/* TODO : ADMIN ROLE에 대한 정의가 없어서 사실상 컨트롤러를 만들어놔도 호출할 사람이 없음.
 * 컨트롤러 제작 의도는 기획자(어드민)이 신규 정책 등록, 기존 정책 수정/삭제를 위한 API
 */
@RestController
@RequestMapping(path = GRADE_POLICY_MANAGEMENT_API_PATH)
@RequiredArgsConstructor
public class PointPolicyManagementController {
    public static final String GRADE_POLICY_MANAGEMENT_API_PATH = "/api/v1/policy/grade";

    private final GradePointPolicyManagementService gradePointPolicyManagementService;

    @PostMapping("/")
    public GradePointPolicyManagementDto registerGradePolicy(
            @RequestBody @Valid GradePointPolicyManagementRequest request) {
        return this.gradePointPolicyManagementService.insertGradePointPolicy(request);
    }

    @PostMapping("/search")
    public Page<GradePolicyDto> findGradePolicies(
            @RequestBody GradePointPolicyManagementRequest request, final Pageable pageable) {
        return this.gradePointPolicyManagementService.findGradePolicies(request, pageable);
    }

    @DeleteMapping("/{gradePolicyId}")
    public void deleteGradePolicies(@PathVariable("gradePolicyId") Long gradePolicyId){
      this.gradePointPolicyManagementService.removeGradePointPolicy(gradePolicyId);
    }

    @PutMapping("/{gradePolicyId}")
    public GradePointPolicyManagementDto updateGradePolicy(
            @PathVariable("gradePolicyId") Long gradePolicyId,
            @RequestBody @Valid GradePointPolicyManagementRequest request
    ) {

        return this.gradePointPolicyManagementService.modifyGradePointPolicy(gradePolicyId, request);
    }
}
