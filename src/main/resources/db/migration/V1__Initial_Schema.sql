/** Indentify, Non-Identify Referenece 들은 추후 작업 진행 예정
   꼭 필요한 경우가 아니면 FK Reference를 DB에서 가져갈 필요가 없다고 생각...
  */
DROP TABLE IF EXISTS `tb_user_info`;

CREATE TABLE `tb_user_info` (
    `user_info_id`	bigint(20)	 NOT NULL AUTO_INCREMENT COMMENT 'user_info_id',
    `user_grade_id`	bigint(20)	NOT NULL COMMENT 'user_grade_id',
    `user_account_id`	varchar(150)	NOT NULL COMMENT '사용자 계정 ID(로그인 타입에 따라 가변적)',
    `sns_provider_type`	varchar(15)	NOT NULL COMMENT 'Social Login Provider', /** Not NULL이면 NO-SNS-USER 같은게 셋업이 되야 될듯 */
    `name`	varchar(100)	NOT NULL COMMENT '성명(AES 양방향 Hashed)',
    `email`	varchar(100)	NOT NULL COMMENT 'email(AES 양방향 Hashed)',
    `password`	varchar(150)	NULL COMMENT 'password(단방향 Hashed)',
    `phone`	varchar(25)	NULL COMMENT '전화번호',
    `address`	varchar(100)	NULL COMMENT '주소(AES 양방향 HASHED)',
    `user_status`	varchar(30)	NULL	COMMENT '회원상태코드',
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

-- 코드 발번 여부만 기록해서는 안될듯..
-- DDL 짜면서 봣는데 생각했던 필드가 없어서 의아햇는데...
-- 인증 성공 여부는 이 테이블이 가져가야 의미상 맞아 보입니다.
--  => 이력 관리 용으로 보겠다고 하면, 유저가 재시도를 하는 케이스가 있기 때문에 얘도 상태를 가지고 있어야 관리가 수월함
--  => User_INFO 테이블은 email인증 여부를 유저 상태 코드에 값을 추가해서 관리할 수 있음.
--  => 코드가 제대로 생성이 되도 재인증 요청이나 네트워크 문제로 메일이 전송이 안되고 유실될 가능성도 있기에 만료일시만으로는 관리가 힘듬.
CREATE TABLE `tb_email_verification` (
  `email_verification_code_id`	bigint(20)	NOT NULL AUTO_INCREMENT COMMENT 'email_verification_code_id',
  `user_id`	bigint(20)	NOT NULL COMMENT 'user_id',
  `verification_code`	varchar(36)	NOT NULL COMMENT '인증코드',
  `verification_code_status`	varchar(20)	NOT NULL COMMENT '인증 코드 상태',
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
   `policy_object`	varchar(20)	NULL	COMMENT '정책적용대상(포인트, 주문가격)',
   `policy_type`	varchar(12)	NULL	COMMENT '(증가/할인)',
   `unit_of_measure`	varchar(12)	NULL	COMMENT '단위',
   `policy_name`	varchar(300)	NULL	COMMENT '정책 명',
   `applied_value`	decimal(10,5)	NULL	COMMENT '정책 적용 값',
   `policy_status`	varchar(20)	NULL COMMENT '정책 상태',
   `created_at`	datetime	NULL COMMENT '생성일시',
   `created_by`	varchar(50)	NULL COMMENT '생성주체',
   `last_modified_at`	datetime	NULL COMMENT '최종수정일시',
   `last_modified_by`	varchar(50)	NULL COMMENT '최종수정주체',
   PRIMARY KEY (`grade_policy_id`)
) COMMENT='등급별 졍책 관리';

DROP TABLE IF EXISTS `tb_point_event`;

CREATE TABLE `tb_point_event` (
  `point_event_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
  `user_info_id`	bigint(20)	NOT NULL,
  `event_type`	varchar(15)	NOT NULL	COMMENT '(적립, 사용)',
  `point`	int	NOT NULL	COMMENT '금액',
  `code`	varchar(15)	NOT NULL	COMMENT '(REVIEW , ORDER, EVENT)',
  `code_id`	varchar(50)	NULL	COMMENT '추적용 테이블 아이디 값',
  `detail`	VARCHAR(255)	NULL	COMMENT '적립, 사용에 대한 상세 이유',
  `point_expired_date`	datetime	NOT NULL	COMMENT '만료일자',
  `created_at`	datetime	NULL	COMMENT '최초생성일시',
  `created_by`	varchar(50)	NULL	COMMENT '최초생성주체',
  `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
  `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
  PRIMARY KEY (`point_event_id`),
  KEY index_user_info_id(`user_info_id`),
  KEY index_code_code_id(`code`, `code_id`)
)COMMENT='포인트 이벤트';

DROP TABLE IF EXISTS `tb_point_detail`;

CREATE TABLE `tb_point_detail` (
   `point_detail_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
   `point_event_id`	bigint(20)	NOT NULL	COMMENT '원본 포인트 아이디',
   `event_type`	varchar(15)	NOT NULL	COMMENT '(적립, 사용)',
   `detail_accumulated_point_id`	bigint(20)	NOT NULL	COMMENT '상세 적립 아이디',
   `point`	int	NOT NULL	COMMENT '거래 포인트',
   `point_processed_date`	datetime	NOT NULL	COMMENT '처리 일자',
   `point_expired_date`	datetime	NULL	COMMENT '만료일자',
   `created_at`	datetime	NULL	COMMENT '최초생성일시',
   `created_by`	varchar(50)	NULL	COMMENT '최초변경주체',
   `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
   `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
   PRIMARY KEY (`point_detail_id`),
   KEY index_point_event_id(`point_event_id`),
   KEY index_d_a_point_id(`detail_accumulated_point_id`)
)COMMENT='포인트 이벤트 상세';

DROP TABLE IF EXISTS `tb_product_category`;

CREATE TABLE `tb_product_category` (
   `product_category_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
   `parent`	bigint(20)	NULL,
   `product_category_name`	varchar(250)	NOT NULL	COMMENT '상품 카테고리명칭',
   `created_at`	datetime	NULL	COMMENT '최초생성일시',
   `created_by`	varchar(50)	NULL	COMMENT '최초생성주체',
   `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
   `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
   PRIMARY KEY (`product_category_id`),
   KEY index_parent(`parent`)
)COMMENT='상품 카테고리';

ALTER TABLE `tb_product_category` ADD CONSTRAINT `FK_TB_PRODUCT_CATEGORY_TO_TB_PRODUCT_CATEGORY_1`
    FOREIGN KEY (`parent`)
    REFERENCES `tb_product_category` (`product_category_id`);


DROP TABLE IF EXISTS `tb_product_info`;

CREATE TABLE `tb_product_info` (
   `product_info_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
   `product_category_id`	bigint(20)	NOT NULL,
   `product_name`	varchar(45)	NOT NULL	COMMENT '상품명',
   `price`	decimal(10,5)	NOT NULL	COMMENT '가격',
   `tax_rate`	decimal(10,5)	NOT NULL	COMMENT '과세율',
   `product_status`	varchar(20)	NOT NULL	COMMENT '상품 상태',
   `minimum_purchase_age`	int	NOT NULL	COMMENT '최소 구매 연령',
   `stock_quantity`	int	NULL	COMMENT '재고 수량',
   `created_at`	datetime	NULL	COMMENT '최초생성일시',
   `created_by`	varchar(50)	NULL	COMMENT '최초생성주체',
   `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
   `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
   PRIMARY KEY (`product_info_id`),
   KEY index_p_category_id(`product_category_id`)
) COMMENT ='상품정보';

DROP TABLE IF EXISTS `tb_order_info`;

CREATE TABLE `tb_order_info` (
     `order_info_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
     `order_number`	varchar(30)	NOT NULL	COMMENT '유니크 주문 번호',
     `user_id`	bigint(20)	NOT NULL	COMMENT 'user_id',
     `address`	varchar(250)	NULL	COMMENT '주소',
     `phone_number`	varchar(20)	NULL	COMMENT '전화번호(sub)',
     `user_name`	varchar(150)	NULL	COMMENT '수취인',
     `order_price`	decimal(10,5)	NULL	COMMENT '주문금액',
     `created_at`	datetime	NULL	COMMENT '최초생성일시',
     `created_by`	varchar(50)	NULL	COMMENT '최초생성주체',
     `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
     `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
     PRIMARY KEY (`order_info_id`),
     UNIQUE KEY uk_order_number(`order_number`),
     KEY index_user_id(`user_id`)
)COMMENT ='주문 정보';

DROP TABLE IF EXISTS `tb_order_items`;

CREATE TABLE `tb_order_items` (
      `order_items_id`	bigint(20)	NOT NULL AUTO_INCREMENT,
      `product_info_id`	bigint(20)	NOT NULL,
      `order_info_id`	bigint(20)	NOT NULL,
      `purchase_quantity`	int(10)	NOT NULL	COMMENT '구매 수량',
      `created_at`	datetime	NULL	COMMENT '최초생성일시',
      `created_by`	varchar(50)	NULL	COMMENT '최초생성주체',
      `last_modified_at`	datetime	NULL	COMMENT '최종변경일시',
      `last_modified_by`	varchar(50)	NULL	COMMENT '최종변경주체',
      PRIMARY KEY (`order_items_id`),
      KEY index_p_info_id(`product_info_id`),
      KEY index_o_info_id(`order_info_id`)
)COMMENT ='주문 품목';


