create TABLE report (
  report_id INT AUTO_INCREMENT NOT NULL,
   bus_id INT NOT NULL,
   route_id INT NOT NULL,
   station_id INT NOT NULL,
   no_of_passengers INT NOT NULL,
   date_time datetime NOT NULL,
   CONSTRAINT pk_report PRIMARY KEY (report_id)
);

alter table report add CONSTRAINT FK_REPORT_ON_BUS FOREIGN KEY (bus_id) REFERENCES bus (bus_id);

alter table report add CONSTRAINT FK_REPORT_ON_ROUTE FOREIGN KEY (route_id) REFERENCES route (route_id);

alter table report add CONSTRAINT FK_REPORT_ON_STATION FOREIGN KEY (station_id) REFERENCES station (station_id);