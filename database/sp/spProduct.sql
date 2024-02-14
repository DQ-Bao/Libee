create or alter procedure spProduct_GetAll
as
begin
	select p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath] 
	from Product as p
	join Category as c on p.[CategoryId] = c.[Id];
end