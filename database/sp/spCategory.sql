create or alter procedure spCategory_GetAll
as
begin
	select [Id], [Name] from Category;
end
go

create or alter procedure spCategory_GetSubCategories
	@CategoryName nvarchar(100)
as
begin
	select sc.[Id], sc.[Name], sc.[CategoryId]
	from SubCategory as sc
	join Category as c on sc.[CategoryId] = c.[Id]
	where c.[Name] = @CategoryName;
end
go

create or alter procedure spCategory_AddOne
	@Name nvarchar(100)
as
begin
	insert into [Category]([Name])
	values (@Name);
end
go

create or alter procedure spSubCategory_AddOne
	@Name nvarchar(100),
	@CategoryId int
as
begin
	insert into [SubCategory]([Name], [CategoryId])
	values (@Name, @CategoryId);
end