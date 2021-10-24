DROP TABLE IF EXISTS `tb_user_info`;

CREATE TABLE `tb_user_info` (
    `user_info_id`	bigint(20)	 NOT NULL AUTO_INCREMENT COMMENT 'user_info_id',
    `user_grade_id`	bigint(20)	NOT NULL COMMENT 'user_grade_id',
    `user_account_id`	varchar(150)	NOT NULL COMMENT '사용자 계정 ID(로그인 타입에 따라 가변적)',
    `sns_provider_type`	varchar(15)	NOT NULL COMMENT 'Social Login Provider',
    `name`	varchar(100)	NOT NULL COMMENT '성명(AES 양방향 Hashed)',
    `email`	varchar(100)	NOT NULL COMMENT 'email(AES 양방향 Hashed)',
    `password`	varchar(150)	NULL COMMENT 'password(단방향 Hashed)',
    `phone`	varchar(25)	NULL COMMENT '전화번호',
    `address`	varchar(100)	NULL COMMENT '주소(AES 양방향 HASHED)',
    `email_verification_status`	varchar(10)	NOT NULL COMMENT 'Email 인증 상태', /*인증 상태가 있을 거면.. Email Verification 테이블로 있어야 될거 같은데...*/
    `user_status_code`	varchar(4)	NULL	COMMENT '회원상태코드',
    `created_at`	datetime	NULL COMMENT '생성일시',
    `created_by`	varchar(50)	NULL COMMENT '생성주체',
    `last_modified_at`	datetime	NULL COMMENT '최종수정일시',
    `last_modified_by`	varchar(50)	NULL COMMENT '최종수정주체',
    PRIMARY KEY (`user_info_id`),
    UNIQUE KEY uk_user_account_id (user_account_id),
    KEY index_user_grade_id (`user_grade_id`),
    KEY index_email(`email`)
) COMMENT='사용자 정보';

DROP TABLE IF EXISTS `tb_user_grade`;

CREATE TABLE `tb_user_grade` (
     `user_grade_id`	bigint(20)	NOT NULL AUTO_INCREMENT COMMENT 'user_grade_id',
     `grade_name`	varchar(50)	NULL COMMENT '등급 명',
     `created_at`	datetime	NULL COMMENT '생성일시',
     `created_by`	varchar(50)	NULL COMMENT '생성주체',
     `last_modified_at`	datetime	NULL COMMENT '최종수정일시',
     `last_modified_by`	varchar(50)	NULL COMMENT '최종수정주체',
     PRIMARY KEY (`user_grade_id`)
)COMMENT='사용자 등급';

DROP TABLE IF EXISTS `tb_email_verification`;


CREATE TABLE `tb_email_verification` (
  `email_verification_code_id`	bigint(20)	NOT NULL AUTO_INCREMENT COMMENT 'email_verification_code_id',
  `user_id`	bigint(20)	NOT NULL COMMENT 'user_id',
  `verification_code`	varchar(36)	NOT NULL COMMENT '인증코드',
  `expired_date`	datetime	NULL COMMENT '코드만료일시',
  `created_at`	datetime	NULL COMMENT '생성일시',
  `created_by`	varchar(50)	NULL COMMENT '생성주체',
  `last_modified_at`	datetime	NULL COMMENT '최종수정일시',
  `last_modified_by`	varchar(50)	NULL COMMENT '최종수정주체',
  PRIMARY KEY (`email_verification_code_id`),
  KEY index_user_id (`user_id`)

) COMMENT='Email 인증 관리';

DROP TABLE IF EXISTS `tb_grade_policy`;

CREATE TABLE `tb_grade_policy` (
   `grade_policy_id`	bigint(20)	NOT NULL AUTO_INCREMENT	COMMENT '정책 Id',
   `user_grade_id`	bigint(20)	NOT NULL COMMENT 'user_grade_id',
   `policy_object`	varchar(1)	NULL	COMMENT '정책적용대상(포인트, 주문가격)',
   `policy_type`	varchar(4)	NULL	COMMENT '(증가/할인)',
   `unit_of_measure`	varchar(12)	NULL	COMMENT '단위',
   `policy_name`	varchar(300)	NULL	COMMENT '정책 명',
   `applied_value`	decimal(10,5)	NULL	COMMENT '정책 적용 값',
   `policy_status`	varchar(4)	NULL COMMENT '정책 상태',
   `created_at`	datetime	NULL COMMENT '생성일시',
   `created_by`	varchar(50)	NULL COMMENT '생성주체',
   `last_modified_at`	datetime	NULL COMMENT '최종수정일시',
   `last_modified_by`	varchar(50)	NULL COMMENT '최종수정주체',
   PRIMARY KEY (`grade_policy_id`)
) COMMENT='등급별 졍책 관리';




