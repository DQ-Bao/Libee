create or alter procedure spProduct_GetAll
as
begin
	select p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath] 
	from Product as p
	join Category as c on p.[CategoryId] = c.[Id];
end
go

create or alter procedure spProduct_GetById
	@ProductId int
as
begin
	select p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath] 
	from Product as p
	join Category as c on p.[CategoryId] = c.[Id]
	where p.[Id] = @ProductId;
end
go

create or alter procedure spProduct_SearchByName
	@SearchText nvarchar(max)
as
begin
	select p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath] 
	from Product as p
	join Category as c on p.[CategoryId] = c.[Id]
	where p.[Name] like (N'%' + @SearchText + N'%');
end
go

create or alter procedure spProduct_AddOne
	@Name nvarchar(100),
	@Price decimal(19, 4),
	@Description nvarchar(max),
	@QuantityInStock int,
	@ImagePath nvarchar(max)
as
begin
	insert into Product([Name], [Price], [Description], [QuantityInStock], [ImagePath])
	values (@Name, @Price, @Description, @QuantityInStock, @ImagePath);
end
go

create or alter procedure spProduct_UpdateOne
	@Id int,
	@Name int,
	@Price decimal(19, 4),
	@Description nvarchar(max),
	@QuantityInStock int,
	@ImagePath nvarchar(max)
as
begin
	update Product
	set [Name] = @Name, [Price] = @Price, [Description] = @Description, [QuantityInStock] = @QuantityInStock, [ImagePath] = @ImagePath
	where [Id] = @Id;
end
go

create or alter procedure spProduct_DeleteOne
	@ProductId int
as
begin
	delete from Product where [Id] = @ProductId;
end