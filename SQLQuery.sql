create database QUIZONLINE_LAB_TIEN
use QUIZONLINE_LAB_TIEN

create table [User]
(
	email nvarchar(50) primary key not null,
	name nvarchar(50) not null,
	[password] nvarchar(500) not null,
	isAdmin bit not null,
	[status] bit not null
)

create table Quiz
(
	quizID int primary key,
	email nvarchar(50) foreign key references [User](email) not null,
	dateTakeQuiz datetime not null,
	timeTakeQuiz time not null,
	point int not null,
	questionAmount int not null,
)

create table [Subject]
(
	subjectID nvarchar(10) primary key,
	subjectName nvarchar(50) not null,
	[status] bit not null
)

create table Question
(
	questionID int primary key,
	question nvarchar(500) not null,
	createDate datetime not null,
	subjectID nvarchar(10) foreign key references [Subject](subjectID) not null,
	[status] bit not null
)

create table Question_Choice
(
	choiceID int primary key,
	questionID int foreign key references Question(questionID) not null,
	isCorrect bit not null,
	choice nvarchar(500) not null,
	[status] bit not null
)

create table User_Question_Answer
(
	quizID int foreign key references Quiz(quizID) not null,
	questionID int foreign key references Question(questionID) not null,
	choice nvarchar(500) not null,
	isCorrect bit not null,
	primary key (quizID, questionID),
)

ALTER TABLE Quiz
DROP COLUMN point;

ALTER TABLE Quiz
ADD point float not null;

ALTER TABLE Subject
ALTER COLUMN timeTakeQuiz time not null;

ALTER TABLE Subject
ALTER COLUMN questionAmount int not null;