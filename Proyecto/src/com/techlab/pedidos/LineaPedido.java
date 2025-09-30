package techlab.pedidos;

import techlab.productos.Producto;


public class LineaPedido {
  private Producto producto;
  private int cantidad;

  public LineaPedido(Producto producto, int cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
  }

  public double calcularSubtotal() {
    return producto.getPrecio() * cantidad;
  }

  public Producto getProducto() { return producto; }
  public int getCantidad() { return cantidad; }
}
