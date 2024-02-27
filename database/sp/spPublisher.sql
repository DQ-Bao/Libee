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