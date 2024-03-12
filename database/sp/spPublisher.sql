create or alter procedure spPublisher_GetAll
as
begin
	select [Id], [Name] from Publisher;
end
go

create or alter procedure spPublisher_AddOne
	@Name nvarchar(100)
as
begin
	insert into [Publisher]([Name])
	values (@Name);
end
go

create or alter procedure spPublisher_UpdateOne
	@Id int,
	@Name nvarchar(100)
as
begin
	update Publisher set [Name] = @Name where [Id] = @Id;
end
go

create or alter procedure spPublisher_DeleteOne
	@Id int
as
begin
	delete from Publisher where [Id] = @Id;
end