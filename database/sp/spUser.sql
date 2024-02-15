create or alter procedure spUser_GetByEmail
	@Email varchar(max)
as
begin
	declare @UserId int;
	select @UserId = [Id] from [User] where [Email] = @Email;
	
	select [Id], [FirstName], [LastName], [Email], [CreatedDate], [Password] 
	from [User] 
	where [Email] = @Email;
	
	select r.[Id], r.[Name] 
	from [UserHasRole] as uhr 
	join [Role] as r on uhr.[RoleId] = r.[Id]
	where uhr.[UserId] = @UserId;
end
go

create or alter procedure spUser_Register
	@FirstName nvarchar(50),
	@LastName nvarchar(50),
	@Email varchar(max),
	@Password char(69),
	@Success bit output
as
begin
	declare @ExistUser int;
	select @ExistUser = count(*) from [User] where [Email] = @Email;
	if @ExistUser = 0
	begin
		insert into [User]([FirstName], [LastName], [Email], [Password])
		values (@FirstName, @LastName, @Email, @Password);
		insert into [UserHasRole]([UserId], [RoleId])
		values (
			(select top 1 [Id] from [User] where [Email] = @Email),
			(select top 1 [Id] from [Role] where [Name] = N'Customer')
		);
		select @Success = 1;
	end
	else
	begin
		select @Success = 0;
	end
end