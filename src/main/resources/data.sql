INSERT INTO roles (id, role_name)
VALUES ('8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8', 'ADMIN'),
       ('9bcd7e45-1234-4f56-a8b9-2345c789d012', 'USER'),
       ('ab12cd34-5678-4e9f-8901-23456789abcd', 'MANAGER'),
       ('bc34de56-7890-4a12-3456-789abcdef012', 'SITTER');

-- Insert data into the roles table with hardcoded UUIDs
INSERT INTO roles (id, role_name)
VALUES ('8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8', 'ADMIN'),
       ('9bcd7e45-1234-4f56-a8b9-2345c789d012', 'USER'),
       ('ab12cd34-5678-4e9f-8901-23456789abcd', 'MANAGER'),
       ('bc34de56-7890-4a12-3456-789abcdef012', 'SITTER');

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
        'admin@example.com', 'Admin User', NULL, NULL, NULL, NULL, NULL, NOW(), 1);

-- Associate the admin user with the ADMIN role
INSERT INTO users_roles (user_id, roles_id)
VALUES ('d8a9f7e6-1234-4c56-89a7-23456789abcd', '8e9a6b12-3456-4aaf-b7a5-8799a0f4f2d8');


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
VALUES (3, 4.00, 1, null, null, '2c6de1c2-c2c6-48ba-b857-230dde885bc0', 'd8a9f7e6-1234-4c56-89a7-23456789abcd',
        'Pet grooming, medication administration, behavioral training',
        'Spacious home with a secure backyard and a dedicated pet play area.', 'New York, NY',
        'Passionate pet sitter with over 5 years of experience caring for cats and dogs.',
        'Worked at local animal shelter, extensive experience with pet grooming and administering medications.');

INSERT INTO public.pet_profiles (age, status, vaccination_status, weight, created_at, updated_at, gender, id, user_id,
                                 breed, microchip_number, pet_name, species, profile_picture, description,
                                 medical_conditions, special_needs, vaccination_info)
VALUES (3, 1, true, 25.50, null, null, 'Female', '61c9a75d-f294-43e2-9841-0c69105d742b',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Golden Retriever', 'A123456789', 'Bella', 'Dog',
        'https://example.com/images/bella.jpg', 'Friendly and playful, loves outdoor walks and treats.', 'Diabetes',
        'Requires daily insulin injections', 'Up-to-date with all vaccinations as of July 2023');


INSERT INTO service_types (id, type, description, created_at, updated_at)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Feeding', 'Providing food for pets as per the owner’s instructions',
        NOW(), NOW()),
       ('550e8400-e29b-41d4-a716-446655440002', 'Grooming', 'Basic grooming services such as brushing and bathing',
        NOW(), NOW()),
       ('550e8400-e29b-41d4-a716-446655440003', 'Playtime', 'Engaging in playful activities with the pet', NOW(),
        NOW()),
       ('550e8400-e29b-41d4-a716-446655440004', 'Health Check', 'Monitoring health and physical condition', NOW(),
        NOW()),
       ('550e8400-e29b-41d4-a716-446655440005', 'Training', 'Providing basic training exercises for pets', NOW(),
        NOW());

INSERT INTO config_services (id, service_type_id, is_required, ceil_price, floor_price, created_at, updated_at)
VALUES ('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', TRUE, 50, 20, NOW(),
        NOW()), -- Feeding
       ('660e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', FALSE, 100, 50, NOW(),
        NOW()), -- Grooming
       ('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440003', FALSE, 40, 15, NOW(),
        NOW()), -- Playtime
       ('660e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440004', TRUE, 80, 30, NOW(),
        NOW()), -- Health Check
       ('660e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440005', FALSE, 150, 70, NOW(),
        NOW()); -- Training

INSERT INTO services (id, config_service_id, sitter_id, service_name, service_type, price, duration, start_time, status)
VALUES ('770e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Basic Feeding', 'Feeding', 30, 30, 9, 1),
       ('770e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440002',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Standard Grooming', 'Grooming', 75, 60, 10, 1),
       ('770e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440003',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Play Session', 'Playtime', 25, 45, 8, 0),
       ('770e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440004',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Health Check-up', 'Health Check', 60, 30, 7, 1),
       ('770e8400-e29b-41d4-a716-446655440005', '660e8400-e29b-41d4-a716-446655440005',
        'd8a9f7e6-1234-4c56-89a7-23456789abcd', 'Training Basics', 'Training', 100, 90, 15, 1);
