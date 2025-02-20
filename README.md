# Proyecto gestión de créditos
## Descripción del negocio
La empresa x realiza créditos a las personas que requieran sus servicios con una taza de interés variable y a distintos plazos. Actualmente la información relacionada a los créditos de cada cliente se almacena en un bloc de notas con el siguiente formato “Nombre del cliente—cantidad prestada—plazo—interés—fecha de realización del crédito”, debajo de cada registro se van añadiendo los pagos realizados por el cliente y el saldo restante, esto hace del sistema rudimentario y susceptible a muchos errores, en el peor de los casos, la perdida de la información total de los clientes. Teniendo en cuenta lo anterior, la empresa x desea implementar un sistema de gestión de créditos que le permita gestionar la información de cada cliente de una forma más eficiente y así evitar posibles errores relacionados a la perdida de información.
>## Levantamiento de requerimientos
### Requerimientos funcionales
1. El administrador debe poder autenticarse y también definir su contraseña a través del código 2064
2. Se debe poder crear clientes
3. Se debe poder crear créditos
4. Cada cliente tendrá un estado, algo parecido a un “data crédito” que se vera afectado si el cliente no realiza el pago de forma oportuna
5. Un cliente puede tener asociado varios créditos solo si su estado así lo permite.
6. Se debe registrar los pagos de los clientes a sus respectivos créditos
7. Llevar un historial de pagos de cada cliente
8. La información se debe almacenar en una base de datos local
9. Para garantizar la persistencia de datos, cada cierto tiempo se debe enviar la información de toda la base de datos local a una base de datos en la nube (Firebase)
10. En caso de perder la base de datos local, se puede traer la información desde la base de datos en la nube (Firebase)
11. Reto: mostrar un calendario donde se resalten los clientes que deben pagar en el mes actual
12. Reto: Cuando se aproxime la fecha de pago de un cliente se debe enviar un correo al admin
### Requerimientos no funcionales
1. Usar Spring Boot para la lógica
2. Realizar una interfaz agradable
3. la interfaz se debe realizar con Java Fx
4. Para respaldar la información de la bd local se debe usar Firebase
