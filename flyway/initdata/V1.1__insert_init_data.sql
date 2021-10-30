-- INSERT SAMPLE USER

INSERT INTO shopdb.tb_user_info (user_info_id, user_grade_id, user_account_id, sns_provider_type, name, email, password, phone, address, email_verification_status, user_status_code, created_at, created_by, last_modified_at, last_modified_by)
VALUES (1, 1, 'sampleUser', 'sns_provider', 'kWt413risq9mPEDZ7du4pA==', 'hFCXfu+hVGDod/khzqzgabXj1AU32KYFmDcSmV5HvVA=', 'address', 'encrypted', '/B5JlWGHtGtiRtW3O8TN3w==', 'READY', '0001', '2021-10-24 15:58:20', 'point-shop', '2021-10-24 15:58:20', 'point-shop');

-- INSERT SAMPLE GRADE
INSERT INTO shopdb.tb_user_grade (user_grade_id, grade_name, created_at, created_by, last_modified_at, last_modified_by) VALUES (1, 'BRONZE', '2021-10-24 16:12:34', 'point-shop', '2021-10-24 16:12:34', 'point-shop');
INSERT INTO shopdb.tb_user_grade (user_grade_id, grade_name, created_at, created_by, last_modified_at, last_modified_by) VALUES (2, 'SILVER', '2021-10-24 16:12:34', 'point-shop', '2021-10-24 16:12:34', 'point-shop');
INSERT INTO shopdb.tb_user_grade (user_grade_id, grade_name, created_at, created_by, last_modified_at, last_modified_by) VALUES (3, 'GOLD', '2021-10-24 16:12:34', 'point-shop', '2021-10-24 16:12:34', 'point-shop');