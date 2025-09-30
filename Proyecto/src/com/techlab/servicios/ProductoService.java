package techlab.servicios;

import techlab.productos.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
  private List<Producto> productos = new ArrayList<>();

  public void agregarProducto(String nombre, double precio, int stock) {
    productos.add(new Producto(nombre, precio, stock));
  }

  public List<Producto> listarProductos() {
    return productos;
  }

  public Producto buscarPorId(int id) {
    return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
  }

  public void eliminarProducto(int id) {
    productos.removeIf(p -> p.getId() == id);
  }
}
