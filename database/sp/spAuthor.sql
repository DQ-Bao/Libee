create or alter procedure spAuthor_GetAll
as
begin
	select [Id], [Name], [Description], [ImagePath] from [Author];
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