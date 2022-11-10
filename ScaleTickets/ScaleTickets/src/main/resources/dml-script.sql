INSERT INTO public.roles(
	 authority)
	VALUES ('ADMIN');

    INSERT INTO public.roles(
	 authority)
	VALUES ('USER');


INSERT INTO public.users(
	email, password, username)
	VALUES ('test@test.com', '$2a$10$Y9myPErnxDl/qhI1hMmVLetLT7dVzc5t8tz8QOhQzCI8Ux2QZFrgm', 'admin');

INSERT INTO public.users_roles(
	user_id, role_id)
	VALUES (1, 1);