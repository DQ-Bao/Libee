create or alter procedure spBook_GetAll
as
begin
	select 
	p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath],
	b.[ISBN10], b.[ISBN13], b.[Language], b.[PublisherId], pub.[Name] as PublisherName, b.[PublicationDate]
	from Product as p 
	join Book as b on p.[Id] = b.[ProductId]
	join Category as c on p.[CategoryId] = c.[Id]
	join Publisher as pub on b.[PublisherId] = pub.[Id]
end
go

create or alter procedure spBook_GetSubCategories
	@BookId int
as
begin
	select sc.[Id], sc.[Name], bhsc.[Primary]
	from Book as b
	join BookHasSubCategory as bhsc on b.[ProductId] = bhsc.[BookId]
	join SubCategory as sc on bhsc.[SubCategoryId] = sc.[Id]
	where b.[ProductId] = @BookId;
end
go

create or alter procedure spBook_GetAuthors
	@BookId int
as
begin
	select a.[Id], a.[Name], a.[Description], a.[ImagePath]
	from Book as b
	join AuthorOfBook as aob on b.[ProductId] = aob.[BookId]
	join Author as a on aob.[AuthorId] = a.[Id]
	where b.[ProductId] = @BookId;
end