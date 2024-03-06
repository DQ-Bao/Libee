create or alter procedure spCart_GetActiveCartOfUser
	@UserId int
as
begin
	set nocount on;

	declare @CartId int;
	select @CartId = c.[Id] from Cart as c where c.[UserId] = @UserId and c.[SaleDate] is null;
	
	if @CartId is null
	begin
		insert into Cart([UserId], [CreatedDate])
		values (@UserId, getutcdate());
		select @CartId = c.[Id] from Cart as c where c.[UserId] = @UserId and c.[SaleDate] is null;
	end

	select c.[Id], c.[UserId], c.[CreatedDate], c.[Total] 
	from Cart as c where c.[Id] = @CartId;

	select ci.[CartId], p.[Id] as ProductId, p.[Name], p.[Price], p.[Description], p.[CategoryId], ca.[Name] as CategoryName, p.[QuantityInStock], p.[ImagePath],
	ci.[Quantity], ci.[PurchasePrice]
	from CartItem as ci
	join Product as p on ci.[ProductId] = p.[Id]
	join Category as ca on p.[CategoryId] = ca.[Id]
	where ci.[CartId] = @CartId;
end
go

create or alter procedure spCart_AddOneItem
	@CartId int,
	@ProductId int,
	@Quantity int,
	@PurchasePrice decimal(19, 4)
as
begin
	insert into CartItem([CartId], [ProductId], [Quantity], [PurchasePrice])
	values (@CartId, @ProductId, @Quantity, @PurchasePrice);
end
go

create or alter procedure spCart_DeleteOneItem
	@CartId int,
	@ProductId int
as
begin
	delete from CartItem where [CartId] = @CartId and [ProductId] = @ProductId;
end
go

create or alter procedure spCart_CalcTotal
	@CartId int
as
begin
	declare @Total decimal(19, 4);
	select @Total = sum([PurchasePrice] * [Quantity]) from CartItem where [CartId] = @CartId;
	if @Total is not null
	begin
		update Cart
		set [Total] = @Total
		where [Id] = @CartId;
	end
	else
	begin
		update Cart
		set [Total] = 0.0
		where [Id] = @CartId;
	end
end
go

create or alter procedure spCart_Checkout
	@CartId int
as
begin
	update Product
	set [QuantityInStock] = [QuantityInStock] - ci.[Quantity]
	from CartItem as ci
	join Product as p on ci.[ProductId] = p.[Id]
	where ci.[CartId] = @CartId;

	update Cart
	set [SaleDate] = getutcdate()
	where [Id] = @CartId;
end