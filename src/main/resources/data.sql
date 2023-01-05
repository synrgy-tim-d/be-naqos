insert into t_setup_kost_types(id,type_name)
values(1,'3x4');

insert into t_setup_kost_facilities(id,facilities_details,is_active)
values(1,'kamar mandi dalam', true);

insert into t_setup_kost_specification(id,specification_details,is_active)
values(1,'free wifi',true);

insert into t_setup_cities(id,name)
values(1,'Jakarta');

insert into t_setup_subcities(id,postal_code,name,cities_id)
values(1,12345,'Jakarta',1);

insert into t_setup_prices_category(id,name)
values(1,'Expensive');