create or alter procedure spBook_GetAll
as
begin
	select 
	p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath],
	b.[ISBN10], b.[ISBN13], b.[Language], b.[PublisherId], pub.[Name] as PublisherName, b.[PublicationDate]
	from Product as p 
	join Book as b on p.[Id] = b.[ProductId]
	join Category as c on p.[CategoryId] = c.[Id]
	join Publisher as pub on b.[PublisherId] = pub.[Id];
	
	select b.[ProductId] as BookId, sc.[Id] as SubCategoryId, sc.[Name], bhsc.[Primary]
	from Book as b
	join BookHasSubCategory as bhsc on b.[ProductId] = bhsc.[BookId]
	join SubCategory as sc on bhsc.[SubCategoryId] = sc.[Id];
	
	select b.[ProductId] as BookId, a.[Id] as AuthorId, a.[Name], a.[Description], a.[ImagePath]
	from Book as b
	join AuthorOfBook as aob on b.[ProductId] = aob.[BookId]
	join Author as a on aob.[AuthorId] = a.[Id];
end
go

create or alter procedure spBook_GetById
	@BookId int
as
begin
	select 
	p.[Id], p.[Name], p.[Price], p.[Description], p.[CategoryId], c.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath],
	b.[ISBN10], b.[ISBN13], b.[Language], b.[PublisherId], pub.[Name] as PublisherName, b.[PublicationDate]
	from Product as p 
	join Book as b on p.[Id] = b.[ProductId]
	join Category as c on p.[CategoryId] = c.[Id]
	join Publisher as pub on b.[PublisherId] = pub.[Id]
	where p.[Id] = @BookId;

	select b.[ProductId] as BookId, sc.[Id] as SubCategoryId, sc.[Name], bhsc.[Primary]
	from Book as b
	join BookHasSubCategory as bhsc on b.[ProductId] = bhsc.[BookId]
	join SubCategory as sc on bhsc.[SubCategoryId] = sc.[Id]
	where b.[ProductId] = @BookId;
	
	select b.[ProductId] as BookId, a.[Id] as AuthorId, a.[Name], a.[Description], a.[ImagePath]
	from Book as b
	join AuthorOfBook as aob on b.[ProductId] = aob.[BookId]
	join Author as a on aob.[AuthorId] = a.[Id]
	where b.[ProductId] = @BookId;
end
go

create or alter procedure spBook_AddOne
	@Name nvarchar(100),
	@Price decimal(19, 4),
	@Description nvarchar(max),
	@CategoryId int,
	@QuantityInStock int,
	@ImagePath nvarchar(max),
	@ISBN10 char(10),
	@ISBN13 char(13),
	@Language nvarchar(100),
	@PublisherId int,
	@PublicationDate datetime2,
	@AuthorList varchar(max),
	@SubCategoryList varchar(max)
as
begin
	declare @Product table([ProductId] int not null);
	insert into Product([Name], [Price], [Description], [CategoryId], [QuantityInStock], [ImagePath])
	output inserted.[Id] into @Product
	values (@Name, @Price, @Description, @CategoryId, @QuantityInStock, @ImagePath);

	select [ProductId] from @Product;

	declare @ProductId int;
	select @ProductId = [ProductId] from @Product;

	insert into Book([ProductId], [ISBN10], [ISBN13], [Language], [PublisherId], [PublicationDate])
	values (@ProductId, @ISBN10, @ISBN13, @Language, @PublisherId, @PublicationDate);

	insert into AuthorOfBook([BookId], [AuthorId]) select @ProductId, value from string_split(@AuthorList, ',');
	insert into BookHasSubCategory([BookId], [SubCategoryId], [Primary]) 
	select 
	@ProductId,
	cast(substring(value, 1, charindex('-', value) - 1) as int),
	cast(substring(value, charindex('-', value), len(value)) as int)
	from string_split(@SubCategoryList, ',');
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