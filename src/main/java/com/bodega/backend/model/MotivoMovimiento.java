package com.bodega.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "motivos_movimiento")
public class MotivoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String codigo; // COMPRA, CONSUMO, AJUSTE_POS...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MotivoTipo tipo; // IN, OUT, ADJUST

    @Column(length = 120)
    private String descripcion;

    // getters y setters
    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public MotivoTipo getTipo() { return tipo; }
    public void setTipo(MotivoTipo tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
