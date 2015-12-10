----------------------------------------------------------------------------------
-- Table des entites
------------------

-- Table ADRESSE
create table ADRESSE (
	AID				int,							-- Identifiant de l'adresse
	ANUMERO 		varchar(15) not null,			-- Numero
	ARUE 			varchar(50) not null,			-- Rue
	ACP 			varchar(10) not null,			-- Code postal
	AVILLE 			varchar(50) not null,			-- Ville
	constraint ADRESSE_PK primary key(AID),
	constraint ADRESSE_UK unique (ANUMERO, ARUE, ACP, AVILLE)
);

-- Table CLIENT
create table CLIENT (
	CSECU			int,							-- Numero de securite social
	CNOM 			varchar(100) not null,   		-- Nom
    CTELEPHONE		varchar(20) not null,   		-- Telephone
	CADRESSE		int,							-- Adresse
	constraint CLIENT_PK primary key(CSECU),
	constraint CLIENT_FK_CADRESSE foreign key(CADRESSE) references ADRESSE(AID)
);

-- Table COMMANDECLIENT
create table COMMANDECLIENT (
	CSECU 			int,							-- Numero de securite social
	CCNUMERO 		int,							-- Numero de la commande client
	CCADRESSE 		int, 							-- Adresse de livraison de la commande
	constraint COMMANDECLIENT_PK primary key(CSECU, CCNUMERO),
	constraint COMMANDECLIENT_FK_CSECU foreign key(CSECU) references CLIENT(CSECU),
	constraint COMMANDECLIENT_FK_CCADRESSE foreign key(CCADRESSE) references ADRESSE(AID)
);

-- Table FOURNISSEUR
create table FOURNISSEUR (
	FNOM 			varchar(100),					-- Nom du fournisseur
	FTELEPHONE 		varchar(20) not null,			-- Telephone
	FADRESSE 		int,							-- Adresse
	constraint FOURNISSEUR_PK primary key(FNOM),
	constraint FOURNISSEUR_FK_FADRESSE foreign key(FADRESSE) references ADRESSE(AID)
);

-- Table COMMANDEFOURNISSEUR
create table COMMANDEFOURNISSEUR (
	CFNUMERO		int,							-- Numero de la commande fournisseur
	constraint COMMANDEFOURNISSEUR_PK primary key(CFNUMERO)
);

-- Table ENTREPOT
create table ENTREPOT (
	ENOM 			varchar(100),					-- Nom de l'entrepot
	EADRESSE 		int,							-- Adresse de l'entrepot
	constraint ENTREPOT_PK primary key(ENOM),
	constraint ENTREPOT_FK_EADRESSE foreign key(EADRESSE) references ADRESSE(AID)
);

-- Table TypeProduit
create table TYPEPRODUIT (
	TPNOM 			varchar(100), 					-- Nom du type de produit
	constraint TYPEPRODUIT_PK primary key(TPNOM)
);

-- Table PRODUIT
create table PRODUIT (
	PID 			int,							-- ID du produit
	PNOM 			varchar(100) not null,			-- Nom du produit
	PFOURNISSEUR 	varchar(100) not null,			-- Nom du fournisseur
	PPRIX 			decimal(10, 2) not null			-- Prix
	check (PPRIX >= 0.0),
	PTYPE 			varchar(100), 					-- Type du produit
	PVOLUME 		int not null					-- Volume du produit
	check (PVOLUME > 0),
	constraint PRODUIT_PK primary key(PID), 
	constraint PRODUIT_FK_PFOURNISSEUR foreign key(PFOURNISSEUR) references FOURNISSEUR(FNOM),
	constraint PRODUIT_FK_PTYPE foreign key(PTYPE) references TYPEPRODUIT(TPNOM),
	constraint PRODUIT_UK unique(PNOM, PFOURNISSEUR)
);

-- Table VETEMENT
create table VETEMENT (
	PID 			int,							-- ID du produit
	VCOULEUR 		varchar(50) not null,			-- Couleur
	VTAILLE 		varchar(6) not null 			-- Taille
	check (VTAILLE in ('XS', 'S', 'M', 'L', 'XL', 'XXL', 'Courte', 'Longue')),
	constraint VETEMENT_PK primary key(PID, VCOULEUR, VTAILLE),
	constraint VETEMENT_FK_PID foreign key(PID) references PRODUIT(PID)
);

-- Table ALIMENT
create table ALIMENT (
	PID 			int,							-- ID du produit
	ADATE 			date not null,					-- Date de peremption
	ATEMPERATURE 	decimal(10, 2) not null,		-- Temperature de conservation
	constraint ALIMENT_PK primary key(PID, ADATE),
	constraint ALIMENT_FK_PID foreign key(PID) references PRODUIT(PID)
);

-- Table LOT
create table LOT (
	LID 			int,							-- ID du lot
	LNUMERO 		int not null, 					-- Numero du lot
	PID 			int,							-- ID du produit
	QUANTITE 		int not null					-- Quantite dans le lot
	check (QUANTITE >= 0),
	constraint LOT_PK primary key(LID),
	constraint LOT_FK_PID foreign key(PID) references PRODUIT(PID),
	constraint LOT_UK unique (LNUMERO, PID)
);

----------------------------------------------------------------------------------
-- Table des associations
------------------

-- Table ADRESSELIVRAISONCLIENT
create table ADRESSELIVRAISONCLIENT (
	CSECU 			int, 							-- Numero de securite social
	AID 			int,							-- Adresse
	constraint ALC_PK primary key(CSECU, AID),
	constraint ALC_FK_CSECU foreign key(CSECU) references CLIENT(CSECU),
	constraint ALC_FK_AID foreign key(AID) references ADRESSE(AID)
);

-- Table CONTENUCOMMANDECLIENT
create table CONTENUCOMMANDECLIENT (
	CSECU 			int, 							-- Numero de securite social
	CCNUMERO 		int,							-- Numero de commande client
	PID 			int,							-- ID du produit
	QUANTITE 		int not null					-- Quantite
	check (QUANTITE > 0),
	PRIX 			decimal(10, 2) not null 		-- Prix negocie du produit
	check (PRIX >= 0.0), 
	constraint CCC_PK primary key(CSECU, CCNUMERO, PID),
	constraint CCC_FK_COMMANDECLIENT foreign key(CSECU, CCNUMERO) references COMMANDECLIENT(CSECU, CCNUMERO), 
	constraint CCC_FK_PID foreign key(PID) references PRODUIT(PID)
);

-- Table CONTENUCOMMANDEFOURNISSEUR
create table CONTENUCOMMANDEFOURNISSEUR (
	CFNUMERO 		int,							-- Numero de commande fournisseur
	PID 			int,							-- ID du produit
	QUANTITE 		int not null 					-- Quantite
	check (QUANTITE > 0),
	PRIX 			decimal(10, 2) not null			-- Prix negocie du produit
	check (PRIX >= 0.0),
	constraint CCF_PK primary key(CFNUMERO, PID), 
	constraint CCF_FK_CFNUMERO foreign key(CFNUMERO) references COMMANDEFOURNISSEUR(CFNUMERO), 
	constraint CCF_FK_PID foreign key(PID) references PRODUIT(PID)
);

-- Table ENTREPOTSTOCK
create table ENTREPOTSTOCK (
	ENOM 			varchar(100),					-- Nom de l'entrepot
	PID 			int,							-- ID du produit
	QUANTITE 		int not null 					-- Quantite
	check (QUANTITE >= 0), 
	constraint ENTREPOTSTOCK_PK primary key(ENOM, PID),
	constraint ENTREPOTSTOCK_FK_ENOM foreign key(ENOM) references ENTREPOT(ENOM),
	constraint ENTREPOTSTOCK_FK_PID foreign key(PID) references PRODUIT(PID)
);

-- Table LOTCOMMANDE
create table LOTCOMMANDE (
	CSECU 			int, 							-- Numero de securite social
	CCNUMERO 		int,							-- Numero de la commande client
	LID 			int,							-- ID du lot
	QUANTITE 		int not null 					-- Quantite
	check (QUANTITE > 0),
	constraint LOTCOMMANDE_PK primary key(CSECU, CCNUMERO, LID),
	constraint LOTCOMMANDE_FK_COMMANDECLIENT foreign key(CSECU, CCNUMERO) references COMMANDECLIENT(CSECU, CCNUMERO),
	constraint LOTCOMMANDE_FK_LOT foreign key(LID) references LOT(LID)
);

-- Table ENTREPOTSAITSTOCKER
create table ENTREPOTSAITSTOCKER (
	ENOM 			varchar(100),					-- Nom de l'entrepot
	TPNOM 			varchar(100), 					-- Nom du type de produit
	CAPACITE 		int not null 					-- Capacite totale en volume que peut stocker l'entrepot pour le type concerne
	check (CAPACITE > 0),
	constraint ENTREPOTSAITSTOCKER_PK primary key(ENOM, TPNOM),
	constraint ENTREPOTSAITSTOCKER_FK_ENOM foreign key(ENOM) references ENTREPOT(ENOM),
	constraint ENTREPOTSAITSTOCKER_FK_TPNOM foreign key(TPNOM) references TYPEPRODUIT(TPNOM)
);

-- Table ENTREPOTSTOCKLOT
create table ENTREPOTSTOCKLOT (
	ENOM 			varchar(100),					-- Nom de l'entrepot
	LID 			int,							-- ID du lot
	QUANTITE 		int not null 					-- Quantite du lot stocke
	check (QUANTITE >= 0),
	constraint ENTREPOTSTOCKLOT_PK primary key(ENOM, LID),
	constraint ENTREPOTSTOCKLOT_FK_ENOM foreign key(ENOM) references ENTREPOT(ENOM),
	constraint ENTREPOTSTOCKLOT_FK_LID foreign key(LID) references LOT(LID)
);

----------------------------------------------------------------------------------
-- Triggers & Sequences
------------------

-- Table de rejet a utiliser dans les triggers
create table REJET (
	ID 				int,
	constraint REJET_PK primary key(ID)
);
insert into REJET values(0);

-- Sequence auto-increment de l'identifiant de l'adresse
create sequence ID_ADRESSE_PK start with 1 increment by 1;

-- Trigger INCREMENTADRESSEID
create trigger INCREMENTADRESSEID
	before insert on ADRESSE
	for each row
begin
	select ID_ADRESSE_PK.nextval into :new.AID from DUAL;
end;
/

-- Sequence auto-increment de l'identifiant d'un produit
create sequence ID_PRODUIT_PK start with 1 increment by 1;

-- Trigger INCREMENTPRODUITID
create trigger INCREMENTPRODUITID
	before insert on PRODUIT
	for each row
begin
	select ID_PRODUIT_PK.nextval into :new.PID from DUAL;
end;
/

-- Sequence auto-increment de l'identifiant d'un lot
create sequence ID_LOT_PK start with 1 increment by 1;

-- Trigger INCREMENTLOTID
create trigger INCREMENTLOTID
	before insert on LOT
	for each row
begin
	select ID_LOT_PK.nextval into :new.LID from DUAL;
end;
/

----------------------------------------------------------------------
-- Peuplement de la BDD
------------------

-- Table ADRESSE --
insert into ADRESSE values(1, '8 bis', 'Avenue du General De Gaulle', '94160', 'Saint-Mande');
insert into ADRESSE values(2, '27', 'Rue Charles Floquet', '94500', 'Champigny sur Marne');
insert into ADRESSE values(3, '30', 'Avenue de la Republique', '94800', 'Villejuif');
insert into ADRESSE values(4, '15', 'Avenue de Paris', '94220', 'Charenton-le-Pont');
insert into ADRESSE values(5, '109', 'Avenue du General De Gaulle', '80000', 'Amiens');
insert into ADRESSE values(6, '33', 'Rue de la liberte', '69110', 'Lyon');
insert into ADRESSE values(7, '7 bis', 'Avenue du General De Gaulle', '13000', 'Marseille');
insert into ADRESSE values(8, '8 ter', 'Rue du Midi', '34000', 'Nîmes');
insert into ADRESSE values(9, '150', 'Avenue de la Republique', '31000', 'Toulouse');
insert into ADRESSE values(10, '10', 'Avenue de la Liberte', '44000', 'Nantes');
insert into ADRESSE values(11, '11', 'Allee des Arts', '45000', 'Orlean');
insert into ADRESSE values(12, '12', 'Rue Baudin', '59000', 'Lille');
insert into ADRESSE values(13, '13', 'Rue de la Salade', '78360', 'Montesson');
insert into ADRESSE values(14, '14', 'Rue de la Chemise', '34500', 'Uchaud');
insert into ADRESSE values(15, '15', 'Rue des pattes', '03000', 'Moulins');
insert into ADRESSE values(16, '16', 'Sous le pont', '75000', 'Paris');
insert into ADRESSE values(17, '17', 'Rue de la Victoire', '75110', 'Paris');


-- Table ENTREPOT -- 
insert into ENTREPOT values('Paris', 3);
insert into ENTREPOT values('Marseille', 7);
insert into ENTREPOT values('Lyon', 6);
insert into ENTREPOT values('Amiens', 5);
insert into ENTREPOT values('Orlean', 11);
insert into ENTREPOT values('Nantes', 10);

-- Table CLIENT -- 
insert into CLIENT values(000000000000001, 'to john-michaël', '0600000001', 1);
insert into CLIENT values(000000000000002, 'mora benjamin', '0600000002', 4);
insert into CLIENT values(000000000000003, 'Jean Christophe', '0600000003', 12);
insert into CLIENT values(000000000000004, 'Max Puissant', '0600000004', 16);


-- Table FOURNISSEUR -- 
insert into FOURNISSEUR values('Perrier', '0900000001', 8);
insert into FOURNISSEUR values('Danone', '0900000002', 9);
insert into FOURNISSEUR values('IKEA', '0900000003', 2);
insert into FOURNISSEUR values('Gianni Ferruci', '0900000004', 14);
insert into FOURNISSEUR values('Montesson', '0900000005', 13);
insert into FOURNISSEUR values('Lustucru', '0900000006', 15);

-- Table TypeProduit -- 
insert into TYPEPRODUIT values('Palette');
insert into TYPEPRODUIT values('Carton');
insert into TYPEPRODUIT values('Surgele');
insert into TYPEPRODUIT values('Fragile');
insert into TYPEPRODUIT values('Pattes');
insert into TYPEPRODUIT values('Vetement');

-- Table PRODUIT --
insert into PRODUIT values(1, 'Eau', 'Perrier', 1.5, 'Carton', 4);
insert into PRODUIT values(2, 'Yahourt', 'Danone', 3.5, 'Palette', 2);
insert into PRODUIT values(3, 'Lait', 'Danone', 7, 'Carton', 3);
insert into PRODUIT values(4, 'Chemise', 'IKEA', 35, 'Vetement', 5);
insert into PRODUIT values(5, 'Meuble', 'IKEA', 45, 'Fragile', 45);
insert into PRODUIT values(6, 'Salade', 'Montesson', 15, 'Surgele', 1);
insert into PRODUIT values(7, 'Pattes fraiches', 'Lustucru', 6, 'Palette', 4);
insert into PRODUIT values(8, 'Costume', 'Gianni Ferruci', 350, 'Vetement', 9);
insert into PRODUIT values(9, 'Cravatte', 'Gianni Ferruci', 35, 'Vetement', 1);
insert into PRODUIT values(10, 'Tomate', 'Montesson', 15, 'Surgele', 1);

-- Table VETEMENT --
insert into VETEMENT values(4, 'Blanc', 'S');
insert into VETEMENT values(4, 'Blanc', 'M');
insert into VETEMENT values(4, 'Blanc', 'L');
insert into VETEMENT values(4, 'Blanc', 'XL');
insert into VETEMENT values(4, 'Blanc', 'XXL');
insert into VETEMENT values(4, 'Rouge', 'S');
insert into VETEMENT values(4, 'Rouge', 'M');
insert into VETEMENT values(4, 'Rouge', 'L');
insert into VETEMENT values(4, 'Rouge', 'XL');
insert into VETEMENT values(4, 'Rouge', 'XXL');
insert into VETEMENT values(4, 'Noir', 'S');
insert into VETEMENT values(4, 'Noir', 'M');
insert into VETEMENT values(4, 'Noir', 'L');
insert into VETEMENT values(4, 'Noir', 'XL');
insert into VETEMENT values(4, 'Noir', 'XXL');
insert into VETEMENT values(4, 'Rose', 'S');
insert into VETEMENT values(4, 'Rose', 'M');
insert into VETEMENT values(4, 'Rose', 'L');
insert into VETEMENT values(4, 'Rose', 'XL');
insert into VETEMENT values(4, 'Rose', 'XXL');
insert into VETEMENT values(4, 'Vert', 'S');
insert into VETEMENT values(4, 'Vert', 'M');
insert into VETEMENT values(4, 'Vert', 'L');
insert into VETEMENT values(4, 'Vert', 'XL');
insert into VETEMENT values(4, 'Vert', 'XXL');
insert into VETEMENT values(8, 'Noir', 'S');
insert into VETEMENT values(8, 'Noir', 'M');
insert into VETEMENT values(8, 'Noir', 'L');
insert into VETEMENT values(8, 'Noir', 'XL');
insert into VETEMENT values(8, 'Noir', 'XXL');
insert into VETEMENT values(9, 'Noir', 'Courte');
insert into VETEMENT values(9, 'Noir', 'Longue');

-- Table ALIMENT --
insert into ALIMENT values(6, date '2014-07-02', '10');
insert into ALIMENT values(6, date '2014-07-03', '10');
insert into ALIMENT values(6, date '2014-06-05', '10');
insert into ALIMENT values(7, date '2014-06-05', '15')
insert into ALIMENT values(7, date '2014-06-06', '15')
insert into ALIMENT values(7, date '2014-06-07', '15')
insert into ALIMENT values(7, date '2014-06-08', '15')
insert into ALIMENT values(2, date '2014-06-14', '8')
insert into ALIMENT values(2, date '2014-06-15', '8')
insert into ALIMENT values(2, date '2014-06-16', '8')
insert into ALIMENT values(2, date '2014-06-17', '8')
insert into ALIMENT values(3, date '2014-06-19', '9')
insert into ALIMENT values(3, date '2014-06-20', '9')
insert into ALIMENT values(3, date '2014-06-21', '9')
insert into ALIMENT values(3, date '2014-06-22', '9')
insert into ALIMENT values(10, date '2014-06-22', '14')
insert into ALIMENT values(10, date '2014-06-23', '14')
insert into ALIMENT values(10, date '2014-06-24', '14')
insert into ALIMENT values(10, date '2014-06-25', '14')

-- Table LOT -- 
insert into LOT values(1, 1, 1, 100);
insert into LOT values(2, 2, 2, 200);
insert into LOT values(3, 3, 3, 250);
insert into LOT values(4, 4, 4, 75);
insert into LOT values(5, 5, 5, 15);
insert into LOT values(6, 6, 6, 500);
insert into LOT values(7, 7, 7, 80);
insert into LOT values(8, 8, 8, 15);
insert into LOT values(9, 9, 9, 45);
insert into LOT values(10, 10, 10, 150);

-- Table COMMANDECLIENT --
insert into COMMANDECLIENT values(000000000000003, 1, 12);
insert into COMMANDECLIENT values(000000000000003, 2, 12);

-- Table COMMANDEFOURNISSEUR -- 
insert into COMMANDEFOURNISSEUR values(1);

-- Table ADRESSELIVRAISONCLIENT -- 
insert into ADRESSELIVRAISONCLIENT values(000000000000001, 1);
insert into ADRESSELIVRAISONCLIENT values(000000000000002, 4);
insert into ADRESSELIVRAISONCLIENT values(000000000000003, 12);
insert into ADRESSELIVRAISONCLIENT values(000000000000004, 17);

-- Table CONTENUCOMMANDECLIENT -- 
insert into CONTENUCOMMANDECLIENT values(000000000000003, 1, 6, 50, 14);
insert into CONTENUCOMMANDECLIENT values(000000000000003, 1, 10, 50, 14);
insert into CONTENUCOMMANDECLIENT values(000000000000003, 2, 5, 3, 44);

-- Table CONTENUCOMMANDEFOURNISSEUR -- 
insert into CONTENUCOMMANDEFOURNISSEUR values(1, 1, 250, 2); 

-- Table ENTREPOTSTOCK -- 
insert into ENTREPOTSTOCK values('Paris', 1, 25);
insert into ENTREPOTSTOCK values('Paris', 3, 150);
insert into ENTREPOTSTOCK values('Paris', 4, 75);
insert into ENTREPOTSTOCK values('Marseille', 1, 25);
insert into ENTREPOTSTOCK values('Marseille', 2, 50);
insert into ENTREPOTSTOCK values('Marseille', 3, 100);
insert into ENTREPOTSTOCK values('Marseille', 5, 15);
insert into ENTREPOTSTOCK values('Marseille', 10, 150);
insert into ENTREPOTSTOCK values('Lyon', 1, 25);
insert into ENTREPOTSTOCK values('Lyon', 6, 500);
insert into ENTREPOTSTOCK values('Orlean', 2, 50);
insert into ENTREPOTSTOCK values('Orlean', 8, 25);
insert into ENTREPOTSTOCK values('Amiens', 1, 25);
insert into ENTREPOTSTOCK values('Amiens', 2, 50);
insert into ENTREPOTSTOCK values('Amiens', 7, 80);
insert into ENTREPOTSTOCK values('Nantes', 2, 50);
insert into ENTREPOTSTOCK values('Nantes', 9, 50);

-- Table ENTREPOTSAITSTOCKER -- 
insert into ENTREPOTSAITSTOCKER values('Paris', 'Palette', 1500);
insert into ENTREPOTSAITSTOCKER values('Paris', 'Carton', 1500);
insert into ENTREPOTSAITSTOCKER values('Paris', 'Fragile', 200);
insert into ENTREPOTSAITSTOCKER values('Paris', 'Pattes', 1500);
insert into ENTREPOTSAITSTOCKER values('Paris', 'Vetement', 500);
insert into ENTREPOTSAITSTOCKER values('Marseille', 'Palette', 1500);
insert into ENTREPOTSAITSTOCKER values('Marseille', 'Carton', 1500);
insert into ENTREPOTSAITSTOCKER values('Marseille', 'Surgele', 1500);
insert into ENTREPOTSAITSTOCKER values('Lyon', 'Palette', 250);
insert into ENTREPOTSAITSTOCKER values('Lyon', 'Carton', 500);
insert into ENTREPOTSAITSTOCKER values('Lyon', 'Surgele', 2500);
insert into ENTREPOTSAITSTOCKER values('Amiens', 'Palette', 1500);
insert into ENTREPOTSAITSTOCKER values('Amiens', 'Carton', 1500);
insert into ENTREPOTSAITSTOCKER values('Orlean', 'Palette', 1500);
insert into ENTREPOTSAITSTOCKER values('Orlean', 'Fragile', 1000);
insert into ENTREPOTSAITSTOCKER values('Orlean', 'Vetement', 1500);
insert into ENTREPOTSAITSTOCKER values('Nantes', 'Palette', 1500);
insert into ENTREPOTSAITSTOCKER values('Nantes', 'Carton', 150);
insert into ENTREPOTSAITSTOCKER values('Nantes', 'Surgele', 250);
insert into ENTREPOTSAITSTOCKER values('Nantes', 'Fragile', 300);
insert into ENTREPOTSAITSTOCKER values('Nantes', 'Vetement', 1500);

-- Table LOTCOMMANDE -- 
insert into LOTCOMMANDE values (000000000000003, 1, 6, 50);
insert into LOTCOMMANDE values (000000000000003, 1, 10, 50);
insert into LOTCOMMANDE values (000000000000003, 2, 5, 3);

-- Table ENTREPOTSTOCKLOT -- 
insert into ENTREPOTSTOCKLOT values('Paris', 1, 25);

-------------------------------------------------------------------
-- Autres triggers
------------------
-- Trigger ADDPRODCF : Produits provenant d'un même fournisseur dans une commande fournisseur
create or replace trigger ADDPRODCF
	before insert or update on CONTENUCOMMANDEFOURNISSEUR
	for each row
declare
	DIFFER number := 0;
begin
	select count(PFOURNISSEUR) into DIFFER from CONTENUCOMMANDEFOURNISSEUR natural join PRODUIT where CFNUMERO = :new.CFNUMERO and PFOURNISSEUR <> (select PFOURNISSEUR from PRODUIT where PID = :new.PID);
	if (DIFFER > 0) then
		insert into REJET values(0);
	end if;
end;
/

-- Trigger VERIF_STOCK_DISPO
create or replace trigger VERIF_STOCK_DISPO
	before insert or update on CONTENUCOMMANDECLIENT
	for each row
declare
	SOMME number := 0;
begin
	select coalesce(sum(QUANTITE), 0) into SOMME from ENTREPOTSTOCK where PID = :old.PID;
	if (SOMME < :old.QUANTITE) then
		insert into REJET values(0);
	end if;
end;
/

-- Trigger INSERT_LOT_COMMANDE
create or replace trigger INSERT_LOT_COMMANDE
	after insert on LOTCOMMANDE
	for each row
begin
	update LOT set QUANTITE = (QUANTITE - :new.QUANTITE) where LID = :new.LID;
end;
/

-- Trigger DEL_LOT_ZERO
create or replace trigger DEL_LOT_ZERO
	after update on LOT
	for each row
begin
	if(:new.QUANTITE = 0) then
		delete from LOT where LID = :new.LID;
	end if;
end;
/

-- Trigger VERIF_ENTREPOT_STOCK
--create or replace trigger VERIF_ENTREPOT_STOCK
--	before insert or update on ENTREPOTSTOCK
--	for each row
--declare
--	SOMME number := 0;
--	VOLUME_PRODUIT number := 0;
--	QUANTITE_TOTAL number := 0;
--	CAPACITE_TOTAL number := 0;
--	TYPE_STOCKE varchar(100) := '';
--begin
--	select coalesce(PTYPE, 'NO_TYPE'), coalesce(PVOLUME, 0) into TYPE_STOCKE, VOLUME_PRODUIT from PRODUIT where PID = :old.PID;
--	select coalesce(CAPACITE, -1) into CAPACITE_TOTAL from ENTREPOTSAITSTOCKER where ENOM = :old.ENOM and TPNOM = TYPE_STOCKE;
--	select coalesce(sum(QUANTITE * PVOLUME), -1) into SOMME from ENTREPOTSTOCK natural join PRODUIT where ENOM = :old.ENOM and PTYPE = TYPE_STOCKE;
	
--	QUANTITE_TOTAL := SOMME + :old.QUANTITE * VOLUME_PRODUIT;
	
--	if (TYPE_STOCKE = 'NO_TYPE' or CAPACITE_TOTAL = -1 or SOMME = -1 or (CAPACITE_TOTAL > 0 and QUANTITE_TOTAL > CAPACITE_TOTAL)) then
--		insert into REJET values(0);
--	end if;
	
--	exception
--		when NO_DATA_FOUND then
--		insert into REJET values(0);
--end;
--/