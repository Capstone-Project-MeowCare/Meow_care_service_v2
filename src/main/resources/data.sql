-- Insert data into the roles table with hardcoded UUIDs
INSERT INTO roles (id, role_name)
VALUES ('8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8', 'ADMIN'),
       ('9bcd7e45-1234-4f56-a8b9-2345c789d012', 'USER'),
       ('ab12cd34-5678-4e9f-8901-23456789abcd', 'MANAGER'),
       ('bc34de56-7890-4a12-3456-789abcdef012', 'SITTER'),
       ('cd45ef67-9012-4b34-5678-9abcdef01234', 'OWNER');

-- Insert data into the permissions table with hardcoded UUIDs
INSERT INTO permissions (id, permission_name)
VALUES ('a1b2c3d4-5678-4e9f-0123-456789abcdef', 'READ_PRIVILEGES'),
       ('f1e2d3c4-6789-4a12-3456-789abcde0123', 'WRITE_PRIVILEGES');

-- Insert data into the roles_permissions table to establish relationships
INSERT INTO roles_permissions (role_id, permission_id)
VALUES ('8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8', 'a1b2c3d4-5678-4e9f-0123-456789abcdef'), -- ADMIN with READ_PRIVILEGES
       ('8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8', 'f1e2d3c4-6789-4a12-3456-789abcde0123'), -- ADMIN with WRITE_PRIVILEGES
       ('9bcd7e45-1234-4f56-a8b9-2345c789d012', 'a1b2c3d4-5678-4e9f-0123-456789abcdef'), -- USER with READ_PRIVILEGES
       ('ab12cd34-5678-4e9f-8901-23456789abcd', 'a1b2c3d4-5678-4e9f-0123-456789abcdef'), -- MANAGER with READ_PRIVILEGES
       ('bc34de56-7890-4a12-3456-789abcdef012', 'a1b2c3d4-5678-4e9f-0123-456789abcdef');
-- SITTER with READ_PRIVILEGES

-- Insert data into the users table with hardcoded UUID
INSERT INTO users (id, password, email, full_name, avatar, phone_number, dob, gender, address, registration_date,
                   status)
VALUES ('d8a9f7e6-1234-4c56-89a7-23456789abcd', '$2a$10$Sant2RPOKqHbVxgRWA3xQulUz1q1jk/4qa2BTaH.kSxbZJseNegJC',
        'admin@example.com', 'Admin User', NULL, NULL, NULL, NULL, NULL, NOW(), 1),
       ('e7b8f9a6-5678-4c56-89a7-23456789abcd', '$2a$10$Sant2RPOKqHbVxgRWA3xQulUz1q1jk/4qa2BTaH.kSxbZJseNegJC',
        'user@example.com', 'Normal User', NULL, NULL, NULL, NULL, NULL, NOW(), 1),
       ('f8c9d0e7-6789-4c56-89a7-23456789abcd', '$2a$10$Sant2RPOKqHbVxgRWA3xQulUz1q1jk/4qa2BTaH.kSxbZJseNegJC',
        'sitter@example.com', 'Sitter User', NULL, NULL, NULL, NULL, NULL, NOW(), 1);

-- Insert data into the wallets table with hardcoded UUIDs
INSERT INTO wallets (id, user_id, balance, hold_balance)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'd8a9f7e6-1234-4c56-89a7-23456789abcd', 10000000.00, 0.00),
       ('223e4567-e89b-12d3-a456-426614174001', 'e7b8f9a6-5678-4c56-89a7-23456789abcd', 500000.00, 0.00),
       ('323e4567-e89b-12d3-a456-426614174002', 'f8c9d0e7-6789-4c56-89a7-23456789abcd', 300000.00, 0.00);

-- Associate the admin user with the ADMIN role
INSERT INTO users_roles (user_id, roles_id)
VALUES ('d8a9f7e6-1234-4c56-89a7-23456789abcd', '8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8'),
       ('e7b8f9a6-5678-4c56-89a7-23456789abcd', '9bcd7e45-1234-4f56-a8b9-2345c789d012'),
       ('f8c9d0e7-6789-4c56-89a7-23456789abcd', 'bc34de56-7890-4a12-3456-789abcdef012');


INSERT INTO public.quizzes (is_active, id, title, description)
VALUES (true, 'afe23452-61a0-4150-831a-8a2e1ee20e58', 'Pet Care Basics',
        'A quiz to test your knowledge on basic pet care.');

INSERT INTO public.quiz_questions (id, quiz_id, question_type, question_text)
VALUES ('78a580c6-b295-4e2d-a2c3-a9bd251d388c', 'afe23452-61a0-4150-831a-8a2e1ee20e58', 'multiple-choice',
        'What is the best way to introduce a new pet to your home?');
INSERT INTO public.quiz_questions (id, quiz_id, question_type, question_text)
VALUES ('072ed75e-eae9-4815-925d-d4bb685b50b0', 'afe23452-61a0-4150-831a-8a2e1ee20e58', 'multiple-choice',
        'How often should you take your pet to the vet for a check-up?');

INSERT INTO public.quiz_answers (is_correct, id, question_id, answer_text)
VALUES (true, '3266b4d2-6d4c-45b2-8b25-0a8cfa24378e', '78a580c6-b295-4e2d-a2c3-a9bd251d388c',
        'Gradually introduce them to each room.');
INSERT INTO public.quiz_answers (is_correct, id, question_id, answer_text)
VALUES (false, 'c1cbbb6f-45ac-46d3-b72d-ffc32b372bc3', '78a580c6-b295-4e2d-a2c3-a9bd251d388c',
        'Let them roam freely immediately.');
INSERT INTO public.quiz_answers (is_correct, id, question_id, answer_text)
VALUES (true, 'cdc47b53-64fe-4914-b06e-b5c6c090778f', '072ed75e-eae9-4815-925d-d4bb685b50b0', 'Once a year.');
INSERT INTO public.quiz_answers (is_correct, id, question_id, answer_text)
VALUES (false, '5c23072f-d380-4a55-b838-960d229523f1', '072ed75e-eae9-4815-925d-d4bb685b50b0',
        'Only when they are sick.');

INSERT INTO public.sitter_profile (maximum_quantity, rating, status, created_at, updated_at, id, user_id, skill,
                                   environment, location, bio, experience)
VALUES (3, 4.00, 1, null, null, '2c6de1c2-c2c6-48ba-b857-230dde885bc0', 'f8c9d0e7-6789-4c56-89a7-23456789abcd',
        'Chải lông mèo, quản lý thuốc, huấn luyện hành vi',
        'Nhà rộng rãi với sân sau an toàn và khu vui chơi dành riêng cho mèo.', 'Ho Chi Minh City, Vietnam',
        'Người chăm sóc mèo đam mê với hơn 5 năm kinh nghiệm chăm sóc mèo.',
        'Làm việc tại trại động vật địa phương, có nhiều kinh nghiệm chải lông mèo và quản lý thuốc.');

INSERT INTO public.pet_profiles (age, status, weight, created_at, updated_at, gender, id, user_id, breed, pet_name,
                                 profile_picture, description)
VALUES (3, 1, 25.50, null, null, 'Female', '61c9a75d-f294-43e2-9841-0c69105d742b',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Golden Retriever', 'Bella', 'https://example.com/images/bella.jpg',
        'Friendly and playful, loves outdoor walks and treats.'),
       (2, 1, 10.00, null, null, 'Male', '72c9b85d-f294-43e2-9841-0c69105d742c',
        'e7b8f9a6-5678-4c56-89a7-23456789abcd', 'Siamese', 'Whiskers', 'https://example.com/images/whiskers.jpg',
        'Curious and affectionate, enjoys playing with toys.');

INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('e7a1a73e-1c4b-4f6d-a9a6-6789a2c9f9a1', 'Đã triệt sản', 'Thú cưng đã được triệt sản.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('4f1d6f78-bd6e-4a58-bc6b-3e46e4e1d5b2', 'Đã tiêm phòng', 'Thú cưng đã được tiêm phòng đầy đủ.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('dc9f7a2e-0f5e-4bfc-9d6a-b1a6d5e2a3f7', 'Thuần chủng', 'Thú cưng là giống thuần chủng.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('b3c9a4f1-d5f7-4a6c-b7e5-f9c8a7d2b5a4', 'Thân thiện với chó', 'Thú cưng thân thiện với chó.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('1a9b5c7d-e3f6-4d5a-9c6f-d2a3b7e8f5d4', 'Thân thiện với mèo', 'Thú cưng thân thiện với mèo.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('5e7d4f6a-a3c9-4b1d-9d2e-f6b7a4c5e1d3', 'Thân thiện với trẻ em', 'Thú cưng thân thiện với trẻ em.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('9b7e6a5d-b2f1-4c6d-a3e7-c5d4f9b8a6d7', 'Đã gắn vi mạch', 'Thú cưng đã được gắn vi mạch để định danh.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('a6f5d3e4-9c7d-4b2e-a1d9-e6f7a5c3d8b2', 'Có lo âu khi ở một mình', 'Thú cưng cảm thấy lo âu khi ở một mình.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('d4e5c6b7-a2f3-4b1d-9a6f-f5d8b7a9c2e3', 'Có xu hướng hung dữ', 'Thú cưng có xu hướng hung dữ.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('c9b7e5a6-d4f8-4c3d-9b1a-f7e6d2a3f5d8', 'Có vấn đề về vệ sinh', 'Thú cưng gặp khó khăn trong việc vệ sinh.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('f6a9c5b8-e7d3-4b2e-a1f4-c9d7b6f5a2e3', 'Có bệnh truyền nhiễm', 'Thú cưng có bệnh truyền nhiễm.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('a7b6c5d8-e9f3-4a1d-b2e5-d9c6f7a3e5f2', 'Có hồ sơ tiêm phòng đầy đủ', 'Thú cưng có hồ sơ tiêm phòng đầy đủ.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('b1a5f3c7-d9e4-4d6a-b7c2-e6f4a8d3c5f9', 'Có hồ sơ y tế đầy đủ', 'Thú cưng có hồ sơ y tế đầy đủ.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('c6e9b7a8-f4d3-4b1d-9c2a-f5e3d7a9b6f8', 'Không có ve', 'Thú cưng không có ve.');
INSERT INTO medical_conditions (id, condition_name, description)
VALUES ('e3a5d8c9-f7b2-4a1e-b6c5-d9f4e8a7c2b1', 'Cho phép vệ sinh bộ phận riêng tư',
        'Thú cưng cho phép vệ sinh bộ phận riêng tư.');


insert into public.pet_profiles_medical_conditions (pet_profile_id, medical_condition_id)
values ('61c9a75d-f294-43e2-9841-0c69105d742b', 'e7a1a73e-1c4b-4f6d-a9a6-6789a2c9f9a1');
insert into public.pet_profiles_medical_conditions (pet_profile_id, medical_condition_id)
values ('61c9a75d-f294-43e2-9841-0c69105d742b', '4f1d6f78-bd6e-4a58-bc6b-3e46e4e1d5b2');
insert into public.pet_profiles_medical_conditions (pet_profile_id, medical_condition_id)
values ('61c9a75d-f294-43e2-9841-0c69105d742b', 'dc9f7a2e-0f5e-4bfc-9d6a-b1a6d5e2a3f7');
insert into public.pet_profiles_medical_conditions (pet_profile_id, medical_condition_id)
values ('61c9a75d-f294-43e2-9841-0c69105d742b', 'b3c9a4f1-d5f7-4a6c-b7e5-f9c8a7d2b5a4');
insert into public.pet_profiles_medical_conditions (pet_profile_id, medical_condition_id)
values ('61c9a75d-f294-43e2-9841-0c69105d742b', '1a9b5c7d-e3f6-4d5a-9c6f-d2a3b7e8f5d4');


INSERT INTO public.config_services (
    id, service_type, name, action_description, ceil_price, floor_price, created_at, updated_at
) VALUES
      ('f8c9d0e7-6789-4c56-89a7-23456789abcd', 'MAIN_SERVICE', 'Dịch Vụ Trông Thú Cưng Tại Nhà Của Bạn',
       'Cung cấp dịch vụ trông thú cưng tại nhà của bạn, đảm bảo sự an toàn và chăm sóc chu đáo cho thú cưng',
       120000, 80000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z'),
      ('fcd44bcb-c36b-4dd7-b9ee-4625721d28e3', 'MAIN_SERVICE', 'Dịch Vụ Trông Thú Cưng Tại Các Cơ Sở Chăm Sóc',
       'Cung cấp dịch vụ trông thú cưng tại các cơ sở chăm sóc, với chất lượng cao và đội ngũ chuyên nghiệp',
       150000, 100000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z'),
      ('1e888181-c67e-4b14-a166-066a4610cd1c', 'ADDITION_SERVICE', 'Mát-xa Thư Giãn',
       'Cung cấp mát-xa thư giãn cho thú cưng', 50000, 40000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z'),
      ('52adca14-a6a2-4a49-87eb-4ada031d6fbe', 'ADDITION_SERVICE', 'Dịch Vụ Cắt Móng',
       'Cắt móng cho thú cưng một cách an toàn', 25000, 20000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z'),
      ('c7a513aa-251b-4770-8491-fc9fe848bd35', 'ADDITION_SERVICE', 'Dịch Vụ Vệ Sinh Tai',
       'Vệ sinh tai và làm đẹp cho thú cưng', 35000, 30000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z'),
      ('d076038c-014e-47e7-898a-5c28da3499f5', 'ADDITION_SERVICE', 'Dịch Vụ Tắm Cho Mèo',
       'Cung cấp dịch vụ tắm cho mèo', 60000, 50000, '2024-01-01T12:00:00Z', '2024-01-02T12:00:00Z');

INSERT INTO services (
    id, duration, price, start_time, status, sitter_id, menu_id, action_description, name, end_time, service_type
) VALUES
      ('085be7d5-51d2-475d-b2d3-e6127299c05a', 120, 40000, 9, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Đảm bảo thời gian yên tĩnh cho thú cưng', 'Thời Gian Yên Tĩnh', 11, 'CHILD_SERVICE'),
      ('08ddd53b-56a6-47ea-a097-05d667838e6f', 60, 55000, 12, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp dịch vụ cho thú cưng ăn trưa', 'Dịch Vụ Cho Ăn Trưa', 13, 'CHILD_SERVICE'),
      ('1e888181-c67e-4b14-a166-066a4610cd1c', 45, 40000, 18, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp mát-xa thư giãn cho thú cưng', 'Mát-xa Thư Giãn', 20, 'ADDITION_SERVICE'),
      ('3880b19f-b534-45d5-8405-44a6413e694d', NULL, 100000, 8, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp dịch vụ trông thú cưng tại nhà của bạn', 'Dịch Vụ Trông Thú Cưng Tại Nhà Của Bạn', 20, 'MAIN_SERVICE'),
      ('52adca14-a6a2-4a49-87eb-4ada031d6fbe', 20, 20000, 10, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cắt móng cho thú cưng một cách an toàn', 'Dịch Vụ Cắt Móng', 12, 'ADDITION_SERVICE'),
      ('98fe8b8a-1c5e-4ae1-b296-5cd1c1f8e565', 120, 45000, 13, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Đảm bảo thời gian nghỉ ngơi cho thú cưng', 'Thời Gian Nghỉ Ngơi', 15, 'CHILD_SERVICE'),
      ('c7a513aa-251b-4770-8491-fc9fe848bd35', 15, 30000, 6, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Vệ sinh tai và làm đẹp cho thú cưng', 'Dịch Vụ Vệ Sinh Tai', 7, 'ADDITION_SERVICE'),
      ('d076038c-014e-47e7-898a-5c28da3499f5', 30, 50000, 14, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp dịch vụ tắm cho mèo', 'Dịch Vụ Tắm Cho Mèo', 16, 'ADDITION_SERVICE'),
      ('d38be8d8-acc1-45c1-918e-60899e52745d', 60, 50000, 6, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cho thú cưng ăn sáng và dọn khay vệ sinh', 'Dọn Dẹp Sáng Và Bữa Sáng', 7, 'CHILD_SERVICE'),
      ('f31b2fdc-a3c9-40c8-8554-eabd9c6572d2', 60, 35000, 8, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp dịch vụ làm đẹp cho mèo', 'Dịch Vụ Làm Đẹp Cho Mèo', 10, 'CHILD_SERVICE'),
      ('fcd44bcb-c36b-4dd7-b9ee-4625721d28e3', NULL, 150000, 10, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Cung cấp dịch vụ trông thú cưng tại các cơ sở chăm sóc', 'Dịch Vụ Trông Thú Cưng Tại Các Cơ Sở Chăm Sóc', 18, 'MAIN_SERVICE'),
      ('fde28c37-bb92-4cd2-beac-3c550beab37b', 120, 60000, 7, 0, 'f8c9d0e7-6789-4c56-89a7-23456789abcd', NULL,
       'Theo dõi sức khỏe và quan sát hành vi', 'Dịch Vụ Theo Dõi Sức Khỏe', 9, 'CHILD_SERVICE');
