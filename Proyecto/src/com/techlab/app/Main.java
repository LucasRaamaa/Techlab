package techlab.app;

import techlab.productos.Producto;
import techlab.pedidos.LineaPedido;
import techlab.servicios.ProductoService;
import techlab.servicios.PedidoService;
import techlab.excepciones.StockInsuficienteException;

import java.util.*;

public class Main {
  private static Scanner scanner = new Scanner(System.in);
  private static ProductoService productoService = new ProductoService();
  private static PedidoService pedidoService = new PedidoService();

  public static void main(String[] args) {
    int opcion;
    do {
      mostrarMenu();
      try {
        opcion = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        opcion = -1;
      }

      switch (opcion) {
        case 1 -> agregarProducto();
        case 2 -> listarProductos();
        case 3 -> buscarProducto();
        case 4 -> eliminarProducto();
        case 5 -> crearPedido();
        case 6 -> listarPedidos();
        case 7 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
      }
    } while (opcion != 7);
  }

  private static void mostrarMenu() {
    System.out.println("\n========= SISTEMA DE GESTIÓN - TECHLAB =========");
    System.out.println("1) Agregar producto");
    System.out.println("2) Listar productos");
    System.out.println("3) Buscar/Actualizar producto");
    System.out.println("4) Eliminar producto");
    System.out.println("5) Crear un pedido");
    System.out.println("6) Listar pedidos");
    System.out.println("7) Salir");
    System.out.print("Elija una opción: ");
  }

  private static void agregarProducto() {
    try {
      System.out.print("Nombre: ");
      String nombre = scanner.nextLine();
      System.out.print("Precio: ");
      double precio = Double.parseDouble(scanner.nextLine());
      System.out.print("Stock: ");
      int stock = Integer.parseInt(scanner.nextLine());

      productoService.agregarProducto(nombre, precio, stock);
      System.out.println("Producto agregado con éxito.");
    } catch (NumberFormatException e) {
      System.out.println("Error: formato inválido.");
    }
  }

  private static void listarProductos() {
    var productos = productoService.listarProductos();
    if (productos.isEmpty()) {
      System.out.println("No hay productos cargados.");
    } else {
      productos.forEach(System.out::println);
    }
  }

  private static void buscarProducto() {
    System.out.print("Ingrese ID del producto: ");
    try {
      int id = Integer.parseInt(scanner.nextLine());
      Producto p = productoService.buscarPorId(id);
      if (p != null) {
        System.out.println("Encontrado: " + p);
        System.out.print("Actualizar precio? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
          System.out.print("Nuevo precio: ");
          p.setPrecio(Double.parseDouble(scanner.nextLine()));
        }
        System.out.print("Actualizar stock? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
          System.out.print("Nuevo stock: ");
          p.setStock(Integer.parseInt(scanner.nextLine()));
        }
      } else {
        System.out.println("No se encontró el producto.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Error: debe ingresar un número.");
    }
  }

  private static void eliminarProducto() {
    System.out.print("Ingrese ID del producto a eliminar: ");
    try {
      int id = Integer.parseInt(scanner.nextLine());
      productoService.eliminarProducto(id);
      System.out.println("Producto eliminado.");
    } catch (NumberFormatException e) {
      System.out.println("Error: debe ingresar un número.");
    }
  }

  private static void crearPedido() {
    List<LineaPedido> lineas = new ArrayList<>();
    while (true) {
      listarProductos();
      System.out.print("Ingrese ID de producto (0 para terminar): ");
      int id = Integer.parseInt(scanner.nextLine());
      if (id == 0) break;

      Producto p = productoService.buscarPorId(id);
      if (p == null) {
        System.out.println("Producto no encontrado.");
        continue;
      }

      System.out.print("Cantidad: ");
      int cantidad = Integer.parseInt(scanner.nextLine());
      lineas.add(new LineaPedido(p, cantidad));
    }

    try {
      var pedido = pedidoService.crearPedido(lineas);
      System.out.println("Pedido creado:\n" + pedido);
    } catch (StockInsuficienteException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void listarPedidos() {
    var pedidos = pedidoService.listarPedidos();
    if (pedidos.isEmpty()) {
      System.out.println("No hay pedidos registrados.");
    } else {
      pedidos.forEach(System.out::println);
    }
  }
}
