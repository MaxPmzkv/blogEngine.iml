insert into posts (
is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
values
('1', 'ACCEPTED', 'Post text for data insertion test', '2021-02-01 12:02:00', 'POST TITLE', '1', '1', '1');

insert into posts (
is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
values
('1', 'ACCEPTED', '2nd Post text for data insertion test', '2021-02-01 12:04:00', '2nd POST TITLE', '2', '3', '4');

insert into posts (
is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
values
('1', 'ACCEPTED', '3d Post text for data insertion test', '2021-02-01 12:00:05', '3d POST TITLE', '2', '1', '2');

insert into post_comments (
text, time, post_id, user_id)
values('comment text', '2021-02-01 14:00:05', '1', '1');

insert into post_comments (
text, time, post_id, user_id)
values('comment text 2', '2021-02-01 14:00:05', '2', '3');

insert into tags (`name`) values('Java');
insert into tags (`name`) values('Spring');
insert into tags (`name`) values('Hibernate');

insert into tag2post (post_id, tag_id) values('1', '2');
insert into tag2post (post_id, tag_id) values('2', '1');


