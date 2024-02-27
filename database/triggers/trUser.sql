create or alter trigger trUser_Cart
on [User] after insert
as
begin
	declare @UserId int;
	declare @UserCreateDate datetime2;
	select @UserId = [Id], @UserCreateDate = [CreatedDate] from inserted;
	insert into Cart([UserId], [CreatedDate], [Total])
	values (@UserId, @UserCreateDate, 0);
end