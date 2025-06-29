INSERT INTO public.departments(
    id, department_name)
VALUES (1, 'MERCADEO');

INSERT INTO public.departments(
    id, department_name)
VALUES (2, 'CONTABILIDAD');

INSERT INTO public.roles(
    id, name)
VALUES (1, 'ADMIN');

INSERT INTO public.roles(
    id, name)
VALUES (2, 'USER');

INSERT INTO public.employees(
    department_id, id, email, last_name, name, password, username)
VALUES (1, 1, 'alejandrap@gmail.com', 'Paredes', 'Alejandra', '$2a$12$DUAfVgg.ihK0.ZAgCzrgVeu1tLJJNmT47HBSNlYNSMd9s4n7mZC5a', 'aleParedes');

INSERT INTO public.employees(
    department_id, id, email, last_name, name, password, username)
VALUES (2, 2, 'pablog@gmail.com', 'Guzman', 'Pablo', '$2a$12$xaySOSUSoSu545NyRh81T.SdUvFeY67azy0Emhx7T0oGMSsCCyAja', 'pGuzman');

INSERT INTO public.employee_roles(
    employee_id, role_id)
VALUES (1, 2);

INSERT INTO public.employee_roles(
    employee_id, role_id)
VALUES (2, 1);