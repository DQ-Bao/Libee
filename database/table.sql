create table Category(
	[Id] int primary key identity,
	[Name] nvarchar(100) not null
);
create table Product(
	[Id] int primary key identity,
	[Name] nvarchar(100) not null,
	[Price] decimal(19, 4) not null,
	[ImagePath] nvarchar(MAX),
	[Description] nvarchar(MAX),
	[CategoryId] int not null foreign key references Category([Id]),
	[QuantityInStock] int not null default 0
);
create table [Role](
	[Id] int primary key identity,
	[Name] nvarchar(100) not null
);
create table [User](
	[Id] int primary key identity,
	[FirstName] nvarchar(50) not null,
	[LastName] nvarchar(50) not null,
	[Email] nvarchar(256) not null,
	[CreatedDate] datetime2 not null default getutcdate()
);
create table UserHasRole(
	[UserId] int foreign key references [User]([Id]),
	[RoleId] int foreign key references [Role]([Id]),
	primary key([UserId], [RoleId])
);
create table Cart(
	[Id] int primary key identity,
	[UserId] int foreign key references [User]([Id]),
	[CreatedDate] datetime2 not null default getutcdate(),
	[SaleDate] datetime2,
	[Total] decimal(19, 4) not null
);
create table CartItem(
	[CartId] int foreign key references Cart([Id]),
	[ProductId] int foreign key references Product([Id]),
	[Quantity] int not null default 1,
	[PurchasePrice] decimal(19, 4) not null,
	primary key([CartId], [ProductId])
);
create table SubCategory(
	[Id] int primary key identity,
	[Name] nvarchar(100) not null,
	[CategoryId] int not null foreign key references Category([Id])
);
create table Publisher(
	[Id] int primary key identity,
	[Name] nvarchar(100) not null
);
create table Book(
	[ProductId] int primary key foreign key references Product([Id]),
	[ISBN10] char(10) not null,
	[ISBN13] char(13) not null,
	[Language] nvarchar(100) not null,
	[PublisherId] int not null foreign key references Publisher([Id]),
	[PublicationDate] datetime2 not null,
);
create table Author(
	[Id] int primary key identity,
	[Name] nvarchar(100) not null,
	[ImagePath] nvarchar(MAX),
	[Description] nvarchar(MAX)
);
create table AuthorOfBook(
	[BookId] int foreign key references Book([ProductId]),
	[AuthorId] int foreign key references [Author]([Id]),
	primary key([BookId], [AuthorId])
);
create table BookHasSubCategory(
	[BookId] int foreign key references Book([ProductId]),
	[SubCategoryId] int foreign key references SubCategory([Id]),
	[Primary] bit not null,
	primary key([BookId], [SubCategoryId])
);