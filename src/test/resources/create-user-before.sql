delete
from user_role;
delete
from usr;

insert into usr(id, active, password, username)
values (1, true, '$2a$08$LZw5W4UwXiVYyEwSrRQubedYtO0D4aU1zVJx0/QamHEe0.GBwmHEa', 'admin'),
       (2, true, '$2a$08$SAMwToGOLLufC14oUPcVC.uO8DVF56qILAT6/ldTBWWUwzCO6jAEO', 'TestNewUser');


insert into user_role(user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN'),
       (2, 'USER');

