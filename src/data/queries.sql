select * from department
where department_id in(
select department_id from shop_department where shop_id = 6)