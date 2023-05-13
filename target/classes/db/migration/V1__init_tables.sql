create TABLE bus (
  bus_id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_bus PRIMARY KEY (bus_id)
);

create TABLE route (
  route_id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   bus_id INT NULL,
   CONSTRAINT pk_route PRIMARY KEY (route_id)
);

create TABLE route_station (
  route_id INT NOT NULL,
   station_id INT NOT NULL
);

create TABLE station (
  station_id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_station PRIMARY KEY (station_id)
);

alter table route add CONSTRAINT FK_ROUTE_ON_BUS FOREIGN KEY (bus_id) REFERENCES bus (bus_id) ON update RESTRICT ON delete RESTRICT;

alter table route_station add CONSTRAINT fk_rousta_on_route FOREIGN KEY (route_id) REFERENCES route (route_id);

alter table route_station add CONSTRAINT fk_rousta_on_station FOREIGN KEY (station_id) REFERENCES station (station_id);