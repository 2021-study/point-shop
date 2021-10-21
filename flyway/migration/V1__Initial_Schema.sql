DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'product_id',
  `product_meta_id` bigint NOT NULL COMMENT 'product_meta_id',
  `order_id` bigint DEFAULT NULL COMMENT '주문 ID',
  `product_detail_id` bigint DEFAULT NULL COMMENT '삭제예정-역정규화로 detail테이블 없앳스비다.',
  `made_date` date DEFAULT NULL COMMENT '제조일자',
  `distribute_expiry_datetime` datetime DEFAULT NULL COMMENT '유통기한',
  `is_sold` varchar(1) DEFAULT NULL COMMENT '삭제예정- 굳이 상품 판매여부를 기록할 필요가 없을듯..',
  `is_promotion` varchar(1) DEFAULT NULL COMMENT '삭제예정-프로모션기획이 없으므로 필요가 없읍니다.',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `last_modified_at` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `is_active_status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY index_product_meta_id (`product_meta_id`),
  KEY index_order_id(`order_id`)
) COMMENT='상품테이블(테스트용)' ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/** DB Character-Set은 저는 보통 utf-8 general_ci 를 사용하지만 WorkBench 디폴트 설정으로 적용한 부분입니다. */;