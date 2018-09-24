--<ScriptOptions statementTerminator=";"/>

CREATE TABLE hashpassword (
	userId INT NOT NULL,
	userName VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL,
	mailAddress VARCHAR(128) NOT NULL,
	saltValue VARCHAR(128),
	PRIMARY KEY (userId)
) ENGINE=InnoDB;
