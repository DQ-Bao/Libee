create table Publisher(
	[Id] int primary key identity,
	[Name] nvarchar(50) not null
);
create table Book(
	[Id] int primary key identity,
	[ISBN10] char(10) not null,
	[ISBN13] char(13) not null,
	[Title] nvarchar(50) not null,
	[Language] nvarchar(50) not null,
	[CoverPath] nvarchar(50),
	[BuyLink] nvarchar(50),
	[Description] nvarchar(200),
	[PublisherId] int not null foreign key references [Publisher]([Id]),
	[PublicationDate] datetime not null
);
create table Author(
	[Id] int primary key identity,
	[Name] nvarchar(50) not null,
	[ImagePath] nvarchar(50),
	[Description] nvarchar(200)
);
create table AuthorOfBook(
	[Id] int primary key identity,
	[BookId] int not null foreign key references [Book]([Id]),
	[AuthorId] int not null foreign key references [Author]([Id])
);
create table Category(
	[Id] int primary key identity,
	[Name] nvarchar(50) not null
);
create table BookHasCategory(
	[Id] int primary key identity,
	[BookId] int not null foreign key references [Book]([Id]),
	[CategoryId] int not null foreign key references [Category]([Id]),
	[Primary] bit not null
);