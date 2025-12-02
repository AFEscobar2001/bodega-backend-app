package com.bodega.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "movimientos",
    indexes = {
        @Index(name = "idx_mov_prod_bod_created", columnList = "producto_id,bodega_id,created_at"),
        @Index(name = "idx_mov_usuario_created", columnList = "usuario_id,created_at")
    }
)
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bodega_id")
    private Bodega bodega;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MovimientoTipo tipo; 

    @Column(nullable = false, precision = 18, scale = 3)
    private BigDecimal cantidad;

    @Column(length = 255)
    private String comentario;

    @OneToOne
    @JoinColumn(name = "ref_movimiento_id", unique = true)
    private Movimiento refMovimiento; 

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Bodega getBodega() { return bodega; }
    public void setBodega(Bodega bodega) { this.bodega = bodega; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public MovimientoTipo getTipo() { return tipo; }
    public void setTipo(MovimientoTipo tipo) { this.tipo = tipo; }
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Movimiento getRefMovimiento() { return refMovimiento; }
    public void setRefMovimiento(Movimiento refMovimiento) { this.refMovimiento = refMovimiento; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
