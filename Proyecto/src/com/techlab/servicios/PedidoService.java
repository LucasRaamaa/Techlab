package techlab.servicios;

import techlab.excepciones.StockInsuficienteException;
import techlab.pedidos.LineaPedido;
import techlab.pedidos.Pedido;
import techlab.productos.Producto;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
  private List<Pedido> pedidos = new ArrayList<>();

  public Pedido crearPedido(List<LineaPedido> lineas) throws StockInsuficienteException {
    Pedido pedido = new Pedido();
    for (LineaPedido lp : lineas) {
      Producto p = lp.getProducto();
      if (lp.getCantidad() > p.getStock()) {
        throw new StockInsuficienteException("Stock insuficiente para " + p.getNombre());
      }
      p.setStock(p.getStock() - lp.getCantidad());
      pedido.agregarLinea(lp);
    }
    pedidos.add(pedido);
    return pedido;
  }

  public List<Pedido> listarPedidos() {
    return pedidos;
  }
}
