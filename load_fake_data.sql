INSERT INTO patient VALUES (1000, 'Patient 1', '123 Adress Way', STR_TO_DATE('10-12-1987', '%m-%d-%Y'), '0123456789');
INSERT INTO patient VALUES (1532, 'Patient 2', '', STR_TO_DATE('09-05-1990', '%m-%d-%Y'), '9813426513');
INSERT INTO patient VALUES (1533, 'Patient 2', 'Doesnt live at other patient 2 adress!', STR_TO_DATE('03-23-1996', '%m-%d-%Y'), '1203651028');
INSERT INTO patient VALUES (8412, 'Patient 3', '4 Main St', STR_TO_DATE('08-05-2000', '%m-%d-%Y'), '9412358410');
INSERT INTO patient VALUES (6952, 'Patient 4', '5 Edmonton Something Place', STR_TO_DATE('01-12-1950', '%m-%d-%Y'), '');
INSERT INTO patient VALUES (6521, 'Patient 5', '85 Hello Ave', STR_TO_DATE('05-23-1990', '%m-%d-%Y'), '6412354189');
INSERT INTO patient VALUES (7412, 'Patient 6', '64 What Edmonton Way', STR_TO_DATE('10-11-1990', '%m-%d-%Y'), '1203654120');
INSERT INTO patient VALUES (6321, 'Patient 7', '21 Yaaaa St', STR_TO_DATE('08-06-2005', '%m-%d-%Y'), '8742036541');

INSERT INTO doctor VALUES (8412, '654 Whyte Ave Edmonton', '0123456789', '9851236547', 8412);
INSERT INTO doctor VALUES (9621, '741 Yo Way', '0905199041', '9813426513', 6321);

INSERT INTO medical_lab VALUES ('Dyna Life DX', '74 Main St', '2467435673');
INSERT INTO medical_lab VALUES ('Medi Lab', '12 Adress Way Edmonton', '1230456842');
INSERT INTO medical_lab VALUES ('Some Lab', '741 Test Ave Edmonton', '1203547103');

INSERT INTO test_type VALUES (101, 'Blood Test', 'Patient is alive', 'Stick needle til it hurts');
INSERT INTO test_type VALUES (102, 'Stress Test', 'Patient has no past heart troubles', 'Make patient run until he-she faints and then laugh at them');
INSERT INTO test_type VALUES (103, 'CT Scan', 'Patient is awake', 'Put patient in machine press button, rinse, lather, repeat');
INSERT INTO test_type VALUES (104, 'Bone marrow check', 'Patient is in good health', 'Extract bone marrow from patient');

INSERT INTO can_conduct VALUES ('Dyna Life DX', 101);
INSERT INTO can_conduct VALUES ('Dyna Life DX', 102);
INSERT INTO can_conduct VALUES ('Medi Lab', 101);
INSERT INTO can_conduct VALUES ('Some Lab', 102);
INSERT INTO can_conduct VALUES ('Some Lab', 103);
INSERT INTO can_conduct VALUES ('Medi Lab', 104);

INSERT INTO not_allowed VALUES (8412, 102);
INSERT INTO not_allowed VALUES (6952, 101);
INSERT INTO not_allowed VALUES (6952, 102);
INSERT INTO not_allowed VALUES (6321, 101);

INSERT INTO test_record VALUES (100, 101, 1532, 8412, 'Dyna Life DX', 'Yep, hes fine', STR_TO_DATE('12-15-2010', '%m-%d-%Y'), STR_TO_DATE('12-15-2010', '%m-%d-%Y'));
INSERT INTO test_record VALUES (99, 101, 1533, 8412, 'Dyna Life DX', 'Test was normal', STR_TO_DATE('03-20-1999', '%m-%d-%Y'), STR_TO_DATE('03-21-1999', '%m-%d-%Y'));
INSERT INTO test_record VALUES (102, 102, 1532, 8412, 'Dyna Life DX', 'He totally fainted, it was hilarious', STR_TO_DATE('12-15-2010', '%m-%d-%Y'), STR_TO_DATE('12-15-2010', '%m-%d-%Y'));
INSERT INTO test_record VALUES (103, 101, 6952, 9621, 'Medi Lab', 'Eww blood', STR_TO_DATE('09-23-2009', '%m-%d-%Y'), STR_TO_DATE('12-15-2010', '%m-%d-%Y'));
INSERT INTO test_record VALUES (104, 102, 8412, 9621, 'Some Lab', 'Fellow Doc, he performed it on himself', STR_TO_DATE('08-23-2002', '%m-%d-%Y'), STR_TO_DATE('12-15-2010', '%m-%d-%Y'));
INSERT INTO test_record VALUES (105, 103, 1532, 9621, 'Some Lab', 'Scan was normal', STR_TO_DATE('02-21-2005', '%m-%d-%Y'), STR_TO_DATE('12-15-2009', '%m-%d-%Y'));
INSERT INTO test_record VALUES (106, 103, 1532, 8412, 'Some Lab', 'Scan was normal once again', STR_TO_DATE('02-21-2006', '%m-%d-%Y'), STR_TO_DATE('12-15-2006', '%m-%d-%Y'));
INSERT INTO test_record VALUES (107, 103, 7412, 9621, 'Some Lab', 'Scan was normal for this guy', STR_TO_DATE('02-21-2008', '%m-%d-%Y'), STR_TO_DATE('12-15-2009', '%m-%d-%Y'));
INSERT INTO test_record VALUES (108, 103, 7412, 8412, 'Some Lab', 'Scan was normal once again for this guy', STR_TO_DATE('02-21-2009', '%m-%d-%Y'), STR_TO_DATE('12-15-2009', '%m-%d-%Y'));
INSERT INTO test_record VALUES (109, 103, 7412, 8412, 'Some Lab', 'Good CT Scan', STR_TO_DATE('12-25-2009', '%m-%d-%Y'), STR_TO_DATE('12-24-2009','%m-%d-%Y'));
INSERT INTO test_record VALUES (110, 104, 6321, 9621, 'Medi Lab', 'normal', STR_TO_DATE('08-14-2009', '%m-%d-%Y'), STR_TO_DATE('08-24-2009','%m-%d-%Y'));