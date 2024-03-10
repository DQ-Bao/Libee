create or alter procedure spAuthor_GetAll
as
begin
	select [Id], [Name], [Description], [ImagePath] from [Author];
end
go

create or alter procedure spAuthor_GetById
	@Id int
as
begin
	select [Id], [Name], [Description], [ImagePath] from [Author] where [Id] = @Id;
end
go

create or alter procedure spAuthor_GetBooks
	@AuthorId int
as
begin
	select p.*, c.[Name] as CategoryName, b.*
	from Product as p
	join Book as b on p.[Id] = b.[ProductId]
	join Category as c on p.[CategoryId] = c.[Id]
	join AuthorOfBook as aob on b.[ProductId] = aob.[BookId]
	join Author as a on aob.[AuthorId] = a.[Id]
	where a.[Id] = @AuthorId;
end
go

create or alter procedure spAuthor_AddOne
	@Name nvarchar(100),
	@Description nvarchar(max),
	@ImagePath nvarchar(max)
as
begin
	insert into [Author]([Name], [Description], [ImagePath])
	values (@Name, @Description, @ImagePath);
end