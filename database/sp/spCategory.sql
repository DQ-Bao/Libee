create or alter procedure spCategory_GetAll
as
begin
	select [Id], [Name] from Category;
end
go

create or alter procedure spCategory_GetAllSubCategories
as
begin
	select [Id], [Name], [CategoryId] from SubCategory;
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

create or alter procedure spCategory_UpdateOne
	@Id int,
	@Name nvarchar(100)
as
begin
	update Category set [Name] = @Name where [Id] = @Id;
end
go

create or alter procedure spCategory_DeleteOne
	@Id int
as
begin
	delete from Category where [Id] = @Id;
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
go

create or alter procedure spSubCategory_UpdateOne
	@Id int,
	@Name nvarchar(100),
	@CategoryId int
as
begin
	update SubCategory set [Name] = @Name, [CategoryId] = @CategoryId where [Id] = @Id;
end
go

create or alter procedure spSubCategory_DeleteOne
	@Id int
as
begin
	delete from SubCategory where [Id] = @Id;
end