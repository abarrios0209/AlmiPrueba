package com.prueba.almi.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "CUOTAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "FECHA_PAGO")
    private LocalDate fechaPago;

    @Column(name = "MONTO_CAPITAL")
    private double montoCapital;

    @Column(name = "INTERES")
    private float interes;

    @Column(name = "ID_CREDITO")
    private Long idCredito;

    @Column(name = "MONTO_TOTAL")
    private double montoTotal;

    @Column(name = "ESTADO_CUOTA")
    private String estadoDeCuota;

    @Column(name = "TOTAL_FIJO")
    private double totalCuotaFija;
}
