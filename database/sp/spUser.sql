create or alter procedure spUser_GetAll
as
begin
	select u.*, r.[Id] as RoleId, r.[Name] as RoleName
	from [User] as u
	join [UserHasRole] as uhr on u.[Id] = uhr.[UserId]
	join [Role] as r on uhr.[RoleId] = r.[Id]
	order by u.[Id] asc;
end
go

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
go

create or alter procedure spUser_GetById
	@Id int
as
begin
	select [Id], [FirstName], [LastName], [Email], [CreatedDate], [Password] 
	from [User] 
	where [Id] = @Id;
end
go

create or alter procedure spUser_ChangePassword
	@Id int,
	@NewPassword char(69)
as
begin
	update [User] set [Password] = @NewPassword where [Id] = @Id;
end
go

create or alter procedure spUser_DeleteAccount
	@Id int
as
begin
	delete from [User] where [Id] = @Id;
end
go

create or alter procedure spUser_UpdateRoles
	@Id int,
	@RoleIds varchar(max)
as
begin
	delete from [UserHasRole] where [UserId] = @Id;
	insert into [UserHasRole]([UserId], [RoleId]) select @Id, value from string_split(@RoleIds, ',');
end