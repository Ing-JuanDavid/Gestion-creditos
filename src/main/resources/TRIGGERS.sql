USE creditos_db;


SELECT * FROM clientes;
SELECT * FROM creditos;

DESCRIBE creditos;

-- Trigger para la tabla clientes
DELIMITER //
CREATE TRIGGER before_insert_clientes BEFORE INSERT ON clientes
FOR EACH ROW 
	BEGIN
		SET NEW.estado = "activo";
    END;
//
DELIMITER ;

-- Trigger para la tabla creditos
DELIMITER //
CREATE TRIGGER before_insert_credito BEFORE INSERT ON creditos 
	FOR EACH ROW 
		BEGIN
			DECLARE valorT DECIMAL;
            SET valorT = (NEW.monto * NEW.ti) + NEW.monto;
			SET NEW.fechai = current_date();
            SET NEW.valor_total = valorT;
            SET NEW.saldo = valorT;
		END;
//
DELIMITER ;

DESCRIBE pagos;
-- Trigger para la tabla pagos
DELIMITER //
CREATE TRIGGER before_insert_pagos BEFORE INSERT ON pagos
FOR EACH ROW 
	BEGIN
		DECLARE saldoA DECIMAL;
        SELECT saldo INTO saldoA FROM creditos WHERE id_credito = NEW.id_credito;
        SET saldoA = saldoA - NEW.valor;
        SET NEW.fecha = current_date();
        SET NEW.saldo = saldoA;
        UPDATE creditos SET saldo = saldoA WHERE id_credito = NEW.id_credito;
	END;
//
DELIMITER ;

-- seleccionar los creditos asociados a Juan David
SELECT * FROM creditos WHERE id_cliente = 1066527800;

-- seleccionar lo spagos asociado a =l credito 5 de Juan David
SELECT * FROM pagos WHERE id_credito = 3;


INSERT INTO clientes (id_cliente,nombre) VALUES
(1066527800,"Luisa Fernanda Salgado");

INSERT INTO creditos (monto,plazo,ti,id_cliente) VALUES
(25000000,"2027-07-06",.06,1066527800);

INSERT pagos (valor,id_cliente,id_credito) VALUES
(1500000,1066527800,3);



