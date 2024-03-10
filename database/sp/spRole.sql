create or alter procedure spRole_GetAll
as
begin
	select [Id], [Name] from [Role];
end
go

create or alter procedure spRole_AddOne
	@Name nvarchar(100)
as
begin
	insert into [Role]([Name]) values (@Name);
end
go

create or alter procedure spRole_UpdateOne
	@Id int,
	@Name nvarchar(100)
as
begin
	update [Role] set [Name] = @Name where [Id] = @Id;
end
go

create or alter procedure spRole_DeleteOne
	@Id int
as
begin
	delete from [Role] where [Id] = @Id;
end