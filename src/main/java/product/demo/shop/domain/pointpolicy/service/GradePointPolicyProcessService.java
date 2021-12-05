package product.demo.shop.domain.pointpolicy.service;

import product.demo.shop.domain.pointpolicy.dto.GradePolicyAppliedResultDto;
import product.demo.shop.domain.pointpolicy.dto.GradePolicyInputDto;

public interface GradePointPolicyProcessService {
    // GradePolicyInputDto 만 채워 넣으면 사용하고자 하는 로직에서
    // 등급에 대한 정책과 상세 내용을 몰라도 그에 따른 계산 결과를 받아 내기 위한 목적
    // 다른 비즈니스 로직에서 정책에 따른 차등 포인트 지급/할인에 대한 의존도를 낮춤.
    GradePolicyAppliedResultDto appliedGradePolicy(GradePolicyInputDto inputDto);
}
